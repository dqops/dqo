package com.dqops.connectors.duckdb;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DuckdbQueriesProviderTest {

    @Test
    void provideCreateSecretQuery_forS3_createsQuery() {
        DuckdbParametersSpec duckdbParametersSpec = DuckdbConnectionSpecObjectMother
                .createForFilesOnS3(DuckdbSourceFilesType.csv).getDuckdb();

        String createSecretQuery = DuckdbQueriesProvider.provideCreateSecretQuery(duckdbParametersSpec);

        Assertions.assertEquals("""
                        CREATE SECRET (
                            TYPE S3,
                            KEY_ID 'aws_example_key_id',
                            SECRET 'aws_example_secret',
                            REGION 'eu-central-1'
                        );""",
                createSecretQuery);
    }
}