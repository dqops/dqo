package com.dqops.connectors.duckdb;

import com.dqops.BaseTest;
import com.dqops.metadata.sources.ConnectionSpec;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DuckdbQueriesProviderTest extends BaseTest {

    @Test
    void provideCreateSecretQuery_forS3_createsQuery() {
        ConnectionSpec connectionSpec = DuckdbConnectionSpecObjectMother.createForFilesOnS3(DuckdbFilesFormatType.csv);
        String scope = "s3://path";
        String secretName = "s_" + DuckdbSecretManager.calculateSecretHex(scope);
        String createSecretQuery = DuckdbQueriesProvider.provideCreateSecretQuery(connectionSpec, secretName, scope);

        Assertions.assertTrue(createSecretQuery.contains("CREATE SECRET " + secretName));

        org.assertj.core.api.Assertions.assertThat(createSecretQuery)
                        .matches(
                        "CREATE SECRET s_[0-9a-f]* \\(\\s*" +
                        "TYPE S3,\\s*" +
                        "KEY_ID 'aws_example_key_id',\\s*" +
                        "SECRET 'aws_example_secret',\\s*" +
                        "REGION 'eu-central-1',\\s*" +
                        "SCOPE 's3://path'\\s*" +
                        "\\);");
    }
}