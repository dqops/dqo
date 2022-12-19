package ai.dqo.data.storage.parquet;

import net.tlabs.tablesaw.parquet.TablesawParquetReadOptions;

import java.net.URI;

/**
 * Custom tablesaw parquet read options, created to be able to use the builder.
 */
public class DqoTablesawParquetReadOptions extends TablesawParquetReadOptions {
    public DqoTablesawParquetReadOptions(Builder builder) {
        super(builder);
    }

    public static Builder builderForStream() {
        return new Builder((URI)null);
    }

    public static class Builder extends TablesawParquetReadOptions.Builder {
        protected Builder(URI inputURI) {
            super(inputURI);
        }
    }
}
