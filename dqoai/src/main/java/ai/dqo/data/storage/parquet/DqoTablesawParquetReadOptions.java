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
