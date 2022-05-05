/*
 * Copyright © 2021 DQO.ai (support@dqo.ai)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ai.dqo.metadata.sources;

import ai.dqo.BaseTest;
import ai.dqo.core.filesystem.virtual.FileNameSanitizer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PhysicalTableNameTests extends BaseTest {
    @Test
    void toBaseFileName_whenSafeSchemaAndTableGiven_thenReturnCorrectNameWithoutEncoding() {
        PhysicalTableName sut = new PhysicalTableName("schema1", "table1");
        Assertions.assertEquals("schema1.table1", sut.toBaseFileName());
    }

    @Test
    void toBaseFileName_whenUnsafeSchemaAndTableGiven_thenReturnCorrectNameWithEncoding() {
        PhysicalTableName sut = new PhysicalTableName("schema1<", "table1>");
        Assertions.assertEquals("schema1%3C.table1%3E", sut.toBaseFileName());
    }

    @Test
    void toBaseFileName_whenMissingSchema_thenReturnDefaultSchemaName() {
        PhysicalTableName sut = new PhysicalTableName("", "table1");
        Assertions.assertEquals("default.table1", sut.toBaseFileName());
    }

    @Test
    void toBaseFileName_whenMissingTableName_thenReturnDefaultSchemaName() {
        PhysicalTableName sut = new PhysicalTableName("schema1", null);
        Assertions.assertEquals("schema1.default", sut.toBaseFileName());
    }

    @Test
    void fromBaseFileName_whenSimpleNameWithDot_thenParses() {
        PhysicalTableName physicalTableName = PhysicalTableName.fromBaseFileName("abc.def");
        Assertions.assertNotNull(physicalTableName);
        Assertions.assertEquals("abc", physicalTableName.getSchemaName());
        Assertions.assertEquals("def", physicalTableName.getTableName());
    }

    @Test
    void fromBaseFileName_whenNameWithDotThatRequiresEncoding_thenParses() {
        String encodedSchema = FileNameSanitizer.encodeForFileSystem("/");
        String encodedTable = FileNameSanitizer.encodeForFileSystem("<");
        PhysicalTableName physicalTableName = PhysicalTableName.fromBaseFileName(encodedSchema + "." + encodedTable);
        Assertions.assertNotNull(physicalTableName);
        Assertions.assertEquals("/", physicalTableName.getSchemaName());
        Assertions.assertEquals("<", physicalTableName.getTableName());
    }

    @Test
    void fromBaseFileName_whenNameStartingWithDot_thenParsesAsWildcardSchemaAndGivenTable() {
        String encodedTable = FileNameSanitizer.encodeForFileSystem("<");
        PhysicalTableName physicalTableName = PhysicalTableName.fromBaseFileName("." + encodedTable);
        Assertions.assertNotNull(physicalTableName);
        Assertions.assertEquals("*", physicalTableName.getSchemaName());
        Assertions.assertEquals("<", physicalTableName.getTableName());
    }

    @Test
    void fromBaseFileName_whenNameEndingWithDot_thenParsesAsGivenSchemaAndWildcardTable() {
        String encodedSchema = FileNameSanitizer.encodeForFileSystem("/");
        PhysicalTableName physicalTableName = PhysicalTableName.fromBaseFileName(encodedSchema + ".");
        Assertions.assertNotNull(physicalTableName);
        Assertions.assertEquals("/", physicalTableName.getSchemaName());
        Assertions.assertEquals("*", physicalTableName.getTableName());
    }

    @Test
    void fromBaseFileName_whenNameHasNoDots_thenParsesAsWildcardSchemaAndGivenTable() {
        String encodedTable = FileNameSanitizer.encodeForFileSystem("<");
        PhysicalTableName physicalTableName = PhysicalTableName.fromBaseFileName(encodedTable);
        Assertions.assertNotNull(physicalTableName);
        Assertions.assertEquals("*", physicalTableName.getSchemaName());
        Assertions.assertEquals("<", physicalTableName.getTableName());
    }

    @Test
    void fromSchemaTableFilter_whenSimpleNameWithDot_thenParses() {
        PhysicalTableName physicalTableName = PhysicalTableName.fromSchemaTableFilter("abc.def");
        Assertions.assertNotNull(physicalTableName);
        Assertions.assertEquals("abc", physicalTableName.getSchemaName());
        Assertions.assertEquals("def", physicalTableName.getTableName());
    }

    @Test
    void fromSchemaTableFilter_whenNameWithDotAndWildcards_thenParses() {
        PhysicalTableName physicalTableName = PhysicalTableName.fromSchemaTableFilter("s*.t*");
        Assertions.assertNotNull(physicalTableName);
        Assertions.assertEquals("s*", physicalTableName.getSchemaName());
        Assertions.assertEquals("t*", physicalTableName.getTableName());
    }

    @Test
    void fromSchemaTableFilter_whenNameStartingWithDot_thenParsesAsWildcardSchemaAndGivenTable() {
        PhysicalTableName physicalTableName = PhysicalTableName.fromSchemaTableFilter(".tab*");
        Assertions.assertNotNull(physicalTableName);
        Assertions.assertEquals("*", physicalTableName.getSchemaName());
        Assertions.assertEquals("tab*", physicalTableName.getTableName());
    }

    @Test
    void fromSchemaTableFilter_whenNameEndingWithDot_thenParsesAsGivenSchemaAndWildcardTable() {
        PhysicalTableName physicalTableName = PhysicalTableName.fromSchemaTableFilter("s*.");
        Assertions.assertNotNull(physicalTableName);
        Assertions.assertEquals("s*", physicalTableName.getSchemaName());
        Assertions.assertEquals("*", physicalTableName.getTableName());
    }

    @Test
    void fromSchemaTableFilter_whenNameHasNoDots_thenParsesAsWildcardSchemaAndGivenTable() {
        PhysicalTableName physicalTableName = PhysicalTableName.fromSchemaTableFilter("tab*");
        Assertions.assertNotNull(physicalTableName);
        Assertions.assertEquals("*", physicalTableName.getSchemaName());
        Assertions.assertEquals("tab*", physicalTableName.getTableName());
    }

    @Test
    void fromSchemaTableFilter_whenSpecialDatabaseCharactersExist_thenRemoveThem() {
        PhysicalTableName physicalTableName = PhysicalTableName.fromSchemaTableFilter("[test].'test'");
        Assertions.assertNotNull(physicalTableName);
        Assertions.assertEquals("test", physicalTableName.getSchemaName());
        Assertions.assertEquals("test", physicalTableName.getTableName());
    }

    @Test
    void fromSchemaTableFilter_whenSpecialDatabaseCharactersExistButThereAreIncompatible_thenLeaveThem() {
        PhysicalTableName physicalTableName = PhysicalTableName.fromSchemaTableFilter("[test'.'test'");
        Assertions.assertNotNull(physicalTableName);
        Assertions.assertEquals("[test'", physicalTableName.getSchemaName());
        Assertions.assertEquals("test", physicalTableName.getTableName());
    }

    @Test
    void isSearchPattern_whenSimpleName_thenFalse() {
        Assertions.assertFalse(new PhysicalTableName("schema1", "table1").isSearchPattern());
    }

    @Test
    void isSearchPattern_whenWildcardSchema_thenTrue() {
        Assertions.assertTrue(new PhysicalTableName("schema*", "table1").isSearchPattern());
    }

    @Test
    void isSearchPattern_whenWildcardTable_thenTrue() {
        Assertions.assertTrue(new PhysicalTableName("schema1", "table*").isSearchPattern());
    }

    @Test
    void matchPattern_whenPatternDifferentOnSchema_thenFalse() {
        Assertions.assertFalse(new PhysicalTableName("schema", "tab").matchPattern(new PhysicalTableName("schemaother", "tab") ));
    }

    @Test
    void matchPattern_whenPatternDifferentOnTable_thenFalse() {
        Assertions.assertFalse(new PhysicalTableName("schema", "tab").matchPattern(new PhysicalTableName("schema", "tabother") ));
    }

    @Test
    void matchPattern_whenPatternMatchExactly_thenTrue() {
        Assertions.assertTrue(new PhysicalTableName("schema", "tab").matchPattern(new PhysicalTableName("schema", "tab") ));
    }

    @Test
    void matchPattern_whenPatternMatchByTemplate_thenTrue() {
        Assertions.assertTrue(new PhysicalTableName("schema", "tab").matchPattern(new PhysicalTableName("sch*", "tab") ));
        Assertions.assertTrue(new PhysicalTableName("schema", "tab").matchPattern(new PhysicalTableName("sch*", "ta*") ));
        Assertions.assertTrue(new PhysicalTableName("schema", "tab").matchPattern(new PhysicalTableName("*", "ta*") ));
        Assertions.assertTrue(new PhysicalTableName("schema", "tab").matchPattern(new PhysicalTableName("schema", "*") ));
    }

    @Test
    void toString_whenSchemaAndTableGiven_thenCreatesNameWithoutAnyEncodingOrQuoting() {
        Assertions.assertEquals("schema.table 1", new PhysicalTableName("schema", "table 1").toString());
    }
}
