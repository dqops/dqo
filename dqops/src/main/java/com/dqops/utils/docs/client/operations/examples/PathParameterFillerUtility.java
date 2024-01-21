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

import com.dqops.utils.docs.client.operations.OperationParameterDocumentationModel;
import com.dqops.utils.docs.client.operations.OperationParameterType;
import com.dqops.utils.docs.generators.GeneratorUtility;
import com.dqops.utils.docs.generators.SampleLongsRegistry;
import com.dqops.utils.docs.generators.SampleStringsRegistry;
import com.dqops.utils.docs.generators.TypeModel;
import com.google.common.base.CaseFormat;
import com.google.common.base.Strings;

import java.util.*;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public final class PathParameterFillerUtility {
    private static Pattern pathParameterPattern = Pattern.compile("\\{([^{}]*)\\}");
    public static String getSampleCallPath(String pathUrl, List<OperationParameterDocumentationModel> parameters) {
        List<String> pathParameterValues = getSamplePathParameterValues(pathUrl, parameters);
        return substitutePathUrlWithValues(pathUrl, pathParameterValues);
    }

    private static String substitutePathUrlWithValues(String pathUrl, List<String> pathParameterValues) {
        StringBuilder resultBuilder = new StringBuilder();

        Matcher pathParameterMatcher = pathParameterPattern.matcher(pathUrl);

        Iterator<String> pathParameterIt = pathParameterValues.iterator();
        int pathUrlMatchCursor = 0;
        for (Iterator<MatchResult> it = pathParameterMatcher.results().iterator(); it.hasNext(); ) {
            MatchResult matchResult = it.next();
            resultBuilder.append(pathUrl, pathUrlMatchCursor, matchResult.start());
            String replacement = pathParameterIt.next();

            if (replacement.charAt(0) == '\'' && replacement.charAt(replacement.length() - 1) == '\'') {
                // Handle string formatting.
                replacement = replacement.substring(1, replacement.length() - 1);
            } else if (replacement.contains(".")) {
                // Handle enum formatting.
                String[] splitReplacement = replacement.split("\\.");
                replacement = splitReplacement[splitReplacement.length - 1];
            }

            resultBuilder.append(replacement);
            pathUrlMatchCursor = matchResult.end();
        }

        resultBuilder.append(pathUrl, pathUrlMatchCursor, pathUrl.length());
        return resultBuilder.toString();
    }

    public static List<String> getSamplePathParameterValues(String pathUrl, List<OperationParameterDocumentationModel> parameters) {
        Map<String, TypeModel> parameterMap = parameters.stream()
                .filter(p -> p.getOperationParameterType() == OperationParameterType.pathParameter)
                .collect(Collectors.toMap(
                        p -> CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, p.getYamlFieldName()),
                        OperationParameterDocumentationModel::getTypeModel,
                        (key, value) -> value,
                        LinkedHashMap::new
                ));

        String[] pathSplit = pathUrl.split("/");
        List<String> samplePathParametersValues = new ArrayList<>();

        for (int i = 0; i < pathSplit.length; ++i) {
            String pathComponent = pathSplit[i];
            Matcher pathParameterMatcher = pathParameterPattern.matcher(pathComponent);
            if (pathParameterMatcher.find()) {
                String pathParameter = pathParameterMatcher.group(1);
                String samplePathParameterValue = getSampleParameterValue(pathParameter, parameterMap.get(pathParameter));
                samplePathParametersValues.add(samplePathParameterValue);
            }
        }

        return samplePathParametersValues;
    }

    private static String getSampleParameterValue(String parameterName, TypeModel parameterType) {
        switch (parameterType.getDataType()) {
            case string_type:
                String sampleStringValue = SampleStringsRegistry.getMatchingStringForParameter(parameterName);
                return String.format("'%s'", sampleStringValue);
            case long_type:
                return Long.toString(SampleLongsRegistry.getMatchingLongForParameter(parameterName));
            case enum_type:
                String jsonStringValue = GeneratorUtility.generateJsonSampleFromTypeModel(parameterType, false);
                String enumValue = jsonStringValue.replaceAll("^\"|\"$", "");
                return getJavaSimpleClassName(parameterType.getClazz()) + "." + enumValue;
            default:
                return GeneratorUtility.generateJsonSampleFromTypeModel(parameterType, false);
        }
    }

    public static String getJavaSimpleClassName(Class<?> clazz) {
        if (Strings.isNullOrEmpty(clazz.getSimpleName())) {
            return getJavaSimpleClassName(clazz.getSuperclass());
        } else {
            return clazz.getSimpleName();
        }
    }
}
