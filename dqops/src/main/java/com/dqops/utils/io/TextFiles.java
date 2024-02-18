/*
 * Copyright © 2021 DQOps (support@dqops.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.dqops.utils.io;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Helper classes for reading a content of a file, without raising exceptions.
 */
@Slf4j
public final class TextFiles {
    /**
     * Reads a content of a text file, without throwing exceptions (returning null instead).
     * @param textFilePath Path to a file.
     * @return File content decoded as UTF-8 or null.
     */
    public static String readString(Path textFilePath) {
        try {
            String fileContent = Files.readString(textFilePath, StandardCharsets.UTF_8);
            fileContent = fileContent.replace("\r\n", "\n");
            return fileContent;
        } catch (IOException ioe) {
            log.debug("Cannot read a file " + textFilePath.toString() + ", error: " + ioe.getMessage(), ioe);
            return null;
        }
    }
}
