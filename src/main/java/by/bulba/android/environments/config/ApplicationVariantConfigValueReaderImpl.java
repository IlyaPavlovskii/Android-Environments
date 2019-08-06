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

import by.bulba.android.environments.exceptions.UniqueKeyException;
import by.bulba.android.environments.file.ConfigFileProvider;
import org.gradle.internal.impldep.com.google.common.annotations.VisibleForTesting;

import java.io.File;
import java.util.*;

/**
 * Simple implementation of {@link ApplicationVariantConfigValueReader}
 */
public class ApplicationVariantConfigValueReaderImpl implements ApplicationVariantConfigValueReader {

    private final ConfigFileProvider configFileProvider;
    private final ConfigReaderFactory configReaderFactory;

    /**
     * Simple implementation of {@link ApplicationVariantConfigValueReader}
     *
     * @param configFileProvider  application variant config file provider
     * @param configReaderFactory configuration file reader instance
     */
    public ApplicationVariantConfigValueReaderImpl(
            ConfigFileProvider configFileProvider,
            ConfigReaderFactory configReaderFactory) {
        this.configFileProvider = configFileProvider;
        this.configReaderFactory = configReaderFactory;
    }

    @Override
    public Collection<ConfigValue> getConfigValues(String appVariant) {
        List<ConfigValue> totalConfigValues = new ArrayList<>();
        Set<String> keySet = new HashSet<>();
        configFileProvider.getConfigFiles(appVariant)
                .forEach(file ->
                        totalConfigValues.addAll(collectConfigValues(file, keySet))
                );
        keySet.clear();
        return totalConfigValues;
    }

    @VisibleForTesting
    Collection<ConfigValue> collectConfigValues(
            File file,
            Set<String> keySet) {
        List<ConfigValue> list = new ArrayList<>();
        configReaderFactory
                .create(file)
                .getConfigValues()
                .forEach(configValue -> {
                    if (keySet.contains(configValue.getKey())) {
                        throw new UniqueKeyException(file, configValue.getKey());
                    }
                    keySet.add(configValue.getKey());
                    list.add(configValue);
                });
        return list;
    }

}
