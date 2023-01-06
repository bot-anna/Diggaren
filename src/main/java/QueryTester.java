import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Base64;

/**
 * denna klassen måste ligga utanför testmappen för annars funkar inte spotifyhandler??? wierd men aa
 */

public class QueryTester {
    public static void main(String[] args) {
        System.out.println(SpotifyHandler.makeURL("boten anna", "basehunter")); //test för att se vad man får

        String URL = SpotifyHandler.makeURL("boten anna", "basehunter"); //denna används längre ner för att söka efter en låt
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


            response = Unirest.get(URL) //detta är boten anna-urlen
                    .queryString("format", "json") //ange json som returformat
                    .header("Authorization: ", "Bearer "+ SpotifyHandler.getTOKEN()) //skickar med token (först måste alltså token hämtas från spotify innan denna metod körs
                    .asJson();

            JsonNode jsonNode1 = response.getBody();
            JSONObject envelope2 = jsonNode1.getObject(); //hämta envelope ur svarsnoden
            JSONObject tracks = envelope2.getJSONObject("tracks"); //plocka ur tracks ur envelope
            JSONArray items = tracks.getJSONArray("items"); //plocka ur "items" ur tracks (som är en array fastän vi bara ber om 1 låt tillbaka)
            JSONObject item = items.getJSONObject(0); //hämta det enda objektet ur arrayen
            JSONObject external_urls = item.getJSONObject("external_urls"); //hämta external_urls-objektet ur item
            String spotifyURL = external_urls.getString("spotify"); //hämta urlen som tillhör "spotify"
            System.out.println(spotifyURL); //skriv ut url
            System.out.println("Envelope: " + envelope2.toString(1));
            /**
             * vi kan också hämta länk till bild på albumcover ur "item", det hade ju kunnat vara roligt att skicka med och visa upp
             */





        } catch (UnirestException e) {
            e.printStackTrace();
        }
    }
}
