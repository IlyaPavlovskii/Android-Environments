package by.bulba.android.environments.config

enum ConfigType {
    LONG("Long"),
    INTEGER("Integer"),
    FLOAT("Float"),
    BOOLEAN("Boolean"),
    STRING("String")

    ConfigType(String value) {
        this.configString = value
    }
    private final String configString

    String getConfigString() {
        return configString
    }

    @Override
    String toString() {
        return configString
    }
}