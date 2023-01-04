package Test;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.net.http.HttpClient;

public class DiggarenAllChannelsTest {
    public static void main(String[] args) {
        HttpResponse<JsonNode> response;
        try {
            response = Unirest.get("http://localhost:5008/radio/132")
                    .asJson();
            JsonNode jsonNode = response.getBody();
            JSONArray array = jsonNode.getArray();
            System.out.println(array.toString(1));
            for(int i = 0; i < array.length(); i++) {
                System.out.println(i + " " + array.get(i).toString());
            }
            FileWriter writer = new FileWriter("files/songs.txt");
            writer.write(array.toString(1));
            writer.close();
        } catch (UnirestException | IOException e) {
            e.printStackTrace();
        }

    }
}
