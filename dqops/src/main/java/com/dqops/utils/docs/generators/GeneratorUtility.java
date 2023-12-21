/*
 * Copyright © 2023 DQOps (support@dqops.com)
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

package com.dqops.utils.docs.generators;

import com.dqops.utils.reflection.ObjectDataType;
import com.dqops.utils.serialization.JsonSerializer;
import com.dqops.utils.serialization.JsonSerializerImpl;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class GeneratorUtility {
    private static JsonSerializer jsonSerializer = new JsonSerializerImpl();

    public static <T> T generateSample(Class<T> clazz) {
        try {
            SampleValueFactory<T> sampleValueFactory = getSampleValueFactoryFromClass(clazz);
            return sampleValueFactory.createSample();
        } catch (IllegalArgumentException e) {
            // No factory for class.
            System.out.println(e.getMessage() + ". Attempting to generate a default sample.");
        }

        Constructor<?>[] constructors = clazz.getConstructors();
        if (constructors.length == 0) {
            throw new IllegalArgumentException("No constructor for " + clazz.getName());
        }

        Optional<Constructor<?>> defaultConstructorOptional = Arrays.stream(constructors)
                .filter(constructor -> constructor.getParameterCount() == 0)
                .findFirst();
        if (defaultConstructorOptional.isEmpty()) {
            throw new IllegalArgumentException("No parameterless constructor for " + clazz.getName());
        }

        try {
            Constructor<T> defaultConstructor = (Constructor<T>) defaultConstructorOptional.get();
            return defaultConstructor.newInstance();
        } catch (Exception e) {
            throw new IllegalArgumentException("Error generating sample using a default constructor for class " + clazz.getName(), e);
        }
    }

    protected static <T> SampleValueFactory<T> getSampleValueFactoryFromClass(Class<T> clazz) {
        Optional<Class<?>> sampleValueFactoryOptional = Arrays.stream(clazz.getClasses())
                .filter(SampleValueFactory.class::isAssignableFrom)
                .findFirst();
        if (sampleValueFactoryOptional.isEmpty()) {
            throw new IllegalArgumentException("No factory for " + clazz.getName());
        }

        Class<? extends SampleValueFactory> sampleValueFactory = (Class<? extends SampleValueFactory>) sampleValueFactoryOptional.get();

        Constructor<?>[] sampleValueFactoryConstructors = sampleValueFactory.getConstructors();
        Constructor<? extends SampleValueFactory> sampleValueFactoryConstructor = (Constructor<? extends SampleValueFactory>) sampleValueFactoryConstructors[0];
        try {
            return (SampleValueFactory<T>) sampleValueFactoryConstructor.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Get sample string from {@link TypeModel} in a JSON form, assuming it doesn't have a complex nested structure.
     * @param typeModel TypeModel for which to generate a sample. Object, flat list or flat map.
     * @param prettyPrint PrettyPrint flag passed to {@link JsonSerializer}.
     * @return JSON string containing the sample.
     */
    public static String getSampleFromTypeModel(TypeModel typeModel, boolean prettyPrint) {
        switch (typeModel.getDataType()) {
            case enum_type:
            case object_type:
                ObjectDataType objectDataType = Objects.requireNonNullElse(typeModel.getObjectDataType(), ObjectDataType.object_type);
                switch (objectDataType) {
                    case map_type:
                        return "{}";
                    case list_type:
                        TypeModel genericTypeModel = typeModel.getGenericKeyType();
                        assert genericTypeModel.getObjectDataType() == ObjectDataType.object_type;
                        List<?> l = SampleListUtility.generateList(genericTypeModel.getClazz(), 3);
                        return prettyPrint ? jsonSerializer.serializePrettyPrint(l) : jsonSerializer.serialize(l);
                    case object_type:
                        Object sample = generateSample(typeModel.getClazz());
                        return prettyPrint ? jsonSerializer.serializePrettyPrint(sample) : jsonSerializer.serialize(sample);
                }
            case string_list_type:
            case integer_list_type:
                return "[]";
            case string_type:
                return "sample_string_value";
            default:
                throw new IllegalArgumentException(typeModel.toString());
        }
    }
}
