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
 * Support object for build config field on {@link com.android.build.gradle.BaseExtension}.
 */
public class ConfigValue {

    private String key;
    private ConfigType type;
    private String value;

    private ConfigValue() {
    }

    public ConfigValue(String key, ConfigType type, String value) {
        this.key = key;
        this.type = type;
        this.value = value;
    }

    /**
     * @return key name.
     */
    public String getKey() {
        return key;
    }

    /**
     * @return config value type.
     */
    public ConfigType getType() {
        return type;
    }

    /**
     * @return configuration value.
     */
    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Key: " + key +
                " Value: " + value +
                " Type: " + type;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof ConfigValue) {
            return key.equals(((ConfigValue) obj).key) &&
                    value.equals(((ConfigValue) obj).value) &&
                    type == ((ConfigValue) obj).type;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return key.hashCode() + value.hashCode();
    }

    /**
     * {@link ConfigValue} builder.
     */
    public static class Builder {

        private ConfigValue configValue = new ConfigValue();

        public Builder() {
        }

        /**
         * Set configuration key value and return builder instance.
         */
        public Builder key(String key) {
            configValue.key = key;
            return this;
        }

        /**
         * Set configuration build type value and return builder instance.
         */
        public Builder type(ConfigType type) {
            configValue.type = type;
            return this;
        }

        /**
         * Set configuration value and return builder instance.
         */
        public Builder value(String value) {
            configValue.value = value;
            return this;
        }

        /**
         * Build {@link ConfigValue} instance.
         */
        public ConfigValue build() {
            return configValue;
        }

    }

}
