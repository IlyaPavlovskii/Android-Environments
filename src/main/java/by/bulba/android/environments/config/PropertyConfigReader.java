package by.bulba.android.environments.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

public class PropertyConfigReader implements ConfigReader {

    private final Properties properties;

    public PropertyConfigReader(Properties properties) {
        this.properties = properties;
    }

    private static Collection<ConfigValue> readPropertyFile(Properties properties) {
        List<ConfigValue> collection = new ArrayList<>();
        properties.forEach((key, value) -> {
            ConfigValue configValue = new ConfigValue.Builder()
                    .key(toConfigKey((String) key))
                    .type(parseValueType((String) value))
                    .value((String) value)
                    .build();
            collection.add(configValue);
        });
        return collection;
    }

    private static String toConfigKey(String key) {
        return key.replaceAll("(\\.)|(-)", "_")
                .toUpperCase();
    }

    private static ConfigType parseValueType(String value) {
        if (value == null) {
            throw new NullPointerException("Missing configuration value");
        }
        if (value.matches("-?\\d+L")) {
            return ConfigType.LONG;
        }
        if (value.matches("-?\\d+")) {
            return ConfigType.INTEGER;
        }
        if (value.matches("[-+]?[0-9]*\\.?[0-9]+f")) {
            return ConfigType.FLOAT;
        }
        if ("true".equalsIgnoreCase(value) || "false".equalsIgnoreCase(value)) {
            return ConfigType.BOOLEAN;
        }
        return ConfigType.STRING;
    }

    @Override
    public Collection<ConfigValue> getConfigValues() {
        return readPropertyFile(properties);
    }
}
