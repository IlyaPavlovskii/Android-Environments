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

import by.bulba.android.environments.config.ConfigReaderFactory;
import by.bulba.android.environments.config.ConfigReaderFactoryImpl;
import com.android.build.gradle.AppExtension;
import com.android.build.gradle.BaseExtension;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.UnknownDomainObjectException;
import org.gradle.internal.impldep.com.google.common.annotations.VisibleForTesting;
import org.jetbrains.annotations.NotNull;

class AndroidEnvironmentsPlugin implements Plugin<Project> {

    @VisibleForTesting
    AndroidEnvironmentsExtension ext;

    @Override
    public void apply(@NotNull Project project) {
        project.getExtensions().create(AndroidEnvironmentsExtension.EXTENSIONS_NAME,
                AndroidEnvironmentsExtension.class);
        project.afterEvaluate(project1 -> {
            ext = project1.getExtensions()
                    .findByType(AndroidEnvironmentsExtension.class);
            if (ext == null) {
                ext = new AndroidEnvironmentsExtension();
            }
            executeTask(project);
        });
    }

    @VisibleForTesting
    void executeTask(Project project) {
        ConfigReaderFactory readerFactory = new ConfigReaderFactoryImpl(project, ext);
        try {
            Object androidExtension = project
                    .getExtensions()
                    .getByName("android");
            if (ext.useBuildTypes && androidExtension instanceof BaseExtension) {
                processBuildTypes(readerFactory, (BaseExtension) androidExtension);
            }
            if (ext.useProductFlavors && androidExtension instanceof AppExtension) {
                processApplicationVariants(readerFactory, (AppExtension) androidExtension);
            }
        } catch (UnknownDomainObjectException udoe) {
            throw new RuntimeException(udoe);
        }
    }

    @VisibleForTesting
    void processBuildTypes(ConfigReaderFactory readerFactory, BaseExtension extension) {
        extension.getBuildTypes().forEach(buildType -> readerFactory.create(buildType.getName())
                .getConfigValues()
                .forEach(configValue -> buildType.buildConfigField(
                        configValue.getType().getConfigString(),
                        configValue.getKey(),
                        configValue.getValue()
                ))
        );
    }

    @VisibleForTesting
    void processApplicationVariants(ConfigReaderFactory readerFactory, AppExtension extension) {
        extension.getApplicationVariants().forEach(applicationVariant -> readerFactory
                .create(applicationVariant.getFlavorName())
                .getConfigValues()
                .forEach(configValue -> applicationVariant.buildConfigField(
                        configValue.getType().getConfigString(),
                        configValue.getKey(),
                        configValue.getValue()
                ))
        );
    }

}
