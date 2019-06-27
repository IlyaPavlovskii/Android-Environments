package by.bulba.android.environments.config;

import org.gradle.internal.impldep.com.google.common.annotations.VisibleForTesting;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static by.bulba.android.environments.config.ConfigType.STRING;

/**
 * Configuration reader implementation for ".json" file types.
 * */
public class JsonConfigReader extends BaseConfigReader {

    private final JSONObject jsonObject;

    public JsonConfigReader(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    @Override
    public Collection<ConfigValue> getConfigValues() {
        List<ConfigValue> collection = new ArrayList<>();
        jsonObject.forEach((key, value) -> {
            ConfigType valueType = parseValueType(value);
            ConfigValue configValue = new ConfigValue.Builder()
                    .key(toConfigKey((String) key))
                    .value(parseValue(value, valueType))
                    .type(valueType)
                    .build();
            collection.add(configValue);
        });
        return collection;
    }

    @VisibleForTesting
    String parseValue(Object value, ConfigType valueType) {
        switch (valueType) {
            case STRING:
                return String.format("\"%s\"", String.valueOf(value));
            case FLOAT:
                return String.format("%sf", String.valueOf(value));
            case LONG:
            case INTEGER:
            case BOOLEAN:
            default:
                return String.valueOf(value);
        }
    }

    @VisibleForTesting
    ConfigType parseValueType(Object value) {
        if (value == null) {
            throw new NullPointerException("Missing configuration value");
        }
        if (value instanceof Integer || value instanceof Long) {
            return ConfigType.INTEGER;
        }
        if (value instanceof Float || value instanceof Double) {
            return ConfigType.FLOAT;
        }
        if (value instanceof Boolean) {
            return ConfigType.BOOLEAN;
        }
        return STRING;
    }
}
