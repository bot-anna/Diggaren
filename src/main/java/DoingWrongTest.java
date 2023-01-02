import java.util.ArrayList;
import java.util.Map;

public class DoingWrongTest {
    public static void main(String[] args) {
        Song[] song = new Song[2];
        song[RadioHandler.PREVIOUS_SONG_INDEX] = new Song("kgdsalögkak", "gksldgklas");
        song[RadioHandler.CURRENT_SONG_INDEX] = new Song("kglasdss", "sdkgla");
                SpotifyHandler.authorize();
        song = SpotifyHandler.getSpotifyURLS(song);
        ArrayList<Map> maps = new ArrayList<>();

        for (int i = 0; i < song.length; i++) {
            String previousOrCurrent;
            if (i == RadioHandler.PREVIOUS_SONG_INDEX) {
                previousOrCurrent = "previous";
            } else {
                previousOrCurrent = "current";
            }
        }
        for(int i = 0; i < song.length; i++) {
            System.out.println(song[i].toString());
        }
    }
}
