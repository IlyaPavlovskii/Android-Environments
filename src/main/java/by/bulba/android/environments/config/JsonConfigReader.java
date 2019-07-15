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

import by.bulba.android.environments.parser.ConfigTypeParser;
import by.bulba.android.environments.parser.ObjectConfigTypeParser;
import org.gradle.internal.impldep.com.google.common.annotations.VisibleForTesting;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Configuration reader implementation for ".json" file types.
 */
public class JsonConfigReader extends BaseConfigReader<Object> {

    private final JSONObject jsonObject;

    public JsonConfigReader(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    @Override
    public Collection<ConfigValue> getConfigValues() {
        List<ConfigValue> collection = new ArrayList<>();
        jsonObject.forEach((key, value) -> {
            ConfigType valueType = parseConfigType(value);
            ConfigValue configValue = new ConfigValue.Builder()
                    .key(toConfigKey((String) key))
                    .value(parseValue(value, valueType))
                    .type(valueType)
                    .build();
            collection.add(configValue);
        });
        return collection;
    }

    @VisibleForTesting
    String parseValue(Object value, ConfigType valueType) {
        switch (valueType) {
            case STRING:
                return String.format("\"%s\"", String.valueOf(value));
            case FLOAT:
                return String.format("%sf", String.valueOf(value));
            case LONG:
            case INTEGER:
            case BOOLEAN:
            default:
                return String.valueOf(value);
        }
    }

    @Override
    ConfigTypeParser<Object> getConfigTypeParser() {
        return new ObjectConfigTypeParser();
    }
}
