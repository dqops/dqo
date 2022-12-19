package ai.dqo.data.storage.parquet;

import lombok.extern.slf4j.Slf4j;
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

import java.io.IOException;

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
        Builder builder = options.getOutputFile() != null ? new Builder(new Path(options.getOutputFile()), table) :
                new Builder(options.getHadoopOutputFile(), table);

        try (final ParquetWriter<Row> writer = builder
                .withConf(this.configuration)
                .withCompressionCodec(CompressionCodecName.fromConf(options.getCompressionCodec().name()))
                .withWriteMode(options.isOverwrite() ? ParquetFileWriter.Mode.OVERWRITE : ParquetFileWriter.Mode.CREATE)
                .build()) {
            final long start = System.currentTimeMillis();
            for(final Row row : table) {
                writer.write(row);
            }
            final long end = System.currentTimeMillis();
//            log.debug("Finished writing {} rows to {} in {} ms",
//                    table.rowCount(), options.getOutputFile(), (end - start));
        } catch (IOException e) {
            throw new RuntimeIOException(e);
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
