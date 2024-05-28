import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Main class to run the URL monitoring application.
 */
public class Main {
    private static final int SCHEDULE_INTERVAL_SECONDS = 30;
    private static final String LOG_FILE_NAME = "log.txt";
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");

    private final Logger logger;
    private final PingService pingService;
    private final Scanner scanner;
    private final ScheduledExecutorService scheduler;

    /**
     * Constructs a new Main instance with dependencies injected.
     *
     * @param logger      The logger to log status messages.
     * @param pingService The service to ping URLs.
     * @param scanner     The scanner to read user input.
     */
    public Main(Logger logger, PingService pingService, Scanner scanner, ScheduledExecutorService scheduler) {
        this.logger = logger;
        this.pingService = pingService;
        this.scanner = scanner;
        this.scheduler = scheduler;
    }

    public static void main(String[] args) throws IOException {
        Logger logger = Logger.getInstance(LOG_FILE_NAME);
        PingService pingService = new PingService();
        Scanner scanner = new Scanner(System.in);
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        Main mainApp = new Main(logger, pingService, scanner, scheduler);
        mainApp.run();
    }

    /**
     * Runs the main logic of the application.
     */
    public void run() {
        String urlString = promptForValidURL();
        scheduleLoggingTask();

        System.out.printf("%n%s wird überwacht, drücken Sie eine beliebige Taste um das Programm zu beenden...%n", urlString);
        scanner.nextLine();
        shutdown();
    }

    /**
     * Shuts down the application resources.
     */
    private void shutdown() {
        scheduler.shutdown();
        try {
            if (!scheduler.awaitTermination(1, TimeUnit.SECONDS)) {
                scheduler.shutdownNow();
            }
        } catch (InterruptedException e) {
            scheduler.shutdownNow();
        }
        logger.close();
        scanner.close();
    }

    /**
     * Prompts the user for a valid URL until one is provided.
     *
     * @return A valid URL as a String.
     */
    private String promptForValidURL() {
        boolean isValidURL = false;
        String urlString = "";

        while (!isValidURL) {
            System.out.println("Bitte gib eine gültige URL ein: ");
            urlString = scanner.nextLine();

            isValidURL = pingService.isValidURL(urlString);
            if (!isValidURL) {
                System.out.println("Ungültige URL");
            } else {
                pingService.setUrlString(urlString);
            }
        }

        return urlString;
    }

    /**
     * Creates a Runnable that pings the URL and logs the status with a timestamp.
     *
     * @return A Runnable that performs the logging task.
     */
    private Runnable createLoggingTask() {
        return () -> {
            try {
                String status = pingService.ping() ? "erreichbar!" : "nicht erreichbar!";
                String timestamp = DATE_FORMAT.format(new Date());
                String logEntry = String.format("%s Uhr : %s -> %s", timestamp, pingService.getUrlString(), status);
                logger.log(logEntry);
            } catch (Exception e) {
                e.printStackTrace();
                logger.log("Verfügbarkeit der URL konnte nicht getestet werden.");
            }
        };
    }

    /**
     * Schedules the logging task to run at fixed intervals
     */
    private void scheduleLoggingTask() {
        Runnable loggerTask = createLoggingTask();
        scheduler.scheduleAtFixedRate(loggerTask, 0, SCHEDULE_INTERVAL_SECONDS, TimeUnit.SECONDS);
    }
}
