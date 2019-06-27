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

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Collection;

import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
public class BaseConfigReaderTest {

    private static final String SOURCE_KEY = "key.debUg-value";
    private static final String EXPECTED_KEY = "KEY_DEBUG_VALUE";

    private final BaseConfigReader reader = new BaseConfigReader() {
        @Override
        public Collection<ConfigValue> getConfigValues() {
            // Do nothing
            return null;
        }
    };

    @Test
    public void checkConfigKeyConversion() {
        assertEquals(EXPECTED_KEY, reader.toConfigKey(SOURCE_KEY));
    }

}
