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

package com.dqops.rest.server.client;

import com.dqops.utils.exceptions.DqoRuntimeException;
import com.dqops.utils.reflection.*;
import com.dqops.utils.serialization.JsonSerializerImpl;
import io.swagger.annotations.Api;
import io.swagger.parser.OpenAPIParser;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.parser.core.models.SwaggerParseResult;
import org.apache.commons.text.CaseUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Helper class that upgrades the Swagger 2.x file generated from DQO REST Api model to Swagger 3.
 */
public class SwaggerFileUpgradeUtility {
    private static final ReflectionService reflectionService = new ReflectionServiceImpl();

    /**
     * Converts a swagger 2 file to swagger 3 file.
     * @param sourcedSwagger2FilePath Path to the Swagger 2.x file (the source file).
     * @param targetSwagger3FilePath Path to the target Swagger 3.x file that should be overwritten with a new content.
     */
    public static void convertSwagger2ToSwagger3(String sourcedSwagger2FilePath, String targetSwagger3FilePath) {
        Path pathToSourceSwaggerFile = Path.of(sourcedSwagger2FilePath);
        Path pathToTargetSwaggerFile = Path.of(targetSwagger3FilePath);

        try {
            OpenAPIParser openAPIParser = new OpenAPIParser();
            String convertedSwaggerContent = Files.readString(pathToSourceSwaggerFile);
            SwaggerParseResult swaggerParseResult = openAPIParser.readContents(convertedSwaggerContent, null, null);
            if (swaggerParseResult == null || swaggerParseResult.getOpenAPI() == null) {
                String message = "File " + pathToSourceSwaggerFile.toString() + " is invalid and was not parsed.";
                System.err.println(message);
                throw new RuntimeException(message);
            }

            OpenAPIBuilder openAPIBuilder = new OpenAPIBuilder(swaggerParseResult.getOpenAPI());
            OpenAPI openApi = openAPIBuilder
                    .mutateLocalhostAsServerUrl()
                    .mutateRemoveErroneousResponses()
                    .mutateStripBackslashInPaths()
                    .mutateUseReferentiableEnums()
                    .build();

            JsonSerializerImpl jsonSerializer = new JsonSerializerImpl();
            String upgradedSwaggerContent = jsonSerializer.serializePrettyPrint(openApi);

            Files.writeString(pathToTargetSwaggerFile, upgradedSwaggerContent);
        }
        catch (Exception ex) {
            throw new DqoRuntimeException("Cannot convert Swagger 2 to Swagger 3, error: " + ex.getMessage(), ex);
        }
    }

    private static class OpenAPIBuilder {
        private final OpenAPI openApi;

        public OpenAPIBuilder(OpenAPI openApi) {
            this.openApi = openApi;
        }

        public OpenAPI build() {
            return openApi;
        }

        public OpenAPIBuilder mutateLocalhostAsServerUrl() {
            openApi.getServers().forEach(server -> {
                server.setUrl("http://localhost:8888/");  // set the url to a local DQO instance as the default
            });
            return this;
        }

        public OpenAPIBuilder mutateRemoveErroneousResponses() {
            openApi.getPaths().forEach(((s, pathItem) -> {
                pathItem.readOperations().forEach(operation -> {
                    if (operation.getResponses().size() > 1) {
                        // remove additional responses, so the python client will be simpler and return values will be of a single type

                        Optional<String> firstResponseName = operation.getResponses().keySet().stream().findFirst();
                        ApiResponse firstApiResponse = operation.getResponses().get(firstResponseName.get());
                        operation.getResponses().clear();
                        operation.getResponses().addApiResponse(firstResponseName.get(), firstApiResponse);
                    }
                });
            }));
            return this;
        }

        public OpenAPIBuilder mutateStripBackslashInPaths() {
            ArrayList<String> pathStrings = new ArrayList<>(openApi.getPaths().keySet());
            for (String pathStringOriginal : pathStrings) {
                if (pathStringOriginal.startsWith("/")) {
                    String correctPathString = pathStringOriginal.substring(1);
                    PathItem pathItem = openApi.getPaths().get(pathStringOriginal);
                    openApi.getPaths().remove(pathStringOriginal);
                    openApi.getPaths().put(correctPathString, pathItem);
                }
            }
            return this;
        }

        public OpenAPIBuilder mutateUseReferentiableEnums() {
            Map<Class<? extends Enum<?>>, String> enumComponentRefMapping = new HashMap<>();
            useReferentiableEnumsForControllers(enumComponentRefMapping);
            useReferentiableEnumsForModels(enumComponentRefMapping);
            return this;
        }

        private void useReferentiableEnumsForControllers(Map<Class<? extends Enum<?>>, String> enumRefMapping) {
            Map<String, Class<?>> projectControllers = TargetClassSearchUtility.findClasses(
                            "com.dqops.rest.controllers", Path.of("dqops"), Object.class)
                    .stream()
                    .filter(c -> c.isAnnotationPresent(Api.class))
                    .collect(Collectors.toMap(
                            controller -> controller.getAnnotation(Api.class).value(),
                            Function.identity()
                    ));

            List<Operation> apiOperations = openApi.getPaths().values().stream()
                    .flatMap(p -> p.readOperations().stream())
                    .collect(Collectors.toList());
            for (Operation apiOperation : apiOperations) {
                List<Parameter> apiParameters = apiOperation.getParameters();
                if (apiParameters != null) {
                    List<Parameter> apiEnumParameters = apiParameters.stream()
                            .filter(parameter -> parameter.getSchema().getEnum() != null)
                            .collect(Collectors.toList());

                    if (!apiEnumParameters.isEmpty()) {
                        String controllerName = apiOperation.getTags().get(0);
                        String methodName = apiOperation.getOperationId();

                        Method method = Arrays.stream(projectControllers.get(controllerName).getDeclaredMethods())
                                .filter(m -> m.getName().equals(methodName))
                                .findFirst().get();

                        for (Parameter apiEnumParameter : apiEnumParameters) {
                            String apiEnumParameterName = apiEnumParameter.getName();
                            java.lang.reflect.Parameter parameter = Arrays.stream(method.getParameters())
                                    .filter(p -> apiEnumParameterName.equals(p.getName()))
                                    .findFirst().get();

                            String enumRef = useReferentiableEnumSingle(enumRefMapping, parameter.getParameterizedType());
                            apiEnumParameter.schema(new Schema() {{
                                set$ref(enumRef);
                            }});
                        }
                    }
                }

                // TODO: Handle enums in return types.
            }
        }

        private void useReferentiableEnumsForModels(Map<Class<? extends Enum<?>>, String> enumRefMapping) {
            Map<String, Class<?>> projectModels = new HashMap<>();
            for (Class<?> clazz : TargetClassSearchUtility.findClasses("com.dqops", Path.of("dqops"), Object.class)) {
                projectModels.put(clazz.getSimpleName(), clazz);
            }

            List<Map.Entry<String, Schema>> apiModels = openApi.getComponents().getSchemas().entrySet().stream()
                    .filter(modelEntry -> "object".equals(modelEntry.getValue().getType()))
                    .collect(Collectors.toList());

            for (Map.Entry<String, Schema> apiModel : apiModels) {
                Map<String, Schema> apiProperties = apiModel.getValue().getProperties();
                if (apiProperties != null) {
                    List<Map.Entry<String, Schema>> apiEnumParameters = apiProperties.entrySet().stream()
                            .filter(propEntry -> propEntry.getValue().getEnum() != null)
                            .collect(Collectors.toList());

                    if (!apiEnumParameters.isEmpty()) {
                        String modelName = apiModel.getKey();

                        Class<?> model = projectModels.get(modelName);

                        for (Map.Entry<String, Schema> apiEnumParameter : apiEnumParameters) {
                            String apiEnumParameterName = apiEnumParameter.getKey();
                            String parameterName = apiEnumParameterName;
                            if (parameterName.contains("_")) {
                                parameterName = CaseUtils.toCamelCase(parameterName, false, '_');
                            }

                            Field parameter;
                            try {
                                parameter = model.getDeclaredField(parameterName);
                            } catch (NoSuchFieldException e) {
                                throw new RuntimeException(e);
                            }

                            String enumRef = useReferentiableEnumSingle(enumRefMapping, parameter.getGenericType());
                            apiProperties.put(apiEnumParameterName, new Schema<>() {{
                                set$ref(enumRef);
                            }});
                        }
                    }
                }
            }
        }

        private String useReferentiableEnumSingle(Map<Class<? extends Enum<?>>, String> enumRefMapping,
                                                  Type reflectEnumType) {
            Class<?> reflectClass;
            if (reflectEnumType instanceof ParameterizedType) {
                reflectClass = (Class<?>) ((ParameterizedType) reflectEnumType).getActualTypeArguments()[0];
            } else {
                reflectClass = (Class<?>) reflectEnumType;
            }

            Class<? extends Enum<?>> reflectEnumClass = (Class<? extends Enum<?>>) reflectClass;

            if (!enumRefMapping.containsKey(reflectEnumClass)) {
                Schema<?> enumClassAsSchema = getEnumSchema(reflectEnumClass);
                String enumSchemaKey = reflectClass.getSimpleName();
                openApi.getComponents().addSchemas(enumSchemaKey, enumClassAsSchema);
                enumRefMapping.put(reflectEnumClass, "#/components/schemas/" + enumSchemaKey);
            }

            return enumRefMapping.get(reflectEnumClass);
        }

        private Schema<?> getEnumSchema(Class<? extends Enum<?>> reflectEnumClass) {
            Schema<String> enumSchema = new Schema<>();
            Set<String> enumkeyset = reflectionService.getEnumValuesMap(reflectEnumClass).keySet();
            enumSchema._enum(new ArrayList<>(enumkeyset));
            enumSchema.type("string");
            return enumSchema;
        }
    }
}
