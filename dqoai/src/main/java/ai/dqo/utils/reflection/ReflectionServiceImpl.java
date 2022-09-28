package ai.dqo.utils.reflection;

import ai.dqo.metadata.fields.ControlType;
import ai.dqo.metadata.fields.DisplayName;
import ai.dqo.metadata.fields.ParameterDataType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Field reflection service that returns cached objects used to access fields on a class.
 */
@Component
public class ReflectionServiceImpl implements ReflectionService {
    private Map<Class<?>, ClassInfo> reflectedClasses = new HashMap<>();

    /**
     * Returns a cached reflection info for a given class type.
     * @param targetClass Target class type to introspect for fields.
     * @return Class info with a list of fields that will be serialized to YAML.
     */
    @Override
    public synchronized ClassInfo getClassInfoForClass(Class<?> targetClass) {
        ClassInfo classInfo = reflectedClasses.get(targetClass);
        if (classInfo == null) {
            classInfo = reflectClass(targetClass);
            reflectedClasses.put(targetClass, classInfo);
        }

        return classInfo;
    }

    /**
     * Introspects a given class using reflection. Finds all fields on the class.
     * @param targetClass Target class.
     * @return Reflection info of all fields that will be used for JSON serialization.
     */
    public ClassInfo reflectClass(Class<?> targetClass) {
        ClassInfo classInfo = new ClassInfo(targetClass);
        Field[] fields = targetClass.getFields();
        for (Field field : fields) {
            if (field.isSynthetic()) {
                continue;
            }

            if (field.isAnnotationPresent(JsonIgnore.class)) {
                // skipping this field
                continue;
            }

            Class<?> fieldType = field.getType();
            String fieldName = field.getName();

            String helpText = field.isAnnotationPresent(JsonPropertyDescription.class) ?
                    field.getAnnotation(JsonPropertyDescription.class).value() : null;
            String displayName = field.isAnnotationPresent(DisplayName.class) ?
                    field.getAnnotation(DisplayName.class).value() : null;
            ParameterDataType parameterDataType = field.isAnnotationPresent(ControlType.class) ?
                    field.getAnnotation(ControlType.class).value() : null;

            PropertyNamingStrategies.SnakeCaseStrategy snakeCaseStrategy = new PropertyNamingStrategies.SnakeCaseStrategy();
            String yamlFieldName = snakeCaseStrategy.translate(fieldName);

            FieldInfo fieldInfo = new FieldInfo() {{
               setClazz(fieldType);
               setClassFieldName(fieldName);
               setYamlFieldName(yamlFieldName);
               setDisplayName(displayName);
               setHelpText(helpText);
               setDataType(parameterDataType);
            }};

            String getterMethodPrefix = fieldType == Boolean.class ? "is" : "get";
            String getterSetterSuffix = Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
            String getterMethodName = getterMethodPrefix + getterSetterSuffix;
            String setterMethodName = "set" + getterSetterSuffix;

            try {
                Method getterMethod = targetClass.getMethod(getterMethodName);
                fieldInfo.setGetterMethod(getterMethod);
            }
            catch (NoSuchMethodException nex) {
                // ignoring
            }

            try {
                Method setterMethod = targetClass.getMethod(setterMethodName);
                fieldInfo.setSetterMethod(setterMethod);
            }
            catch (NoSuchMethodException nex) {
                // ignoring
            }

            classInfo.getFields().add(fieldInfo);
        }

        return classInfo;
    }
}
