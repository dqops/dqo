/*
 * Copyright © 2023 DQOps (support@dqops.com)
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
package com.dqops.connectors.duckdb;

import com.dqops.connectors.ProviderType;
import com.dqops.metadata.sources.ConnectionSpec;

/**
 * Object mother for a testable DuckDB connection spec that provides access to the database.
 */
public class DuckdbConnectionSpecObjectMother {

    /**
     * Creates a default connection spec to DuckDB that works on files placed in memory.
     * @return Connection spec to a DuckDB.
     */
    public static ConnectionSpec createForInMemory() {
        ConnectionSpec connectionSpec = new ConnectionSpec()
        {{
			setProviderType(ProviderType.duckdb);
			setDuckdb(new DuckdbParametersSpec()
            {{
                setReadMode(DuckdbReadMode.in_memory);
            }});
        }};

        return connectionSpec;
    }

    /**
     * Creates a default connection spec to DuckDB.
     * @return Connection spec to a DuckDB.
     */
    public static ConnectionSpec createForFiles(DuckdbSourceFilesType duckdbSourceFilesType) {
        ConnectionSpec connectionSpec = new ConnectionSpec()
        {{
            setProviderType(ProviderType.duckdb);
            setDuckdb(new DuckdbParametersSpec()
            {{
                setReadMode(DuckdbReadMode.files);
                setSourceFilesType(duckdbSourceFilesType);
            }});
        }};

        return connectionSpec;
    }

    /**
     * Creates a default connection spec to DuckDB with s3 setup.
     * @return Connection spec to a DuckDB.
     */
    public static ConnectionSpec createForFilesOnS3(DuckdbSourceFilesType duckdbSourceFilesType) {
        ConnectionSpec connectionSpec = new ConnectionSpec()
        {{
            setProviderType(ProviderType.duckdb);
            setDuckdb(new DuckdbParametersSpec()
            {{
                setReadMode(DuckdbReadMode.files);
                setSourceFilesType(duckdbSourceFilesType);
                setSecretsType(DuckdbSecretsType.s3);
                setUser("aws_example_key_id");
                setPassword("aws_example_secret");
                setRegion("eu-central-1");
            }});
        }};

        return connectionSpec;
    }

    /**
     * Returns the default schema used for a testable DuckDB database. Tables are created in this schema.
     * @return Schema name.
     */
    public static String getSchemaName() {
        return "main";
    }

}
