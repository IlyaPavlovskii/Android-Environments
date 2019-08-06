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
package by.bulba.android.environments.exceptions;

import java.io.File;

/**
 * Application variant type passed to {@link by.bulba.android.environments.config.ApplicationVariantConfigValueReader}
 * represents as a file, but expected as a directory.
 */
public class DirectoryExpectedException extends RuntimeException {

    public DirectoryExpectedException(File file) {
        super("Expected directory by path: " + file.getAbsolutePath());
    }
}
