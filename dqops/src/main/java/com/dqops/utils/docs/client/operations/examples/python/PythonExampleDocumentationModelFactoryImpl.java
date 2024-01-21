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
package com.dqops.utils.docs.client.operations.examples.python;

import com.dqops.metadata.fields.ParameterDataType;
import com.dqops.utils.docs.DocumentationReflectionService;
import com.dqops.utils.docs.client.apimodel.OperationModel;
import com.dqops.utils.docs.client.operations.OperationParameterDocumentationModel;
import com.dqops.utils.docs.client.operations.examples.PathParameterFillerUtility;
import com.dqops.utils.docs.client.operations.examples.serialization.PythonSerializer;
import com.dqops.utils.docs.generators.GeneratorUtility;
import com.dqops.utils.docs.generators.TypeModel;
import com.dqops.utils.reflection.ClassInfo;
import com.dqops.utils.reflection.FieldInfo;
import com.dqops.utils.string.StringCaseFormat;
import com.google.common.base.CaseFormat;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.swagger.v3.oas.models.PathItem;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Collectors;

public class PythonExampleDocumentationModelFactoryImpl implements PythonExampleDocumentationModelFactory {
    private final DocumentationReflectionService reflectionService;
    private final PythonSerializer pythonSerializer;

    public PythonExampleDocumentationModelFactoryImpl(DocumentationReflectionService reflectionService,
                                                      PythonSerializer pythonSerializer) {
        this.reflectionService = reflectionService;
        this.pythonSerializer = pythonSerializer;
    }

    @Override
    public PythonExampleDocumentationModel createPythonExampleDocumentationModel(OperationModel operationModel,
                                                OperationParameterDocumentationModel requestBody,
                                                List<OperationParameterDocumentationModel> operationParameters,
                                                boolean auth,
                                                boolean async) {
        PythonExampleDocumentationModel pythonExampleDocumentationModel = new PythonExampleDocumentationModel();

        String controllerName = operationModel.getOperation().getTags().get(0);
        String moduleName = CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, controllerName);
        pythonExampleDocumentationModel.setModuleName(moduleName);

        String methodName = operationModel.getOperation().getOperationId();
        String apiMethodName = StringCaseFormat.LOWER_CAMEL.to(StringCaseFormat.LOWER_UNDERSCORE_SEPARATE_NUMBER, methodName);
        pythonExampleDocumentationModel.setApiMethodName(apiMethodName);

//        operationModel.getOperation().getResponses().entrySet().stream()
//                .sorted(Map.Entry.comparingByKey())
//                .map(Map.Entry::getKey)
//                .map(Integer::parseInt)
//                .map(HttpResponseStatus::valueOf)
//                .findFirst()
//                .ifPresent(pythonExampleDocumentationModel::setHttpStatus);
        if (operationModel.getHttpMethod() == PathItem.HttpMethod.GET) {
            pythonExampleDocumentationModel.setHttpStatus(HttpResponseStatus.OK);
        }

        pythonExampleDocumentationModel.setAuth(auth);
        pythonExampleDocumentationModel.setAsync(async);

        List<String> pathParameterValues = PathParameterFillerUtility.getSamplePathParameterValues(
                operationModel.getPath(), operationParameters);
        pythonExampleDocumentationModel.setPathParameters(pathParameterValues);

        Set<String> additionalModelsToImport = new LinkedHashSet<>();
        for (OperationParameterDocumentationModel parameter : operationParameters) {
            searchForModelsToImport(additionalModelsToImport, parameter.getTypeModel());
        }

        if (requestBody != null) {
            searchForModelsToImport(additionalModelsToImport, requestBody.getTypeModel());

            Object sampleRequestBody = GeneratorUtility.generateSampleFromTypeModel(requestBody.getTypeModel());
            String serializedRequestBody = pythonSerializer.serializePrettyPrint(sampleRequestBody);
            pythonExampleDocumentationModel.setRequestBody(serializedRequestBody);
        }

        if (!additionalModelsToImport.isEmpty()) {
            List<String> modelImports = additionalModelsToImport.stream()
                    .sorted()
                    .collect(Collectors.toList());
            pythonExampleDocumentationModel.setModelImports(modelImports);
        }

        return pythonExampleDocumentationModel;
    }

    private void searchForModelsToImport(Set<String> acc, TypeModel typeModel) {
        String className = typeModel.getClazz().getSimpleName();

        if (typeModel.getDataType() == ParameterDataType.enum_type) {
            acc.add(className);
        }
        else if (typeModel.getDataType() == ParameterDataType.object_type) {
            switch (typeModel.getObjectDataType()) {
                case map_type:
                    searchForModelsToImport(acc, typeModel.getGenericValueType());
                case list_type:
                    searchForModelsToImport(acc, typeModel.getGenericKeyType());
                    break;

                case object_type:
                    acc.add(className);
                    Object model = GeneratorUtility.generateSample(typeModel.getClazz());
                    ClassInfo modelClassInfo = reflectionService.getClassInfoForClass(typeModel.getClazz());
                    for (FieldInfo fieldInfo : modelClassInfo.getFields()) {
                        Method fieldGetter = fieldInfo.getGetterMethod();
                        if (fieldGetter == null) {
                            // TODO: Verify if the list or map-like objects are rendered properly, in curl and python.
                            continue;
                        }

                        TypeModel fieldTypeModel;
                        try {
                            Object fieldValue = fieldGetter.invoke(model);
                            if (fieldValue == null) {
                                continue;
                            }

                            Type objectsType = Objects.requireNonNullElse(fieldInfo.getGenericDataType(), fieldInfo.getClazz());
                            fieldTypeModel = reflectionService.getObjectsTypeModel(objectsType, _s -> null);
                        } catch (IllegalAccessException | InvocationTargetException e) {
                            continue;
                        }
                        searchForModelsToImport(acc, fieldTypeModel);
                    }
            }
        }
    }
}

