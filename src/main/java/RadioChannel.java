import netscape.javascript.JSObject;
import org.json.JSONObject;

public class RadioChannel {
    private String channelName;
    private String channelID;
    private String channelType;
    private String liveAudioURL;

    public void setChannelID(String channelID) {
        this.channelID = channelID;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public void setLiveAudioURL(String liveAudioURL) {
        this.liveAudioURL = liveAudioURL;
    }

    public void setChannelType(String channelType) {
        this.channelType = channelType;
    }

    public String getChannelID() {
        return channelID;
    }

    public String getChannelName() {
        return channelName;
    }

    public String getChannelType() {
        return channelType;
    }

    public String getLiveAudioURL() {
        return liveAudioURL;
    }
}
