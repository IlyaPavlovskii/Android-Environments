package by.bulba.android.environments.config

class PropertyConfigReader implements ConfigReader {

    File propertiesFile

    PropertyConfigReader() {
    }

    @Override
    Collection<ConfigValue> getConfigValues() {
        def properties = new Properties()
        if (propertiesFile.exists()) {
            properties.load(new FileInputStream(propertiesFile))
        }
        return readPropertyFile(properties)
    }

    private static Collection<ConfigValue> readPropertyFile(Properties properties) {
        def collection = new ArrayList()
        properties.entrySet().forEach { entry ->
            def configValue = new ConfigValue.Builder()
                    .key(toConfigKey(entry.key as String))
                    .type(parseValueType(entry.value as String))
                    .value(entry.value as String)
                    .build()
            collection.add(configValue)
        }
        return collection
    }

    private static String toConfigKey(String key) {
        return key.replaceAll("(\\.)|(-)", "_")
                .toUpperCase()
    }

    private static ConfigType parseValueType(String value) {
        if (value == null) {
            throw new NullPointerException("Missing configuration value")
        }
        if (value =~ "[0-9]*L") {
            return ConfigType.LONG
        }
        if (value.isInteger()) {
            return ConfigType.INTEGER
        }
        if (value.isFloat()) {
            return ConfigType.FLOAT
        }
        if ("true" == value.toLowerCase() || "false" == value.toLowerCase()) {
            return ConfigType.BOOLEAN
        }
        return ConfigType.STRING
    }
}
