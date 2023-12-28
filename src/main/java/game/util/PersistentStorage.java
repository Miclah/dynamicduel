package game.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

/**
 * Utility class for persistent storage of key-value pairs using properties.
 * Or simply put "a boolean file reader/writer"
 */
public class PersistentStorage {

    // File path for the storage properties file
    private static final String FILE_PATH = "src/main/resources/Settings/storage.properties";
    private static Properties properties;

    // Static block to initialize the properties and load them from the storage file
    static {
        properties = new Properties();
        loadProperties();
    }

    /**
     * Loads properties from the storage file.
     */
    private static void loadProperties() {
        try (InputStream input = new FileInputStream(FILE_PATH)) {
            properties.load(input);
        } catch (IOException e) {
            // If the file does not exist, create an empty one
            createEmptyFile();
        }
    }

    /**
     * Creates an empty storage file.
     */
    private static void createEmptyFile() {
        try (OutputStream output = new FileOutputStream(FILE_PATH)) {
            properties.store(output, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets a property value based on the specified key.
     *
     * @param key          The key of the property.
     * @param defaultValue The default value if the key is not found.
     * @return The value associated with the key or the default value if not found.
     */
    public static String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }

    /**
     * Sets a property with the specified key and value.
     *
     * @param key   The key of the property.
     * @param value The value to be set.
     */
    public static void setProperty(String key, String value) {
        properties.setProperty(key, value);
        // Save the properties after modifying them
        saveProperties();
    }

    /**
     * Gets a boolean property value based on the specified key.
     *
     * @param key          The key of the boolean property.
     * @param defaultValue The default value if the key is not found.
     * @return The boolean value associated with the key or the default value if not found.
     */
    public static boolean getBoolean(String key, boolean defaultValue) {
        String value = getProperty(key, Boolean.toString(defaultValue));
        return Boolean.parseBoolean(value);
    }

    /**
     * Sets a boolean property with the specified key and value.
     *
     * @param key   The key of the boolean property.
     * @param value The boolean value to be set.
     */
    public static void setBoolean(String key, boolean value) {
        // Convert boolean value to string and set the property
        setProperty(key, Boolean.toString(value));
    }

    /**
     * Saves the properties to the storage file.
     */
    private static void saveProperties() {
        try (OutputStream output = new FileOutputStream(FILE_PATH)) {
            properties.store(output, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
