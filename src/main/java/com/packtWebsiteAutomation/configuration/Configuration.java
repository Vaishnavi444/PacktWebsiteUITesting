package com.packtWebsiteAutomation.configuration;

import java.io.InputStream;
import java.util.Properties;

public class Configuration {
    private static Properties properties = new Properties();

    static {
        try (InputStream input = Configuration.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input != null) {
                properties.load(input);
            } else {
                System.err.println("Sorry, unable to find config.properties. Make sure the file is in the classpath.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Handle exception
        }
    }

    public static String getEmail() {
        return properties.getProperty("email");
    }

    public static String getPassword() {
        return properties.getProperty("password");
    }
}
