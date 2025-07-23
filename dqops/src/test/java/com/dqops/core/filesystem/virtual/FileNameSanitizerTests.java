/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.core.filesystem.virtual;

import com.dqops.BaseTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.file.Path;

@SpringBootTest
public class FileNameSanitizerTests extends BaseTest {
    @Test
    void encodeForFileSystem_whenOnlyLegalCharacters_thenReturnsSelf() {
        Assertions.assertEquals("a", FileNameSanitizer.encodeForFileSystem("a"));
        Assertions.assertEquals("abc", FileNameSanitizer.encodeForFileSystem("abc"));
    }

    @Test
    void encodeForFileSystem_whenSpacePresent_thenIsNotEncoded() {
        Assertions.assertEquals("a b", FileNameSanitizer.encodeForFileSystem("a b"));
    }

    @Test
    void encodeForFileSystem_whenSpecialCharacterPresent_thenIsEncoded() {
        Assertions.assertEquals("a+", FileNameSanitizer.encodeForFileSystem("a+"));
        Assertions.assertEquals("a%2F", FileNameSanitizer.encodeForFileSystem("a/"));
        Assertions.assertEquals("a%5C", FileNameSanitizer.encodeForFileSystem("a\\"));
        Assertions.assertEquals("a%26", FileNameSanitizer.encodeForFileSystem("a&"));
        Assertions.assertEquals("a%3E", FileNameSanitizer.encodeForFileSystem("a>"));
    }

    @Test
    void encodeForFileSystem_whenEmptyOrNullName_thenReturnsDefault() {
        Assertions.assertEquals("default", FileNameSanitizer.encodeForFileSystem(null));
        Assertions.assertEquals("default", FileNameSanitizer.encodeForFileSystem(""));
        Assertions.assertEquals("default", FileNameSanitizer.encodeForFileSystem("  "));
    }

    @Test
    void decodeFileSystemName_whenOnlyLegalCharacters_thenReturnsSelf() {
        Assertions.assertEquals("a", FileNameSanitizer.decodeFileSystemName("a"));
        Assertions.assertEquals("abc", FileNameSanitizer.decodeFileSystemName("abc"));
    }

    @Test
    void encodeForFileSystem_whenIllegalCharacters_thenReturnsEncoded() {
        Assertions.assertEquals(".a.%2F%5C%3F%3A%22%3C%3E%25-.", FileNameSanitizer.encodeForFileSystem(".a./\\?:\"<>%-."));
    }

    @Test
    void decodeFileSystemName_whenSanitizedNameGiven_thenRestoresOriginal() {
        final String original = "a./\\?:\"<>%-";
        String sanitized = FileNameSanitizer.encodeForFileSystem(original);
        String decoded = FileNameSanitizer.decodeFileSystemName(sanitized);
        Assertions.assertEquals(original, decoded);
    }

    @Test
    void convertEncodedPathToFullyUrlEncodedPath_whenPathHasEqualsSign_thenEqualsSignsAreNotEncoded() {
        final String original = ".data/check_results/c=bigquery-public-data/t=austin_311.311_service_requests/m=2024-02-01/check_results.0.parquet";
        String sanitized = FileNameSanitizer.convertEncodedLocalPathToFullyUrlEncodedBucketPath(Path.of(original)).toString()
                .replace('\\', '/');
        Assertions.assertEquals(original, sanitized);
    }
}
