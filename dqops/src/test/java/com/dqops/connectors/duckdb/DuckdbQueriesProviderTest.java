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
        ConnectionSpec connectionSpec = DuckdbConnectionSpecObjectMother.createForFilesOnS3(DuckdbFilesFormatType.csv);
        HashCode hashCode = DuckdbSecretManager.calculateHash64(connectionSpec);
        String createSecretQuery = DuckdbQueriesProvider.provideCreateSecretQuery(connectionSpec, hashCode);

        Assertions.assertTrue(createSecretQuery.contains("CREATE SECRET secret_" + hashCode));

        org.assertj.core.api.Assertions.assertThat(createSecretQuery)
                        .matches(
                        "CREATE SECRET secret_[0-9a-f]{16} \\(\\s*" +
                        "TYPE S3,\\s*" +
                        "KEY_ID 'aws_example_key_id',\\s*" +
                        "SECRET 'aws_example_secret',\\s*" +
                        "REGION 'eu-central-1'\\s*" +
                        "\\);");
    }
}