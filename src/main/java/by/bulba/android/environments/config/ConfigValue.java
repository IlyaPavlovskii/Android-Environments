package by.bulba.android.environments.config;

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

    public String getKey() {
        return key;
    }

    public ConfigType getType() {
        return type;
    }

    public String getValue() {
        return value;
    }

    static class Builder {

        private ConfigValue configValue = new ConfigValue();

        Builder() {
        }

        Builder key(String key) {
            configValue.key = key;
            return this;
        }

        Builder type(ConfigType type) {
            configValue.type = type;
            return this;
        }

        Builder value(String value) {
            configValue.value = value;
            return this;
        }

        ConfigValue build() {
            return configValue;
        }

    }

}
