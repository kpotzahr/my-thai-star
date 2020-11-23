package com.devonfw.mts.api.config;

import org.springframework.util.ResourceUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class TestConfiguration {
    public static String getApiUrl() {
        try (InputStream input = new FileInputStream(ResourceUtils.getFile("classpath:application.properties"))) {
            Properties prop = new Properties();
            prop.load(input);
            return prop.getProperty("mythaistar.url");
        } catch (IOException ex) {
            ex.printStackTrace();
            return "http://localhost:8081/";
        }
    }
}
