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
import com.dqops.utils.docs.DocumentationReflectionService;
import com.dqops.utils.docs.DocumentationReflectionServiceImpl;
import com.dqops.utils.docs.TypeModel;
import com.dqops.utils.docs.client.apimodel.ComponentModel;
import com.dqops.utils.reflection.ClassInfo;
import com.dqops.utils.reflection.FieldInfo;
import com.dqops.utils.reflection.ObjectDataType;
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

    private final DocumentationReflectionService documentationReflectionService = new DocumentationReflectionServiceImpl(new ReflectionServiceImpl());
    private static final CommentFormatter commentFormatter = new CommentFormatter();

    @Override
    public ModelsSuperiorObjectDocumentationModel createDocumentationForSharedModels(Collection<ComponentModel> componentModels) {
        List<ComponentModel> sharedModels = componentModels.stream()
                .filter(componentModel -> componentModel.getDocsLink() == null
                        || componentModel.getDocsLink().toUri().getPath().contains("models/#"))
                .sorted(Comparator.comparing(ComponentModel::getClassName))
                .collect(Collectors.toList());

        Map<String, ComponentModel> componentModelMap = componentModels.stream().collect(Collectors.toMap(ComponentModel::getClassName, Function.identity()));

        return generateModelsSuperiorObjectDocumentationModel("index.md", sharedModels, componentModelMap);
    }

    @Override
    public List<ModelsSuperiorObjectDocumentationModel> createDocumentationForModels(Collection<ComponentModel> componentModels) {
        Map<String, ComponentModel> componentModelMap = componentModels.stream().collect(Collectors.toMap(ComponentModel::getClassName, Function.identity()));

        List<ComponentModel> exclusiveModels = componentModels.stream()
                .filter(this::isComponentModelExclusive)
                .collect(Collectors.toList());

        Map<String, List<ComponentModel>> modelsForController = new TreeMap<>();
        for (ComponentModel model : exclusiveModels) {
            String controllerName = getControllerNameFromDocsLink(model.getDocsLink());
            if (!modelsForController.containsKey(controllerName)) {
                modelsForController.put(controllerName, new ArrayList<>());
            }
            modelsForController.get(controllerName).add(model);
        }

        List<ModelsSuperiorObjectDocumentationModel> documentationModels = new ArrayList<>();
        for (Map.Entry<String, List<ComponentModel>> controllerModelsEntry : modelsForController.entrySet()) {
            List<ComponentModel> subModels = componentModels.stream()
                    .filter(componentModel -> componentModel.getDocsLink() == null)
                    .collect(Collectors.toList());
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

            ClassInfo classInfo = documentationReflectionService.getClassInfoForClass(targetClass);
            List<FieldInfo> infoFields = classInfo.getFields();

            modelsObjectDocumentationModel.setClassFullName(classInfo.getReflectedClass().getName());
            modelsObjectDocumentationModel.setClassSimpleName(classInfo.getReflectedClass().getSimpleName());
            modelsObjectDocumentationModel.setReflectedClass(classInfo.getReflectedClass());
            modelsObjectDocumentationModel.setObjectClassPath(
                    Path.of(absoluteFilePathToRef("/docs/client/models/" + destinationPath))
                            .resolve("#" + classInfo.getReflectedClass().getSimpleName())
            );

            for (FieldInfo info : infoFields) {
                if (info.getDataType() == null) {
                    continue;
                }

                ModelsDocumentationModel modelsDocumentationModel = new ModelsDocumentationModel();
                Type infoType = Objects.requireNonNullElse(info.getGenericDataType(), info.getClazz());
                TypeModel infoTypeModel = getObjectsTypeModel(infoType, componentModelMap);
                modelsDocumentationModel.setTypeModel(infoTypeModel);

                if (info.getDataType().equals(ParameterDataType.object_type) || (info.getDataType().equals(ParameterDataType.enum_type) && info.getClazz() != null)) {
                    if (info.getClazz().getName().contains("float")) {
                        // Float values
                        continue;
                    } else if (!info.getClazz().getName().contains("java.")) {
                        // Custom objects
                        result.addAll(generateModelObjectDocumentationModelRecursive(destinationPath, info.getClazz(), visitedObjects, componentModelMap));
                    } else if (info.getObjectDataType() == ObjectDataType.list_type) {
                        // Objects that can be rendered as lists
                        TypeModel genericType = infoTypeModel.getGenericKeyType();
                        result.addAll(generateModelObjectDocumentationGeneralized(destinationPath, genericType, visitedObjects, componentModelMap));
                    } else if (info.getObjectDataType() == ObjectDataType.map_type) {
                        // Objects that can be rendered as maps
                        TypeModel genericTypeK = infoTypeModel.getGenericKeyType();
                        TypeModel genericTypeV = infoTypeModel.getGenericValueType();
                        result.addAll(generateModelObjectDocumentationGeneralized(destinationPath, genericTypeK, visitedObjects, componentModelMap));
                        result.addAll(generateModelObjectDocumentationGeneralized(destinationPath, genericTypeV, visitedObjects, componentModelMap));
                    } else {
                        // Java std objects that cannot be rendered as maps
                        continue;
                    }
                }

                modelsDocumentationModel.setClassFieldName(info.getClassFieldName());
                modelsDocumentationModel.setYamlFieldName(info.getYamlFieldName());
                modelsDocumentationModel.setDisplayName(info.getDisplayName());
                modelsDocumentationModel.setHelpText(info.getHelpText());
                modelsDocumentationModel.setEnumValuesByName(info.getEnumValuesByName());
                modelsDocumentationModel.setDefaultValue(info.getDefaultValue());
                modelsDocumentationModel.setSampleValues(info.getSampleValues());

                modelsDocumentationModels.add(modelsDocumentationModel);
            }

            modelsObjectDocumentationModel.setObjectFields(modelsDocumentationModels);

            ComponentModel objectComponentModel = componentModelMap.get(targetClass.getSimpleName());
            if (objectComponentModel != null) {
                if (objectComponentModel.getObjectSchema().getEnum() != null) {
                    List<String> enumValues = objectComponentModel.getObjectSchema().getEnum().stream()
                            .map(v -> (String)v)
                            .collect(Collectors.toList());
                    modelsObjectDocumentationModel.setEnumValues(enumValues);
                }
                objectComponentModel.setDocsLink(modelsObjectDocumentationModel.getObjectClassPath());
            }

            visitedObjects.put(targetClass, modelsObjectDocumentationModel);
            result.add(modelsObjectDocumentationModel);
        }
        return result;
    }

    private List<ModelsObjectDocumentationModel> generateModelObjectDocumentationGeneralized(String destinationPath,
                                                                                             TypeModel typeModel,
                                                                                             Map<Class<?>, ModelsObjectDocumentationModel> visitedObjects,
                                                                                             Map<String, ComponentModel> componentModelMap) {
        if (typeModel.getDataType() == ParameterDataType.enum_type || typeModel.getObjectDataType() == ObjectDataType.object_type) {
            return generateModelObjectDocumentationModelRecursive(destinationPath, typeModel.getClazz(), visitedObjects, componentModelMap);
        } else {
            return new ArrayList<>();
        }
    }

    private TypeModel getObjectsTypeModel(Type type, Map<String, ComponentModel> componentModelMap) {
        TypeModel typeModel = new TypeModel();

        Class<?> clazz;
        ParameterizedType genericType;
        FieldInfo fieldInfoContainer = new FieldInfo();

        if (type instanceof ParameterizedType) {
            genericType = (ParameterizedType) type;
            clazz = (Class<?>) genericType.getRawType();
        } else {
            clazz = (Class<?>) type;
            genericType = null;

            ComponentModel componentModel = componentModelMap.get(clazz.getSimpleName());
            if (componentModel != null && componentModel.getDocsLink() != null) {
                typeModel.setClassUsedOnTheFieldPath(componentModel.getDocsLink().toString());
            } else {
                typeModel.setClassUsedOnTheFieldPath("#" + clazz.getSimpleName());
            }
        }

        ParameterDataType parameterDataType = documentationReflectionService.getReflectionService().determineParameterDataType(clazz, genericType, fieldInfoContainer);

        String classSimpleName = clazz.getSimpleName();
        typeModel.setClassNameUsedOnTheField(classSimpleName);
        typeModel.setClazz(clazz);
        typeModel.setDataType(parameterDataType);
        typeModel.setObjectDataType(fieldInfoContainer.getObjectDataType());

        if (parameterDataType == ParameterDataType.object_type ||
                (parameterDataType == ParameterDataType.enum_type && typeModel.getClassUsedOnTheFieldPath() != null)) {
            ParameterizedType parameterizedType = fieldInfoContainer.getGenericDataType();
            ObjectDataType objectDataType = Objects.requireNonNullElse(fieldInfoContainer.getObjectDataType(), ObjectDataType.object_type);
            switch (objectDataType) {
                case object_type:
                    break;

                case list_type:
                    Type listType = parameterizedType.getActualTypeArguments()[0];
                    TypeModel listTypeModel = getObjectsTypeModel(listType, componentModelMap);
                    typeModel.setGenericKeyType(listTypeModel);
                    break;

                case map_type:
                    Type keyType = parameterizedType.getActualTypeArguments()[0];
                    TypeModel keyTypeModel = getObjectsTypeModel(keyType, componentModelMap);
                    typeModel.setGenericKeyType(keyTypeModel);

                    Type valueType = parameterizedType.getActualTypeArguments()[1];
                    TypeModel valueTypeModel = getObjectsTypeModel(valueType, componentModelMap);
                    typeModel.setGenericValueType(valueTypeModel);
                    break;

                default:
                    throw new RuntimeException(new NoSuchFieldException("Enum value is not present"));
            }
        }

        return typeModel;
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

