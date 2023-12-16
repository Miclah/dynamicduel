package game.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public class PersistentStorage {

    private static final String FILE_PATH = "src/main/resources/Settings/storage.properties";
    private static Properties properties;

    static {
        properties = new Properties();
        loadProperties();
    }

    private static void loadProperties() {
        try (InputStream input = new FileInputStream(FILE_PATH)) {
            properties.load(input);
        } catch (IOException e) {
            // If the file does not exist, create an empty one
            createEmptyFile();
        }
    }

    private static void createEmptyFile() {
        try (OutputStream output = new FileOutputStream(FILE_PATH)) {
            properties.store(output, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }

    public static void setProperty(String key, String value) {
        properties.setProperty(key, value);
        saveProperties();
    }

    public static boolean getBoolean(String key, boolean defaultValue) {
        String value = getProperty(key, Boolean.toString(defaultValue));
        return Boolean.parseBoolean(value);
    }

    public static void setBoolean(String key, boolean value) {
        setProperty(key, Boolean.toString(value));
    }

    private static void saveProperties() {
        try (OutputStream output = new FileOutputStream(FILE_PATH)) {
            properties.store(output, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
