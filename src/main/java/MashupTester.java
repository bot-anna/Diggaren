import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Base64;

/**
 * hämtar låtar från radion och använder deras info för att hämta spotify-länk
 */
public class MashupTester {
    public static void main(String[] args) {
        Song[] songs = RadioHandler.getSongsFromRadio("164");

        String url = "https://accounts.spotify.com/api/token"; //url för att hämta token
        HttpResponse<JsonNode> response;
        String clientStuff = ClientInfo.getFullClientString(); // HÄR behövs den "superprivata" klassen ClientInfo
        String encodedString = Base64.getEncoder().encodeToString(clientStuff.getBytes()); //encoda klientsträngen till base64 som spotify begär

        try {
            response = Unirest.post(url)
                    .header("Authorization", "Basic " + encodedString)
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .field("grant_type", "client_credentials")
                    .asJson();

            JsonNode jsonNode = response.getBody();
            JSONObject envelope = jsonNode.getObject();
            String access_token = envelope.getString("access_token"); //hämta accesstoken ur returobjektet
            System.out.println(access_token);
            SpotifyHandler.setTOKEN(access_token); //setta token i SpotifyHandler


            for (int i = 0; i < songs.length; i++) {
                if (songs[i] != null) {
                    System.out.println(songs[i].toString());
                    String URL = SpotifyHandler.makeURL(songs[i].getSongTitle(), songs[i].getArtist());
                    response = Unirest.get(URL) //detta är boten anna-urlen
                            .queryString("format", "json") //ange json som returformat
                            .header("Authorization: ", "Bearer " + SpotifyHandler.getTOKEN()) //skickar med token (först måste alltså token hämtas från spotify innan denna metod körs
                            .asJson();

                    JsonNode jsonNode1 = response.getBody();
                    JSONObject envelope2 = jsonNode1.getObject(); //hämta envelope ur svarsnoden
                    JSONObject tracks = envelope2.getJSONObject("tracks"); //plocka ur tracks ur envelope
                    JSONArray items = tracks.getJSONArray("items"); //plocka ur "items" ur tracks (som är en array fastän vi bara ber om 1 låt tillbaka)
                    JSONObject item = items.getJSONObject(0); //hämta det enda objektet ur arrayen
                    JSONObject external_urls = item.getJSONObject("external_urls"); //hämta external_urls-objektet ur item
                    String spotifyURL = external_urls.getString("spotify"); //hämta urlen som tillhör "spotify"
                    System.out.println(spotifyURL); //skriv ut url
                    /**
                     * vi kan också hämta länk till bild på albumcover ur "item", det hade ju kunnat vara roligt att skicka med och visa upp
                     */}
            }


        } catch (UnirestException e) {
            e.printStackTrace();
        }
    }
}
