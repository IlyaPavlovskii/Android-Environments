/*
 * Copyright (C) 2019. Ilya Pavlovskii
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package by.bulba.android.environments.config;

import by.bulba.android.environments.ConfigFormat;
import by.bulba.android.environments.exceptions.ConfigReadException;
import by.bulba.android.environments.exceptions.ParseConfigException;
import by.bulba.android.environments.parser.YamlParser;
import org.gradle.internal.impldep.org.junit.runners.Parameterized;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.Objects;
import java.util.Properties;
import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

public class ConfigReaderFactoryImplTest {

    private static final String YML = "test_config.yml";
    private static final String PROPERTIES = "config.properties";
    private static final String JSON = "config.json";
    private final File ymlFile = new File(Objects.requireNonNull(
            this.getClass().getClassLoader().getResource(YML)).getFile());
    private final File propertiesFile = new File(Objects.requireNonNull(
            this.getClass().getClassLoader().getResource(PROPERTIES)).getFile());
    private final File jsonFile = new File(Objects.requireNonNull(
            this.getClass().getClassLoader().getResource(JSON)).getFile());
    @Mock
    private File file;
    @Mock
    private JSONParser jsonParser;
    @Mock
    private YamlParser yamlParser;
    @Mock
    private Properties properties;
    private ConfigReaderFactoryImpl factory;

    @Parameterized.Parameters
    public static Stream<Arguments> provideParametrizedData() {
        return Stream.of(
                Arguments.of(ConfigFormat.PROPERTIES, PropertyConfigReader.class),
                Arguments.of(ConfigFormat.JSON, JsonConfigReader.class),
                Arguments.of(ConfigFormat.YML, YamlConfigReader.class)
        );
    }

    @Before
    public void before() {
        setup();
    }

    @BeforeEach
    public void beforeEach() {
        setup();
    }

    private void setup() {
        MockitoAnnotations.initMocks(this);
        when(file.exists()).thenReturn(false);
        factory = new ConfigReaderFactoryImpl(ConfigFormat.PROPERTIES);
    }

    @ParameterizedTest(name =
            "Factory with format {0} must create reader: {1}"
    )
    @MethodSource("provideParametrizedData")
    public void checkCreateFactory(
            ConfigFormat format,
            Class<? extends ConfigReader> configClass) {
        factory = new ConfigReaderFactoryImpl(format);
        assertThat(factory.create(file), instanceOf(configClass));
    }

    @Test
    public void createEmptyPropertyReaderWhenFileDoesNotExists() {
        assertThat(
                factory.createPropertyConfigReader(file),
                instanceOf(PropertyConfigReader.class)
        );
    }

    @Test
    public void createPropertyReaderWhenFileExists() {
        file = new File("test_file");
        assertThat(
                factory.createPropertyConfigReader(file),
                instanceOf(PropertyConfigReader.class)
        );
    }

    @Test
    public void createEmptyYmlReaderWhenFileDoesNotExists() {
        assertThat(
                factory.createYamlConfigReader(file),
                instanceOf(YamlConfigReader.class)
        );
    }

    @Test
    public void createYmlReaderWhenFileExists() {
        file = new File("test.yml");
        assertThat(
                factory.createYamlConfigReader(file),
                instanceOf(YamlConfigReader.class)
        );
    }

    @Test
    public void createEmptyJsonReaderWhenFileDoesNotExists() {
        assertThat(
                factory.createJsonConfigReader(file),
                instanceOf(JsonConfigReader.class)
        );
    }

    @Test
    public void createJsonReaderWhenFileExists() {
        file = new File("test.yml");
        assertThat(
                factory.createJsonConfigReader(file),
                instanceOf(JsonConfigReader.class)
        );
    }

    @Test(expected = ConfigReadException.class)
    public void throwConfigReadExceptionWhenParserThrowIOException() throws IOException, ParseException {
        when(jsonParser.parse(any(Reader.class))).thenThrow(IOException.class);
        factory.loadJsonFile(ymlFile, jsonParser);
    }

    @Test(expected = ParseConfigException.class)
    public void throwParseConfigExceptionWhenParserThrowParserException() throws IOException, ParseException {
        when(jsonParser.parse(any(Reader.class))).thenThrow(ParseException.class);
        factory.loadJsonFile(ymlFile, jsonParser);
    }

    @Test(expected = ConfigReadException.class)
    public void throwConfigReadExceptionWhenYamlParserThrowIOException() throws IOException {
        when(yamlParser.parse(any(Reader.class))).thenThrow(IOException.class);
        factory.loadYamlFile(ymlFile, yamlParser);
    }

    @Test(expected = ConfigReadException.class)
    public void throwConfigReadExceptionWhenLoadPropertiesThrowIoException() throws IOException {
        doThrow(IOException.class).when(properties).load(any(InputStream.class));
        factory.loadPropertyFile(ymlFile, properties);
    }

    @Test
    public void loadPropertyFileSuccess() {
        properties = new Properties();
        factory.loadPropertyFile(propertiesFile, properties);
        assertTrue(properties.size() > 0);
    }

    @Test
    public void loadJsonFileSuccess() {
        assertTrue(factory.loadJsonFile(jsonFile, new JSONParser()).size() > 0);
    }

    @Test
    public void loadYmlFileSuccess() {
        assertTrue(factory.loadYamlFile(ymlFile, new YamlParser()).size() > 0);
    }
}