package by.bulba.android.environments.parser;


import org.gradle.internal.impldep.org.junit.runners.Parameterized;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

class YamlParserTest {

    private static final String FILE_NAME = "test_config.yml";

    private final File yamlConfig = new File(Objects.requireNonNull(
            this.getClass().getClassLoader().getResource(FILE_NAME)).getFile());
    private final YamlParser yamlParser = new YamlParser(yamlConfig);

    public YamlParserTest() throws FileNotFoundException {
    }

    @Parameterized.Parameters
    public static Stream<Arguments> provideFileData() {
        return Stream.of(
                Arguments.of("key.yaml.test", "\"yaml debug value\""),
                Arguments.of("key.yaml.build.type.bool", "false"),
                Arguments.of("key.yaml.build.type.int", "3"),
                Arguments.of("key.yaml.build.type.float", "7.4")
        );
    }

    @ParameterizedTest
    @MethodSource("provideFileData")
    public void checkParserKeys(String key) throws IOException {
        Map<String, String> map = yamlParser.parse();
        assertTrue(map.containsKey(key));
    }

    @ParameterizedTest
    @MethodSource("provideFileData")
    public void checkParserValues(String key, String value) throws IOException {
        Map<String, String> map = yamlParser.parse();
        assertEquals(value, map.get(key));
    }
}