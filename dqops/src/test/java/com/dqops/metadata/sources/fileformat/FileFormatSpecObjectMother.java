package com.dqops.metadata.sources.fileformat;

import com.dqops.sampledata.files.SampleDataFilesProvider;

import java.util.List;
import java.util.Map;

/**
 * Object mother for FileFormatSpec.
 */
public class FileFormatSpecObjectMother {
    /**
     * Creates a FileFormatSpec for a single csv file.
     * @param csvFileName Csv file name.
     * @return FileFormatSpec.
     */
    public static FileFormatSpec createForCsvFile(String csvFileName) {
        return new FileFormatSpec() {{
            setCsv(new CsvFileFormatSpec(){{
                setAutoDetect(true);
            }} );
            setFilePaths(new FilePathListSpec(){{
                    add(SampleDataFilesProvider.getFile(csvFileName).toString());
            }});
        }};
    }

    /**
     * Creates a FileFormatSpec for a folder with multiple csv files.
     * @param csvFileNames List with names of csv files.
     * @param headerNameTypeMap A header of the data.
     * @return FileFormatSpec
     */
    public static FileFormatSpec createForMultipleCsvFiles(List<String> csvFileNames, Map<String, String> headerNameTypeMap) {

        FilePathListSpec filePathListSpec = new FilePathListSpec();
        csvFileNames.stream()
                .filter(s -> !s.contains("header.csv"))
                .forEach(fileName -> filePathListSpec.add(fileName));

        return new FileFormatSpec() {{
            setCsv(new CsvFileFormatSpec(){{
                setAutoDetect(true);
                setHeader(false);
                setColumns(headerNameTypeMap);
            }} );
            setFilePaths(filePathListSpec);
        }};
    }

    /**
     * Creates a FileFormatSpec for a single json file.
     * @param jsonFileName Json file name.
     * @return FileFormatSpec.
     */
    public static FileFormatSpec createForJsonFile(String jsonFileName) {
        return new FileFormatSpec() {{
            setJson(new JsonFileFormatSpec(){{
                setAutoDetect(true);
            }} );
            setFilePaths(new FilePathListSpec(){{
                add(SampleDataFilesProvider.getFile(jsonFileName).toString());
            }});
        }};
    }

    /**
     * Creates a FileFormatSpec for a single parquet file.
     * @param parquetFileName Parquet file name.
     * @return FileFormatSpec.
     */
    public static FileFormatSpec createForParquetFile(String parquetFileName) {
        return new FileFormatSpec() {{
            setParquet(new ParquetFileFormatSpec());
            setFilePaths(new FilePathListSpec(){{
                add(SampleDataFilesProvider.getFile(parquetFileName).toString());
            }});
        }};
    }

}
