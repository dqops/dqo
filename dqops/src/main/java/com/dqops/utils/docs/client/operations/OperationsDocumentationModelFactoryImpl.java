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
package com.dqops.utils.docs.client.operations;

import com.dqops.metadata.fields.ParameterDataType;
import com.dqops.utils.docs.LinkageStore;
import com.dqops.utils.docs.client.OpenApiUtils;
import com.dqops.utils.docs.client.apimodel.ComponentModel;
import com.dqops.utils.docs.client.apimodel.ControllerModel;
import com.dqops.utils.docs.client.apimodel.OpenAPIModel;
import com.dqops.utils.docs.client.apimodel.OperationModel;
import com.google.common.base.CaseFormat;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.parameters.PathParameter;
import io.swagger.v3.oas.models.parameters.QueryParameter;
import io.swagger.v3.oas.models.parameters.RequestBody;
import io.swagger.v3.oas.models.responses.ApiResponse;

import java.nio.file.Path;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class OperationsDocumentationModelFactoryImpl implements OperationsDocumentationModelFactory {
    private static final Map<String, ParameterDataType> KNOWN_DATA_TYPES = new HashMap<>() {{
        put("string", ParameterDataType.string_type);
        put("integer", ParameterDataType.long_type);
        put("number", ParameterDataType.double_type);
        put("boolean", ParameterDataType.boolean_type);
        put("array", ParameterDataType.string_list_type);
    }};

    private static final String clientApiSourceBaseUrl = "https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/";

    @Override
    public List<OperationsSuperiorObjectDocumentationModel> createDocumentationForOperations(OpenAPIModel openAPIModel) {
        List<OperationsSuperiorObjectDocumentationModel> operationsDocumentation = new ArrayList<>();
        Map<String, ComponentModel> componentModelMap = openAPIModel.getModels().stream().collect(Collectors.toMap(ComponentModel::getClassName, Function.identity()));

        for (ControllerModel controller : openAPIModel.getControllers()) {
            String controllerName = controller.getControllerName();
            String controllerSimpleName = getObjectSimpleName(controllerName);
            OperationsSuperiorObjectDocumentationModel operationsSuperiorObjectDocumentationModel = new OperationsSuperiorObjectDocumentationModel();
            operationsSuperiorObjectDocumentationModel.setSuperiorClassFullName(controllerName);
            operationsSuperiorObjectDocumentationModel.setSuperiorClassSimpleName(controllerSimpleName);
            operationsSuperiorObjectDocumentationModel.setSuperiorDescription(controller.getDescription());

            List<OperationModel> controllerMethods = controller.getOperations();
            operationsSuperiorObjectDocumentationModel.setOperationObjects(new ArrayList<>());

            for (OperationModel operationModel : controllerMethods) {
                OperationsOperationDocumentationModel operationsOperationDocumentationModel =
                        generateOperationOperationDocumentationModel(operationModel, componentModelMap);
                operationsSuperiorObjectDocumentationModel.getOperationObjects().add(operationsOperationDocumentationModel);
            }

            operationsDocumentation.add(operationsSuperiorObjectDocumentationModel);
        }
        return operationsDocumentation;
    }

    private String getOperationSourceUrl(String controllerName, OperationModel operationModel) {
        return clientApiSourceBaseUrl +
                getObjectSimpleName(controllerName) +
                "/" +
                getObjectSimpleName(operationModel.getOperation().getOperationId()) +
                ".py";
    }

    private String getObjectSimpleName(String name) {
        return CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, name);
    }

    /**
     * Create a yaml documentation in recursive for given class and add them to map.
     */
    private OperationsOperationDocumentationModel generateOperationOperationDocumentationModel(
            OperationModel operationModel,
            Map<String, ComponentModel> componentModelMap) {
        OperationsOperationDocumentationModel operationsOperationDocumentationModel = new OperationsOperationDocumentationModel();
        operationsOperationDocumentationModel.setOperationJavaName(operationModel.getOperation().getOperationId());
        operationsOperationDocumentationModel.setOperationPythonName(getObjectSimpleName(operationModel.getOperation().getOperationId()));
        operationsOperationDocumentationModel.setOperationDescription(operationModel.getOperation().getDescription());
        operationsOperationDocumentationModel.setClientSourceUrl(getOperationSourceUrl(
                operationModel.getOperation().getTags().get(0),
                operationModel));
        operationsOperationDocumentationModel.setApiCallUrl(operationModel.getPath());
        operationsOperationDocumentationModel.setApiCallMethod(operationModel.getHttpMethod());

        List<OperationParameterDocumentationModel> operationParameterDocumentationModels = new ArrayList<>();
        // Parameters (path and query)
        List<Parameter> operationParameters = operationModel.getOperation().getParameters();
        if (operationParameters != null) {
            for (Parameter operationParameter: operationParameters) {
                String parameterTypeString = operationParameter.getSchema().getType();
                if (parameterTypeString == null) {
                    parameterTypeString = getTypeFrom$ref(operationParameter.getSchema().get$ref());
                }

                OperationParameterDocumentationModel parameterDocumentationModel = new OperationParameterDocumentationModel();
                parameterDocumentationModel.setClassFieldName(parameterTypeString);
                parameterDocumentationModel.setYamlFieldName(getObjectSimpleName(operationParameter.getName()));
                parameterDocumentationModel.setDisplayName(parameterDocumentationModel.getYamlFieldName());
                parameterDocumentationModel.setHelpText(operationParameter.getDescription());
                parameterDocumentationModel.setRequired(Objects.requireNonNullElse(operationParameter.getRequired(), true));

                ParameterDataType parameterDataType = KNOWN_DATA_TYPES.getOrDefault(parameterTypeString, ParameterDataType.object_type);
                parameterDocumentationModel.setDataType(parameterDataType);

                if (operationParameter instanceof PathParameter) {
                    parameterDocumentationModel.setOperationParameterType(OperationParameterType.pathParameter);
                } else if (operationParameter instanceof QueryParameter) {
                    parameterDocumentationModel.setOperationParameterType(OperationParameterType.queryParameter);
                }

                ComponentModel linkageComponent = componentModelMap.get(parameterTypeString);
                if (linkageComponent != null) {
                    String docsLinkString = linkageComponent.getDocsLink() != null ? linkageComponent.getDocsLink().toString() : null;
                    parameterDocumentationModel.setClassUsedOnTheFieldPath(docsLinkString);
                }
                operationParameterDocumentationModels.add(parameterDocumentationModel);
            }
        }

        // Request body
        RequestBody requestBody = operationModel.getOperation().getRequestBody();
        if (requestBody != null) {
            String requestBody$ref = OpenApiUtils.getEffective$refFromContent(requestBody.getContent());
            String parameterTypeString = getTypeFrom$ref(requestBody$ref);

            OperationParameterDocumentationModel parameterDocumentationModel = new OperationParameterDocumentationModel();
            parameterDocumentationModel.setClassFieldName(parameterTypeString);
            parameterDocumentationModel.setYamlFieldName("body");
            parameterDocumentationModel.setDisplayName(parameterDocumentationModel.getYamlFieldName());
            parameterDocumentationModel.setHelpText(requestBody.getDescription());
            parameterDocumentationModel.setRequired(Objects.requireNonNullElse(requestBody.getRequired(), true));
            parameterDocumentationModel.setOperationParameterType(OperationParameterType.requestBodyParameter);

            ParameterDataType parameterDataType = KNOWN_DATA_TYPES.getOrDefault(parameterTypeString, ParameterDataType.object_type);
            parameterDocumentationModel.setDataType(parameterDataType);

            ComponentModel linkageComponent = componentModelMap.get(parameterTypeString);
            if (linkageComponent != null) {
                String docsLinkString = linkageComponent.getDocsLink() != null ? linkageComponent.getDocsLink().toString() : null;
                parameterDocumentationModel.setClassUsedOnTheFieldPath(docsLinkString);
            }
            operationsOperationDocumentationModel.setRequestBodyField(parameterDocumentationModel);
        }
        operationsOperationDocumentationModel.setParametersFields(operationParameterDocumentationModels);

        // Return
        ApiResponse returnResponse = operationModel.getOperation().getResponses().values().stream().findFirst().get();
        Schema<?> returnSchema = OpenApiUtils.getEffectiveSchemaFromContent(returnResponse.getContent());
        String return$ref = OpenApiUtils.getEffective$refFromSchema(returnSchema);
        if (return$ref != null && !return$ref.contains("MonoObject") && !return$ref.contains("MonoVoid")) {
            String returnTypeString = getTypeFrom$ref(return$ref);

            OperationsDocumentationModel returnParameterModel = new OperationsDocumentationModel();
            returnParameterModel.setClassFieldName(returnTypeString);
            returnParameterModel.setYamlFieldName(getObjectSimpleName(returnTypeString));
            returnParameterModel.setDisplayName(returnParameterModel.getYamlFieldName());
            returnParameterModel.setHelpText(returnSchema.getDescription());

            ParameterDataType returnDataType = KNOWN_DATA_TYPES.getOrDefault(returnTypeString, ParameterDataType.object_type);
            returnParameterModel.setDataType(returnDataType);

            ComponentModel linkageComponent = componentModelMap.get(returnTypeString);
            if (linkageComponent != null) {
                String docsLinkString = linkageComponent.getDocsLink() != null ? linkageComponent.getDocsLink().toString() : null;
                returnParameterModel.setClassUsedOnTheFieldPath(docsLinkString);
            }
            operationsOperationDocumentationModel.setReturnValueField(returnParameterModel);
        }

        return operationsOperationDocumentationModel;
    }

    private String getTypeFrom$ref(String $ref) {
        if ($ref == null) {
            return "";
        }

        String[] split$ref = $ref.split("/");
        return split$ref[split$ref.length - 1];
    }
}

