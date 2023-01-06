public class MashupTesterV2 {
    public static void main(String[] args) {
        Song[] songs = RadioHandler.getSongsFromRadio("164");
        SpotifyHandler.authorize();
        songs = SpotifyHandler.getSpotifyID(songs);
        RadioHandler handler = new RadioHandler();


        for(int i = 0; i < songs.length; i++) {
            System.out.println(songs[i].toString());
        }
    }
}
