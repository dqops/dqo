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

import com.dqops.metadata.fields.DisplayHint;
import com.dqops.metadata.fields.ParameterDataType;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

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
    private ObjectDataType objectDataType;
    private ParameterizedType genericDataType;
    private DisplayHint displayHint;
    private Map<String, EnumValueInfo> enumValuesByName;
    private Method getterMethod;
    private Method setterMethod;
    private Constructor<?> constructor;
    private Object defaultValue;
    private boolean isDirectField;
    private String[] sampleValues;

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
     * Returns the specific data type of the object field used when rendering it for display.
     * @return Field type (object type). Null if not of object type.
     */
    public ObjectDataType getObjectDataType() {
        return objectDataType;
    }

    /**
     * Sets the data type of the object field to be used when rendering it for display.
     * @param objectDataType Field type (object type). Null if not of object type.
     */
    public void setObjectDataType(ObjectDataType objectDataType) {
        this.objectDataType = objectDataType;
    }

    /**
     * Returns the data type that is wrapped by the generic class.
     * @return Data type wrapped by the generic class.
     */
    public ParameterizedType getGenericDataType() {
        return genericDataType;
    }

    /**
     * Sets the data type that is wrapped by the generic class that this field represents.
     * @param genericDataType Data type wrapped by the generic class.
     */
    public void setGenericDataType(ParameterizedType genericDataType) {
        this.genericDataType = genericDataType;
    }

    /**
     * Returns an optional display hint.
     * @return Optional display hint.
     */
    public DisplayHint getDisplayHint() {
        return displayHint;
    }

    /**
     * Sets an optional display hint.
     * @param displayHint Display hint.
     */
    public void setDisplayHint(DisplayHint displayHint) {
        this.displayHint = displayHint;
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
     * Returns the parameterless constructor that can create a new instance of the object.
     * The constructor is not used for simple fields (int, boolean, etc.).
     * @return Parameterless constructor.
     */
    public Constructor<?> getConstructor() {
        return constructor;
    }

    /**
     * Set a reference to the parameterless constructor.
     * @param constructor Constructor reference.
     */
    public void setConstructor(Constructor<?> constructor) {
        this.constructor = constructor;
    }

    /**
     * Returns the default value of the object. It is used to detect if the field value is not default and must be serialized.
     * @return The default value of the field.
     */
    public Object getDefaultValue() {
        return defaultValue;
    }

    /**
     * Sets the default value of the field that will be compared to detect that the field value was customized.
     * @param defaultValue Default value to store for comparison.
     */
    public void setDefaultValue(Object defaultValue) {
        this.defaultValue = defaultValue;
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
     * Returns an array of sample values for a field.
     * @return Array of sample values for a field.
     */
    public String[] getSampleValues() {
        return sampleValues;
    }

    /**
     * Sets an array of sample values for a field.
     * @param sampleValues Sample values.
     */
    public void setSampleValues(String[] sampleValues) {
        this.sampleValues = sampleValues;
    }

    /**
     * Retrieves the raw field value. Enum fields are not converted to strings.
     * @param parentObject Parent object to read the field using the getter method.
     * @return Field value.
     */
    public Object getRawFieldValue(Object parentObject) {
        if (this.getterMethod == null) {
            throw new NullPointerException("Field " + this.classFieldName + " on class " + parentObject.getClass().getName() + " has no getter.");
        }

        try {
            Object result = this.getterMethod.invoke(parentObject);
            if (result == null) {
                return null;
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
     * Retrieves the field value. Enum fields are converted to the name (string).
     * @param parentObject Parent object to read the field using the getter method.
     * @return Field value.
     */
    public Object getFieldValue(Object parentObject) {
        if (this.getterMethod == null) {
            throw new NullPointerException("Field " + this.classFieldName + " on class " + parentObject.getClass().getName() + " has no getter.");
        }

        try {
            Object result = this.getterMethod.invoke(parentObject);
            if (result == null) {
                return null;
            }

            if (this.clazz.isEnum()) {
                // convert to a java name
                String javaEnumFieldName = ((Enum<?>) result).name();
                return this.getEnumValuesByName().get(javaEnumFieldName).getYamlName();
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
     * Retrieves a field value. If the field value is null then creates and returns a new instance of an object.
     * It is used to retrieve a new instance of a complex object like a parameter specification.
     * @param parentObject Parent object.
     * @return Field value (when not null) or a new instance of the object that is of the type of the field.
     */
    public Object getFieldValueOrNewObject(Object parentObject) {
        Object fieldValue = this.getFieldValue(parentObject);
        if (fieldValue != null) {
            return fieldValue;
        }

        try {
            Object newInstance = this.constructor.newInstance();
            return newInstance;
        } catch (InstantiationException e) {
            throw new FieldAccessException("Instantiation exception", e);
        } catch (InvocationTargetException e) {
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
                final Object expectedValue = value;
                Optional<EnumValueInfo> enumInfo = this.enumValuesByName.values()
                        .stream()
                        .filter(e -> Objects.equals(e.getYamlName(), expectedValue))
                        .findFirst();

                value = enumInfo.<Object>map(EnumValueInfo::getEnumInstance)
                        .orElse(null); // when the enum value is unknown, we are storing null
            }

            if (this.setterMethod == null) {
                throw new NullPointerException("Field " + this.classFieldName + " on class " + targetObject.getClass().getName() + " has no setter.");
            }

            this.setterMethod.invoke(targetObject, value);
        }
        catch (InvocationTargetException e) {
            throw new FieldAccessException("Invocation exception", e);
        } catch (IllegalAccessException e) {
            throw new FieldAccessException("Illegal access exception", e);
        }
    }

    /**
     * Sets the raw field value. Does not convert enum names to enum values.
     * @param value Value to store in the field.
     * @param targetObject Target object.
     */
    public void setRawFieldValue(Object value, Object targetObject) {
        try {
            if (this.setterMethod == null) {
                throw new NullPointerException("Field " + this.classFieldName + " on class " + targetObject.getClass().getName() + " has no setter.");
            }

            this.setterMethod.invoke(targetObject, value);
        }
        catch (IllegalArgumentException e) {
            throw new FieldAccessException("Cannot store value of type: " + (value != null ? value.getClass().getCanonicalName() : "null") +
                    " on class " + targetObject.getClass().getCanonicalName() + " for field " + this.classFieldName + ", exception: " + e.getMessage(), e);
        }
        catch (InvocationTargetException e) {
            throw new FieldAccessException("Invocation exception", e);
        } catch (IllegalAccessException e) {
            throw new FieldAccessException("Illegal access exception", e);
        }
    }

    @Override
    public String toString() {
        return "FieldInfo{" +
                "classFieldName='" + classFieldName + '\'' +
                ", clazz=" + clazz +
                ", dataType=" + dataType +
                '}';
    }
}
