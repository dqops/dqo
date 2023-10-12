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
import com.dqops.utils.docs.client.apimodel.OpenAPIModel;
import com.dqops.utils.docs.client.apimodel.OperationModel;
import com.dqops.utils.docs.client.models.ModelsDocumentationModel;
import com.dqops.utils.docs.client.models.ModelsObjectDocumentationModel;
import com.dqops.utils.reflection.ClassInfo;
import com.dqops.utils.reflection.FieldInfo;
import com.dqops.utils.reflection.ReflectionServiceImpl;
import com.github.therapi.runtimejavadoc.ClassJavadoc;
import com.github.therapi.runtimejavadoc.CommentFormatter;
import com.github.therapi.runtimejavadoc.RuntimeJavadoc;
import com.google.common.base.CaseFormat;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.parameters.PathParameter;
import io.swagger.v3.oas.models.parameters.QueryParameter;
import io.swagger.v3.oas.models.parameters.RequestBody;
import io.swagger.v3.oas.models.responses.ApiResponse;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class OperationsDocumentationModelFactoryImpl implements OperationsDocumentationModelFactory {
    private static final Map<String, ParameterDataType> KNOWN_DATA_TYPES = new HashMap<>() {{
        put("string", ParameterDataType.string_type);
        put("integer", ParameterDataType.long_type);
        put("number", ParameterDataType.double_type);
        put("boolean", ParameterDataType.boolean_type);
        put("array", ParameterDataType.string_list_type);
    }};

    private final ReflectionServiceImpl reflectionService = new ReflectionServiceImpl();
    private static final CommentFormatter commentFormatter = new CommentFormatter();
    private static final String clientApiSourceBaseUrl = "https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/";

    @Override
    public List<OperationsSuperiorObjectDocumentationModel> createDocumentationForOperations(OpenAPIModel openAPIModel,
                                                                                             LinkageStore<String> linkageStore) {
        List<OperationsSuperiorObjectDocumentationModel> operationsDocumentation = new ArrayList<>();

        for (Map.Entry<String, List<OperationModel>> controller : openAPIModel.getControllersMethods().entrySet()) {
            String controllerName = controller.getKey();
            String controllerSimpleName = getObjectSimpleName(controllerName);
            OperationsSuperiorObjectDocumentationModel operationsSuperiorObjectDocumentationModel = new OperationsSuperiorObjectDocumentationModel();
            operationsSuperiorObjectDocumentationModel.setSuperiorClassFullName(controllerName);
            operationsSuperiorObjectDocumentationModel.setSuperiorClassSimpleName(controllerSimpleName);
            operationsSuperiorObjectDocumentationModel.setClassObjects(new ArrayList<>());

            String controllerLinkageIndicator = "operations/" + controllerSimpleName;
            List<ComponentModel> componentModels = openAPIModel.getModels().stream()
                    .filter(componentModel -> componentModel.getDocsLink() == null || componentModel.getDocsLink().toUri().toString().contains(controllerLinkageIndicator))
                    .collect(Collectors.toList());
            List<ComponentModel> topLevelComponentModels = componentModels.stream()
                    .filter(componentModel -> componentModel.getDocsLink() != null && componentModel.getDocsLink().toUri().toString().contains(controllerLinkageIndicator))
                    .collect(Collectors.toList());

            Map<Class<?>, OperationsObjectDocumentationModel> visitedObjects = new HashMap<>();
            for (ComponentModel componentModel : topLevelComponentModels) {
                Class<?> componentClazz = componentModel.getReflectedClass();
                List<OperationsObjectDocumentationModel> generatedComponentModels = generateOperationsObjectDocumentationModelRecursive(
                        Path.of("/docs/client/", controllerLinkageIndicator),
                        componentClazz,
                        visitedObjects,
                        linkageStore
                );
                operationsSuperiorObjectDocumentationModel.getClassObjects().addAll(generatedComponentModels);
                for (OperationsObjectDocumentationModel generatedComponentModel : generatedComponentModels) {
                    linkageStore.putIfAbsent(generatedComponentModel.getClassSimpleName(), generatedComponentModel.getObjectClassPath());
                }
            }

            List<OperationModel> controllerMethods = controller.getValue();
            operationsSuperiorObjectDocumentationModel.setOperationObjects(new ArrayList<>());

            for (OperationModel operationModel : controllerMethods) {
                OperationsOperationDocumentationModel operationsOperationDocumentationModel = generateOperationOperationDocumentationModel(operationModel,
                        linkageStore);
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
            LinkageStore<String> linkageStore) {
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

                Path linkagePath = linkageStore.get(parameterTypeString);
                if (linkagePath != null) {
                    parameterDocumentationModel.setClassUsedOnTheFieldPath(linkagePath.toString());
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

            Path linkagePath = linkageStore.get(parameterTypeString);
            if (linkagePath != null) {
                parameterDocumentationModel.setClassUsedOnTheFieldPath(linkagePath.toString());
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

            Path linkagePath = linkageStore.get(returnTypeString);
            if (linkagePath != null) {
                returnParameterModel.setClassUsedOnTheFieldPath(linkagePath.toString());
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

    /**
     * Create a yaml documentation in recursive for given class and add them to map.
     *
     * @param targetClass    Class for which fields to generate documentation.
     * @param visitedObjects Data structure to add created model.
     */
    private List<OperationsObjectDocumentationModel> generateOperationsObjectDocumentationModelRecursive(Path baseModelPath,
                                                                                                         Class<?> targetClass,
                                                                                                         Map<Class<?>, OperationsObjectDocumentationModel> visitedObjects,
                                                                                                         LinkageStore<String> linkageStore) {
        List<OperationsObjectDocumentationModel> result = new LinkedList<>();
        if (targetClass == null) {
            return result;
        }

        Path linkagePath = linkageStore.get(targetClass.getSimpleName());

        if (!visitedObjects.containsKey(targetClass) && (
                linkagePath == null || linkagePath.startsWith(baseModelPath)
        )) {
            visitedObjects.put(targetClass, null);

            OperationsObjectDocumentationModel operationsObjectDocumentationModel = new OperationsObjectDocumentationModel();
            List<OperationsDocumentationModel> operationsDocumentationModels = new ArrayList<>();

            ClassJavadoc classJavadoc = RuntimeJavadoc.getJavadoc(targetClass);
            if (classJavadoc != null) {
                if (classJavadoc.getComment() != null) {
                    String formattedClassComment = commentFormatter.format(classJavadoc.getComment());
                    operationsObjectDocumentationModel.setClassDescription(formattedClassComment);
                }
            }

            if (targetClass.getSuperclass() != null) {
                Class<?> superClass = targetClass.getSuperclass();
                if (isGenericSuperclass(superClass)) {
                    Type genericSuperclass = targetClass.getGenericSuperclass();
                    result.addAll(processGenericTypes(baseModelPath, genericSuperclass, visitedObjects, linkageStore));
                }
            }

            ClassInfo classInfo = reflectionService.getClassInfoForClass(targetClass);
            List<FieldInfo> infoFields = classInfo.getFields();

            operationsObjectDocumentationModel.setClassFullName(classInfo.getReflectedClass().getName());
            operationsObjectDocumentationModel.setClassSimpleName(classInfo.getReflectedClass().getSimpleName());
            operationsObjectDocumentationModel.setReflectedClass(classInfo.getReflectedClass());
            operationsObjectDocumentationModel.setObjectClassPath(
                    baseModelPath.resolve("#" + classInfo.getReflectedClass().getSimpleName())
            );

            for (FieldInfo info : infoFields) {
                if (info.getDataType() == null) {
                    continue;
                }

                if (info.getDataType().equals(ParameterDataType.object_type)) {
                    if (info.getClazz().getName().contains("java.") || info.getClazz().getName().contains("float")) {
                        continue;
                    }
                    result.addAll(generateOperationsObjectDocumentationModelRecursive(baseModelPath, info.getClazz(), visitedObjects, linkageStore));
                }

                OperationsDocumentationModel operationsDocumentationModel = new OperationsDocumentationModel();
                operationsDocumentationModel.setClassNameUsedOnTheField(info.getClazz().getSimpleName());

                if (linkageStore.containsKey(info.getClazz().getSimpleName())) {
                    Path infoClassPath = linkageStore.get(info.getClazz().getSimpleName());
                    operationsDocumentationModel.setClassUsedOnTheFieldPath(infoClassPath.toString());
                } else {
                    operationsDocumentationModel.setClassUsedOnTheFieldPath(
                            "#" + operationsDocumentationModel.getClassNameUsedOnTheField());
                }

                operationsDocumentationModel.setClassFieldName(info.getClazz().getSimpleName());
                operationsDocumentationModel.setYamlFieldName(info.getYamlFieldName());
                operationsDocumentationModel.setDisplayName(info.getDisplayName());
                operationsDocumentationModel.setHelpText(info.getHelpText());
                operationsDocumentationModel.setClazz(info.getClazz());
                operationsDocumentationModel.setDataType(info.getDataType());
                operationsDocumentationModel.setEnumValuesByName(info.getEnumValuesByName());
                operationsDocumentationModel.setDefaultValue(info.getDefaultValue());
                operationsDocumentationModel.setSampleValues(info.getSampleValues());

                operationsDocumentationModels.add(operationsDocumentationModel);

            }
            operationsObjectDocumentationModel.setObjectFields(operationsDocumentationModels);
            visitedObjects.put(targetClass, operationsObjectDocumentationModel);
            result.add(operationsObjectDocumentationModel);
        }
        return result;
    }

    /**
     * Checks if the specified class is a generic superclass.
     */
    private boolean isGenericSuperclass(Class<?> clazz) {
        Type genericSuperclass = clazz.getGenericSuperclass();
        return genericSuperclass instanceof ParameterizedType;
    }

    /**
     * Process and parse generic types, and then invoke appropriate actions on those types in a recursive.
     */
    private List<OperationsObjectDocumentationModel> processGenericTypes(Path baseModelPath, Type type, Map<Class<?>, OperationsObjectDocumentationModel> visitedObjects, LinkageStore<String> linkageStore) {
        if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type;
            Type[] typeArguments = parameterizedType.getActualTypeArguments();
            for (Type typeArgument : typeArguments) {
                if (typeArgument instanceof Class) {
                    Class<?> genericClass = (Class<?>) typeArgument;
                    if (!isJavaClass(genericClass)) {
                        return generateOperationsObjectDocumentationModelRecursive(baseModelPath, genericClass, visitedObjects, linkageStore);
                    }
                }
            }
        }
        return new LinkedList<>();
    }

    /**
     * Checks if the specified class is a java inbuilt class
     */
    private boolean isJavaClass(Class<?> clazz) {
        return clazz.getClassLoader() == null;
    }
}

