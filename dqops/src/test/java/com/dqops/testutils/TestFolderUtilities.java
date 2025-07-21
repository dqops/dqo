/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.testutils;

import org.junit.jupiter.api.Assertions;
import org.springframework.util.FileSystemUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Test utility methods to manage testable folders.
 */
public class TestFolderUtilities {
    /**
     * Returns the root folder (the "target" folder in maven convention) used to create temporary files.
     *
     * @return Temporary root folder.
     */
    public static String getTemporaryFolderRoot() {
        String dqoTemporaryHome = System.getenv("DQO_TEST_TEMPORARY_FOLDER");
        return dqoTemporaryHome;
    }

    /**
     * Creates an empty folder.
     * @param name Subfolder name inside the "target" folder.
     */
    public static String createEmptyTestableFolder(String name) {
        try {
            String temporaryFolderName = getTemporaryFolderRoot();
            Path temporaryFolderPath = Path.of(temporaryFolderName);
            Path path = temporaryFolderPath.resolve(name);

            if (Files.exists(path)) {
                FileSystemUtils.deleteRecursively(path);
            }
            Files.createDirectories(path);

            return path.toAbsolutePath().toString();
        }
        catch (IOException ioe) {
            Assertions.fail(ioe);
            return null;
        }
    }

    /**
     * Ensures that a testable folder exists. A folder is created when it is missing. It is cleaned when requested.
     * @param name Subfolder name inside the "target" folder.
     * @param recreateFolder Recreates the target folder (forcibly).
     */
    public static String ensureTestableFolder(String name, boolean recreateFolder) {
        try {
            String temporaryFolderName = getTemporaryFolderRoot();
            Path temporaryFolderPath = Path.of(temporaryFolderName);
            Path path = temporaryFolderPath.resolve(name);

            if (!Files.exists(path)) {
                Files.createDirectories(path);
            }

            if (recreateFolder) {
                if (Files.exists(path)) {
                    FileSystemUtils.deleteRecursively(path);
                }
                Files.createDirectories(path);
            }

            return path.toAbsolutePath().toString();
        }
        catch (IOException ioe) {
            Assertions.fail(ioe);
            return null;
        }
    }
}
