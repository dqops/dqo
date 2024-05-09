package com.dqops.connectors.duckdb;

import com.dqops.BaseTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class DuckdbParametersSpecTest extends BaseTest {

    @Test
    void getScopes_whenWildcardsInPath_cutsWildcards() {

        DuckdbParametersSpec spec = new DuckdbParametersSpec();

        spec.setDirectories(Map.of(
                "files1", "s3://path1/**/*.json",
                "files2", "s3://path2/*.csv",
                "files3", "s3://path3/absolute/file.csv"
        ));

        List<String> scopes = spec.getScopes();

        assertThat(scopes)
                .hasSize(3)
                .containsExactlyInAnyOrder(
                        "s3://path1/",
                        "s3://path2/",
                        "s3://path3/absolute/file.csv"
                );

    }

}