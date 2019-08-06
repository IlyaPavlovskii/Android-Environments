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
package by.bulba.android.environments.file;

import java.io.File;
import java.util.Collection;

/**
 * Configuration files provider. Read configuration files from subfolder located on root conf directory
 * {@link by.bulba.android.environments.AndroidEnvironmentsExtension#configPath}
 * Config files filters by file format {@link by.bulba.android.environments.AndroidEnvironmentsExtension#format}
 *
 * @see by.bulba.android.environments.ConfigFormat
 * @see by.bulba.android.environments.AndroidEnvironmentsExtension
 */
public interface ConfigFileProvider {

    /**
     * Read configuration files from subfolder located on root configuration directory.
     * Filter config files by configuration format.
     *
     * @param subfolder configuration sub directory
     * */
    Collection<File> getConfigFiles(String subfolder);

}
