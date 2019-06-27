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
import java.util.Properties;

/**
 * {@link ConfigValue} for '.property' extension files.
 */
public class PropertyConfigReader extends BaseConfigReader {

    private final Properties properties;

    public PropertyConfigReader(Properties properties) {
        this.properties = properties;
    }

    @VisibleForTesting
    Collection<ConfigValue> readPropertyFile(Properties properties) {
        List<ConfigValue> collection = new ArrayList<>();
        properties.forEach((key, value) -> {
            ConfigValue configValue = new ConfigValue.Builder()
                    .key(toConfigKey((String) key))
                    .type(parseValueType((String) value))
                    .value((String) value)
                    .build();
            collection.add(configValue);
        });
        return collection;
    }

    @VisibleForTesting
    ConfigType parseValueType(String value) {
        if (value == null) {
            throw new NullPointerException("Missing configuration value");
        }
        if (value.matches("-?\\d+L")) {
            return ConfigType.LONG;
        }
        if (value.matches("-?\\d+")) {
            return ConfigType.INTEGER;
        }
        if (value.matches("[-+]?[0-9]*\\.?[0-9]+f")) {
            return ConfigType.FLOAT;
        }
        if ("true".equalsIgnoreCase(value) || "false".equalsIgnoreCase(value)) {
            return ConfigType.BOOLEAN;
        }
        return ConfigType.STRING;
    }

    @Override
    public Collection<ConfigValue> getConfigValues() {
        return readPropertyFile(properties);
    }
}
