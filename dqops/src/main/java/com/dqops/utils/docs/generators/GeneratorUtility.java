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
import java.util.function.Function;
import java.util.stream.Stream;

public class GeneratorUtility {
    private static JsonSerializer jsonSerializer = new JsonSerializerImpl();

    public static <T> T generateSample() {
        SampleValueFactory
    }

    protected static <T> SampleValueFactory<T> getSampleValueFactoryFromClass(Class<T> clazz) {
        Optional<Class<?>> sampleValueFactoryOptional = Arrays.stream(clazz.getClasses())
                .filter(SampleValueFactory.class::isAssignableFrom)
                .findFirst();
        if (sampleValueFactoryOptional.isEmpty()) {
            throw new IllegalArgumentException("No factory for " + clazz.getName());
        }

        Class<? extends SampleValueFactory> sampleValueFactory = (Class<? extends SampleValueFactory>) sampleValueFactoryOptional.get();

        Constructor<? extends SampleValueFactory> sampleValueFactoryConstructor = (Constructor<? extends SampleValueFactory>) sampleValueFactory.getConstructors()[0];
        try {
            SampleValueFactory<T> t = sampleValueFactoryConstructor.newInstance();
            return t;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String getSampleFromTypeModel(TypeModel typeModel, boolean prettyPrint) {
        switch (typeModel.getDataType()) {
            case enum_type:
            case object_type:
                ObjectDataType objectDataType = Objects.requireNonNullElse(typeModel.getObjectDataType(), ObjectDataType.object_type);
                switch (objectDataType) {
                    case map_type:
                        return "{}";
                    case list_type:
                        return "[]";
                    case object_type:
                        Optional<Class<?>> sampleValueFactoryOptional = Arrays.stream(typeModel.getClazz().getClasses())
                                .filter(SampleValueFactory.class::isAssignableFrom)
                                .findFirst();
                        if (sampleValueFactoryOptional.isEmpty()) {
                            throw new IllegalArgumentException("No factory for " + typeModel.getClassNameUsedOnTheField());
                        }

                        Class<? extends SampleValueFactory> sampleValueFactory = (Class<? extends SampleValueFactory>) sampleValueFactoryOptional.get();

                        Constructor<? extends SampleValueFactory> s = (Constructor<? extends SampleValueFactory>) sampleValueFactory.getConstructors()[0];
                        Object sample;
                        try {
                            SampleValueFactory<?> t = s.newInstance();
                            sample = t.createSample();
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }

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
