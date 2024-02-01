package com.dqops.metadata.sources.fileformat;

import com.dqops.sampledata.files.CsvSampleFilesObjectMother;

/**
 * Object mother for FileFormatSpec.
 */
public class FileFormatSpecObjectMother {
    /**
     * Creates a FileFormatSpec for a csv file format.
     * @param csvFileName Csv file name.
     * @return FileFormatSpec.
     */
    public static FileFormatSpec createForCsvFile(String csvFileName) {
        return new FileFormatSpec() {{
            setCsvFileFormat( new CsvFileFormatSpec(){{
                setAutoDetect(true);
            }} );
            setFilePaths(new FilePathListSpec(){{
                    add(CsvSampleFilesObjectMother.getFile(csvFileName).toString());
            }});
        }};
    }
}
