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
package com.dqops.utils.reflection;

import com.dqops.BaseTest;
import com.dqops.checks.AbstractCheckCategorySpec;
import com.dqops.checks.CheckTarget;
import com.dqops.checks.CheckTimeScale;
import com.dqops.checks.CheckType;
import com.dqops.checks.table.checkspecs.volume.TableRowCountAnomalyDifferencingCheckSpec;
import com.dqops.checks.table.checkspecs.volume.TableRowCountCheckSpec;
import com.dqops.connectors.DataTypeCategory;
import com.dqops.data.checkresults.normalization.CheckResultsNormalizedResult;
import com.dqops.metadata.fields.ParameterDataType;
import com.dqops.metadata.fields.ParameterDefinitionSpec;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMap;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.dqops.rules.AbstractRuleParametersSpec;
import com.dqops.rules.RuleTimeWindowSettingsSpec;
import com.dqops.rules.averages.PercentMovingAverageRuleParametersSpec;
import com.dqops.rules.comparison.MinCountRule1ParametersSpec;
import com.dqops.sensors.column.acceptedvalues.ColumnNumericExpectedNumbersInUseCountSensorParametersSpec;
import com.dqops.sensors.column.text.ColumnTextTextLengthInRangePercentSensorParametersSpec;
import com.dqops.sensors.column.text.TextBuiltInDateFormats;
import com.dqops.sensors.column.datetime.ColumnDateInRangePercentSensorParametersSpec;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Objects;

@SpringBootTest
public class ReflectionServiceImplTests extends BaseTest {
    private ReflectionServiceImpl sut;

    @BeforeEach
    void setUp() {
        this.sut = new ReflectionServiceImpl();
    }

    @Test
    void getClassInfoForClass_whenCalledForTheFirstTime_thenReturnsClassInfo() {
        ClassInfo classInfo = this.sut.getClassInfoForClass(ParameterDefinitionSpec.class);
        Assertions.assertNotNull(classInfo);
        Assertions.assertEquals(8, classInfo.getFields().size());
    }

    @Test
    void getClassInfoForClass_whenCalledForTheSecondTime_thenReturnsTheSameClassInfo() {
        ClassInfo classInfo = this.sut.getClassInfoForClass(ParameterDefinitionSpec.class);
        Assertions.assertNotNull(classInfo);
        Assertions.assertSame(classInfo, this.sut.getClassInfoForClass(ParameterDefinitionSpec.class));
    }

    @Test
    void reflectClass_whenDirectDescendantOfAbstractSpec_thenReturnsFields() {
        ClassInfo classInfo = this.sut.reflectClass(ParameterDefinitionSpec.class);
        Assertions.assertNotNull(classInfo);
        Assertions.assertEquals(8, classInfo.getFields().size());
    }

    @Test
    void reflectClass_whenReflectingCheckSpecification_thenReturnsAllBaseFields() {
        ClassInfo classInfo = this.sut.reflectClass(TableRowCountCheckSpec.class);
        Assertions.assertNotNull(classInfo);
        Assertions.assertEquals(12, classInfo.getFields().size());
        Assertions.assertTrue(classInfo.getFields().stream().anyMatch(f -> Objects.equals(f.getClassFieldName(), "comments")));
        Assertions.assertTrue(classInfo.getFields().stream().anyMatch(f -> Objects.equals(f.getClassFieldName(), "disabled")));
        Assertions.assertTrue(classInfo.getFields().stream().anyMatch(f -> Objects.equals(f.getClassFieldName(), "excludeFromKpi")));
        Assertions.assertTrue(classInfo.getFields().stream().anyMatch(f -> Objects.equals(f.getClassFieldName(), "includeInSla")));
        Assertions.assertTrue(classInfo.getFields().stream().anyMatch(f -> Objects.equals(f.getClassFieldName(), "qualityDimension")));
        Assertions.assertTrue(classInfo.getFields().stream().anyMatch(f -> Objects.equals(f.getClassFieldName(), "displayName")));
        Assertions.assertTrue(classInfo.getFields().stream().anyMatch(f -> Objects.equals(f.getClassFieldName(), "dataGrouping")));
    }

    @Test
    void reflectClass_whenReflectingCheckSpecification_thenReturnsAlsoInheritedFieldsFields() {
        ClassInfo classInfo = this.sut.reflectClass(TableRowCountCheckSpec.class);
        Assertions.assertNotNull(classInfo);
        Assertions.assertEquals(12, classInfo.getFields().size());
        Assertions.assertTrue(classInfo.getFields().stream().anyMatch(f -> Objects.equals(f.getClassFieldName(), "parameters")));
        Assertions.assertTrue(classInfo.getFields().stream().anyMatch(f -> Objects.equals(f.getClassFieldName(), "error")));
        Assertions.assertTrue(classInfo.getFields().stream().anyMatch(f -> Objects.equals(f.getClassFieldName(), "warning")));
        Assertions.assertTrue(classInfo.getFields().stream().anyMatch(f -> Objects.equals(f.getClassFieldName(), "fatal")));
        Assertions.assertTrue(classInfo.getFields().stream().anyMatch(f -> Objects.equals(f.getClassFieldName(), "disabled")));
    }

    @Test
    void reflectClass_whenReflectingSensorParametersSpecification_thenReturnsAllRequestedFields() {
        ClassInfo classInfo = this.sut.reflectClass(ColumnTextTextLengthInRangePercentSensorParametersSpec.class);
        Assertions.assertNotNull(classInfo);
        Assertions.assertEquals(3, classInfo.getFields().size());
        Assertions.assertTrue(classInfo.getFields().stream().anyMatch(f -> Objects.equals(f.getClassFieldName(), "minLength")));
        Assertions.assertTrue(classInfo.getFields().stream().anyMatch(f -> Objects.equals(f.getClassFieldName(), "maxLength")));
        Assertions.assertTrue(classInfo.getFields().stream().anyMatch(f -> Objects.equals(f.getClassFieldName(), "filter")));
    }
    @Test
    void reflectClass_whenReflectingRuleParametersClass_thenReturnsAllRequestedFields() {
        ClassInfo classInfo = this.sut.reflectClass(MinCountRule1ParametersSpec.class);
        Assertions.assertNotNull(classInfo);
        Assertions.assertEquals(1, classInfo.getFields().size());
        Assertions.assertTrue(classInfo.getFields().stream().anyMatch(f -> Objects.equals(f.getClassFieldName(), "minCount")));
    }

    @Test
    void createEnumValue_whenEnumFieldGivenWithPropertyName_thenReturnsEnumInfo() {
        EnumValueInfo enumValue = this.sut.createEnumValue(TextBuiltInDateFormats.ISO8601);
        Assertions.assertNotNull(enumValue);
        Assertions.assertSame(TextBuiltInDateFormats.ISO8601, enumValue.getEnumInstance());
        Assertions.assertEquals("ISO8601", enumValue.getJavaName());
        Assertions.assertEquals("YYYY-MM-DD", enumValue.getYamlName());
        Assertions.assertEquals("YYYY-MM-DD", enumValue.getDisplayName());
    }

    @Test
    void makeFieldInfo_whenFieldIsStatic_thenIsNotReturned() throws Exception {
        Field field = ParameterDefinitionSpec.class.getField("FIELDS");
        FieldInfo fieldInfo = this.sut.makeFieldInfo(field.getDeclaringClass(), field);
        Assertions.assertNull(fieldInfo);
    }

    @Test
    void makeFieldInfo_whenSimpleStringFieldWithJsonPropertyTypeAnnotation_thenReturnsFieldInfo() throws Exception {
        Field field = ParameterDefinitionSpec.class.getDeclaredField("fieldName");
        FieldInfo fieldInfo = this.sut.makeFieldInfo(field.getDeclaringClass(), field);
        Assertions.assertNotNull(fieldInfo);
        Assertions.assertSame(field.getType(), fieldInfo.getClazz());
        Assertions.assertEquals(ParameterDataType.string_type, fieldInfo.getDataType());
        Assertions.assertEquals("fieldName", fieldInfo.getClassFieldName());
        Assertions.assertEquals("field_name", fieldInfo.getYamlFieldName());
        Assertions.assertEquals("field_name", fieldInfo.getDisplayName());
        Assertions.assertEquals("Field name that matches the field name (snake_case) used in the YAML specification.", fieldInfo.getHelpText());
        Assertions.assertNotNull(fieldInfo.getGetterMethod());
        Assertions.assertNotNull(fieldInfo.getSetterMethod());
        Assertions.assertEquals(null, fieldInfo.getDefaultValue());
        Assertions.assertEquals(null, fieldInfo.getConstructor());
    }

    @Test
    void makeFieldInfo_whenSimpleBooleanField_thenReturnsFieldInfo() throws Exception {
        Field field = ParameterDefinitionSpec.class.getDeclaredField("required");
        FieldInfo fieldInfo = this.sut.makeFieldInfo(field.getDeclaringClass(), field);
        Assertions.assertNotNull(fieldInfo);
        Assertions.assertSame(field.getType(), fieldInfo.getClazz());
        Assertions.assertEquals(ParameterDataType.boolean_type, fieldInfo.getDataType());
        Assertions.assertEquals("required", fieldInfo.getClassFieldName());
        Assertions.assertEquals("required", fieldInfo.getYamlFieldName());
        Assertions.assertEquals("required", fieldInfo.getDisplayName());
        Assertions.assertEquals("True when the value for the parameter must be provided.", fieldInfo.getHelpText());
        Assertions.assertNotNull(fieldInfo.getGetterMethod());
        Assertions.assertNotNull(fieldInfo.getSetterMethod());
        Assertions.assertEquals(false, fieldInfo.getDefaultValue());
        Assertions.assertEquals(null, fieldInfo.getConstructor());
    }

    @Test
    void makeFieldInfo_whenSimpleIntField_thenReturnsFieldInfo() throws Exception {
        Field field = RuleTimeWindowSettingsSpec.class.getDeclaredField("predictionTimeWindow");
        FieldInfo fieldInfo = this.sut.makeFieldInfo(field.getDeclaringClass(), field);
        Assertions.assertNotNull(fieldInfo);
        Assertions.assertSame(field.getType(), fieldInfo.getClazz());
        Assertions.assertEquals(ParameterDataType.integer_type, fieldInfo.getDataType());
        Assertions.assertEquals("predictionTimeWindow", fieldInfo.getClassFieldName());
        Assertions.assertEquals("prediction_time_window", fieldInfo.getYamlFieldName());
        Assertions.assertEquals("prediction_time_window", fieldInfo.getDisplayName());
        Assertions.assertEquals("Number of historic time periods to look back for results. Returns results from previous time periods before the sensor readout timestamp to be used in a rule. Time periods are used in rules that need historic data to calculate an average to detect anomalies. e.g. when the sensor is configured to use a 'day' time period, the rule will receive results from the time_periods number of days before the time period in the sensor readout. The default is 14 (days).", fieldInfo.getHelpText());
        Assertions.assertNotNull(fieldInfo.getGetterMethod());
        Assertions.assertNotNull(fieldInfo.getSetterMethod());
        Assertions.assertEquals(0, fieldInfo.getDefaultValue());
        Assertions.assertEquals(null, fieldInfo.getConstructor());
    }

    @Test
    void makeFieldInfo_whenSimpleDoubleObjectField_thenReturnsFieldInfo() throws Exception {
        Field field = PercentMovingAverageRuleParametersSpec.class.getDeclaredField("maxPercentAbove");
        FieldInfo fieldInfo = this.sut.makeFieldInfo(field.getDeclaringClass(), field);
        Assertions.assertNotNull(fieldInfo);
        Assertions.assertSame(field.getType(), fieldInfo.getClazz());
        Assertions.assertEquals(ParameterDataType.double_type, fieldInfo.getDataType());
        Assertions.assertEquals("maxPercentAbove", fieldInfo.getClassFieldName());
        Assertions.assertEquals("max_percent_above", fieldInfo.getYamlFieldName());
        Assertions.assertEquals("max_percent_above", fieldInfo.getDisplayName());
        Assertions.assertEquals("The maximum percentage (e.g., 3%) by which the current sensor readout can be above a moving average within the time window. Set the time window at the threshold level for all severity levels (warning, error, fatal) at once. The default is a 14 time periods (days, etc.) time window, but at least 7 readouts must exist to run the calculation.", fieldInfo.getHelpText());
        Assertions.assertNotNull(fieldInfo.getGetterMethod());
        Assertions.assertNotNull(fieldInfo.getSetterMethod());
        Assertions.assertEquals(null, fieldInfo.getDefaultValue()); // the field is nullable
        Assertions.assertEquals(null, fieldInfo.getConstructor());
    }

    @Test
    void makeFieldInfo_whenSimpleDoubleValueField_thenReturnsFieldInfo() throws Exception {
        Field field = TestableRuleParametersSpec.class.getDeclaredField("minPercent");
        FieldInfo fieldInfo = this.sut.makeFieldInfo(field.getDeclaringClass(), field);
        Assertions.assertNotNull(fieldInfo);
        Assertions.assertSame(field.getType(), fieldInfo.getClazz());
        Assertions.assertEquals(ParameterDataType.double_type, fieldInfo.getDataType());
        Assertions.assertEquals("minPercent", fieldInfo.getClassFieldName());
        Assertions.assertEquals("min_percent", fieldInfo.getYamlFieldName());
        Assertions.assertEquals("min_percent", fieldInfo.getDisplayName());
        Assertions.assertEquals("Minimum accepted value for the actual_value returned by the sensor (inclusive).", fieldInfo.getHelpText());
        Assertions.assertNotNull(fieldInfo.getGetterMethod());
        Assertions.assertNotNull(fieldInfo.getSetterMethod());
        Assertions.assertEquals(0.0, fieldInfo.getDefaultValue()); // the field is not nullable
        Assertions.assertEquals(null, fieldInfo.getConstructor());
    }

    @EqualsAndHashCode(callSuper = true)
    public static class TestableRuleParametersSpec extends AbstractRuleParametersSpec {
        private static final ChildHierarchyNodeFieldMapImpl<TestableRuleParametersSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractRuleParametersSpec.FIELDS) {
            {
            }
        };

        /**
         * Default constructor, the minimum accepted value is 0.
         */
        public TestableRuleParametersSpec() {
        }

        /**
         * Creates a rule with a given value.
         * @param minPercent Minimum accepted value.
         */
        public TestableRuleParametersSpec(double minPercent) {
            this.minPercent = minPercent;
        }

        @JsonPropertyDescription("Minimum accepted value for the actual_value returned by the sensor (inclusive).")
        private double minPercent = 100.0;

        /**
         * Minimum value for a data quality check readout, for example a minimum row count.
         * @return Minimum value for a data quality check readout.
         */
        public double getMinPercent() {
            return minPercent;
        }

        /**
         * Changes the minimum value (threshold) for a data quality readout.
         * @param minPercent Minimum value.
         */
        public void setMinPercent(double minPercent) {
            this.setDirtyIf(!Objects.equals(this.minPercent, minPercent));
            this.minPercent = minPercent;
        }

        /**
         * Returns the child map on the spec class with all fields.
         *
         * @return Return the field map.
         */
        @Override
        protected ChildHierarchyNodeFieldMap getChildMap() {
            return FIELDS;
        }

        /**
         * Returns a rule definition name. It is a name of a python module (file) without the ".py" extension. Rule names are related to the "rules" folder in DQO_HOME.
         *
         * @return Rule definition name (python module name without .py extension).
         */
        @Override
        public String getRuleDefinitionName() {
            return "comparison/min_percent";
        }

        /**
         * Decreases the rule severity by changing the parameters.
         * NOTE: this method is allowed to do nothing if changing the rule severity is not possible
         *
         * @param checkResultsSingleCheck Historical results for the check to decide how much to change.
         */
        @Override
        public void decreaseRuleSensitivity(CheckResultsNormalizedResult checkResultsSingleCheck) {

        }
    }

    @Test
    void makeFieldInfo_whenFieldIsListOfStrings_thenReturnsFieldInfo() throws Exception {
        Field field = ParameterDefinitionSpec.class.getDeclaredField("allowedValues");
        FieldInfo fieldInfo = this.sut.makeFieldInfo(field.getDeclaringClass(), field);
        Assertions.assertNotNull(fieldInfo);
        Assertions.assertSame(field.getType(), fieldInfo.getClazz());
        Assertions.assertEquals(ParameterDataType.string_list_type, fieldInfo.getDataType());
        Assertions.assertEquals("allowedValues", fieldInfo.getClassFieldName());
        Assertions.assertEquals("allowed_values", fieldInfo.getYamlFieldName());
        Assertions.assertEquals("allowed_values", fieldInfo.getDisplayName());
        Assertions.assertEquals("List of allowed values for a field that is of an enum type.", fieldInfo.getHelpText());
        Assertions.assertNotNull(fieldInfo.getGetterMethod());
        Assertions.assertNotNull(fieldInfo.getSetterMethod());
        Assertions.assertNotNull(fieldInfo.getConstructor());
    }

    @Test
    void makeFieldInfo_whenFieldIsListOfLongs_thenReturnsFieldInfo() throws Exception {
        Field field = ColumnNumericExpectedNumbersInUseCountSensorParametersSpec.class.getDeclaredField("expectedValues");
        FieldInfo fieldInfo = this.sut.makeFieldInfo(field.getDeclaringClass(), field);
        Assertions.assertNotNull(fieldInfo);
        Assertions.assertSame(field.getType(), fieldInfo.getClazz());
        Assertions.assertEquals(ParameterDataType.integer_list_type, fieldInfo.getDataType());
        Assertions.assertEquals("expectedValues", fieldInfo.getClassFieldName());
        Assertions.assertEquals("expected_values", fieldInfo.getYamlFieldName());
        Assertions.assertEquals("expected_values", fieldInfo.getDisplayName());
        Assertions.assertNotNull(fieldInfo.getHelpText());
        Assertions.assertNotNull(fieldInfo.getGetterMethod());
        Assertions.assertNotNull(fieldInfo.getSetterMethod());
        Assertions.assertNotNull(fieldInfo.getConstructor());
    }

    @Test
    void makeFieldInfo_whenFieldIsJavaEnum_thenReturnsFieldInfoWithEnumValues() throws Exception {
        Field field = ParameterDefinitionSpec.class.getDeclaredField("dataType");
        FieldInfo fieldInfo = this.sut.makeFieldInfo(field.getDeclaringClass(), field);
        Assertions.assertNotNull(fieldInfo);
        Assertions.assertSame(field.getType(), fieldInfo.getClazz());
        Assertions.assertEquals(ParameterDataType.enum_type, fieldInfo.getDataType());
        Assertions.assertEquals("dataType", fieldInfo.getClassFieldName());
        Assertions.assertEquals("data_type", fieldInfo.getYamlFieldName());
        Assertions.assertEquals("data_type", fieldInfo.getDisplayName());
        Assertions.assertEquals("Parameter data type.", fieldInfo.getHelpText());
        Assertions.assertNotNull(fieldInfo.getGetterMethod());
        Assertions.assertNotNull(fieldInfo.getSetterMethod());
        Map<String, EnumValueInfo> enumValuesByYamlName = fieldInfo.getEnumValuesByName();
        Assertions.assertNotNull(enumValuesByYamlName);
        Assertions.assertEquals(12, enumValuesByYamlName.size());
        Assertions.assertSame(ParameterDataType.string_type, enumValuesByYamlName.get("string").getEnumInstance());
        Assertions.assertEquals(null, fieldInfo.getDefaultValue());
        Assertions.assertEquals(null, fieldInfo.getConstructor());
    }

    @Test
    void makeFieldInfo_whenFieldIsLocalDate_thenReturnsFieldInfoWithLocalDateType() throws Exception {
        Field field = ColumnDateInRangePercentSensorParametersSpec.class.getDeclaredField("minDate");
        FieldInfo fieldInfo = this.sut.makeFieldInfo(field.getDeclaringClass(), field);
        Assertions.assertNotNull(fieldInfo);
        Assertions.assertSame(field.getType(), fieldInfo.getClazz());
        Assertions.assertEquals(ParameterDataType.date_type, fieldInfo.getDataType());
        Assertions.assertEquals("minDate", fieldInfo.getClassFieldName());
        Assertions.assertEquals("min_date", fieldInfo.getYamlFieldName());
        Assertions.assertEquals("min_date", fieldInfo.getDisplayName());
        Assertions.assertEquals("The earliest accepted date.", fieldInfo.getHelpText());
        Assertions.assertNotNull(fieldInfo.getGetterMethod());
        Assertions.assertNotNull(fieldInfo.getSetterMethod());
        Assertions.assertEquals(null, fieldInfo.getDefaultValue());
        Assertions.assertEquals(null, fieldInfo.getConstructor());
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    @EqualsAndHashCode(callSuper = true)
    public static class CustomJsonPropertyChecksSpec extends AbstractCheckCategorySpec {
        public static final ChildHierarchyNodeFieldMapImpl<CustomJsonPropertyChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
            {
                put("customSerialization_option（笑）", o -> o.ordinarilyNamedCheck);
            }
        };

        @JsonProperty("customSerialization_option（笑）")
        @JsonPropertyDescription("Some description.")
        private TableRowCountAnomalyDifferencingCheckSpec ordinarilyNamedCheck;


        public TableRowCountAnomalyDifferencingCheckSpec getOrdinarilyNamedCheck() {
            return ordinarilyNamedCheck;
        }

        public void setOrdinarilyNamedCheck(TableRowCountAnomalyDifferencingCheckSpec ordinarilyNamedCheck) {
            this.ordinarilyNamedCheck = ordinarilyNamedCheck;
        }



        /**
         * Returns the child map on the spec class with all fields.
         *
         * @return Return the field map.
         */
        @Override
        protected ChildHierarchyNodeFieldMap getChildMap() {
            return FIELDS;
        }

        /**
         * Gets the check target appropriate for all checks in this category.
         *
         * @return Corresponding check target.
         */
        @Override
        //@JsonIgnore
        public CheckTarget getCheckTarget() {
            return null;
        }

        /**
         * Gets the check type appropriate for all checks in this category.
         *
         * @return Corresponding check type.
         */
        @Override
        //@JsonIgnore
        public CheckType getCheckType() {
            return null;
        }

        /**
         * Gets the check timescale appropriate for all checks in this category.
         *
         * @return Corresponding check timescale.
         */
        @Override
        //@JsonIgnore
        public CheckTimeScale getCheckTimeScale() {
            return null;
        }

        /**
         * Returns an array of supported data type categories. DQOps uses this list when activating default data quality checks.
         *
         * @return Array of supported data type categories.
         */
        @Override
        public DataTypeCategory[] getSupportedDataTypeCategories() {
            return DataTypeCategory.ANY;
        }
    }

    @Test
    void makeFieldInfo_whenFieldHasJsonProperty_thenReturnsFieldInfoWithCorrespondingYamlName() throws Exception {
        Field field = CustomJsonPropertyChecksSpec.class.getDeclaredField("ordinarilyNamedCheck");
        FieldInfo fieldInfo = this.sut.makeFieldInfo(field.getDeclaringClass(), field);
        Assertions.assertNotNull(fieldInfo);
        Assertions.assertSame(field.getType(), fieldInfo.getClazz());
        Assertions.assertEquals("ordinarilyNamedCheck", fieldInfo.getClassFieldName());
        Assertions.assertEquals("customSerialization_option（笑）", fieldInfo.getYamlFieldName());
        Assertions.assertEquals("customSerialization_option（笑）", fieldInfo.getDisplayName());
        Assertions.assertEquals("Some description.", fieldInfo.getHelpText());
        Assertions.assertNotNull(fieldInfo.getGetterMethod());
        Assertions.assertNotNull(fieldInfo.getSetterMethod());
        Assertions.assertEquals(null, fieldInfo.getDefaultValue());
    }
}
