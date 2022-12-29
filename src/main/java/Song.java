/**
 * Sång-klass för inkapsling av songinformation för lättare hantering av sång-data när den skickas mellan
 * radio och spotifyklasserna
 */
public class Song {
    private String songTitle;
    private String artist;

    private String spotifyURL;

    public Song(String songTitle, String artist) {
        this.songTitle = songTitle;
        this.artist = artist;
    }

    public String getArtist() {
        return artist;
    }

    public String getSongTitle() {
        return songTitle;
    }

    public void setSpotifyURL(String spotifyURL) {
        this.spotifyURL = spotifyURL;
    }

    public String getSpotifyURL() {
        return spotifyURL;
    }

    public String toString() {
        if (spotifyURL == null){
            return String.format("Song title: %s Artist: %s", songTitle, artist);
        }
        else {
            return String.format("Song title: %s Artist: %s Spotify URL: %s", songTitle, artist, spotifyURL);
        }
    }
}
