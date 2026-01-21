package org.example;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Config {
    private static final Config INSTANCE = new Config();
    private final Properties props = new Properties();
    private static final Logger LOG = LoggerFactory.getLogger(Config.class);

    private Config() {
        try (InputStream in = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            if (in != null) {
                props.load(in);
            }
        } catch (IOException e) {
            // Log and continue; callers may provide system overrides
            LOG.warn("Failed to load config.properties from classpath", e);
        }
    }

    public static Config getInstance() {
        return INSTANCE;
    }

    public String get(String key) {
        return props.getProperty(key, "");
    }
}
