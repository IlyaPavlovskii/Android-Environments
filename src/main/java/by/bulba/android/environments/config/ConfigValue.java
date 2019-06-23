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

    public static class Builder {

        private ConfigValue configValue = new ConfigValue();

        public Builder() {
        }

        public Builder key(String key) {
            configValue.key = key;
            return this;
        }

        public Builder type(ConfigType type) {
            configValue.type = type;
            return this;
        }

        public Builder value(String value) {
            configValue.value = value;
            return this;
        }

        public ConfigValue build() {
            return configValue;
        }

    }

}
