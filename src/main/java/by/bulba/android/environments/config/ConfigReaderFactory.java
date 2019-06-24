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

/**
 * {@link ConfigReader} factory.
 * Plugin have to more than one implementation of configuration reader and have to use
 * different types of reader of each extension.
 */
public interface ConfigReaderFactory {

    /**
     * Creates new {@link ConfigReader} implementation of this sub config.
     *
     * @param subConfig sub configuration path.
     * @return {@link ConfigReader} implementation for current config.
     */
    ConfigReader create(String subConfig);

}
