package ai.dqo.data.errors.snapshot;

import ai.dqo.core.filesystem.BuiltInFolderNames;
import ai.dqo.core.synchronization.contract.DqoRoot;
import ai.dqo.data.errors.factory.ErrorsColumnNames;
import ai.dqo.data.storage.FileStorageSettings;
import ai.dqo.data.storage.ParquetPartitionStorageService;
import ai.dqo.data.storage.TableDataSnapshot;
import ai.dqo.metadata.sources.PhysicalTableName;
import net.tlabs.tablesaw.parquet.TablesawParquetWriteOptions;
import tech.tablesaw.api.Table;

/**
 * Check execution errors snapshot that contains an in-memory error result snapshot
 * for a single table and selected time ranges.
 */
public class ErrorsSnapshot extends TableDataSnapshot {
    public static final String PARQUET_FILE_NAME = "errors.0.parquet";

    /**
     * Default constructor that creates an error's snapshot.
     * @param connectionName Connection name.
     * @param tableName Table name (schema.table).
     * @param storageService Backend storage service used to load missing data and save the results.
     * @param newResults Empty normalized table that will be appended with new errors (captured during the current sensor execution).
     */
    public ErrorsSnapshot(String connectionName,
                          PhysicalTableName tableName,
                          ParquetPartitionStorageService storageService,
                          Table newResults) {
        super(connectionName, tableName, storageService, createErrorsStorageSettings(), newResults);
    }

    /**
     * Creates a read-only errors snapshot limited to a set of columns.
     * @param connectionName Connection name.
     * @param tableName Table name (schema.table).
     * @param storageService Backend storage service used to load missing data and save the results.
     * @param columnNames Column names that will be loaded.
     * @param tableResultsSample Empty table with the expected schema (columns).
     */
    public ErrorsSnapshot(String connectionName,
                          PhysicalTableName tableName,
                          ParquetPartitionStorageService storageService,
                          String[] columnNames,
                          Table tableResultsSample) {
        super(connectionName, tableName, storageService, createErrorsStorageSettings(), columnNames, tableResultsSample);
    }

    /**
     * Creates the storage settings for storing the errors.
     * @return Storage settings.
     */
    public static FileStorageSettings createErrorsStorageSettings() {
        return new FileStorageSettings(DqoRoot.data_errors,
                BuiltInFolderNames.ERRORS,
                PARQUET_FILE_NAME,
                ErrorsColumnNames.ERROR_TIMESTAMP_COLUMN_NAME,
                ErrorsColumnNames.ID_COLUMN_NAME,
                TablesawParquetWriteOptions.CompressionCodec.UNCOMPRESSED);
    }
}
