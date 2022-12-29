
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.javalin.Javalin;

public class Runner {
    private static Controller controller;
    public static void main(String[] args) {
        setUp();
    }

    /**
     * called when we run main, starts "the runner" (starts the server) and creates
     * the controller
     */
    private static void setUp() {
        startRunner();
        controller = new Controller();

    }

    /**
     * Start server/setting up framework
     */
    private static void startRunner() {
        Runner Runner = new Runner();
        Javalin app = Javalin.create(config -> {
            config.plugins.enableCors(cors -> {
                cors.add(it ->
                        it.anyHost());
            });
                })
                .get("/allchannels", ctx -> { controller.getRadioChannels(ctx); })
                .get("/radio/{id}", ctx -> {controller.getSongs(ctx);
                })
                .start(5008); //run server on port 5008
    }

}
