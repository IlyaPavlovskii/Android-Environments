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
import org.gradle.internal.impldep.com.google.common.annotations.VisibleForTesting;

/**
 * Base {@link ConfigReader} implementation with basis implementation of common methods.
 */
public abstract class BaseConfigReader<T> implements ConfigReader {

    abstract ConfigTypeParser<T> getConfigTypeParser();

    @VisibleForTesting
    protected String toConfigKey(String key) {
        return key.replaceAll("(\\.)|(-)", "_")
                .toUpperCase();
    }

    /**
     * Parse config type by generic source type.
     *
     * @param value source config type value to parse
     * @return target configuration type from source value
     */
    protected ConfigType parseConfigType(T value) {
        return getConfigTypeParser().parse(value);
    }

}
