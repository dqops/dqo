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
package com.dqops.utils.docs.yaml;

import com.dqops.metadata.fields.ParameterDataType;
import com.dqops.utils.reflection.ClassInfo;
import com.dqops.utils.reflection.FieldInfo;
import com.dqops.utils.reflection.ReflectionServiceImpl;
import com.github.therapi.runtimejavadoc.ClassJavadoc;
import com.github.therapi.runtimejavadoc.CommentFormatter;
import com.github.therapi.runtimejavadoc.RuntimeJavadoc;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Yaml documentation model factory that creates a yaml documentation.
 * It should be only used from post processor classes that are called by Maven during build.
 */
public class YamlDocumentationModelFactoryImpl implements YamlDocumentationModelFactory {

    private final ReflectionServiceImpl reflectionService = new ReflectionServiceImpl();
    private static final CommentFormatter commentFormatter = new CommentFormatter();

    /**
     * Create a yaml documentation models.
     * @param yamlDocumentationSchema Yaml documentation schema.
     * @return Yaml superior documentation models.
     */
    @Override
    public List<YamlSuperiorObjectDocumentationModel> createDocumentationForYaml(List<YamlDocumentationSchemaNode> yamlDocumentationSchema) {
        List<YamlSuperiorObjectDocumentationModel> yamlDocumentation = new ArrayList<>();
        Map<Class<?>, YamlObjectDocumentationModel> accumulatedYamlObjectModels = new LinkedHashMap<>();

        for (YamlDocumentationSchemaNode yamlDocumentationNode : yamlDocumentationSchema) {
            Class<?> yamlClass = yamlDocumentationNode.getClazz();
            YamlSuperiorObjectDocumentationModel yamlSuperiorObjectDocumentationModel = new YamlSuperiorObjectDocumentationModel();
            yamlSuperiorObjectDocumentationModel.setSuperiorClassFullName(yamlClass.getName());
            yamlSuperiorObjectDocumentationModel.setSuperiorClassSimpleName(yamlDocumentationNode.getPathToFile().toString());

            Map<Class<?>, YamlObjectDocumentationModel> yamlObjectDocumentationModels = new LinkedHashMap<>();
            generateYamlObjectDocumentationModelRecursive(yamlSuperiorObjectDocumentationModel.getSuperiorClassSimpleName(),
                    yamlClass, yamlObjectDocumentationModels, accumulatedYamlObjectModels);

            yamlSuperiorObjectDocumentationModel.setReflectedSuperiorClass(yamlClass);
            yamlSuperiorObjectDocumentationModel.setClassObjects(new ArrayList<>());

            for (Map.Entry<Class<?>, YamlObjectDocumentationModel> yamlObject : yamlObjectDocumentationModels.entrySet()) {
                yamlSuperiorObjectDocumentationModel.getClassObjects().add(yamlObject.getValue());
                accumulatedYamlObjectModels.put(yamlObject.getKey(), yamlObject.getValue());
            }
            yamlDocumentation.add(yamlSuperiorObjectDocumentationModel);
        }
        return yamlDocumentation;
    }

    /**
     * Create a yaml documentation in recursive for given class and add them to map.
     *
     * @param targetClass    Class for which fields to generate documentation.
     * @param visitedObjects Data structure to add created model.
     */
    private void generateYamlObjectDocumentationModelRecursive(String superiorObjectFileName,
                                                               Class<?> targetClass,
                                                               Map<Class<?>, YamlObjectDocumentationModel> visitedObjects,
                                                               Map<Class<?>, YamlObjectDocumentationModel> previouslyVisitedObjects) {
        if (!visitedObjects.containsKey(targetClass) && !previouslyVisitedObjects.containsKey(targetClass)) {
            visitedObjects.put(targetClass, null);

            YamlObjectDocumentationModel yamlObjectDocumentationModel = new YamlObjectDocumentationModel();
            List<YamlFieldsDocumentationModel> yamlFieldsDocumentationModels = new ArrayList<>();

            ClassJavadoc classJavadoc = RuntimeJavadoc.getJavadoc(targetClass);
            if (classJavadoc != null) {
                if (classJavadoc.getComment() != null) {
                    String formattedClassComment = commentFormatter.format(classJavadoc.getComment());
                    yamlObjectDocumentationModel.setClassDescription(formattedClassComment);
                }
            }

            if (targetClass.getSuperclass() != null) {
                Class<?> superClass = targetClass.getSuperclass();
                if (isGenericSuperclass(superClass)) {
                    Type genericSuperclass = targetClass.getGenericSuperclass();
                    processGenericTypes(superiorObjectFileName, genericSuperclass, visitedObjects, previouslyVisitedObjects);
                }
            }

            ClassInfo classInfo = reflectionService.getClassInfoForClass(targetClass);
            List<FieldInfo> infoFields = classInfo.getFields();

            yamlObjectDocumentationModel.setClassFullName(classInfo.getReflectedClass().getName());
            yamlObjectDocumentationModel.setClassSimpleName(classInfo.getReflectedClass().getSimpleName());
            yamlObjectDocumentationModel.setReflectedClass(classInfo.getReflectedClass());
            yamlObjectDocumentationModel.setClassLocationFile(superiorObjectFileName);

            for (FieldInfo info : infoFields) {
                if (info.getDataType().equals(ParameterDataType.object_type)) {
                    if (info.getClazz().getName().contains("java.") || info.getClazz().getName().contains("float")) {
                        continue;
                    }
                    generateYamlObjectDocumentationModelRecursive(superiorObjectFileName, info.getClazz(), visitedObjects, previouslyVisitedObjects);
                }

                YamlFieldsDocumentationModel yamlFieldsDocumentationModel = new YamlFieldsDocumentationModel();
                yamlFieldsDocumentationModel.setClassNameUsedOnTheField(info.getClazz().getSimpleName());

                if (previouslyVisitedObjects.containsKey(info.getClazz())) {
                    YamlObjectDocumentationModel infoClassObject = previouslyVisitedObjects.get(info.getClazz());
                    // Assumption: superior objects located in nested paths don't contain references to other files.
                    yamlFieldsDocumentationModel.setClassUsedOnTheFieldPath("../" + infoClassObject.getClassLocationFile() +
                            "/#" + yamlFieldsDocumentationModel.getClassNameUsedOnTheField());
                } else {
                    yamlFieldsDocumentationModel.setClassUsedOnTheFieldPath(
                            "#" + yamlFieldsDocumentationModel.getClassNameUsedOnTheField());
                }

                yamlFieldsDocumentationModel.setClassFieldName(info.getClassFieldName());
                yamlFieldsDocumentationModel.setYamlFieldName(info.getYamlFieldName());
                yamlFieldsDocumentationModel.setDisplayName(info.getDisplayName());
                yamlFieldsDocumentationModel.setHelpText(info.getHelpText());
                yamlFieldsDocumentationModel.setClazz(info.getClazz());
                yamlFieldsDocumentationModel.setDataType(info.getDataType());
                yamlFieldsDocumentationModel.setEnumValuesByName(info.getEnumValuesByName());
                yamlFieldsDocumentationModel.setDefaultValue(info.getDefaultValue());
                yamlFieldsDocumentationModel.setSampleValues(info.getSampleValues());

                yamlFieldsDocumentationModels.add(yamlFieldsDocumentationModel);

            }
            yamlObjectDocumentationModel.setObjectFields(yamlFieldsDocumentationModels);
            visitedObjects.put(targetClass, yamlObjectDocumentationModel);
        }
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
    private void processGenericTypes(String superiorObjectFileName, Type type, Map<Class<?>, YamlObjectDocumentationModel> visitedObjects,
                                     Map<Class<?>, YamlObjectDocumentationModel> previouslyVisitedObjects) {
        if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type;
            Type[] typeArguments = parameterizedType.getActualTypeArguments();
            for (Type typeArgument : typeArguments) {
                if (typeArgument instanceof Class) {
                    Class<?> genericClass = (Class<?>) typeArgument;
                    if (!isJavaClass(genericClass)) {
                        generateYamlObjectDocumentationModelRecursive(superiorObjectFileName, genericClass, visitedObjects, previouslyVisitedObjects);
                    }
                }
            }
        }
    }

    /**
     * Checks if the specified class is a java inbuilt class
     */
    private boolean isJavaClass(Class<?> clazz) {
        return clazz.getClassLoader() == null;
    }
}

