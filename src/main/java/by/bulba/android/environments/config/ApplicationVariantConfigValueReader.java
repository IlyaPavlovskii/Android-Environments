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

import java.util.Collection;

/**
 * Application variant configuration value reader.
 * Read values from app variant and collect it into single collection.
 * All keys must be unique.
 * */
public interface ApplicationVariantConfigValueReader {

    /**
     * Read values from app variant and collect it into a
     * single collection. All keys must be unique.
     *
     * @param appVariant application variant name
     * */
    Collection<ConfigValue> getConfigValues(String appVariant);

}
