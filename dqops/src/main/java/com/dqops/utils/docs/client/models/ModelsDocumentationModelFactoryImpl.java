/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.utils.docs.client.models;

import com.dqops.metadata.fields.ParameterDataType;
import com.dqops.utils.docs.DocumentationReflectionService;
import com.dqops.utils.docs.DocumentationReflectionServiceImpl;
import com.dqops.utils.docs.generators.TypeModel;
import com.dqops.utils.docs.client.apimodel.ComponentModel;
import com.dqops.utils.reflection.ClassInfo;
import com.dqops.utils.reflection.FieldInfo;
import com.dqops.utils.reflection.ObjectDataType;
import com.dqops.utils.reflection.ReflectionServiceImpl;
import com.github.therapi.runtimejavadoc.ClassJavadoc;
import com.github.therapi.runtimejavadoc.CommentFormatter;
import com.github.therapi.runtimejavadoc.RuntimeJavadoc;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.nio.file.Path;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ModelsDocumentationModelFactoryImpl implements ModelsDocumentationModelFactory {

    public static final String SHARED_MODELS_IDENTIFIER = "common";

    private final DocumentationReflectionService documentationReflectionService = new DocumentationReflectionServiceImpl(new ReflectionServiceImpl());
    private static final CommentFormatter commentFormatter = new CommentFormatter();
    private static final Set<Class<?>> excludedClasses = Set.of(
            Object.class
    );

    @Override
    public ModelsSuperiorObjectDocumentationModel createDocumentationForSharedModels(Collection<ComponentModel> componentModels) {
        List<ComponentModel> sharedModels = componentModels.stream()
                .filter(componentModel -> componentModel.getDocsLink() == null
                        || componentModel.getDocsLink().toUri().getPath().contains(SHARED_MODELS_IDENTIFIER))
                .sorted(Comparator.comparing(ComponentModel::getClassName))
                .collect(Collectors.toList());

        Map<String, ComponentModel> componentModelMap = componentModels.stream()
                .collect(Collectors.toMap(
                        ComponentModel::getClassName,
                        Function.identity(),
                        (key, value) -> value,
                        LinkedHashMap::new));

        ModelsSuperiorObjectDocumentationModel modelsSuperiorObjectDocumentationModel =
                generateModelsSuperiorObjectDocumentationModel(SHARED_MODELS_IDENTIFIER + ".md",
                    "common",
                    sharedModels,
                    componentModelMap);
        return modelsSuperiorObjectDocumentationModel;
    }

    @Override
    public List<ModelsSuperiorObjectDocumentationModel> createDocumentationForModels(Collection<ComponentModel> componentModels) {
        Map<String, ComponentModel> componentModelMap = componentModels.stream()
                .collect(Collectors.toMap(
                        ComponentModel::getClassName,
                        Function.identity(),
                        (key, value) -> value,
                        LinkedHashMap::new));

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
                    controllerName + ".md", controllerName, controllerModels, componentModelMap);
            documentationModels.add(controllerDocumentation);
        }
        documentationModels.sort(
                Comparator.comparing(ModelsSuperiorObjectDocumentationModel::getLocationFilePath));
        return documentationModels;
    }

    private boolean isComponentModelExclusive(ComponentModel componentModel) {
        String docsLink = componentModel.getDocsLink() != null
                ? componentModel.getDocsLink().toUri().getPath()
                : null;
        return docsLink != null
                && docsLink.contains("client/")
                && !docsLink.contains(SHARED_MODELS_IDENTIFIER);
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
        if (docsLinkSplit.length == 0) {
            return null;
        }

        String controllerReference;
        String last = docsLinkSplit[docsLinkSplit.length - 1];
        if (last.charAt(0) == '#') {
            // '/' between controller name and component anchor '#'
            if (docsLinkSplit.length == 1) {
                return null;
            }

            controllerReference = docsLinkSplit[docsLinkSplit.length - 2];
        } else {
            // anchor '#' directly after controller name
             int indexOfAnchor = last.indexOf('#');
             if (indexOfAnchor == -1) {
                 // No anchor
                 controllerReference = last;
             } else {
                 controllerReference = last.substring(0, indexOfAnchor);
             }
        }

        String mdExtension = ".md";
        if (controllerReference.endsWith(mdExtension)) {
            return controllerReference.substring(0, controllerReference.length() - mdExtension.length());
        }
        return controllerReference;
    }

    private ModelsSuperiorObjectDocumentationModel generateModelsSuperiorObjectDocumentationModel(String destinationPath,
                                                                                                  String modelGroupName,
                                                                                                  Collection<ComponentModel> componentModels,
                                                                                                  Map<String, ComponentModel> componentModelMap) {
        List<ComponentModel> topLevelModels = componentModels.stream()
                .filter(componentModel -> componentModel.getDocsLink() != null)
                .sorted(Comparator.comparing(ComponentModel::getClassName))
                .collect(Collectors.toList());

        Map<Class<?>, ModelsObjectDocumentationModel> renderedModelObjects = new LinkedHashMap<>();
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
        modelsSuperiorObjectDocumentationModel.setModelsGroupName(modelGroupName);
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
                && !isComponentReferencedExternally(destinationPath, targetClass, componentModelMap)
                && !excludedClasses.contains(targetClass)) {
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
                    Path.of("/docs/client/models/" + ensureEndsWithFileExtension(destinationPath) + "#"
                                    + classInfo.getReflectedClass().getSimpleName().toLowerCase()).toString()
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
                objectComponentModel.setDocsLink(Path.of(modelsObjectDocumentationModel.getObjectClassPath()));
            }

            visitedObjects.put(targetClass, modelsObjectDocumentationModel);
            result.add(modelsObjectDocumentationModel);
        }
        return result;
    }

    private String ensureEndsWithFileExtension(String s) {
        String mdExtension = ".md";
        if (s.endsWith(mdExtension)) {
            return s;
        }
        return s + mdExtension;
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
        Function<Class<?>, String> linkAccessor = (clazz) -> {
            if (excludedClasses.contains(clazz)) {
                return null;
            }

            String simpleClassName = clazz.getSimpleName();
            ComponentModel componentModel = componentModelMap.get(simpleClassName);
            if (componentModel != null && componentModel.getDocsLink() != null) {
                return componentModel.getDocsLink().toString();
            }

            return "#" + simpleClassName.toLowerCase();
        };

        return documentationReflectionService.getObjectsTypeModel(type, linkAccessor);
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

