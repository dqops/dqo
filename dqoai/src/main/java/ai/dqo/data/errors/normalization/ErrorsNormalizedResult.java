package ai.dqo.data.errors.normalization;

import ai.dqo.data.errors.factory.ErrorsColumnNames;
import ai.dqo.data.readouts.normalization.SensorReadoutsNormalizedResult;
import ai.dqo.utils.tables.TableColumnUtility;
import tech.tablesaw.api.DateTimeColumn;
import tech.tablesaw.api.StringColumn;
import tech.tablesaw.api.Table;

/**
 * Describes the dataset (dataframe) returned from a failed sensor. Identifies the time series column, data stream columns, etc.
 * The columns are normalized.
 */
public class ErrorsNormalizedResult extends SensorReadoutsNormalizedResult {
    private final StringColumn readoutIdColumn;
    private final StringColumn errorMessageColumn;
    private final StringColumn errorSourceColumn;
    private final DateTimeColumn errorTimestampColumn;

    /**
     * Creates a check error dataset, extracting key columns.
     * WARNING: this method has an intended side result - it adds missing columns to the table.
     *
     * @param table Sorted table with check errors - may be modified by adding missing columns.
     */
    public ErrorsNormalizedResult(Table table) {
        super(table);
        this.readoutIdColumn = TableColumnUtility.getOrAddStringColumn(table, ErrorsColumnNames.READOUT_ID_COLUMN_NAME);
        this.errorMessageColumn = TableColumnUtility.getOrAddStringColumn(table, ErrorsColumnNames.ERROR_MESSAGE_COLUMN_NAME);
        this.errorSourceColumn = TableColumnUtility.getOrAddStringColumn(table, ErrorsColumnNames.ERROR_SOURCE_COLUMN_NAME);
        this.errorTimestampColumn = TableColumnUtility.getOrAddDateTimeColumn(table, ErrorsColumnNames.ERROR_TIMESTAMP_COLUMN_NAME);
    }

    /**
     * Returns the error with a unique ID of the sensor readout. There could be multiple rows with the same readout_id, especially for checkpoints and partitioned checks.
     * @return Column with the readout ID that is the same ID that would be used as the "ID" column in sensor_readouts or rule_results.
     */
    public StringColumn getReadoutIdColumn() {
        return readoutIdColumn;
    }

    /**
     * Returns the column that contains the error message.
     * @return Error message column.
     */
    public StringColumn getErrorMessageColumn() {
        return errorMessageColumn;
    }

    /**
     * Returns the column that returns the error source, values are in {@link ai.dqo.data.errors.factory.ErrorSource} - sensor or rule.
     * @return Error source column.
     */
    public StringColumn getErrorSourceColumn() {
        return errorSourceColumn;
    }

    /**
     * Returns the error timestamp column. It is the time when the error happened, but it is stored as a local timestamp of the DQO instance that executed the failed check.
     * This column is used as a partitioning key to store monthly parquet partitions.
     * @return Error timetamp column.
     */
    public DateTimeColumn getErrorTimestampColumn() {
        return errorTimestampColumn;
    }
}
