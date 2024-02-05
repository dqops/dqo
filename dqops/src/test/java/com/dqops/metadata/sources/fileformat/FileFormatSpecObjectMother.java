package com.dqops.metadata.sources.fileformat;

import com.dqops.sampledata.files.csv.CsvFileProvider;

import java.util.List;
import java.util.SortedMap;

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
            setCsvFileFormat( new CsvFileFormatSpec(){{
                setAutoDetect(true);
            }} );
            setFilePaths(new FilePathListSpec(){{
                    add(CsvFileProvider.getFile(csvFileName).toString());
            }});
        }};
    }

    // todo: description
    public static FileFormatSpec createForCsvFiles(List<String> csvFileNames, SortedMap<String, String> headerNameTypeMap) {

        FilePathListSpec filePathListSpec = new FilePathListSpec();
        csvFileNames.forEach(fileName -> filePathListSpec.add(fileName));

        return new FileFormatSpec() {{
            setCsvFileFormat( new CsvFileFormatSpec(){{
                setAutoDetect(true);
                setHeader(false);
                setColumns(headerNameTypeMap);
            }} );
            setFilePaths(filePathListSpec);
        }};
    }
}
