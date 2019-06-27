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

import org.json.simple.JSONObject;
import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Collection;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class JsonConfigReaderTest {

    private final JSONObject jsonObject = new JSONObject();
    private final JsonConfigReader reader = new JsonConfigReader(jsonObject);

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
        jsonObject.put("some_key", "some_value");
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
