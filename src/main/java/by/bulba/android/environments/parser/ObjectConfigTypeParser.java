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

import static by.bulba.android.environments.config.ConfigType.STRING;

/**
 * Simple implementation of {@link ConfigTypeParser} for {@link Object} key type.
 * Convert Object source type to target known {@link ConfigType}.
 * */
public class ObjectConfigTypeParser implements ConfigTypeParser<Object> {

    @Override
    public ConfigType parse(Object value) {
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
