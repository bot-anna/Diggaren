package Test;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONObject;

public class SpotifySearchTest {
    public static void main(String[] args) {
        searchSpotifyTrack();
    }

    private static void searchSpotifyTrack() {
        try {
            HttpResponse<JsonNode> jsonResponse
                    = Unirest.get("Endpoint \thttps://api.spotify.com/v1/search")
                    .header("accept", "application/json").queryString("apiKey", "123")
                    .asJson();

        } catch (UnirestException e) {
            e.printStackTrace();
        }

    }

    private void getAccessToken(){

    }

}
