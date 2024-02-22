package com.dqops.connectors.duckdb;

import com.dqops.BaseTest;
import com.dqops.metadata.sources.ConnectionSpec;
import com.google.common.hash.HashCode;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DuckdbQueriesProviderTest extends BaseTest {

    @Test
    void provideCreateSecretQuery_forS3_createsQuery() {
        ConnectionSpec connectionSpec = DuckdbConnectionSpecObjectMother.createForFilesOnS3(DuckdbSourceFilesType.csv);
        HashCode hashCode = DuckdbSecretManager.calculateHash64(connectionSpec);
        String createSecretQuery = DuckdbQueriesProvider.provideCreateSecretQuery(connectionSpec, hashCode);

        Assertions.assertEquals("""
                        CREATE SECRET secret_bd95a74421bc7192 (
                            TYPE S3,
                            KEY_ID 'aws_example_key_id',
                            SECRET 'aws_example_secret',
                            REGION 'eu-central-1'
                        );""",
                createSecretQuery);
    }
}