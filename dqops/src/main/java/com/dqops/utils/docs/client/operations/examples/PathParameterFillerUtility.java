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

        String sampleCallPath = String.join("/", pathSplit);
        if (sampleCallPath.charAt(sampleCallPath.length() - 1) == '/') {
            sampleCallPath = sampleCallPath.substring(0, sampleCallPath.length() - 1);
        }

        return sampleCallPath;
    }

    protected static String getSampleParameterValue(String parameterName, TypeModel parameterType) {
        switch (parameterType.getDataType()) {
            case string_type:
                return SampleStringsRegistry.getMatchingStringForParameter(parameterName);
            case long_type:
                return Long.toString(SampleLongsRegistry.getMatchingLongForParameter(parameterName));
            case enum_type:
                String jsonStringValue = GeneratorUtility.getSampleFromTypeModel(parameterType, false);
                return jsonStringValue.replaceAll("^\"|\"$", "");
            default:
                return GeneratorUtility.getSampleFromTypeModel(parameterType, false);
        }
    }
}
