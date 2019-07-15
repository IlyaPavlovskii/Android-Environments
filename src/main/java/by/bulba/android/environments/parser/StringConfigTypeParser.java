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
package by.bulba.android.environments.parser;

import by.bulba.android.environments.config.ConfigType;


/**
 * String implementation of {@link ConfigTypeParser} for {@link String} key type.
 * Convert String source to target known {@link ConfigType}.
 * */
public class StringConfigTypeParser implements ConfigTypeParser<String> {

    @Override
    public ConfigType parse(String value) {
        if (value == null) {
            throw new NullPointerException("Missing configuration value");
        }
        if (value.matches("-?\\d+L")) {
            return ConfigType.LONG;
        }
        if (value.matches("-?\\d+")) {
            return ConfigType.INTEGER;
        }
        if (value.matches("[+-]?(\\d+\\.)?\\d+[f]?$")) {
            return ConfigType.FLOAT;
        }
        if ("true".equalsIgnoreCase(value) || "false".equalsIgnoreCase(value)) {
            return ConfigType.BOOLEAN;
        }
        return ConfigType.STRING;
    }
}
