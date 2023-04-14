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
package ai.dqo.utils.reflection;

import ai.dqo.BaseTest;
import ai.dqo.checks.table.profiling.TableProfilingCheckCategoriesSpec;
import ai.dqo.checks.table.profiling.TableProfilingStandardChecksSpec;
import ai.dqo.metadata.fields.ParameterDataType;
import ai.dqo.metadata.fields.ParameterDefinitionSpec;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.Field;

@SpringBootTest
public class FieldInfoTests extends BaseTest {
    private ReflectionServiceImpl reflectionService;

    @BeforeEach
    void setUp() {
        this.reflectionService = new ReflectionServiceImpl();
    }

    @Test
    void getFieldValue_whenStringFieldNull_thenReturnsNull() throws Exception {
        Field field = ParameterDefinitionSpec.class.getDeclaredField("fieldName");
        FieldInfo sut = this.reflectionService.makeFieldInfo(field.getDeclaringClass(), field);

        ParameterDefinitionSpec target = new ParameterDefinitionSpec();
        target.setFieldName(null);

        Object fieldValue = sut.getFieldValue(target);
        Assertions.assertNull(fieldValue);
    }

    @Test
    void getFieldValue_whenStringFieldNotNull_thenReturnsValue() throws Exception {
        Field field = ParameterDefinitionSpec.class.getDeclaredField("fieldName");
        FieldInfo sut = this.reflectionService.makeFieldInfo(field.getDeclaringClass(), field);

        ParameterDefinitionSpec target = new ParameterDefinitionSpec();
        target.setFieldName("some_name");

        Object fieldValue = sut.getFieldValue(target);
        Assertions.assertEquals("some_name", fieldValue);
    }

    @Test
    void getFieldValue_whenEnumFieldNotNull_thenReturnsStringValue() throws Exception {
        Field field = ParameterDefinitionSpec.class.getDeclaredField("dataType");
        FieldInfo sut = this.reflectionService.makeFieldInfo(field.getDeclaringClass(), field);

        ParameterDefinitionSpec target = new ParameterDefinitionSpec();
        target.setDataType(ParameterDataType.double_type);

        Object fieldValue = sut.getFieldValue(target);
        Assertions.assertEquals("double", fieldValue);
    }

    @Test
    void setFieldValue_whenStringFieldNull_thenStoresValue() throws Exception {
        Field field = ParameterDefinitionSpec.class.getDeclaredField("fieldName");
        FieldInfo sut = this.reflectionService.makeFieldInfo(field.getDeclaringClass(), field);

        ParameterDefinitionSpec target = new ParameterDefinitionSpec();
        target.setFieldName("not empty");

        sut.setFieldValue(null, target);
        Assertions.assertNull(target.getFieldName());
    }

    @Test
    void setFieldValue_whenStringFieldNotNull_thenStoresValue() throws Exception {
        Field field = ParameterDefinitionSpec.class.getDeclaredField("fieldName");
        FieldInfo sut = this.reflectionService.makeFieldInfo(field.getDeclaringClass(), field);

        ParameterDefinitionSpec target = new ParameterDefinitionSpec();
        target.setFieldName("not empty");

        sut.setFieldValue("other", target);
        Assertions.assertEquals("other", target.getFieldName());
    }

    @Test
    void setFieldValue_whenEnumFieldNotNull_thenStoresValue() throws Exception {
        Field field = ParameterDefinitionSpec.class.getDeclaredField("dataType");
        FieldInfo sut = this.reflectionService.makeFieldInfo(field.getDeclaringClass(), field);

        ParameterDefinitionSpec target = new ParameterDefinitionSpec();
        target.setDataType(ParameterDataType.double_type);

        sut.setFieldValue("string", target);
        Assertions.assertEquals(ParameterDataType.string_type, target.getDataType());
    }

    @Test
    void getFieldValueOrNewObject_whenFieldValueIsSpecObjectAndIsFilled_thenReturnsExistingValue() throws Exception {
        Field field = TableProfilingCheckCategoriesSpec.class.getDeclaredField("standard");
        FieldInfo sut = this.reflectionService.makeFieldInfo(field.getDeclaringClass(), field);

        TableProfilingCheckCategoriesSpec target = new TableProfilingCheckCategoriesSpec();
        TableProfilingStandardChecksSpec expected = new TableProfilingStandardChecksSpec();
        target.setStandard(expected);

        Object result = sut.getFieldValueOrNewObject(target);
        Assertions.assertNotNull(result);
        Assertions.assertSame(expected, result);
    }

    @Test
    void getFieldValueOrNewObject_whenFieldValueIsSpecObjectAndIsNull_thenCreatesNewObjectThatIsNotStored() throws Exception {
        Field field = TableProfilingCheckCategoriesSpec.class.getDeclaredField("standard");
        FieldInfo sut = this.reflectionService.makeFieldInfo(field.getDeclaringClass(), field);

        TableProfilingCheckCategoriesSpec target = new TableProfilingCheckCategoriesSpec();
        target.setStandard(null);
        TableProfilingStandardChecksSpec result = (TableProfilingStandardChecksSpec)sut.getFieldValueOrNewObject(target);
        Assertions.assertNotNull(result);

        TableProfilingStandardChecksSpec result2 = (TableProfilingStandardChecksSpec)sut.getFieldValueOrNewObject(target);
        Assertions.assertNotNull(result2);
        Assertions.assertNotSame(result, result2);
    }
}
