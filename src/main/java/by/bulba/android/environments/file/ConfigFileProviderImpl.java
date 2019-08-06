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

import by.bulba.android.environments.ConfigFormat;
import by.bulba.android.environments.exceptions.DirectoryExpectedException;
import by.bulba.android.environments.exceptions.DirectoryNotFoundException;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

/**
 * Simple {@link ConfigFileProvider} implementation.
 */
public class ConfigFileProviderImpl implements ConfigFileProvider {

    private final File rootConfigPath;
    private final ConfigFormat configFormat;

    /**
     * @param rootConfigPath root configuration files directory
     * @param configFormat   configuration file format instance
     */
    public ConfigFileProviderImpl(
            File rootConfigPath,
            ConfigFormat configFormat) {
        this.rootConfigPath = rootConfigPath;
        this.configFormat = configFormat;
    }

    @Override
    public Collection<File> getConfigFiles(String subfolder) {
        File applicationVariantDir = new File(rootConfigPath, subfolder);
        if (!applicationVariantDir.exists()) {
            throw new DirectoryNotFoundException();
        }
        if (!applicationVariantDir.isDirectory()) {
            throw new DirectoryExpectedException(applicationVariantDir);
        }
        File[] configFiles = getFilteredConfigFiles(applicationVariantDir);
        if (configFiles != null && configFiles.length > 0) {
            return Arrays.asList(configFiles);
        } else {
            return Collections.emptyList();
        }
    }

    private File[] getFilteredConfigFiles(File dir) {
        return dir.listFiles(pathname ->
                pathname.isFile() && pathname.getName().endsWith(configFormat.getExtension())
        );
    }
}
