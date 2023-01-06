
import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;

/**
 * The class that starts and runs the server, creates endpoits and a controller.
 */
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
            config.staticFiles.add("static", Location.CLASSPATH);
            config.plugins.enableCors(cors -> {
                cors.add(it ->
                        it.anyHost());
            });
                })
                .get("/radio/allchannels", ctx -> { controller.getRadioChannels(ctx); })
                .get("/radio/{id}", ctx -> {controller.getSongs(ctx);}) //returnerar spotify-IDt för låtarna
                .get("/api/{id}", ctx -> { controller.getFullSongs(ctx);}) //returnerar hela spotifyURLen
                .get("/", ctx -> { ctx.redirect("index.html");}) //redirectar till hemsidan
                .start(5008); //run server on port 5008
    }

}
