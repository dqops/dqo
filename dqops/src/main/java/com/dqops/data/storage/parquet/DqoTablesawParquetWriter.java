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

import com.dqops.utils.exceptions.DqoRuntimeException;
import net.tlabs.tablesaw.parquet.TablesawParquetWriteOptions;
import net.tlabs.tablesaw.parquet.TablesawParquetWriter;
import net.tlabs.tablesaw.parquet.TablesawWriteSupport;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.parquet.hadoop.ParquetFileWriter;
import org.apache.parquet.hadoop.ParquetWriter;
import org.apache.parquet.hadoop.api.WriteSupport;
import org.apache.parquet.hadoop.metadata.CompressionCodecName;
import org.apache.parquet.io.OutputFile;
import tech.tablesaw.api.Row;
import tech.tablesaw.api.Table;
import tech.tablesaw.io.RuntimeIOException;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.Locale;

/**
 * Customized parquet writer for tablesaw that uses a shared hadoop configuration.
 */
//@Slf4j
public class DqoTablesawParquetWriter extends TablesawParquetWriter {
    private Configuration configuration;

    /**
     * Creates a parquet writer with a given (shared) hadoop configuration.
     * @param configuration Hadoop configuration.
     */
    public DqoTablesawParquetWriter(Configuration configuration) {
        this.configuration = configuration;
    }

    /**
     * Writes a table object to a parquet file.
     * @param table Table to write.
     * @param options Write options.
     */
    public void write(final Table table, final DqoTablesawParquetWriteOptions options) {
        try {
            try (DqoInMemoryFileSystem inMemoryFileSystem =
                    new DqoInMemoryFileSystem(new URI("ramfs://inmemory/"), this.configuration)) {

                String inMemoryFileName = "ramfs://inmemory/output.parquet" + (options.getCompressionCodec() == TablesawParquetWriteOptions.CompressionCodec.UNCOMPRESSED ? "" :
                        "." + options.getCompressionCodec().name().toLowerCase(Locale.ROOT));

                DqoInMemoryPath inMemoryParquetPath = new DqoInMemoryPath(inMemoryFileName, inMemoryFileSystem);
                Builder builder = new Builder(inMemoryParquetPath, table);

                try (final ParquetWriter<Row> writer = builder
                        .withConf(this.configuration)
                        .withCompressionCodec(CompressionCodecName.fromConf(options.getCompressionCodec().name()))
                        .withWriteMode(options.isOverwrite() ? ParquetFileWriter.Mode.OVERWRITE : ParquetFileWriter.Mode.CREATE)
                        .withPageWriteChecksumEnabled(false)
                        .build()) {

                    for (final Row row : table) {
                        writer.write(row);
                    }
                }

                File nioCurrentFile = java.nio.file.Path.of(options.getOutputFile()).toFile();
                if (nioCurrentFile.exists()) {
                    nioCurrentFile.delete();
                }
                try {
                    inMemoryFileSystem.copyToLocalFile(false, inMemoryParquetPath, new Path(options.getOutputFile()), true);
                } catch (Exception ex) {
                    if (nioCurrentFile.exists()) {
                        nioCurrentFile.delete();
                    }
                    throw ex;
                }
            }
        }
        catch (IOException e) {
            throw new RuntimeIOException(e);
        }
        catch (Exception e) {
            throw new DqoRuntimeException(e);
        }
    }

    /**
     * Writes a tablesaw table object to an in-memory object.
     * @param table Table to write.
     */
    public byte[] writeToByteArray(final Table table) {
        try {
            try (DqoInMemoryFileSystem inMemoryFileSystem =
                    new DqoInMemoryFileSystem(new URI("ramfs://inmemory/"), this.configuration)) {
                String inMemoryFileName = "ramfs://inmemory/output.parquet";

                DqoInMemoryPath inMemoryParquetPath = new DqoInMemoryPath(inMemoryFileName, inMemoryFileSystem);
                Builder builder = new Builder(inMemoryParquetPath, table);

                try (final ParquetWriter<Row> writer = builder
                        .withConf(this.configuration)
                        .withCompressionCodec(CompressionCodecName.UNCOMPRESSED)
                        .withWriteMode(ParquetFileWriter.Mode.CREATE)
                        .withPageWriteChecksumEnabled(false)
                        .build()) {

                    for (final Row row : table) {
                        writer.write(row);
                    }
                }

                byte[] inMemoryFileContent = inMemoryFileSystem.getInMemoryFileContent(inMemoryParquetPath);
                inMemoryFileSystem.close();

                return inMemoryFileContent;
            }
        }
        catch (IOException e) {
            throw new RuntimeIOException(e);
        }
        catch (Exception e) {
            throw new DqoRuntimeException(e);
        }
    }

    protected static class Builder extends ParquetWriter.Builder<Row, Builder> {
        private final Table table;

        protected Builder(final Path path, final Table table) {
            super(path);
            this.table = table;
        }

        protected Builder(final OutputFile file, final Table table) {
            super(file);
            this.table = table;
        }

        @Override
        protected Builder self() {
            return this;
        }

        @Override
        protected WriteSupport<Row> getWriteSupport(final Configuration conf) {
            return new TablesawWriteSupport(this.table);
        }
    }
}
