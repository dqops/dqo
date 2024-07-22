package com.dqops.metadata.sources.fileformat;

import com.dqops.metadata.sources.fileformat.csv.CsvFileFormatSpec;
import com.dqops.metadata.sources.fileformat.deltalake.DeltaLakeFileFormatSpec;
import com.dqops.metadata.sources.fileformat.iceberg.IcebergFileFormatSpec;
import com.dqops.metadata.sources.fileformat.json.JsonFileFormatSpec;
import com.dqops.metadata.sources.fileformat.json.JsonFormatType;
import com.dqops.sampledata.files.SampleDataFilesProvider;

import java.util.List;

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
     * @return FileFormatSpec
     */
    public static FileFormatSpec createForMultipleCsvFiles(List<String> csvFileNames) {

        FilePathListSpec filePathListSpec = new FilePathListSpec();
        csvFileNames.stream()
                .filter(s -> !s.contains("header.csv"))
                .forEach(fileName -> filePathListSpec.add(fileName));

        return new FileFormatSpec() {{
            setCsv(new CsvFileFormatSpec(){{
                setAutoDetect(true);
                setHeader(false);
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
                setFormat(JsonFormatType.auto);
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

    /**
     * Creates a FileFormatSpec for Iceberg table format.
     * @param icebergMetadataDirectory Iceberg metadata directory
     * @return FileFormatSpec.
     */
    public static FileFormatSpec createForIcebergFormat(String icebergMetadataDirectory) {
        return new FileFormatSpec() {{
            setIceberg(new IcebergFileFormatSpec());
            setFilePaths(new FilePathListSpec(){{
                add(SampleDataFilesProvider.getFile(icebergMetadataDirectory).toString());
            }});
        }};
    }

    /**
     * Creates a FileFormatSpec for Delta Lake table format.
     * @param deltaLakeDirectory Delta Lake metadata directory
     * @return FileFormatSpec.
     */
    public static FileFormatSpec createForDeltaLakeFormat(String deltaLakeDirectory) {
        return new FileFormatSpec() {{
            setDeltaLake(new DeltaLakeFileFormatSpec());
            setFilePaths(new FilePathListSpec(){{
                add(SampleDataFilesProvider.getFile(deltaLakeDirectory).toString());
            }});
        }};
    }

}
