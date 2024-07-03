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

import com.dqops.utils.exceptions.DqoRuntimeException;
import com.dqops.utils.tables.TableColumnUtility;
import net.tlabs.tablesaw.parquet.TablesawParquetReadOptions;
import net.tlabs.tablesaw.parquet.TablesawParquetReader;
import net.tlabs.tablesaw.parquet.TablesawReadSupport;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.parquet.hadoop.ParquetReader;
import org.apache.parquet.hadoop.util.HadoopInputFile;
import tech.tablesaw.api.Row;
import tech.tablesaw.api.StringColumn;
import tech.tablesaw.api.Table;
import tech.tablesaw.columns.Column;
import tech.tablesaw.io.RuntimeIOException;
import tech.tablesaw.io.Source;

import java.io.*;
import java.net.URI;
import java.nio.file.Files;
import java.util.List;

/**
 * Customized parquet reader that can use an in-memory file system to read parquet files, avoiding
 * parsing the hadoop configuration on every load and using Windows hadoop libraries.
 */
//@Slf4j
public class DqoTablesawParquetReader extends TablesawParquetReader implements AutoCloseable {
    private Configuration configuration;
    private DqoInMemoryFileSystem inMemoryFileSystem;

    public DqoTablesawParquetReader(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public Table read(final Source source) {
        final File file = source.file();
        if (file != null) {
            return read(TablesawParquetReadOptions.builder(file).build());
        }
        final InputStream inStream = source.inputStream();
        if (inStream != null) {
            return readFromStream(inStream);
        }
        throw new UnsupportedOperationException("Reading parquet from a character stream is not supported");
    }

    @Override
    public Table read(final TablesawParquetReadOptions options) {
        final TablesawReadSupport readSupport = new TablesawReadSupport(options);
        try (final ParquetReader<Row> reader = makeReader(options.getInputURI(), readSupport)) {
            Table loadedTable = readInternal(reader, readSupport, options.getSanitizedinputPath());

            Column<?>[] columns = loadedTable.columnArray();
            boolean stringColumnConvertedToText = false;
            for (int i = 0; i < columns.length; i++) {
                if (columns[i] instanceof StringColumn) {
                    columns[i] = TableColumnUtility.convertToTextColumn(columns[i]);
                    stringColumnConvertedToText = true;
                }
            }

            if (stringColumnConvertedToText) {
                return Table.create(columns);
            }

            return loadedTable;
        } catch (Exception ex) {
            throw new DqoRuntimeException(ex);
        }
    }

    private Table readInternal(final ParquetReader<Row> reader, final TablesawReadSupport readSupport, final String displayName) throws IOException {
        final long start = System.currentTimeMillis();
        int i = 0;
        while (reader.read() != null) {
            i++;
        }
        final long end = System.currentTimeMillis();
//        log.debug("Finished reading {} rows from {} in {} ms", i, displayName, (end - start));
        return readSupport.getTable();
    }

    private ParquetReader<Row> makeReader(final URI uri, final TablesawReadSupport readSupport) throws IOException {
        final String scheme = uri.getScheme();
        if(scheme != null) {
            switch(scheme) {
                case "http":   // fall through
                case "https":  // fall through
                case "ftp":    // fall through
                case "ftps":   // fall through
                    try (final InputStream inStream = uri.toURL().openStream()) {
                        return makeReaderFromStream(inStream, readSupport);
                    }
                case "file":
                    String filePath = uri.getPath();
                    String myOS = System.getProperty("os.name");
                    if (StringUtils.containsIgnoreCase(myOS, "windows")) {
                        filePath = filePath.substring(1); // skip the first "/"
                    }
                    try (final InputStream inStream = Files.newInputStream(java.nio.file.Path.of(filePath))) {
                        return makeReaderFromStream(inStream, readSupport);
                    }
                default:
                    // fall through
            }
        }

        // alternative brutal implementation
//        ParquetReader<Row> rowParquetReader = new ParquetReader<>(SHARED_HADOOP_CONF, hadoopPath, readSupport);


        // we are using a depreciated constructor because it is the only one that accepts both our readSupport and a preloaded hadoop Configuration
        Path hadoopPath = new Path(uri);
        FileSystem fileSystem = hadoopPath.getFileSystem(this.configuration);
        FileStatus fileStatus = fileSystem.getFileStatus(hadoopPath);
        HadoopInputFile hadoopInputFile = HadoopInputFile.fromStatus(fileStatus, this.configuration);

        ParquetReader<Row> rowParquetReader = DqoParquetReader.builder(hadoopInputFile, readSupport).build();
        return rowParquetReader;
    }

    private Table readFromStream(final InputStream inStream) {
        TablesawParquetReadOptions tablesawParquetReadOptions = DqoTablesawParquetReadOptions.builderForStream().build();
        final TablesawReadSupport readSupport = new TablesawReadSupport(tablesawParquetReadOptions);
        try {
            return readInternal(makeReaderFromStream(inStream, readSupport), readSupport, "stream");
        } catch (IOException e) {
            throw new RuntimeIOException(e);
        }
        finally {
            try {
                inStream.close();
            }
            catch (IOException ioe) {
            }
        }
    }

    /**
     * Opens a parquet reader from an input stream. The input stream is copied to a hadoop in memory file system.
     * @param inStream Input stream.
     * @param readSupport Read support for parquet (parquet parser).
     * @return Parquet reader.
     * @throws IOException
     */
    private ParquetReader<Row> makeReaderFromStream(final InputStream inStream,
                                                    final TablesawReadSupport readSupport) throws IOException {
        this.inMemoryFileSystem = new DqoInMemoryFileSystem(new Path("ramfs://inmemory/").toUri(), this.configuration);
        DqoInMemoryPath inMemoryFilePath = new DqoInMemoryPath("ramfs://inmemory/file.parquet", this.inMemoryFileSystem);
        try (FSDataOutputStream fsDataOutputStream = this.inMemoryFileSystem.create(inMemoryFilePath)) {
            IOUtils.copyLarge(inStream, fsDataOutputStream);
        }

        HadoopInputFile hadoopInputFile = HadoopInputFile.fromStatus(this.inMemoryFileSystem.getFileStatus(inMemoryFilePath), this.configuration);
        ParquetReader<Row> parquetReader = DqoParquetReader.builder(hadoopInputFile, readSupport)
                .build();
        return parquetReader;
    }

    /**
     * Closes this resource, relinquishing any underlying resources.
     * This method is invoked automatically on objects managed by the
     * {@code try}-with-resources statement.
     */
    @Override
    public void close() throws Exception {
        if (this.inMemoryFileSystem != null) {
            this.inMemoryFileSystem.close();
            this.inMemoryFileSystem = null;
        }
    }
}
