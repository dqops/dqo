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
import com.dqops.utils.docs.DocumentationReflectionService;
import com.dqops.utils.docs.DocumentationReflectionServiceImpl;
import com.dqops.utils.docs.generators.GeneratorUtility;
import com.dqops.utils.docs.generators.TypeModel;
import com.dqops.utils.docs.client.OpenApiUtils;
import com.dqops.utils.docs.client.apimodel.ComponentModel;
import com.dqops.utils.docs.client.apimodel.ControllerModel;
import com.dqops.utils.docs.client.apimodel.OpenAPIModel;
import com.dqops.utils.docs.client.apimodel.OperationModel;
import com.dqops.utils.docs.client.operations.examples.PathParameterFillerUtility;
import com.dqops.utils.reflection.ObjectDataType;
import com.dqops.utils.reflection.ReflectionServiceImpl;
import com.dqops.utils.string.StringCaseFormat;
import com.google.common.base.CaseFormat;
import com.google.inject.internal.MoreTypes;
import io.swagger.v3.oas.models.media.ArraySchema;
import io.swagger.v3.oas.models.media.MapSchema;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.parameters.PathParameter;
import io.swagger.v3.oas.models.parameters.QueryParameter;
import io.swagger.v3.oas.models.parameters.RequestBody;
import io.swagger.v3.oas.models.responses.ApiResponse;

import java.lang.reflect.Type;
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

    private static final Map<String, Class<?>> KNOWN_CLASSES = new HashMap<>() {{
        put("string", String.class);
        put("integer", Long.class);
        put("number", Double.class);
        put("boolean", Boolean.class);
    }};

    private static final String clientApiSourceBaseUrl = "https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/";

    private final DocumentationReflectionService documentationReflectionService = new DocumentationReflectionServiceImpl(new ReflectionServiceImpl());

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
        return StringCaseFormat.UPPER_CAMEL.to(StringCaseFormat.LOWER_UNDERSCORE_SEPARATE_NUMBER, name);
    }

    /**
     * Create a yaml documentation in recursive for given class and add them to map.
     */
    private OperationsOperationDocumentationModel generateOperationOperationDocumentationModel(
            OperationModel operationModel,
            Map<String, ComponentModel> componentModelMap) {
        OperationsOperationDocumentationModel operationsOperationDocumentationModel = new OperationsOperationDocumentationModel();
        operationsOperationDocumentationModel.setOperationModel(operationModel);
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

                if (operationParameter instanceof PathParameter) {
                    parameterDocumentationModel.setOperationParameterType(OperationParameterType.pathParameter);
                } else if (operationParameter instanceof QueryParameter) {
                    parameterDocumentationModel.setOperationParameterType(OperationParameterType.queryParameter);
                }

                TypeModel parameterTypeModel = getTypeModelForType(parameterTypeString, operationParameter.getSchema(), componentModelMap);
                parameterDocumentationModel.setTypeModel(parameterTypeModel);

                operationParameterDocumentationModels.add(parameterDocumentationModel);
            }
        }

        // Request body
        RequestBody requestBody = operationModel.getOperation().getRequestBody();
        if (requestBody != null) {
            Schema<?> requestBodySchema = OpenApiUtils.getEffectiveSchemaFromContent(requestBody.getContent());
            String requestBody$ref = OpenApiUtils.getEffective$refFromSchema(requestBodySchema);
            String parameterTypeString = getTypeFrom$ref(requestBody$ref);

            OperationParameterDocumentationModel parameterDocumentationModel = new OperationParameterDocumentationModel();
            parameterDocumentationModel.setClassFieldName(parameterTypeString);
            parameterDocumentationModel.setYamlFieldName("body");
            parameterDocumentationModel.setDisplayName(parameterDocumentationModel.getYamlFieldName());
            parameterDocumentationModel.setHelpText(requestBody.getDescription());
            parameterDocumentationModel.setRequired(Objects.requireNonNullElse(requestBody.getRequired(), true));
            parameterDocumentationModel.setOperationParameterType(OperationParameterType.requestBodyParameter);

            TypeModel parameterTypeModel = getTypeModelForType(parameterTypeString, requestBodySchema, componentModelMap);
            parameterDocumentationModel.setTypeModel(parameterTypeModel);

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

            TypeModel returnParameterTypeModel = getTypeModelForType(returnTypeString, returnSchema, componentModelMap);
            returnParameterModel.setTypeModel(returnParameterTypeModel);

            operationsOperationDocumentationModel.setReturnValueField(returnParameterModel);

            String sampleReturnValue = GeneratorUtility.getSampleFromTypeModel(returnParameterTypeModel, true);
            operationsOperationDocumentationModel.setOperationDescription(sampleReturnValue);
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

    private TypeModel getTypeModelForType(String parameterTypeString,
                                          Schema<?> parameterSchema,
                                          Map<String, ComponentModel> componentModelMap) {
        Function<Class<?>, String> linkAccessor = (clazz) -> {
            String simpleClassName = clazz.getSimpleName();
            ComponentModel linkageComponent = componentModelMap.get(simpleClassName);
            if (linkageComponent != null && linkageComponent.getDocsLink() != null) {
                return linkageComponent.getDocsLink().toString().replace('\\', '/');
            }
            return null;
        };

        Class<?> clazz;
        ComponentModel componentModel = componentModelMap.get(parameterTypeString);
        if (componentModel == null) {
            clazz = KNOWN_CLASSES.get(parameterTypeString);
        } else {
            clazz = componentModel.getReflectedClass();
        }

        Type type;
        if (parameterSchema instanceof ArraySchema) {
            type = new MoreTypes.ParameterizedTypeImpl(null, List.class, clazz);
        } else if (parameterSchema instanceof MapSchema) {
            type = new MoreTypes.ParameterizedTypeImpl(null, Map.class, String.class, clazz);
        } else {
            type = clazz;
        }

        return documentationReflectionService.getObjectsTypeModel(type, linkAccessor);
    }

    private TypeModel getTypeModelForParameterDataType(String parameterType) {
        ParameterDataType parameterDataType = KNOWN_DATA_TYPES.getOrDefault(parameterType, ParameterDataType.object_type);
        TypeModel typeModel = new TypeModel();
        typeModel.setDataType(parameterDataType);
        if (parameterDataType == ParameterDataType.object_type) {
            typeModel.setObjectDataType(ObjectDataType.object_type);
        }
        typeModel.setClassNameUsedOnTheField(parameterType);
        return typeModel;
    }
}

