package Test;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * se https://sverigesradio.se/api/documentation/v2/metoder/kanaler.html för dokumentation
 */
public class FetchChannelsTest {
    public static void main(String[] args) {
        HttpResponse<JsonNode> response;

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
            for(int i = 0; i < results.length(); i++) {
                System.out.println(i + 1);
                JSONObject currChannel = results.getJSONObject(i); //tar ut nuvarande kanal i loopen och lägger i tempobjekt
                JSONObject liveaudio = currChannel.getJSONObject("liveaudio"); //för att kunna skriva ut url, liveaudio är objekt i kanalen
                System.out.println("Channel name: " + currChannel.get("name"));
                System.out.println("Channel id: " + currChannel.get("id"));
                System.out.println("Channel type: " + currChannel.get("channeltype"));
                System.out.println("Channel url " + liveaudio.get("url"));
            }
        } catch (UnirestException e) {
            throw new RuntimeException(e);
        }

    }
}
