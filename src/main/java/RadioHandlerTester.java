/**
 * testar RadioHandlers getSongsFromRadio och skriver ut de låtar den fick tillbaka
 */
public class RadioHandlerTester {
    public static void main(String[] args) {
        Song[] songs = RadioHandler.getSongsFromRadio("164");
        System.out.println("Previous song: " + songs[RadioHandler.PREVIOUS_SONG_INDEX].toString());
        if (songs[RadioHandler.CURRENT_SONG_INDEX] != null) {
            System.out.println("Current song: " + songs[RadioHandler.CURRENT_SONG_INDEX].toString());
        }
        else {
            System.out.println("No song playing right now.");
        }
    }
}
