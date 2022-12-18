package ai.dqo.data.storage.parquet;

import org.apache.hadoop.fs.Path;
import org.apache.parquet.hadoop.ParquetReader;
import org.apache.parquet.hadoop.api.ReadSupport;
import org.apache.parquet.io.InputFile;

import java.io.IOException;

/**
 * Custom parquet reader - extended only to create a custom builder.
 * @param <T>
 */
public class DqoParquetReader<T> extends ParquetReader<T> {
    /**
     * @param file
     * @param readSupport
     * @deprecated
     */
    public DqoParquetReader(Path file, ReadSupport<T> readSupport) throws IOException {
        super(file, readSupport);
    }

    public static <R> Builder<R> builder(InputFile file, ReadSupport<R> readSupport) {
        return new Builder<R>(file, readSupport);
    }

    /**
     * Custom parquet reader builder with a custom read support.
     */
    public static class Builder<T> extends ParquetReader.Builder<T> {
        private final ReadSupport<T> readSupport;

        protected Builder(InputFile file, ReadSupport<T> readSupport) {
            super(file);
            this.readSupport = readSupport;
        }

        @Override
        protected ReadSupport<T> getReadSupport() {
            return this.readSupport;
        }
    }
}
