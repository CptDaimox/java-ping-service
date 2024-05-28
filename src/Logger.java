import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Singleton class to handle logging messages to a file.
 */
public class Logger {
    private static volatile Logger instance;
    private final BufferedWriter writer;

    /**
     * Private constructor to initialize the BufferedWriter.
     *
     * @param fileName The name of the file to write logs to.
     * @throws IOException If an I/O error occurs.
     */
    private Logger(String fileName) throws IOException {
        writer = new BufferedWriter(new FileWriter(fileName, true));
    }

    /**
     * Returns the singleton instance of the Logger.
     *
     * @param fileName The name of the file to write logs to.
     * @return The singleton instance of the Logger.
     * @throws IOException If an I/O error occurs.
     */
    public static Logger getInstance(String fileName) throws IOException {
        if (instance == null) {
            synchronized (Logger.class) {
                if (instance == null) {
                    instance = new Logger(fileName);
                }
            }
        }
        return instance;
    }

    /**
     * Logs a message to the file.
     *
     * @param message The message to log.
     */
    public void log(String message) {
        try {
            writer.write(message);
            writer.newLine();
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Closes the BufferedWriter.
     */
    public void close() {
        try {
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
