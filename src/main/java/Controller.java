import javax.naming.Context;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import io.javalin.http.*;
import io.javalin.rendering.template.JavalinPebble;
import io.javalin.Javalin;
import com.google.gson.*;
public class Controller {
    RadioHandler radioHandler;
    SpotifyHandler spotifyHandler;
    private Gson gson = null;
    public Controller(){
        setUp();
    }

    /**
     * setting up RadioHandler and SpotifyHandler
     */
    private void setUp() {
        radioHandler = new RadioHandler();
        spotifyHandler = new SpotifyHandler();
        gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    }

    /**
     * @param ctx @todo beskrivning
     * hämtar sånger från radio, skickar in dessa i SpotifHandler som returnerar ett url sångerna. Allt detta paketeras
     * om i en HashMap och skickas till send()
     */
    protected void getSongs(io.javalin.http.Context ctx){ //todo fyll i om en låt är tom
        Song[] song = radioHandler.getSongsFromRadio(ctx.pathParam("id"));
        SpotifyHandler.authorize();
        song = SpotifyHandler.getSpotifyID(song);
        ArrayList<Map> maps = new ArrayList<>();

        for (int i=0; i < song.length; i++){
            String previousOrCurrent;
            if(i == RadioHandler.PREVIOUS_SONG_INDEX){
                previousOrCurrent = "previous";
            }
            else {
                previousOrCurrent = "current";
            }

            if(song[i] != null){
                HashMap map = new HashMap();
                map.put("previousorcurrent", previousOrCurrent); //previous or current song
                map.put("title", song[i].getSongTitle());
                map.put("artist", song[i].getArtist());
                map.put("spotifyurl", song[i].getSpotifyURL());
                maps.add(map);
            }
        }

        send(ctx, maps);
    }

    protected void getFullSongs(io.javalin.http.Context ctx){ //todo fyll i om en låt är tom
        Song[] song = radioHandler.getSongsFromRadio(ctx.pathParam("id"));
        SpotifyHandler.authorize();
        song = SpotifyHandler.getSpotifyURLS(song);
        ArrayList<Map> maps = new ArrayList<>();

        for (int i=0; i < song.length; i++){
            String previousOrCurrent;
            if(i == RadioHandler.PREVIOUS_SONG_INDEX){
                previousOrCurrent = "previous";
            }
            else {
                previousOrCurrent = "current";
            }

            if(song[i] != null){
                HashMap map = new HashMap();
                map.put("previousorcurrent", previousOrCurrent); //previous or current song
                map.put("title", song[i].getSongTitle());
                map.put("artist", song[i].getArtist());
                map.put("spotifyurl", song[i].getSpotifyURL());
                maps.add(map);
            }
        }

        send(ctx, maps);
    }

    /**
     * kör en metod i RadioHandler som hämtar alla kanaler och skickar ett returmeddelande
     */
    protected void getRadioChannels(io.javalin.http.Context ctx){
        ArrayList<RadioChannel> channels = radioHandler.getChannels();
        ArrayList<Map> maps = new ArrayList<>();
        for (int i = 0; i < channels.size(); i++ ){
            HashMap map = new HashMap();
            map.put("name", channels.get(i).getChannelName());
            map.put("id", channels.get(i).getChannelID());
            map.put("channeltype", channels.get(i).getChannelType());
            maps.add(map); //
        }

        send(ctx, maps); //skickar returmeddelande
    }

    /**
     * @param ctx @todo beskrivning
     * sends stuff out in the world
     */
    private void send(io.javalin.http.Context ctx, ArrayList list){
        ctx.json(gson.toJson(list));
    }

    public void loadHomePage(io.javalin.http.Context ctx) {

    }
}
