/*
 * Copyright © 2021 DQOps (support@dqops.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.dqops.data.storage.parquet;

/*-
 * #%L
 * Tablesaw-Parquet
 * %%
 * Copyright (C) 2020 - 2021 Tlabs-data
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

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
