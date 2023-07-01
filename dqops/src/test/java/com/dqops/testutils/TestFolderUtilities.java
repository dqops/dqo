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
