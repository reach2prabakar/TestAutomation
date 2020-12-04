package com.client.library;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class PropertyReader {
    private static final Logger logger = LogManager.getLogger(PropertyReader.class);
    private final Properties prop = new Properties();

    public PropertyReader() {
        String propFileName = System.getProperty("env")!=null? System.getProperty("env") : "test";
        InputStream resourceStream = this.getClass().getClassLoader().getResourceAsStream( propFileName+ ".properties");
        loadProperties(resourceStream);
    }

    private void loadProperties(InputStream stream) {
        try {
            if (stream != null) {
                prop.load(stream);
            }
        } catch (IOException e) {
            logger.error("Could not load user properties file", e);
        }
    }

    public String getProperty(String name) {
        String systemProperty = System.getProperty(name);

        if (!StringUtils.isEmpty(systemProperty)) {
            return systemProperty;
        } else {
            String var = prop.getProperty(name);
            if (var==null){
                throw new RuntimeException("The name:"+name +" is not defined in the property file");
            }
            return var;
        }
    }
}