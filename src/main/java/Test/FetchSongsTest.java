package Test;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONObject;

/**
 * se https://sverigesradio.se/api/documentation/v2/metoder/musik.html för dokumentation
 */
public class FetchSongsTest {
    public static void main(String[] args) {
        //

        HttpResponse<JsonNode> response;

        try {
            response = Unirest.get("http://api.sr.se/api/v2/playlists/rightnow?channelid=164")
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
            System.out.println(envelope.toString(1));
            System.out.println("Previous song");
            System.out.println("Title: " + previousSong.get("title")); //skriv ut titeln på föregående sång
            System.out.println("Artist: " + previousSong.get("artist")); //skriv ut artisten på föregående sång
            System.out.println("Album: " + previousSong.get("albumname")); //skriv ut albumnamn på föregående sång

        } catch (UnirestException e) {
            e.printStackTrace();
        }
    }
}
