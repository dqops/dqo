/*
 * Copyright © 2023 DQO.ai (support@dqo.ai)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.dqops.utils.docs.client.models;

import com.dqops.metadata.fields.ParameterDataType;
import com.dqops.utils.docs.LinkageStore;
import com.dqops.utils.docs.client.apimodel.ComponentModel;
import com.dqops.utils.docs.client.apimodel.OpenAPIModel;
import com.dqops.utils.docs.client.apimodel.OperationModel;
import com.dqops.utils.reflection.ClassInfo;
import com.dqops.utils.reflection.FieldInfo;
import com.dqops.utils.reflection.ReflectionServiceImpl;
import com.github.therapi.runtimejavadoc.ClassJavadoc;
import com.github.therapi.runtimejavadoc.CommentFormatter;
import com.github.therapi.runtimejavadoc.RuntimeJavadoc;
import com.google.common.base.CaseFormat;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.nio.file.Path;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ModelsDocumentationModelFactoryImpl implements ModelsDocumentationModelFactory {

    private final ReflectionServiceImpl reflectionService = new ReflectionServiceImpl();
    private static final CommentFormatter commentFormatter = new CommentFormatter();

    @Override
    public ModelsSuperiorObjectDocumentationModel createDocumentationForSharedModels(Collection<ComponentModel> componentModels) {
        List<ComponentModel> sharedModels = componentModels.stream()
                .filter(componentModel -> componentModel.getDocsLink() == null
                        || componentModel.getDocsLink().toUri().getPath().contains("models/#"))
                .sorted(Comparator.comparing(ComponentModel::getClassName))
                .collect(Collectors.toList());

        List<ComponentModel> componentModels1 = sharedModels.stream().filter(c->c.getClassName().toLowerCase().contains("postgres")).collect(Collectors.toList());

        Map<String, ComponentModel> componentModelMap = componentModels.stream().collect(Collectors.toMap(ComponentModel::getClassName, Function.identity()));

        List<Map.Entry<String, ComponentModel>> componennnnn = componentModelMap.entrySet().stream().filter(c->c.getKey().toLowerCase().contains("postgres")).collect(Collectors.toList());

        return generateModelsSuperiorObjectDocumentationModel("index.md", sharedModels, componentModelMap);
    }

    @Override
    public List<ModelsSuperiorObjectDocumentationModel> createDocumentationForModels(Collection<ComponentModel> componentModels) {
        Map<String, ComponentModel> componentModelMap = componentModels.stream().collect(Collectors.toMap(ComponentModel::getClassName, Function.identity()));

        List<ComponentModel> subModels = componentModels.stream()
                .filter(componentModel -> componentModel.getDocsLink() == null)
                .collect(Collectors.toList());
        List<ComponentModel> exclusiveModels = componentModels.stream()
                .filter(this::isComponentModelExclusive)
                .collect(Collectors.toList());

        Map<String, List<ComponentModel>> modelsForController = new HashMap<>();
        for (ComponentModel model : exclusiveModels) {
            String controllerName = getControllerNameFromDocsLink(model.getDocsLink());
            if (!modelsForController.containsKey(controllerName)) {
                modelsForController.put(controllerName, new ArrayList<>());
            }
            modelsForController.get(controllerName).add(model);
        }

        List<ModelsSuperiorObjectDocumentationModel> documentationModels = new ArrayList<>();
        for (Map.Entry<String, List<ComponentModel>> controllerModelsEntry : modelsForController.entrySet()) {
            String controllerName = controllerModelsEntry.getKey();
            List<ComponentModel> controllerModels = controllerModelsEntry.getValue();
            controllerModels.sort(Comparator.comparing(ComponentModel::getClassName));
            controllerModels.addAll(subModels);
            ModelsSuperiorObjectDocumentationModel controllerDocumentation = generateModelsSuperiorObjectDocumentationModel(
                    controllerName + ".md", controllerModels, componentModelMap);
            documentationModels.add(controllerDocumentation);
        }
        return documentationModels;
    }

    private boolean isComponentModelExclusive(ComponentModel componentModel) {
        String docsLink = componentModel.getDocsLink() != null
                ? componentModel.getDocsLink().toUri().getPath()
                : null;
        return docsLink != null
                && docsLink.contains("client/")
                && !docsLink.contains("models/#");
    }

    private String absoluteFilePathToRef(String absoluteFilePath) {
        String md = ".md";
        String index = "index";

        String ref = absoluteFilePath;
        if (ref.endsWith(md)) {
            ref = ref.substring(0, ref.length() - md.length());
        }
        if (ref.endsWith(index)) {
            ref = ref.substring(0, ref.length() - index.length());
        }

        return ref;
    }

    private boolean isComponentReferencedExternally(String destinationPath, Class<?> targetClass, Map<String, ComponentModel> componentModelMap) {
        String absoluteFilePath = "/docs/client/models/" + destinationPath;

        String absolutePath = absoluteFilePathToRef(absoluteFilePath);
        if (targetClass == null || !componentModelMap.containsKey(targetClass.getSimpleName())) {
            return false;
        }

        Path docsLink = componentModelMap.get(targetClass.getSimpleName()).getDocsLink();
        if (docsLink == null) {
            return false;
        }
        String docsPath = docsLink.toUri().getPath();

        return !docsPath.contains(absolutePath);
    }

    private String getControllerNameFromDocsLink(Path docsLink) {
        String[] docsLinkSplit = docsLink.toUri().getPath().split("/");
        if (docsLinkSplit.length < 2) {
            return null;
        }
        return docsLinkSplit[docsLinkSplit.length - 2];
    }

    private ModelsSuperiorObjectDocumentationModel generateModelsSuperiorObjectDocumentationModel(String destinationPath,
                                                                                                  Collection<ComponentModel> componentModels,
                                                                                                  Map<String, ComponentModel> componentModelMap) {
        List<ComponentModel> topLevelModels = componentModels.stream()
                .filter(componentModel -> componentModel.getDocsLink() != null)
                .sorted(Comparator.comparing(ComponentModel::getClassName))
                .collect(Collectors.toList());

        Map<Class<?>, ModelsObjectDocumentationModel> renderedModelObjects = new HashMap<>();
        List<ModelsObjectDocumentationModel> renderedObjectsList = new LinkedList<>();

        for (ComponentModel model : topLevelModels) {
            if (renderedModelObjects.containsKey(model.getReflectedClass()) || isComponentReferencedExternally(destinationPath, model.getReflectedClass(), componentModelMap)) {
                continue;
            }
            List<ModelsObjectDocumentationModel> generatedModels =
                    generateModelObjectDocumentationModelRecursive(destinationPath, model.getReflectedClass(), renderedModelObjects, componentModelMap);
            renderedObjectsList.addAll(generatedModels);
        }

        ModelsSuperiorObjectDocumentationModel modelsSuperiorObjectDocumentationModel = new ModelsSuperiorObjectDocumentationModel();
        modelsSuperiorObjectDocumentationModel.setClassObjects(renderedObjectsList);
        modelsSuperiorObjectDocumentationModel.setLocationFilePath(destinationPath);
        return modelsSuperiorObjectDocumentationModel;
    }

    private String getObjectSimpleName(String name) {
        return CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, name);
    }

    /**
     * Create a yaml documentation in recursive for given class and add them to map.
     *
     * @param targetClass    Class for which fields to generate documentation.
     * @param visitedObjects Data structure to add created model.
     */
    private List<ModelsObjectDocumentationModel> generateModelObjectDocumentationModelRecursive(String destinationPath,
                                                                                                Class<?> targetClass,
                                                                                                Map<Class<?>, ModelsObjectDocumentationModel> visitedObjects,
                                                                                                Map<String, ComponentModel> componentModelMap) {
        List<ModelsObjectDocumentationModel> result = new LinkedList<>();
        if (targetClass != null && !visitedObjects.containsKey(targetClass)
                && !isComponentReferencedExternally(destinationPath, targetClass, componentModelMap)) {
            visitedObjects.put(targetClass, null);

            ModelsObjectDocumentationModel modelsObjectDocumentationModel = new ModelsObjectDocumentationModel();
            List<ModelsDocumentationModel> modelsDocumentationModels = new ArrayList<>();

            ClassJavadoc classJavadoc = RuntimeJavadoc.getJavadoc(targetClass);
            if (classJavadoc != null) {
                if (classJavadoc.getComment() != null) {
                    String formattedClassComment = commentFormatter.format(classJavadoc.getComment());
                    modelsObjectDocumentationModel.setClassDescription(formattedClassComment);
                }
            }

            if (targetClass.getSuperclass() != null) {
                Class<?> superClass = targetClass.getSuperclass();
                if (isGenericSuperclass(superClass)) {
                    Type genericSuperclass = targetClass.getGenericSuperclass();
                    result.addAll(processGenericTypes(destinationPath, genericSuperclass, visitedObjects, componentModelMap));
                }
            }

            ClassInfo classInfo = reflectionService.getClassInfoForClass(targetClass);
            List<FieldInfo> infoFields = classInfo.getFields();

            modelsObjectDocumentationModel.setClassFullName(classInfo.getReflectedClass().getName());
            modelsObjectDocumentationModel.setClassSimpleName(classInfo.getReflectedClass().getSimpleName());
            modelsObjectDocumentationModel.setReflectedClass(classInfo.getReflectedClass());
            modelsObjectDocumentationModel.setObjectClassPath(
                    Path.of("/docs/client/models/").resolve("#" + classInfo.getReflectedClass().getSimpleName())
            );

            for (FieldInfo info : infoFields) {
                if (info.getDataType() == null) {
                    continue;
                }

                if (info.getDataType().equals(ParameterDataType.object_type) || (info.getDataType().equals(ParameterDataType.enum_type) && info.getClazz() != null)) {
                    if (info.getClazz().getName().contains("java.") || info.getClazz().getName().contains("float")) {
                        continue;
                    }
                    result.addAll(generateModelObjectDocumentationModelRecursive(destinationPath, info.getClazz(), visitedObjects, componentModelMap));
                }

                ModelsDocumentationModel modelsDocumentationModel = new ModelsDocumentationModel();
                modelsDocumentationModel.setClassNameUsedOnTheField(info.getClazz().getSimpleName());

                ComponentModel componentModel = componentModelMap.get(info.getClazz().getSimpleName());
                if (componentModel != null && componentModel.getDocsLink() != null) {
                    modelsDocumentationModel.setClassUsedOnTheFieldPath(componentModel.getDocsLink().toString());
                    modelsDocumentationModel.setDataType(ParameterDataType.object_type);
                } else {
                    modelsDocumentationModel.setDataType(info.getDataType());
                    modelsDocumentationModel.setClassUsedOnTheFieldPath(
                            "#" + modelsDocumentationModel.getClassNameUsedOnTheField());
                }

                modelsDocumentationModel.setClassFieldName(info.getClassFieldName());
                modelsDocumentationModel.setYamlFieldName(info.getYamlFieldName());
                modelsDocumentationModel.setDisplayName(info.getDisplayName());
                modelsDocumentationModel.setHelpText(info.getHelpText());
                modelsDocumentationModel.setClazz(info.getClazz());
                modelsDocumentationModel.setEnumValuesByName(info.getEnumValuesByName());
                modelsDocumentationModel.setDefaultValue(info.getDefaultValue());
                modelsDocumentationModel.setSampleValues(info.getSampleValues());

                modelsDocumentationModels.add(modelsDocumentationModel);
            }

            modelsObjectDocumentationModel.setObjectFields(modelsDocumentationModels);

            ComponentModel objectComponentModel = componentModelMap.get(targetClass.getSimpleName());
            if (objectComponentModel != null && objectComponentModel.getObjectSchema().getEnum() != null) {
                List<String> enumValues = objectComponentModel.getObjectSchema().getEnum().stream()
                        .map(v -> (String)v)
                        .collect(Collectors.toList());
                modelsObjectDocumentationModel.setEnumValues(enumValues);
            }

            visitedObjects.put(targetClass, modelsObjectDocumentationModel);
            result.add(modelsObjectDocumentationModel);
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
    private List<ModelsObjectDocumentationModel> processGenericTypes(String destinationPath, Type type, Map<Class<?>, ModelsObjectDocumentationModel> visitedObjects, Map<String, ComponentModel> componentModelMap) {
        if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type;
            Type[] typeArguments = parameterizedType.getActualTypeArguments();
            for (Type typeArgument : typeArguments) {
                if (typeArgument instanceof Class) {
                    Class<?> genericClass = (Class<?>) typeArgument;
                    if (!isJavaClass(genericClass)) {
                        return generateModelObjectDocumentationModelRecursive(destinationPath, genericClass, visitedObjects, componentModelMap);
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

