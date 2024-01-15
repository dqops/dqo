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
import java.util.*;
import java.util.stream.Collectors;

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
     * Get sample instance from {@link TypeModel}, assuming it doesn't have a complex nested structure of generic types.
     * @param typeModel TypeModel for which to generate a sample. Object, flat list or flat map.
     * @return Sample instance from {@link TypeModel}.
     */
    public static Object generateSampleFromTypeModel(TypeModel typeModel) {
        switch (typeModel.getDataType()) {
            case enum_type:
                return generateSample(typeModel.getClazz());

            case object_type:
                ObjectDataType objectDataType = Objects.requireNonNullElse(typeModel.getObjectDataType(), ObjectDataType.object_type);
                switch (objectDataType) {
                    case map_type:
                        return new LinkedHashMap<String, String>();
                    case list_type:
                        TypeModel genericTypeModel = typeModel.getGenericKeyType();
                        assert genericTypeModel.getObjectDataType() == ObjectDataType.object_type;
                        List<?> l = SampleListUtility.generateList(genericTypeModel.getClazz(), 3);
                        return l;
                    case object_type:
                        Object sample = generateSample(typeModel.getClazz());
                        return sample;
                }

            case string_list_type:
                return SampleListUtility.generateStringList("sampleString", 3);
            case integer_list_type:
                return SampleListUtility.generateStringList("", 5).stream()
                        .map(s -> s.substring(1))
                        .map(Long::parseLong)
                        .map(n -> n * n + SampleLongsRegistry.getSequenceNumber() % 4200)
                        .map(Long::intValue)
                        .collect(Collectors.toList());
            case string_type:
                return "sample_string_value";
            default:
                throw new IllegalArgumentException(typeModel.toString());
        }
    }

    /**
     * Get sample string from {@link TypeModel} in a JSON form, assuming it doesn't have a complex nested structure.
     * @param typeModel TypeModel for which to generate a sample. Object, flat list or flat map.
     * @param prettyPrint PrettyPrint flag passed to {@link JsonSerializer}.
     * @return JSON string containing the sample.
     */
    public static String generateJsonSampleFromTypeModel(TypeModel typeModel, boolean prettyPrint) {
        Object serializedSample = generateSampleFromTypeModel(typeModel);
        return prettyPrint
                ? jsonSerializer.serializePrettyPrint(serializedSample)
                : jsonSerializer.serialize(serializedSample);
    }
}
