/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
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
