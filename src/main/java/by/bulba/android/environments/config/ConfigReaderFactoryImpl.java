/*
 * Copyright (C) 2019. Ilya Pavlovskii
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package by.bulba.android.environments.config;

import by.bulba.android.environments.ConfigFormat;
import by.bulba.android.environments.exceptions.ConfigReadException;
import by.bulba.android.environments.exceptions.ParseConfigException;
import by.bulba.android.environments.parser.YamlParser;
import org.gradle.internal.impldep.com.google.common.annotations.VisibleForTesting;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Simple implementation of {@link ConfigReaderFactory}.
 */
public class ConfigReaderFactoryImpl implements ConfigReaderFactory {

    private final ConfigFormat configFormat;

    /**
     * @param configFormat configuration file format
     */
    public ConfigReaderFactoryImpl(ConfigFormat configFormat) {
        this.configFormat = configFormat;
    }

    @Override
    public ConfigReader create(File file) {
        switch (configFormat) {
            case JSON:
                return createJsonConfigReader(file);
            case YML:
                return createYamlConfigReader(file);
            case PROPERTIES:
            default:
                return createPropertyConfigReader(file);
        }
    }

    @VisibleForTesting
    ConfigReader createJsonConfigReader(File file) {
        JSONObject jsonObject;
        if (file.exists()) {
            JSONParser jsonParser = new JSONParser();
            try (Reader reader = new FileReader(file)) {
                jsonObject = (JSONObject) jsonParser.parse(reader);
            } catch (IOException ioe) {
                throw new ConfigReadException(ioe);
            } catch (ParseException pe) {
                throw new ParseConfigException(pe);
            }
        } else {
            jsonObject = new JSONObject();
        }
        return new JsonConfigReader(jsonObject);
    }

    @VisibleForTesting
    ConfigReader createYamlConfigReader(File file) {
        Map<String, String> yamlModel;
        try {
            if (file.exists()) {
                YamlParser parser = new YamlParser(file);
                yamlModel = parser.parse();
            } else {
                yamlModel = new HashMap<>();
            }
        } catch (IOException e) {
            throw new ConfigReadException(e);
        }
        return new YamlConfigReader(yamlModel);
    }

    @VisibleForTesting
    ConfigReader createPropertyConfigReader(File propertiesFile) {
        Properties properties = new Properties();
        if (propertiesFile.exists()) {
            try (FileInputStream fis = new FileInputStream(propertiesFile)) {
                properties.load(fis);
            } catch (IOException e) {
                throw new ConfigReadException(e);
            }
        }
        return new PropertyConfigReader(properties);
    }

}
