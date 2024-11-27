package utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class PropertyData {
    public static String value;
    public static String getProperty(String key) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(System.getProperty("user.dir")+"/propertiesFile/data.properties");
        Properties properties = new Properties();
        properties.load(fileInputStream);
        value = properties.getProperty(key);
        return value;
    }
}
