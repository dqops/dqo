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

import com.dqops.utils.docs.SampleStringsRegistry;
import com.dqops.utils.docs.SampleValueFactory;
import com.dqops.utils.docs.TypeModel;
import com.dqops.utils.docs.client.operations.OperationParameterDocumentationModel;
import com.dqops.utils.docs.client.operations.OperationParameterType;
import com.google.common.base.CaseFormat;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class PathParameterFillerUtility {
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
            case enum_type:
                Class<? extends SampleValueFactory> sampleValueFactory = (Class<? extends SampleValueFactory>)
                        Arrays.stream(parameterType.getClazz().getClasses())
                                .filter(SampleValueFactory.class::isAssignableFrom)
                                .findFirst().get();

                Constructor<? extends SampleValueFactory> s = (Constructor<? extends SampleValueFactory>) sampleValueFactory.getConstructors()[0];
                try {
                    SampleValueFactory<?> t = s.newInstance();
                    return t.createSample().toString();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            default:
                return "other";
        }
    }
}
