package com.dqops.connectors.duckdb;

import com.dqops.metadata.id.HierarchyId;
import com.dqops.metadata.sources.ConnectionSpec;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DuckdbQueriesProviderTest {

    @Test
    void provideCreateSecretQuery_forS3_createsQuery() {
        ConnectionSpec connectionSpec = DuckdbConnectionSpecObjectMother.createForFilesOnS3(DuckdbSourceFilesType.csv);
        connectionSpec.setHierarchyId(new HierarchyId("connection_name_example", "nothing")); // the last but one name is a connection name

        String createSecretQuery = DuckdbQueriesProvider.provideCreateSecretQuery(connectionSpec);

        Assertions.assertEquals("""
                        CREATE SECRET connection_name_example (
                            TYPE S3,
                            KEY_ID 'aws_example_key_id',
                            SECRET 'aws_example_secret',
                            REGION 'eu-central-1'
                        );""",
                createSecretQuery);
    }
}