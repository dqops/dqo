package ai.dqo.utils.reflection;

import ai.dqo.metadata.fields.ParameterDataType;

import java.lang.reflect.Method;

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
    private Method getterMethod;
    private Method setterMethod;

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
}
