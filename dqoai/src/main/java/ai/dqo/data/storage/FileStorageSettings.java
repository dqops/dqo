package ai.dqo.data.storage;

import ai.dqo.core.filesystem.filesystemservice.contract.DqoRoot;
import net.tlabs.tablesaw.parquet.TablesawParquetWriteOptions;

/**
 * Configuration for the parquet file storage for each type of supported table (sensor readouts, rule results, etc.)
 */
public class FileStorageSettings {
    private DqoRoot tableType;
    private String dataSubfolderName;
    private String parquetFileName;
    private String timePeriodColumnName;
    private String idStringColumnName;
    private TablesawParquetWriteOptions.CompressionCodec compressionCodec;

    /**
     * Creates a file storage configuration for a single type of table that is stored as parquet files.
     * @param tableType Table type identified as a folder inside the .data folder.
     * @param dataSubfolderName The name of a subfolder inside the .data folder where the table data (parquet files) are stored.
     * @param parquetFileName The name of a parquet file that is stored in each partition's folder.
     * @param timePeriodColumnName {@link tech.tablesaw.api.DateTimeColumn} that stores the exact time that is used for monthly partitioning
     *                             (but it is not the column that stores date of the first day of the month).
     * @param idStringColumnName Column name (of String type) that stores the primary key for each row, that is used to find rows to be deleted.
     * @param compressionCodec Compression codec to apply on the parquet files.
     */
    public FileStorageSettings(DqoRoot tableType,
                               String dataSubfolderName,
                               String parquetFileName,
                               String timePeriodColumnName,
                               String idStringColumnName,
                               TablesawParquetWriteOptions.CompressionCodec compressionCodec) {
        this.tableType = tableType;
        this.dataSubfolderName = dataSubfolderName;
        this.parquetFileName = parquetFileName;
        this.timePeriodColumnName = timePeriodColumnName;
        this.idStringColumnName = idStringColumnName;
        this.compressionCodec = compressionCodec;
    }

    /**
     * Returns the table type identified as a folder inside the .data folder.
     * @return Returns the table type (as a dqo root).
     */
    public DqoRoot getTableType() {
        return tableType;
    }

    /**
     * Returns the name of a subfolder inside the .data folder where the table data (parquet files) are stored.
     * @return The folder name inside the .data folder.
     */
    public String getDataSubfolderName() {
        return dataSubfolderName;
    }

    /**
     * Returns the name of a parquet file that is stored in each partition's folder.
     * @return Parquet file name.
     */
    public String getParquetFileName() {
        return parquetFileName;
    }

    /**
     * Returns the {@link tech.tablesaw.api.DateTimeColumn} that stores the exact time that is used for monthly partitioning
     * (but it is not the column that stores date of the first day of the month)
     * @return DateTime column name used for date partitioning (by months).
     */
    public String getTimePeriodColumnName() {
        return timePeriodColumnName;
    }

    /**
     * Returns the name of a String column  {@link tech.tablesaw.api.StringColumn} that stores the primary key of each row.
     * Used to find rows to be deleted.
     * @return ID column name.
     */
    public String getIdStringColumnName() {
        return idStringColumnName;
    }

    /**
     * Compression codec to apply on the parquet files.
     * @return Compression codec.
     */
    public TablesawParquetWriteOptions.CompressionCodec getCompressionCodec() {
        return compressionCodec;
    }
}
