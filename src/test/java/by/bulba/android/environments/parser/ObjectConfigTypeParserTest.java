package by.bulba.android.environments.parser;

import by.bulba.android.environments.config.ConfigType;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class ObjectConfigTypeParserTest {

    private ConfigTypeParser<Object> parser = new ObjectConfigTypeParser();

    private static Stream<Arguments> provideConfigTypeArguments() {
        return Stream.of(
                Arguments.of(1, ConfigType.INTEGER, "1"),
                Arguments.of(2L, ConfigType.INTEGER, "2"),
                Arguments.of(3.1, ConfigType.FLOAT, "3.1f"),
                Arguments.of(true, ConfigType.BOOLEAN, "true"),
                Arguments.of("some_res", ConfigType.STRING, "\"some_res\"")
        );
    }

    @ParameterizedTest
    @MethodSource("provideConfigTypeArguments")
    public void checkParseConfigType(Object value, ConfigType expectedType) {
        assertEquals(expectedType, parser.parse(value));
    }

}