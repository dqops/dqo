/*
 * Copyright © 2021 DQOps (support@dqops.com)
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

package com.dqops.connectors.duckdb.schema;

import com.dqops.BaseTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DuckDBDataTypeParserImplTests extends BaseTest {
    private DuckDBDataTypeParserImpl sut;

    @BeforeEach
    void setUp() {
        this.sut = new DuckDBDataTypeParserImpl();
    }

    @Test
    void parseFieldType_whenInteger_thenParses() {
        DuckDBField field = this.sut.parseFieldType("INTEGER", "f1");
        Assertions.assertEquals("f1", field.getFieldName());
        Assertions.assertEquals("INTEGER", field.getTypeName());
        Assertions.assertTrue(field.isNullable());
        Assertions.assertFalse(field.isStruct());
        Assertions.assertFalse(field.isArray());
    }

    @Test
    void parseFieldType_whenIntegerArrayWithoutLength_thenParsesAndSetsArrayFlag() {
        DuckDBField field = this.sut.parseFieldType("INTEGER[]", "f1");
        Assertions.assertEquals("f1", field.getFieldName());
        Assertions.assertEquals("INTEGER", field.getTypeName());
        Assertions.assertTrue(field.isNullable());
        Assertions.assertFalse(field.isStruct());
        Assertions.assertTrue(field.isArray());
    }

    @Test
    void parseFieldType_whenIntegerArrayWithLength_thenParsesAndSetsArrayFlag() {
        DuckDBField field = this.sut.parseFieldType("INTEGER[5]", "f1");
        Assertions.assertEquals("f1", field.getFieldName());
        Assertions.assertEquals("INTEGER", field.getTypeName());
        Assertions.assertTrue(field.isNullable());
        Assertions.assertFalse(field.isStruct());
        Assertions.assertTrue(field.isArray());
    }

    @Test
    void parseFieldType_whenIntegerNotNUll_thenParsesAndSetsNotNull() {
        DuckDBField field = this.sut.parseFieldType("INTEGER NOT NULL", "f1");
        Assertions.assertEquals("f1", field.getFieldName());
        Assertions.assertEquals("INTEGER", field.getTypeName());
        Assertions.assertFalse(field.isNullable());
        Assertions.assertFalse(field.isStruct());
        Assertions.assertFalse(field.isArray());
    }

    @Test
    void parseFieldType_whenTimestampNotNUll_thenParsesAndSetsNotNull() {
        DuckDBField field = this.sut.parseFieldType("TIMESTAMP NOT NULL", "f1");
        Assertions.assertEquals("f1", field.getFieldName());
        Assertions.assertEquals("TIMESTAMP", field.getTypeName());
        Assertions.assertFalse(field.isNullable());
        Assertions.assertFalse(field.isStruct());
        Assertions.assertFalse(field.isArray());
    }

    @Test
    void parseFieldType_whenTimestampWithTimeZoneNotNUll_thenParsesAndSetsNotNull() {
        DuckDBField field = this.sut.parseFieldType("TIMESTAMP WITH TIME ZONE NOT NULL", "f1");
        Assertions.assertEquals("f1", field.getFieldName());
        Assertions.assertEquals("TIMESTAMP WITH TIME ZONE", field.getTypeName());
        Assertions.assertFalse(field.isNullable());
        Assertions.assertFalse(field.isStruct());
        Assertions.assertFalse(field.isArray());
    }

    @Test
    void parseFieldType_whenVarcharWithSize_thenParsesAlsoLength() {
        DuckDBField field = this.sut.parseFieldType("VARCHAR(40)", "f1");
        Assertions.assertEquals("f1", field.getFieldName());
        Assertions.assertEquals("VARCHAR", field.getTypeName());
        Assertions.assertEquals(40, field.getLength());
        Assertions.assertTrue(field.isNullable());
        Assertions.assertFalse(field.isStruct());
        Assertions.assertFalse(field.isArray());
    }

    @Test
    void parseFieldType_wheDecimalWithPrecisionAndScale_thenParsesAlsoPrecisionAndScale() {
        DuckDBField field = this.sut.parseFieldType("DECIMAL(10, 3)", "f1");
        Assertions.assertEquals("f1", field.getFieldName());
        Assertions.assertEquals("DECIMAL", field.getTypeName());
        Assertions.assertEquals(null, field.getLength());
        Assertions.assertEquals(10, field.getPrecision());
        Assertions.assertEquals(3, field.getScale());
        Assertions.assertTrue(field.isNullable());
        Assertions.assertFalse(field.isStruct());
        Assertions.assertFalse(field.isArray());
    }

    @Test
    void parseFieldType_whenIntegerPrimaryKey_thenParsesButIgnoresPrimaryKey() {
        DuckDBField field = this.sut.parseFieldType("INTEGER PRIMARY KEY", "f1");
        Assertions.assertEquals("f1", field.getFieldName());
        Assertions.assertEquals("INTEGER", field.getTypeName());
        Assertions.assertTrue(field.isNullable());
        Assertions.assertFalse(field.isStruct());
        Assertions.assertFalse(field.isArray());
    }

    @Test
    void parseFieldType_whenIntegerCheck_thenParsesButIgnoresCheck() {
        DuckDBField field = this.sut.parseFieldType("INTEGER CHECK((1) + (2+4))", "f1");
        Assertions.assertEquals("f1", field.getFieldName());
        Assertions.assertEquals("INTEGER", field.getTypeName());
        Assertions.assertTrue(field.isNullable());
        Assertions.assertFalse(field.isStruct());
        Assertions.assertFalse(field.isArray());
    }

    @Test
    void parseFieldType_whenStructWithOneField_thenParsesThatField() {
        DuckDBField field = this.sut.parseFieldType("STRUCT(f2 INTEGER)", "f1");
        Assertions.assertEquals("f1", field.getFieldName());
        Assertions.assertEquals("STRUCT", field.getTypeName());
        Assertions.assertTrue(field.isNullable());
        Assertions.assertTrue(field.isStruct());
        Assertions.assertFalse(field.isArray());
        Assertions.assertNotNull(field.getNestedFields());
        Assertions.assertEquals(1, field.getNestedFields().size());
        Assertions.assertEquals("f2", field.getNestedFields().get(0).getFieldName());
        Assertions.assertEquals("INTEGER", field.getNestedFields().get(0).getTypeName());
    }

    @Test
    void parseFieldType_whenStructWithTwoFields_thenParsesThatFields() {
        DuckDBField field = this.sut.parseFieldType("STRUCT(f2 INTEGER, f3 VARCHAR)", "f1");
        Assertions.assertEquals("f1", field.getFieldName());
        Assertions.assertEquals("STRUCT", field.getTypeName());
        Assertions.assertTrue(field.isNullable());
        Assertions.assertTrue(field.isStruct());
        Assertions.assertFalse(field.isArray());
        Assertions.assertNotNull(field.getNestedFields());
        Assertions.assertEquals(2, field.getNestedFields().size());
        Assertions.assertEquals("f2", field.getNestedFields().get(0).getFieldName());
        Assertions.assertEquals("INTEGER", field.getNestedFields().get(0).getTypeName());
        Assertions.assertEquals("f3", field.getNestedFields().get(1).getFieldName());
        Assertions.assertEquals("VARCHAR", field.getNestedFields().get(1).getTypeName());
    }

    @Test
    void parseFieldType_whenMapWithIntegerAndVarcharKeyValue_thenParsesThemAsFields() {
        DuckDBField field = this.sut.parseFieldType("MAP(INTEGER, VARCHAR)", "f1");
        Assertions.assertEquals("f1", field.getFieldName());
        Assertions.assertEquals("MAP", field.getTypeName());
        Assertions.assertTrue(field.isNullable());
        Assertions.assertFalse(field.isStruct());
        Assertions.assertTrue(field.isMap());
        Assertions.assertFalse(field.isArray());
        Assertions.assertNotNull(field.getNestedFields());
        Assertions.assertEquals(2, field.getNestedFields().size());
        Assertions.assertEquals("key", field.getNestedFields().get(0).getFieldName());
        Assertions.assertEquals("INTEGER", field.getNestedFields().get(0).getTypeName());
        Assertions.assertEquals("value", field.getNestedFields().get(1).getFieldName());
        Assertions.assertEquals("VARCHAR", field.getNestedFields().get(1).getTypeName());
    }

    @Test
    void parseFieldType_whenStructWithTwoFieldsAndOneFieldIsQuoted_thenParsesThatFields() {
        DuckDBField field = this.sut.parseFieldType("STRUCT(f2 INTEGER, \"f3\" VARCHAR)", "f1");
        Assertions.assertEquals("f1", field.getFieldName());
        Assertions.assertEquals("STRUCT", field.getTypeName());
        Assertions.assertTrue(field.isNullable());
        Assertions.assertTrue(field.isStruct());
        Assertions.assertFalse(field.isArray());
        Assertions.assertNotNull(field.getNestedFields());
        Assertions.assertEquals(2, field.getNestedFields().size());
        Assertions.assertEquals("f2", field.getNestedFields().get(0).getFieldName());
        Assertions.assertEquals("INTEGER", field.getNestedFields().get(0).getTypeName());
        Assertions.assertEquals("f3", field.getNestedFields().get(1).getFieldName());
        Assertions.assertEquals("VARCHAR", field.getNestedFields().get(1).getTypeName());
    }

    @Test
    void parseFieldType_whenStructWithTwoFieldsAndOneFieldHasUnderscore_thenParsesThatFields() {
        DuckDBField field = this.sut.parseFieldType("STRUCT(f2 INTEGER, f_3 VARCHAR)", "f1");
        Assertions.assertEquals("f1", field.getFieldName());
        Assertions.assertEquals("STRUCT", field.getTypeName());
        Assertions.assertTrue(field.isNullable());
        Assertions.assertTrue(field.isStruct());
        Assertions.assertFalse(field.isArray());
        Assertions.assertNotNull(field.getNestedFields());
        Assertions.assertEquals(2, field.getNestedFields().size());
        Assertions.assertEquals("f2", field.getNestedFields().get(0).getFieldName());
        Assertions.assertEquals("INTEGER", field.getNestedFields().get(0).getTypeName());
        Assertions.assertEquals("f_3", field.getNestedFields().get(1).getFieldName());
        Assertions.assertEquals("VARCHAR", field.getNestedFields().get(1).getTypeName());
    }
}
