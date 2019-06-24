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
 * Android environment extension file. Contains plugin settings.
 * */
public class AndroidEnvironmentsExtension {

    public static final String EXTENSIONS_NAME = "environments";

    /**
     * Relative path of configuration folder.
     * */
    public String configPath = "config";
    /**
     * Configuration property file name.
     * */
    public String configFile = "config.properties";
    /**
     * Read configuration properties for build types.
     * */
    public boolean useBuildTypes = false;
    /**
     * Read configuration properties for each product flavor.
     * */
    public boolean useProductFlavors = false;

    @Override
    public String toString() {
        return "Path: " + configPath +
                " File: " + configFile +
                " UseBuildTypes: " + useBuildTypes +
                " UseProductFlavors: " + useProductFlavors;
    }
}
