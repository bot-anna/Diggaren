import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.apache.http.client.utils.URIUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class SpotifyHandler {
    private static final String URIstart = "https://api.spotify.com/v1/search?q="; //TODO ändra till SEARCH_URL_START eller nått
    private static final String URIend = "&type=track&limit=1"; //just nu returnar den bara 1 låt, men vi kan ändra sen om vi tycker att det behövs
    private static String TOKEN;


    /**
     * @param songTitle name of the song
     * @param artist artist
     * @return the URI to send to the spotify API
     *
     * method for making an URI to send to the spotify API
     */
    public static String makeURL(String songTitle, String artist) {
        return URIstart + encodeSongInfo(songTitle, artist) + URIend;
    }

    /**
     * @param songTitle title of song
     * @param artist artist of the song
     * @return the encoded query
     *
     * method for encoding a query
     */
    public static String encodeSongInfo(String songTitle, String artist) {

        String query = (songTitle + " " + artist);
        query = URLEncoder.encode(query, StandardCharsets.UTF_8)
                .replaceAll("\\+", "%20")
                .replaceAll("\\%21", "!")
                .replaceAll("\\%27", "'")
                .replaceAll("\\%28", "(")
                .replaceAll("\\%29", ")")
                .replaceAll("\\%7E", "~");
        return query;
    }

    public static void setTOKEN(String TOKEN) {
        SpotifyHandler.TOKEN = TOKEN;
    }

    public static String getTOKEN() {
        return TOKEN;
    }

    public static void authorize() {
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

        } catch (UnirestException e) {
            throw new RuntimeException(e);
        }


    }

    public static Song[] getSpotifyURLS(Song[] songs) {
        HttpResponse<JsonNode> response;
        String spotifyURL;
        try {
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
                        if(items.length() != 0) {
                        JSONObject item = items.getJSONObject(0); //hämta det enda objektet ur arrayen
                        JSONObject external_urls = item.getJSONObject("external_urls"); //hämta external_urls-objektet ur item
                        spotifyURL = external_urls.getString("spotify"); //hämta urlen som tillhör "spotify"
                        System.out.println(spotifyURL); //skriv ut url
                    /**
                     * vi kan också hämta länk till bild på albumcover ur "item", det hade ju kunnat vara roligt att skicka med och visa upp
                     */
                    } else {
                        spotifyURL = "Not found on spotify";
                    }
                    songs[i].setSpotifyURL(spotifyURL);
                }
            }
        } catch (UnirestException e) {
            e.printStackTrace();
        } catch (JSONException e) {

        }
        return songs;
        }
    }


