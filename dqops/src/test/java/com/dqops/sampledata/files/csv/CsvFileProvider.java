package com.dqops.sampledata.files.csv;

import org.junit.jupiter.api.Assertions;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CsvFileProvider {

    /**
     * Returns a file object of a csv file in the dqo/sampledata folder.
     * @param fileName CSV file name inside the dqo/sampledata folder.
     * @return File object of a csv file.
     */
    public static File getFile(String fileName) {
        String sampleDataFolder = System.getenv("SAMPLE_DATA_FOLDER");
        Path sampleFilePath = Path.of(sampleDataFolder).resolve(fileName);
        File sampleFile = sampleFilePath.toFile();
        Assertions.assertTrue(Files.exists(sampleFilePath));
        return sampleFile;
    }

    /**
     * Returns multiple csv files with data and header as a separate file from the dqo/sampledata/{path} path.
     * @param path A file path with csv files in dqo/sampledata folder
     * @return File object of multiple csv files.
     */
    public static List<File> getFiles(String path) {
        String sampleDataFolder = System.getenv("SAMPLE_DATA_FOLDER");
        Path sampleFilesPath = Path.of(sampleDataFolder).resolve(path);
        File sampleFiles = sampleFilesPath.toFile();
        Assertions.assertTrue(Files.exists(sampleFilesPath));

        List<File> files = Arrays.stream(sampleFiles.listFiles()).collect(Collectors.toList());
        Assertions.assertNotEquals(0, files);
        Assertions.assertEquals(1, files.stream().filter(file -> file.getName().equals("header.csv")).count());

        return files;
    }

}
