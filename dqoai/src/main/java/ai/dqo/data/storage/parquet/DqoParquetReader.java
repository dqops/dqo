/*
 * Copyright © 2021 DQO.ai (support@dqo.ai)
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
