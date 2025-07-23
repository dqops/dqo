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

import net.tlabs.tablesaw.parquet.TablesawParquetWriteOptions;
import org.apache.parquet.io.OutputFile;
import tech.tablesaw.io.WriteOptions;

import java.io.File;
import java.io.Writer;

/**
 * Custom write options to write parquet files.
 */
public class DqoTablesawParquetWriteOptions extends WriteOptions {
    public static Builder dqoBuilder(final File file) {
        return new Builder(file.getAbsolutePath());
    }

    private final String outputFile;
    private final OutputFile hadoopOutputFile;
    private final TablesawParquetWriteOptions.CompressionCodec compressionCodec;
    private final boolean overwrite;


    protected DqoTablesawParquetWriteOptions(final Builder builder) {
        super(builder);
        this.outputFile = builder.outputFile;
        this.hadoopOutputFile = builder.hadoopOutputFile;
        this.compressionCodec = builder.compressionCodec;
        this.overwrite = builder.overwrite;
    }

    public OutputFile getHadoopOutputFile() {
        return hadoopOutputFile;
    }

    public String getOutputFile() {
        return outputFile;
    }

    public TablesawParquetWriteOptions.CompressionCodec getCompressionCodec() {
        return compressionCodec;
    }

    public boolean isOverwrite() {
        return overwrite;
    }

    public static class Builder extends WriteOptions.Builder {
        private final String outputFile;
        private final OutputFile hadoopOutputFile;
        private TablesawParquetWriteOptions.CompressionCodec compressionCodec = TablesawParquetWriteOptions.CompressionCodec.SNAPPY;
        private boolean overwrite = true;

        public Builder(final String outputFile) {
            super((Writer) null);
            this.outputFile = outputFile;
            this.hadoopOutputFile = null;
        }

        public Builder(final OutputFile hadoopOutputFile) {
            super((Writer) null);
            this.outputFile = null;
            this.hadoopOutputFile = hadoopOutputFile;
        }

        public Builder withCompressionCode(final TablesawParquetWriteOptions.CompressionCodec compressionCodec) {
            this.compressionCodec = compressionCodec;
            return this;
        }

        public Builder withOverwrite(final boolean overwrite) {
            this.overwrite = overwrite;
            return this;
        }

        public DqoTablesawParquetWriteOptions build() {
            return new DqoTablesawParquetWriteOptions(this);
        }
    }
}
