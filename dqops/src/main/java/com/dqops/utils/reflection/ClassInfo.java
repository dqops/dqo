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

import com.dqops.utils.exceptions.DqoRuntimeException;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Describes all fields used in YAML serialization in a class, their file names, YAML snake_case names, descriptions,
 * expected UI control types.
 */
public class ClassInfo {
    private final Class<?> reflectedClass;
    private final Constructor<?> constructor;
    private final List<FieldInfo> fields = new ArrayList<>();
    private final String metaDescription;

    /**
     * Creates a class reflection info, storing the class type.
     *
     * @param reflectedClass Target class type.
     * @param constructor The default parameterless constructor.
     * @param metaDescription Metadata description of the class to be used for documentation.
     */
    public ClassInfo(Class<?> reflectedClass, Constructor<?> constructor, String metaDescription) {
        this.reflectedClass = reflectedClass;
        this.constructor = constructor;
        this.metaDescription = metaDescription;
    }

    /**
     * Returns the Java class of the reflected class, whose fields are described.
     * @return Java class of the reflected type.
     */
    public Class<?> getReflectedClass() {
        return reflectedClass;
    }

    /**
     * Returns the default parameterless constructor for the class.
     * @return The default parameterless constructor for the class.
     */
    public Constructor<?> getConstructor() {
        return constructor;
    }

    /**
     * Returns an editable list of fields found on the class.
     * @return List of reflected fields.
     */
    public List<FieldInfo> getFields() {
        return fields;
    }

    /**
     * Returns the meta description content to be used to document this object.
     * @return Meta description.
     */
    public String getMetaDescription() {
        return metaDescription;
    }

    /**
     * Retrieves a field given the class field name.
     * @param classFieldName Field's name in the Java class.
     * @return Field info or null when the field was not found.
     */
    public FieldInfo getField(String classFieldName) {
        for (int i = 0; i < fields.size(); i++) {
            FieldInfo fieldInfo = fields.get(i);
            if (Objects.equals(classFieldName, fieldInfo.getClassFieldName())) {
                return fieldInfo;
            }
        }

        return null;
    }

    /**
     * Retrieves a field given the yaml field name.
     * @param yamlCheckName Field's name in the YAML file.
     * @return Field info or null when the field was not found.
     */
    public FieldInfo getFieldByYamlName(String yamlCheckName) {
        for (int i = 0; i < fields.size(); i++) {
            FieldInfo fieldInfo = fields.get(i);
            if (Objects.equals(yamlCheckName, fieldInfo.getYamlFieldName())) {
                return fieldInfo;
            }
        }

        return null;
    }

    /**
     * Creates a new instance of the object, using the default constructor.
     * @return A new instance of the object.
     */
    public Object createNewInstance() {
        try {
            Object newInstance = this.constructor.newInstance();
            return newInstance;
        } catch (InstantiationException e) {
            throw new DqoRuntimeException("Instantiation exception", e);
        } catch (InvocationTargetException e) {
            throw new DqoRuntimeException("Invocation exception", e);
        } catch (IllegalAccessException e) {
            throw new DqoRuntimeException("Illegal access exception", e);
        }
    }
}
