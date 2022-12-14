package ai.dqo.data.errors.snapshot;

import ai.dqo.core.filesystem.BuiltInFolderNames;
import ai.dqo.core.filesystem.filesystemservice.contract.DqoRoot;
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
    public static final String PARQUET_FILE_NAME = "errors.0.parquet.snappy";

    /**
     * Default constructor that creates a snapshot.
     * @param connectionName Connection name.
     * @param tableName Table name (schema.table).
     * @param storageService Backend storage service used to load missing data and save the results.
     * @param newResults Empty normalized table that will be appended with new errors (captured during the current sensor execution).
     */
    public ErrorsSnapshot(String connectionName,
                          PhysicalTableName tableName,
                          ParquetPartitionStorageService storageService,
                          Table newResults) {
        super(connectionName, tableName, storageService, createRuleResultsStorageSettings(), newResults);
    }

    /**
     * Creates the storage settings for storing the rule results.
     * @return Storage settings.
     */
    public static FileStorageSettings createRuleResultsStorageSettings() {
        return new FileStorageSettings(DqoRoot.DATA_PROFILING_RESULTS,
                BuiltInFolderNames.ERRORS,
                PARQUET_FILE_NAME,
                ErrorsColumnNames.ERROR_TIMESTAMP_COLUMN_NAME,
                ErrorsColumnNames.ID_COLUMN_NAME,
                TablesawParquetWriteOptions.CompressionCodec.SNAPPY);
    }
}
