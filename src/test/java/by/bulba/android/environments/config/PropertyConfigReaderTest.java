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

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collection;
import java.util.Properties;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PropertyConfigReaderTest {

    private static final String SOURCE_KEY = "key.debUg-value";
    private static final String SOURCE_VALUE = "debug-value";
    private static final String EXPECTED_KEY = "KEY_DEBUG_VALUE";

    @Mock
    private Properties properties;
    private PropertyConfigReader reader;

    private static Stream<Arguments> provideParseValueArguments() {
        return Stream.of(
                Arguments.of("123L", ConfigType.LONG),
                Arguments.of("123", ConfigType.INTEGER),
                Arguments.of("32.1f", ConfigType.FLOAT),
                Arguments.of("true", ConfigType.BOOLEAN),
                Arguments.of("True", ConfigType.BOOLEAN),
                Arguments.of("trustme", ConfigType.STRING)
        );
    }

    @Before
    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        reader = new PropertyConfigReader(properties);
    }

    @Test
    public void getEmptyConfigValuesWhenPropertiesIsEmpty() {
        assertTrue(reader.getConfigValues().isEmpty());
    }

    @Test
    public void readPropertyFileWhenPropertiesAreNotEmpty() {
        Properties props = new Properties();
        props.setProperty(SOURCE_KEY, SOURCE_VALUE);
        Collection<ConfigValue> collection = reader.readPropertyFile(props);

        ConfigValue value = collection.iterator().next();
        ConfigValue expected = new ConfigValue(EXPECTED_KEY, ConfigType.STRING, SOURCE_VALUE);
        assertEquals(expected, value);

    }

    @ParameterizedTest
    @MethodSource("provideParseValueArguments")
    public void checkParseValueTypeConversion(String source, ConfigType expected) {
        ConfigType configType = reader.parseValueType(source);
        assertEquals(expected, configType);
    }
}