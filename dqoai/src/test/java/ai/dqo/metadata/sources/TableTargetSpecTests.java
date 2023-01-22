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
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.LinkedHashMap;

@SpringBootTest
public class TableTargetSpecTests extends BaseTest {
    private TableTargetSpec sut;

    @BeforeEach
    void setUp() {
		this.sut = new TableTargetSpec();
    }

    @Test
    void isDirty_whenTableNameSet_thenIsDirtyIsTrue() {
		this.sut.setTableName("test");
        Assertions.assertEquals("test", this.sut.getTableName());
        Assertions.assertTrue(this.sut.isDirty());
    }

    @Test
    void isDirty_whenSameTableNameStringAsCurrentSet_thenIsDirtyIsTru() {
		this.sut.setTableName("test");
        Assertions.assertTrue(this.sut.isDirty());
		this.sut.clearDirty(true);
        Assertions.assertFalse(this.sut.isDirty());
		this.sut.setTableName("test");
        Assertions.assertFalse(this.sut.isDirty());
    }

    @Test
    void isDirty_whenSchemaNameSet_thenIsDirtyIsTrue() {
		this.sut.setSchemaName("test");
        Assertions.assertEquals("test", this.sut.getSchemaName());
        Assertions.assertTrue(this.sut.isDirty());
    }

    @Test
    void isDirty_whenSameSchemaNameStringAsCurrentSet_thenIsDirtyIsTru() {
		this.sut.setSchemaName("test");
        Assertions.assertTrue(this.sut.isDirty());
		this.sut.clearDirty(true);
        Assertions.assertFalse(this.sut.isDirty());
		this.sut.setSchemaName("test");
        Assertions.assertFalse(this.sut.isDirty());
    }

    @Test
    void isDirty_whenPropertiesSet_thenIsDirtyIsTrue() {
        LinkedHashMap hashMap = new LinkedHashMap<String, String>();
        hashMap.put("test", "test");
		this.sut.setProperties(hashMap);
        Assertions.assertEquals(hashMap, this.sut.getProperties());
        Assertions.assertTrue(this.sut.isDirty());
    }

    @Test
    void isDirty_whenSamePropertiesObjectAsCurrentSet_thenIsDirtyIsTru() {
        LinkedHashMap hashMap = new LinkedHashMap<String, String>();
        hashMap.put("test", "test");
		this.sut.setProperties(hashMap);
        Assertions.assertTrue(this.sut.isDirty());
		this.sut.clearDirty(true);
        Assertions.assertFalse(this.sut.isDirty());
		this.sut.setProperties(hashMap);
        Assertions.assertFalse(this.sut.isDirty());
    }

    @Test
    void copyFrom_whenPhysicalTableNameGive_thenSetsTableNameAndSchemaName() {
		this.sut.copyFrom(new PhysicalTableName("schemaname", "tabname"));
        Assertions.assertEquals("schemaname", this.sut.getSchemaName());
        Assertions.assertEquals("tabname", this.sut.getTableName());
    }

    @Test
    void toPhysicalTableName_whenCalled_thenReturnsPhysicalTableName() {
		this.sut.setSchemaName("schema1");
		this.sut.setTableName("tab1");

        PhysicalTableName physicalTableName = this.sut.toPhysicalTableName();

        Assertions.assertNotNull(physicalTableName);
        Assertions.assertEquals("schema1", physicalTableName.getSchemaName());
        Assertions.assertEquals("tab1", physicalTableName.getTableName());
    }
}
