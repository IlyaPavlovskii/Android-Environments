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
package by.bulba.android.environments.parser;

import by.bulba.android.environments.config.ConfigType;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

public class StringConfigTypeParserTest {

    private ConfigTypeParser<String> parser = new StringConfigTypeParser();

    private static Stream<Arguments> provideConfigTypeArguments() {
        return Stream.of(
                Arguments.of("123L", ConfigType.LONG),
                Arguments.of("123", ConfigType.INTEGER),
                Arguments.of("32.1f", ConfigType.FLOAT),
                Arguments.of("0.99", ConfigType.FLOAT),
                Arguments.of("true", ConfigType.BOOLEAN),
                Arguments.of("True", ConfigType.BOOLEAN),
                Arguments.of("trustme", ConfigType.STRING)
        );
    }


    @ParameterizedTest
    @MethodSource("provideConfigTypeArguments")
    public void checkParseValueTypeConversion(String value, ConfigType expected) {
        assertEquals(expected, parser.parse(value));
    }

}
