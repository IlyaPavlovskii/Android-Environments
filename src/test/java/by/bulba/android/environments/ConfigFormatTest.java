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
package by.bulba.android.environments;

import org.gradle.internal.impldep.org.junit.runners.Parameterized;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ConfigFormatTest {

    @Parameterized.Parameters
    public static Stream<Arguments> provideParametrizedData() {
        return Stream.of(
                Arguments.of("json", ConfigFormat.JSON),
                Arguments.of("properties", ConfigFormat.PROPERTIES)
        );
    }

    @ParameterizedTest
    @MethodSource("provideParametrizedData")
    public void checkParser(String input, ConfigFormat configFormat) {
        assertEquals(configFormat, ConfigFormat.parse(input));
    }

    @Test
    public void throwExceptionWhenTryToParseUnknownFormat() {
        assertThrows(IllegalArgumentException.class, () -> ConfigFormat.parse("unknown"));
    }

}
