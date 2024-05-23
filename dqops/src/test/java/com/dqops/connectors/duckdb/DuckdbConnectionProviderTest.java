package com.dqops.connectors.duckdb;

import com.dqops.BaseTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

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

        assertEquals(1, mapping.size());
        assertTrue(mapping.containsKey("schema"));
        assertEquals("/directory", mapping.get("schema"));
    }

    @Test
    void parseDirectoriesString_whenMultiMapping_parsesIt() {
        String inputString = "schema1=/directory1,schema2=/directory2";

        Map<String, String> mapping = sut.parseDirectoriesString(inputString);

        assertEquals(2, mapping.size());

        assertTrue(mapping.containsKey("schema1"));
        assertEquals("/directory1", mapping.get("schema1"));

        assertTrue(mapping.containsKey("schema2"));
        assertEquals("/directory2", mapping.get("schema2"));
    }

    @Test
    void parseDirectoriesString_whenTwoPathsInSingleSchema_parsesIt() {
        String inputString = "schema1=/directory1,schema1=/directory2";


        Exception exception = assertThrows(RuntimeException.class, () -> {
            sut.parseDirectoriesString(inputString);
        });

        String expectedMessage = "Schema to path is one-to-one mapping. You cannot add a second path: /directory2 to the schema: schema1";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

}