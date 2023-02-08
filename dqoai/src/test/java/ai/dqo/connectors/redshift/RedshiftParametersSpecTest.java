package ai.dqo.connectors.redshift;

import ai.dqo.connectors.postgresql.PostgresqlParametersSpec;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RedshiftParametersSpecTest {

    private RedshiftParametersSpec sut;

    @BeforeEach
    void setUp() {
        this.sut = new RedshiftParametersSpec();
    }

    @Test
    void isDirty_whenDatabaseSet_thenIsDirtyIsTrue() {
        this.sut.setDatabase("test");
        this.sut.setHost("redshift-cluster-1.cds5vq1bzgx5.us-east-1.redshift.amazonaws.com");
        this.sut.setDatabase("dev");
        this.sut.setPort("5439");

        Assertions.assertEquals("test", this.sut.getDatabase());
        Assertions.assertTrue(this.sut.isDirty());
    }
}
