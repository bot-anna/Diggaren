import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import org.json.JSONObject;

public class RadioHandler {

    public List<String> getPlaylist(String id) {
        HttpResponse<JsonNode> response;

        try {
            response = Unirest.get("http://api.sr.se/api/v2/playlists/rightnow?channelid=" + id)
                    .queryString("format", "json")
                    .asJson();
            /**
             * hämtar ett playlist-JSONobjekt.
             * Kan (men måste inte) innehålla följande sub-objekt:
             * - previoussong - föregående låt som spelas just nu i den valda kanalen
             * - song - den låt som spelas just nu i den valda kanalen
             * - nextsong - nästkommande låt som ska spelas i kanalen
             * - channel - beskriver kanalen med två attribut, id och name
             *
             * vilka subobjekt som kommer med beror på om ex. en låt spelas just nu, om en nästa finns planerad,
             * osv
             * tror att previoussong och channel alltid kommer med
             */
            JsonNode jsonNode = response.getBody(); //hämta jsonobjekt från metoden
            JSONObject envelope = jsonNode.getObject(); //hämta kuvert
            JSONObject playlist = envelope.getJSONObject("playlist"); //hämta playlistobjektet från kuvertet
            JSONObject previousSong = playlist.getJSONObject("previoussong"); //hämta objektet "previous song" från playlist-objektet
            //  JSONObject currentSong = playlist.getJSONObject("song"); //funkar bara ibland, kommer behöva nullcheck eller dylikt
            System.out.println(playlist.toString(1)); //gör så att playlistobjektet printas indenterat
        } catch (UnirestException e) {
            e.printStackTrace();
        }

        return null;
        } //delete?
    private static final String SONG_FETCHING_URL = "http://api.sr.se/api/v2/playlists/rightnow?channelid="; //kommer behöva kompletteras med radiokanal på slutet
    private static final int LENGTH_OF_SONG_ARRAY = 2; //längden på antal låtar som returneras från radion i song-arrayen
    public static final int PREVIOUS_SONG_INDEX = 0; //indexet för "previous song"
    public static final int CURRENT_SONG_INDEX = 1; //indexet för "current song"

    /**
     * @param channelID id på radiokanalen man vill hämta sång ifrån
     * @return en array med två sånger: föregående och nuvarande (om en nuvarande finns)
     *
     * Denna metoden gör en get-request till SR-kanalen vars ID den tar in som parameter.
     * Den extraherar playlistobjektet från det retunerade JSONobjektet och från playlistobjektet
     * hämtas föregående sång och om en nuvarande sång finns hämtas även den, och de läggs sedan
     * in på förbestämda statiska publika index i en Song-array (detta för att undvika hårdkodning
     * och för att enkelt kunna komma åt önskad låt på rätt index från andra klasser)
     */
    public static Song[] getSongsFromRadio(String channelID) {
        HttpResponse<JsonNode> response;
        Song[] songs = new Song[LENGTH_OF_SONG_ARRAY]; //skapa ny array med Song-objekt, använder statisk variabel för att det ska vara lättare om det behövs ändras senare

        try {
            response = Unirest.get(SONG_FETCHING_URL + channelID)
                    .queryString("format", "json")
                    .asJson();

            JsonNode jsonNode = response.getBody(); //hämta jsonobjekt från metoden
            JSONObject envelope = jsonNode.getObject(); //hämta kuvert
            JSONObject playlist = envelope.getJSONObject("playlist"); //hämta playlistobjektet från kuvertet
            JSONObject previousSong = playlist.getJSONObject("previoussong"); //hämta objektet "previous song" från playlist-objektet
            songs[PREVIOUS_SONG_INDEX] = new Song(previousSong.getString("title"), previousSong.getString("artist")); //skapa nytt song-objekt
            if (playlist.has("song")) { //om det är en låt som spelas just nu TODO se över om det här funkar, verkar som song inte kommer med
                JSONObject currentSong = playlist.getJSONObject("song");
                songs[CURRENT_SONG_INDEX] = new Song(currentSong.getString("title"), currentSong.getString("artist"));
            }
        } catch (Exception e) {
            e.printStackTrace(); //todo felhantering
        }
        return songs;
    }

    /**
     * todo
     * hämtar alla kanaler och skriver ut deras namn, id, kanaltyp och url
     */
    public ArrayList<RadioChannel> getChannels(){
        HttpResponse<JsonNode> response;
        ArrayList<RadioChannel> RadioChannels = new ArrayList<RadioChannel>();

        try {
            response = Unirest.get("http://api.sr.se/api/v2/channels")
                    .queryString("format", "json")
                    .asJson();

            /**
             * hämtar alla kanaler och skriver ut deras namn, id, kanaltyp och url
             */
            JsonNode jsonNode = response.getBody(); //hämtar json-objektet från http-responsen
            JSONObject envelope = jsonNode.getObject(); // gör ett kuvert??? haha
            JSONArray results = envelope.getJSONArray("channels"); //gör en array av alla kanal-objekt i jsonobjektet

            /**
             * loopar igenom och skriver ut varje information om varje kanal
             */



            //skapar en ArrayList över alla kanaler
            for(int i = 0; i < results.length(); i++) {
                JSONObject currentChannel = results.getJSONObject(i);
                JSONObject liveAudio = currentChannel.getJSONObject("liveaudio"); //hämtar ljudet
                RadioChannel channel = new RadioChannel();
                channel.setChannelName(currentChannel.getString("name")); //hämtar och sätter namn för current channel
                channel.setChannelID(String.valueOf(currentChannel.getInt("id"))); //hämtar och sätter id för current channel
                channel.setChannelType(currentChannel.getString("channeltype")); //hämtar och sätter typ av kanal för current chanel
                channel.setLiveAudioURL(liveAudio.get("url").toString()); //hämtar url
                RadioChannels.add(channel); //sätter radiokanalen i ArrayList för kanaler
            }
        } catch (UnirestException e) {
            throw new RuntimeException(e);
        }

        return RadioChannels;

    }
}
