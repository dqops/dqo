package ai.dqo.utils.reflection;

import ai.dqo.BaseTest;
import ai.dqo.metadata.fields.ParameterDataType;
import ai.dqo.metadata.fields.ParameterDefinitionSpec;
import ai.dqo.rules.RuleTimeWindowSettingsSpec;
import ai.dqo.rules.averages.PercentMovingAverageRuleParametersSpec;
import ai.dqo.rules.averages.PercentMovingAverageRuleThresholdsSpec;
import ai.dqo.sensors.column.validity.BuiltInDateFormats;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.Field;
import java.util.Map;

@SpringBootTest
public class ReflectionServiceImplTests extends BaseTest {
    private ReflectionServiceImpl sut;

    /**
     * Called before each test.
     * This method should be overridden in derived super classes (test classes), but remember to add {@link BeforeEach} annotation in a derived test class. JUnit5 demands it.
     *
     * @throws Throwable
     */
    @Override
    @BeforeEach
    protected void setUp() throws Throwable {
        super.setUp();
        this.sut = new ReflectionServiceImpl();
    }

    @Test
    void getClassInfoForClass_whenCalledForTheFirstTime_thenReturnsClassInfo() {
        ClassInfo classInfo = this.sut.getClassInfoForClass(ParameterDefinitionSpec.class);
        Assertions.assertNotNull(classInfo);
        Assertions.assertEquals(6, classInfo.getFields().size());
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
        Assertions.assertEquals(6, classInfo.getFields().size());
    }

    @Test
    void createEnumValue_whenEnumFieldGivenWithPropertyName_thenReturnsEnumInfo() {
        EnumValueInfo enumValue = this.sut.createEnumValue(BuiltInDateFormats.ISO8601);
        Assertions.assertNotNull(enumValue);
        Assertions.assertSame(BuiltInDateFormats.ISO8601, enumValue.getEnumInstance());
        Assertions.assertEquals("ISO8601", enumValue.getJavaName());
        Assertions.assertEquals("YYYY-MM-DD", enumValue.getYamlName());
        Assertions.assertEquals("YYYY-MM-DD", enumValue.getDisplayName());
    }

    @Test
    void makeFieldInfo_whenFieldIsStatic_thenIsNotReturned() throws Exception {
        Field field = ParameterDefinitionSpec.class.getField("FIELDS");
        FieldInfo fieldInfo = this.sut.makeFieldInfo(field);
        Assertions.assertNull(fieldInfo);
    }

    @Test
    void makeFieldInfo_whenSimpleStringFieldWithJsonPropertyTypeAnnotation_thenReturnsFieldInfo() throws Exception {
        Field field = ParameterDefinitionSpec.class.getDeclaredField("fieldName");
        FieldInfo fieldInfo = this.sut.makeFieldInfo(field);
        Assertions.assertNotNull(fieldInfo);
        Assertions.assertSame(field.getType(), fieldInfo.getClazz());
        Assertions.assertEquals(ParameterDataType.string_type, fieldInfo.getDataType());
        Assertions.assertEquals("fieldName", fieldInfo.getClassFieldName());
        Assertions.assertEquals("field_name", fieldInfo.getYamlFieldName());
        Assertions.assertEquals("field_name", fieldInfo.getDisplayName());
        Assertions.assertEquals("Field name that matches the field name (snake_case) used in the YAML specification.", fieldInfo.getHelpText());
        Assertions.assertNotNull(fieldInfo.getGetterMethod());
        Assertions.assertNotNull(fieldInfo.getSetterMethod());
    }

    @Test
    void makeFieldInfo_whenSimpleBooleanField_thenReturnsFieldInfo() throws Exception {
        Field field = ParameterDefinitionSpec.class.getDeclaredField("required");
        FieldInfo fieldInfo = this.sut.makeFieldInfo(field);
        Assertions.assertNotNull(fieldInfo);
        Assertions.assertSame(field.getType(), fieldInfo.getClazz());
        Assertions.assertEquals(ParameterDataType.boolean_type, fieldInfo.getDataType());
        Assertions.assertEquals("required", fieldInfo.getClassFieldName());
        Assertions.assertEquals("required", fieldInfo.getYamlFieldName());
        Assertions.assertEquals("required", fieldInfo.getDisplayName());
        Assertions.assertEquals("True when the value for the parameter must be provided.", fieldInfo.getHelpText());
        Assertions.assertNotNull(fieldInfo.getGetterMethod());
        Assertions.assertNotNull(fieldInfo.getSetterMethod());
    }

    @Test
    void makeFieldInfo_whenSimpleIntField_thenReturnsFieldInfo() throws Exception {
        Field field = RuleTimeWindowSettingsSpec.class.getDeclaredField("predictionTimeWindow");
        FieldInfo fieldInfo = this.sut.makeFieldInfo(field);
        Assertions.assertNotNull(fieldInfo);
        Assertions.assertSame(field.getType(), fieldInfo.getClazz());
        Assertions.assertEquals(ParameterDataType.integer_type, fieldInfo.getDataType());
        Assertions.assertEquals("predictionTimeWindow", fieldInfo.getClassFieldName());
        Assertions.assertEquals("prediction_time_window", fieldInfo.getYamlFieldName());
        Assertions.assertEquals("prediction_time_window", fieldInfo.getDisplayName());
        Assertions.assertEquals("Number of historic time periods to look back for results. Returns results from previous time periods before the sensor reading timestamp to be used in a rule. Time periods are used in rules that need historic data to calculate an average to detect anomalies. e.g. when the sensor is configured to use a 'day' time period, the rule will receive results from the time_periods number of days before the time period in the sensor reading. The default is 14 (days).", fieldInfo.getHelpText());
        Assertions.assertNotNull(fieldInfo.getGetterMethod());
        Assertions.assertNotNull(fieldInfo.getSetterMethod());
    }

    @Test
    void makeFieldInfo_whenSimpleDoubleField_thenReturnsFieldInfo() throws Exception {
        Field field = PercentMovingAverageRuleParametersSpec.class.getDeclaredField("maxPercentAbove");
        FieldInfo fieldInfo = this.sut.makeFieldInfo(field);
        Assertions.assertNotNull(fieldInfo);
        Assertions.assertSame(field.getType(), fieldInfo.getClazz());
        Assertions.assertEquals(ParameterDataType.double_type, fieldInfo.getDataType());
        Assertions.assertEquals("maxPercentAbove", fieldInfo.getClassFieldName());
        Assertions.assertEquals("max_percent_above", fieldInfo.getYamlFieldName());
        Assertions.assertEquals("max_percent_above", fieldInfo.getDisplayName());
        Assertions.assertEquals("Maximum percent (e.q. 3%) that the current sensor reading could be above a moving average within the time window. Set the time window at the threshold level for all severity levels (low, medium, high) at once. The default is a 14 time periods (days, etc.) time window, but at least 7 readings must exist to run the calculation.", fieldInfo.getHelpText());
        Assertions.assertNotNull(fieldInfo.getGetterMethod());
        Assertions.assertNotNull(fieldInfo.getSetterMethod());
    }

    @Test
    void makeFieldInfo_whenFieldIsListOfStrings_thenReturnsFieldInfo() throws Exception {
        Field field = ParameterDefinitionSpec.class.getDeclaredField("allowedValues");
        FieldInfo fieldInfo = this.sut.makeFieldInfo(field);
        Assertions.assertNotNull(fieldInfo);
        Assertions.assertSame(field.getType(), fieldInfo.getClazz());
        Assertions.assertEquals(ParameterDataType.string_list_type, fieldInfo.getDataType());
        Assertions.assertEquals("allowedValues", fieldInfo.getClassFieldName());
        Assertions.assertEquals("allowed_values", fieldInfo.getYamlFieldName());
        Assertions.assertEquals("allowed_values", fieldInfo.getDisplayName());
        Assertions.assertEquals("List of allowed values for a field that is of an enum type.", fieldInfo.getHelpText());
        Assertions.assertNotNull(fieldInfo.getGetterMethod());
        Assertions.assertNotNull(fieldInfo.getSetterMethod());
    }

    @Test
    void makeFieldInfo_whenFieldIsJavaEnum_thenReturnsFieldInfoWithEnumValues() throws Exception {
        Field field = ParameterDefinitionSpec.class.getDeclaredField("dataType");
        FieldInfo fieldInfo = this.sut.makeFieldInfo(field);
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
        Assertions.assertEquals(9, enumValuesByYamlName.size());
        Assertions.assertSame(ParameterDataType.string_type, enumValuesByYamlName.get("string_type").getEnumInstance());
    }

    @Test
    void makeFieldInfo_whenFieldIsOtherObject_thenReturnsFieldInfoAsObjectType() throws Exception {
        Field field = PercentMovingAverageRuleThresholdsSpec.class.getDeclaredField("high");
        FieldInfo fieldInfo = this.sut.makeFieldInfo(field);
        Assertions.assertNotNull(fieldInfo);
        Assertions.assertSame(field.getType(), fieldInfo.getClazz());
        Assertions.assertEquals(ParameterDataType.object_type, fieldInfo.getDataType());
        Assertions.assertEquals("high", fieldInfo.getClassFieldName());
        Assertions.assertEquals("high", fieldInfo.getYamlFieldName());
        Assertions.assertEquals("high", fieldInfo.getDisplayName());
        Assertions.assertEquals("Rule threshold for a high severity (3) alert.", fieldInfo.getHelpText());
        Assertions.assertNotNull(fieldInfo.getGetterMethod());
        Assertions.assertNotNull(fieldInfo.getSetterMethod());
    }
}
