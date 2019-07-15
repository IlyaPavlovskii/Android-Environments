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

import java.io.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class YamlParser {

    private final Reader reader;

    public YamlParser(File file) throws FileNotFoundException {
        this(new FileReader(file));
    }

    public YamlParser(Reader reader) {
        this.reader = reader;
    }

    public Map<String, String> parse() throws IOException {
        Map<String, String> map = new LinkedHashMap<>();
        try (BufferedReader br = new BufferedReader(reader)) {
            String currentLine;
            String[] parseArray;
            while ((currentLine = br.readLine()) != null) {
                parseArray = currentLine
                        .replaceAll(": ", ":")
                        .split(":");
                map.put(parseArray[0], parseArray[1]);
            }
        } catch (IOException e) {
            throw new IOException(e);
        }
        return map;
    }

}
