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


import org.gradle.internal.impldep.com.google.common.annotations.VisibleForTesting;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static by.bulba.android.environments.config.ConfigType.STRING;

/**
 * ".yml" configuration reader implementation.
 */
public class YamlConfigReader extends BaseConfigReader {

    private final Iterable<Object> yamlIterable;

    public YamlConfigReader(Iterable<Object> yamlIterable) {
        this.yamlIterable = yamlIterable;
    }

    @Override
    public Collection<ConfigValue> getConfigValues() {
        List<ConfigValue> collection = new ArrayList<>();

        yamlIterable.forEach(map -> {
            if (map instanceof Map) {
                collection.addAll(parseMap((Map) map));
            }
        });
        return collection;
    }

    Collection<ConfigValue> parseMap(Map map) {
        List<ConfigValue> collection = new ArrayList<>();
        map.forEach((key, value) -> {
            ConfigType valueType = parseValueType(value);
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

    @VisibleForTesting
    ConfigType parseValueType(Object value) {
        if (value == null) {
            throw new NullPointerException("Missing configuration value");
        }
        if (value instanceof Integer || value instanceof Long) {
            return ConfigType.INTEGER;
        }
        if (value instanceof Float || value instanceof Double) {
            return ConfigType.FLOAT;
        }
        if (value instanceof Boolean) {
            return ConfigType.BOOLEAN;
        }
        return STRING;
    }

}
