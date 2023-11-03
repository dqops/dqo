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

package com.dqops.utils.docs.client.operations.examples;

import com.dqops.utils.docs.SampleLongsRegistry;
import com.dqops.utils.docs.SampleStringsRegistry;
import com.dqops.utils.docs.SampleValueFactory;
import com.dqops.utils.docs.TypeModel;
import com.dqops.utils.docs.client.operations.OperationParameterDocumentationModel;
import com.dqops.utils.docs.client.operations.OperationParameterType;
import com.dqops.utils.reflection.ObjectDataType;
import com.dqops.utils.serialization.JsonSerializer;
import com.dqops.utils.serialization.JsonSerializerImpl;
import com.google.common.base.CaseFormat;

import java.lang.reflect.Constructor;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class PathParameterFillerUtility {
    private static JsonSerializer jsonSerializer = new JsonSerializerImpl();
    private static Pattern pathParameterPattern = Pattern.compile("\\{([^{}]*)\\}");
    public static String getSampleCallPath(String pathUrl, List<OperationParameterDocumentationModel> parameters) {
        Map<String, TypeModel> parameterMap = parameters.stream()
                .filter(p -> p.getOperationParameterType() == OperationParameterType.pathParameter)
                .collect(Collectors.toMap(
                        p -> CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, p.getYamlFieldName()),
                        OperationParameterDocumentationModel::getTypeModel
                ));

        String[] pathSplit = pathUrl.split("/");

        for (int i = 0; i < pathSplit.length; ++i) {
            String pathComponent = pathSplit[i];
            Matcher pathParameterMatcher = pathParameterPattern.matcher(pathComponent);
            if (pathParameterMatcher.find()) {
                String pathParameter = pathParameterMatcher.group(1);
                String filledPathComponent = getSampleParameterValue(pathParameter, parameterMap.get(pathParameter));
                pathSplit[i] = filledPathComponent;
            }
        }

        return String.join("/", pathSplit);
    }

    protected static String getSampleParameterValue(String parameterName, TypeModel parameterType) {
        switch (parameterType.getDataType()) {
            case string_type:
                return SampleStringsRegistry.getMatchingStringForParameter(parameterName);
            case long_type:
                return Long.toString(SampleLongsRegistry.getMatchingLongForParameter(parameterName));
            default:
                return getSampleFromTypeModel(parameterType);
        }

    }

    public static String getSampleFromTypeModel(TypeModel typeModel) {
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
                        Optional<Class<?>> sampleValueFactoryO = Arrays.stream(typeModel.getClazz().getClasses())
                                .filter(SampleValueFactory.class::isAssignableFrom)
                                .findFirst();
                        if (sampleValueFactoryO.isEmpty()) {
                            throw new IllegalArgumentException("No factory " + typeModel.getClassNameUsedOnTheField());
                        }

                        Class<? extends SampleValueFactory> sampleValueFactory = (Class<? extends SampleValueFactory>) sampleValueFactoryO.get();

                        Constructor<? extends SampleValueFactory> s = (Constructor<? extends SampleValueFactory>) sampleValueFactory.getConstructors()[0];
                        Object sample;
                        try {
                            SampleValueFactory<?> t = s.newInstance();
                            sample = t.createSample();
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }

                        return jsonSerializer.serializePrettyPrint(sample);
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
