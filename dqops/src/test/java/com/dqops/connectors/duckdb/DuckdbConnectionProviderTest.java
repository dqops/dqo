package com.dqops.connectors.duckdb;

import com.dqops.BaseTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

@SpringBootTest
class DuckdbConnectionProviderTest extends BaseTest {

    private DuckdbConnectionProvider sut;


    @BeforeEach
    void setUp() {
        sut = DuckdbConnectionProviderObjectMother.getProvider();
    }

    @Test
    void parseDirectoriesString_whenOneValidMapping_parsesIt() {
        String inputString = "schema=/directory";

        Map<String, String> mapping = sut.parseDirectoriesString(inputString);

        Assertions.assertEquals(1, mapping.size());
        Assertions.assertTrue(mapping.containsKey("schema"));
        Assertions.assertEquals("/directory", mapping.get("schema"));
    }

    @Test
    void parseDirectoriesString_whenMultiMapping_parsesIt() {
        String inputString = "schema1=/directory1,schema2=/directory2";

        Map<String, String> mapping = sut.parseDirectoriesString(inputString);

        Assertions.assertEquals(2, mapping.size());

        Assertions.assertTrue(mapping.containsKey("schema1"));
        Assertions.assertEquals("/directory1", mapping.get("schema1"));

        Assertions.assertTrue(mapping.containsKey("schema2"));
        Assertions.assertEquals("/directory2", mapping.get("schema2"));
    }

}