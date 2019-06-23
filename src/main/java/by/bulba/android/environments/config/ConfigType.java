package by.bulba.android.environments.config;

/**
 * Possible value types for buildConfigValue.
 * */
public enum ConfigType {
    LONG("Long"),
    INTEGER("Integer"),
    FLOAT("Float"),
    BOOLEAN("Boolean"),
    STRING("String");

    private final String configString;

    ConfigType(String value) {
        this.configString = value;
    }

    /**
     * Configuration value type text.
     * */
    public String getConfigString() {
        return configString;
    }

    @Override
    public String toString() {
        return configString;
    }
}
