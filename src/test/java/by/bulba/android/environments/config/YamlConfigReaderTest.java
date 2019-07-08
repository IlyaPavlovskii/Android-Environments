package by.bulba.android.environments.config;

import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.*;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class YamlConfigReaderTest {

    private final Iterable<Object> iterable = new ArrayList<>();
    private final YamlConfigReader reader = new YamlConfigReader(iterable);

    public static Stream<Arguments> provideParametrizedData() {
        return Stream.of(
                Arguments.of(1, ConfigType.INTEGER, "1"),
                Arguments.of(2L, ConfigType.INTEGER, "2"),
                Arguments.of(3.1, ConfigType.FLOAT, "3.1f"),
                Arguments.of(true, ConfigType.BOOLEAN, "true"),
                Arguments.of("some_res", ConfigType.STRING, "\"some_res\"")
        );
    }

    @Test
    public void getEmptyConfigValuesWhenJsonIsEmpty() {
        assertTrue(reader.getConfigValues().isEmpty());
    }

    @Test
    public void collectValuesWhenJsonIsNotEmpty() {
        Map<String, String> map = new LinkedHashMap<>();
        map.put("some_key", "some_value");
        ((List<Object>) iterable).add(map);
        Collection<ConfigValue> collection = reader.getConfigValues();
        assertEquals(1, collection.size());
    }

    @ParameterizedTest
    @MethodSource("provideParametrizedData")
    public void checkParseType(Object value, ConfigType expectedType) {
        assertEquals(expectedType, reader.parseValueType(value));
    }

    @ParameterizedTest
    @MethodSource("provideParametrizedData")
    public void checkParseValue(Object value, ConfigType expectedType, String expectedValue) {
        assertEquals(expectedValue, reader.parseValue(value, expectedType));
    }

}