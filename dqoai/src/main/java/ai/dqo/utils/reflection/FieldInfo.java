package ai.dqo.utils.reflection;

import ai.dqo.metadata.fields.ParameterDataType;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * Describes a single field that is a parameter on a sensor parameter class or a rule.
 */
public class FieldInfo {
    private String classFieldName;
    private String yamlFieldName;
    private String displayName;
    private String helpText;
    private Class<?> clazz;
    private ParameterDataType dataType;
    private Map<String, EnumValueInfo> enumValuesByName;
    private Method getterMethod;
    private Method setterMethod;
    private boolean isDirectField;

    /**
     * Returns the field name used on the class.
     * @return Field name.
     */
    public String getClassFieldName() {
        return classFieldName;
    }

    /**
     * Sets the field name used on the class.
     * @param classFieldName Field name.
     */
    public void setClassFieldName(String classFieldName) {
        this.classFieldName = classFieldName;
    }

    /**
     * Sets the snake_case version of the yaml field name that is used to serialize the field to YAML.
     * @return YAML compatible field name.
     */
    public String getYamlFieldName() {
        return yamlFieldName;
    }

    /**
     * Sets the YAML compatible snake_case field name.
     * @param yamlFieldName YAML compatible field name.
     */
    public void setYamlFieldName(String yamlFieldName) {
        this.yamlFieldName = yamlFieldName;
    }

    /**
     * Returns the display name that should be used to display an editing control in the check edit UI.
     * @return Display name (label) of the field in UI.
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Sets the display name to show in UI.
     * @param displayName Display name.
     */
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    /**
     * Returns the help text of the field to be shown in UI as a hint.
     * @return Help text retrieved from the json property description.
     */
    public String getHelpText() {
        return helpText;
    }

    /**
     * Sets the help text for the field.
     * @param helpText Help text.
     */
    public void setHelpText(String helpText) {
        this.helpText = helpText;
    }

    /**
     * Returns the data type of the field as a Java class.
     * @return Java class that is the data type of the field.
     */
    public Class<?> getClazz() {
        return clazz;
    }

    /**
     * Sets the field data type used in Java.
     * @param clazz Field type as a Java class.
     */
    public void setClazz(Class<?> clazz) {
        this.clazz = clazz;
    }

    /**
     * Returns the data type of the field as a UI friendly enumeration.
     * @return Field type (control type).
     */
    public ParameterDataType getDataType() {
        return dataType;
    }

    /**
     * Sets the field type (display control type) used to edit the field in the UI.
     * @param dataType Field control type.
     */
    public void setDataType(ParameterDataType dataType) {
        this.dataType = dataType;
    }

    /**
     * Stores the dictionary of possible enum values, keyed by the name. Used when the field type is an enum.
     * @return Dictionary of enum values possible for an enum value.
     */
    public Map<String, EnumValueInfo> getEnumValuesByName() {
        return enumValuesByName;
    }

    /**
     * Stores a dictionary of possible enum values supported by the field.
     * @param enumValuesByName Map of enum values.
     */
    public void setEnumValuesByName(Map<String, EnumValueInfo> enumValuesByName) {
        this.enumValuesByName = enumValuesByName;
    }

    /**
     * Returns the declared getter method to read the field value using a declared getter.
     * @return Returns the getter method.
     */
    public Method getGetterMethod() {
        return getterMethod;
    }

    /**
     * Stores the reflection object of the getter method.
     * @param getterMethod Getter method.
     */
    public void setGetterMethod(Method getterMethod) {
        this.getterMethod = getterMethod;
    }

    /**
     * Returns the declared setter method that should be called to store the value of the field.
     * @return Setter method to call.
     */
    public Method getSetterMethod() {
        return setterMethod;
    }

    /**
     * Sets a reference to the reflected setter method that updates the field value.
     * @param setterMethod Setter method reflection info.
     */
    public void setSetterMethod(Method setterMethod) {
        this.setterMethod = setterMethod;
    }

    /**
     * Returns true if the field is directly defined on the superclass. False when the field is inherited from a super class (base class).
     * @return True when directly defined on the class, false if defined in a super class.
     */
    public boolean isDirectField() {
        return isDirectField;
    }

    /**
     * Sets the direct flag to identify fields defined on the target (top most in the class hierarchy) class, or false when the field was inherited.
     * @param directField True - direct, false - inherited.
     */
    public void setDirectField(boolean directField) {
        isDirectField = directField;
    }

    /**
     * Retrieves the field value. Enum fields are converted to the name (string).
     * @param parentObject Parent object to read the field using the getter method.
     * @return Field value.
     */
    public Object getFieldValue(Object parentObject) {
        try {
            Object result = this.getterMethod.invoke(parentObject);
            if (result == null) {
                return null;
            }

            if (this.clazz.isEnum()) {
                // convert to a java name
                return ((Enum<?>)result).name();
            }

            return result;
        }
        catch (InvocationTargetException e) {
            throw new FieldAccessException("Invocation exception", e);
        } catch (IllegalAccessException e) {
            throw new FieldAccessException("Illegal access exception", e);
        }
    }

    /**
     * Sets the field value. Converts enum names to enum values.
     * @param value Value to store in the field.
     * @param targetObject Target object.
     */
    public void setFieldValue(Object value, Object targetObject) {
        try {
            if (value != null && this.clazz.isEnum()) {
                EnumValueInfo enumValueInfo = this.enumValuesByName.get(value);
                value = enumValueInfo.getEnumInstance(); // convert the name to the actual enum instance
            }

            this.setterMethod.invoke(targetObject, value);
        }
        catch (InvocationTargetException e) {
            throw new FieldAccessException("Invocation exception", e);
        } catch (IllegalAccessException e) {
            throw new FieldAccessException("Illegal access exception", e);
        }
    }
}
