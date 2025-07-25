/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.connectors.trino;

import com.dqops.BaseTest;
import com.dqops.connectors.ProviderType;
import com.dqops.connectors.jdbc.AbstractJdbcSourceConnection;
import com.dqops.metadata.sources.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TrinoSourceConnectionTest extends BaseTest {

    @Test
    void generateCreateTableSqlStatementForAthena_engineTypeIsAthena_generatesValidSqlStatement() {

        TrinoSourceConnection trinoSourceConnection = TrinoSourceConnectionObjectMother.getTrinoSourceConnection();
        trinoSourceConnection.setConnectionSpec(new ConnectionSpec(ProviderType.trino));

        TableSpec tableSpec = new TableSpec();
        tableSpec.setPhysicalTableName(new PhysicalTableName("my_schema", "my_table"));
        tableSpec.setColumns(new ColumnSpecMap(){{
            put("id", new ColumnSpec(){{ setTypeSnapshot(new ColumnTypeSnapshotSpec("INTEGER")); }});
            put("name", new ColumnSpec(){{ setTypeSnapshot(new ColumnTypeSnapshotSpec("STRING")); }});
        }});

        String sql = trinoSourceConnection.generateCreateTableSqlStatementForAthena(tableSpec);

        Assertions.assertEquals("""
                CREATE EXTERNAL TABLE IF NOT EXISTS my_schema.my_table (
                    id INTEGER,
                    name STRING
                )
                ROW FORMAT DELIMITED
                FIELDS TERMINATED BY ','
                STORED AS TEXTFILE
                LOCATION 's3://dqops-athena-test/my_table'
                TBLPROPERTIES ('skip.header.line.count'='1');
                """, sql);

    }
}
