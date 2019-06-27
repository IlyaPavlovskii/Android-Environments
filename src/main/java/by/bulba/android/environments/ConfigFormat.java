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
package by.bulba.android.environments;

/**
 * Enumeration of possible configuration file format types.
 * */
public enum ConfigFormat {
    PROPERTIES("properties"),
    JSON("json");

    private final String extension;

    ConfigFormat(String extension) {
        this.extension = extension;
    }

    /**
     * Parse string format value to {@link ConfigFormat}
     *
     * @param format name of configuration format
     *
     * @throws IllegalArgumentException when passed type is unknown.
     * */
    public static ConfigFormat parse(String format) {
        if (JSON.extension.equals(format)) {
            return JSON;
        }
        if (PROPERTIES.extension.equals(format)) {
            return PROPERTIES;
        }
        throw new IllegalArgumentException("Unknown configuration format: " + format);
    }

    public String getExtension() {
        return extension;
    }
}
