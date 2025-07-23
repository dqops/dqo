/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.data.storage.parquet;

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
