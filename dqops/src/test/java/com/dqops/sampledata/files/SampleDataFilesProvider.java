/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.sampledata.files;

import org.junit.jupiter.api.Assertions;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SampleDataFilesProvider {

    /**
     * Returns a file object of a sample data file in the dqo/sampledata folder.
     * @param filePathInSampleData A Path to a file with sample data inside the dqo/sampledata folder.
     * @return File object of a data file.
     */
    public static File getFile(String filePathInSampleData) {
        String sampleDataFolder = System.getenv("SAMPLE_DATA_FOLDER");
        Path sampleFilePath = Path.of(sampleDataFolder).resolve(filePathInSampleData);
        File sampleFile = sampleFilePath.toFile();
        Assertions.assertTrue(Files.exists(sampleFilePath));
        return sampleFile;
    }

    /**
     * Returns multiple csv files with data and header as a separate file from the dqo/sampledata/{path} path.
     * @param path A file path with csv files in dqo/sampledata folder
     * @return File object of multiple csv files.
     */
    public static List<File> getCsvFiles(String path) {
        File sampleFilesDirectory = getFile(path);
        List<File> files = Arrays.stream(sampleFilesDirectory.listFiles()).collect(Collectors.toList());
        Assertions.assertNotEquals(0, files);
        Assertions.assertEquals(1, files.stream().filter(file -> file.getName().equals("header.csv")).count());
        return files;
    }

}
