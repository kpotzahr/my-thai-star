package com.devonfw.mts.shared;

import org.springframework.util.ResourceUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class TestConfiguration {
    private static final Logger LOG = LoggerFactory.getLogger(TestConfiguration.class);

    public static String getApiUrl() {
        try (InputStream input = new FileInputStream(ResourceUtils.getFile("classpath:application.properties"))) {
            Properties prop = new Properties();
            prop.load(input);
            return prop.getProperty("mythaistar.url");
        } catch (IOException ex) {
            LOG.error("Error when accessing application properties; default is used", ex);
            return "http://localhost:8081/";
        }
    }

    public static String getChromedriverPath() {
        return "lib/chromedriver.exe";
    }
}
