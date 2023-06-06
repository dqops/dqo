/*
 * Copyright © 2021 DQO.ai (support@dqo.ai)
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
package ai.dqo.utils.docs.yaml;

import ai.dqo.core.incidents.IncidentNotificationMessage;
import ai.dqo.metadata.fields.ParameterDataType;
import ai.dqo.metadata.storage.localfiles.dashboards.DashboardYaml;
import ai.dqo.metadata.storage.localfiles.ruledefinitions.RuleDefinitionYaml;
import ai.dqo.metadata.storage.localfiles.sensordefinitions.ProviderSensorYaml;
import ai.dqo.metadata.storage.localfiles.sensordefinitions.SensorDefinitionYaml;
import ai.dqo.metadata.storage.localfiles.settings.SettingsYaml;
import ai.dqo.metadata.storage.localfiles.sources.ConnectionYaml;
import ai.dqo.metadata.storage.localfiles.sources.TableYaml;
import ai.dqo.utils.reflection.ClassInfo;
import ai.dqo.utils.reflection.FieldInfo;
import ai.dqo.utils.reflection.ReflectionServiceImpl;
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
     *
     * @return Yaml superior documentation models.
     */
    @Override
    public List<YamlSuperiorObjectDocumentationModel> createDocumentationForYaml() {
        List<YamlSuperiorObjectDocumentationModel> yamlDocumentation = new ArrayList<>();

        List<Class<?>> yamlClasses = new ArrayList<>();
        yamlClasses.add(ConnectionYaml.class);
        yamlClasses.add(DashboardYaml.class);
        yamlClasses.add(ProviderSensorYaml.class);
        yamlClasses.add(RuleDefinitionYaml.class);
        yamlClasses.add(SensorDefinitionYaml.class);
        yamlClasses.add(SettingsYaml.class);
        yamlClasses.add(TableYaml.class);
        yamlClasses.add(IncidentNotificationMessage.class); // the incident notification message format

        for (Class<?> yamlClass : yamlClasses) {
            Map<Class<?>, YamlObjectDocumentationModel> yamlObjectDocumentationModels = new LinkedHashMap<>();
            generateYamlObjectDocumentationModelRecursive(yamlClass, yamlObjectDocumentationModels);

            YamlSuperiorObjectDocumentationModel yamlSuperiorObjectDocumentationModel = new YamlSuperiorObjectDocumentationModel();
            yamlSuperiorObjectDocumentationModel.setSuperiorClassFullName(yamlClass.getName());
            yamlSuperiorObjectDocumentationModel.setSuperiorClassSimpleName(yamlClass.getSimpleName());
            yamlSuperiorObjectDocumentationModel.setReflectedSuperiorClass(yamlClass);
            yamlSuperiorObjectDocumentationModel.setClassObjects(new ArrayList<>());

            for (Map.Entry<Class<?>, YamlObjectDocumentationModel> yamlObject : yamlObjectDocumentationModels.entrySet()) {
                yamlSuperiorObjectDocumentationModel.getClassObjects().add(yamlObject.getValue());
            }
            yamlDocumentation.add(yamlSuperiorObjectDocumentationModel);
        }
        return yamlDocumentation;
    }

    /**
     * Create a yaml documentation in recursive for given class and add them to map.
     *
     * @param targetClass    Class for which method generate documentation.
     * @param visitedObjects Data structure to add created model.
     */
    private void generateYamlObjectDocumentationModelRecursive(Class<?> targetClass, Map<Class<?>, YamlObjectDocumentationModel> visitedObjects) {
        if (!visitedObjects.containsKey(targetClass)) {
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
                    processGenericTypes(genericSuperclass, visitedObjects);
                }
            }

            ClassInfo classInfo = reflectionService.getClassInfoForClass(targetClass);
            List<FieldInfo> infoFields = classInfo.getFields();

            yamlObjectDocumentationModel.setClassFullName(classInfo.getReflectedClass().getName());
            yamlObjectDocumentationModel.setClassSimpleName(classInfo.getReflectedClass().getSimpleName());
            yamlObjectDocumentationModel.setReflectedClass(classInfo.getReflectedClass());

            for (FieldInfo info : infoFields) {
                if (info.getDataType().equals(ParameterDataType.object_type)) {
                    if (info.getClazz().getName().contains("java.") || info.getClazz().getName().contains("float")) {
                        continue;
                    } else {
                        generateYamlObjectDocumentationModelRecursive(info.getClazz(), visitedObjects);
                    }
                }
                YamlFieldsDocumentationModel yamlFieldsDocumentationModel = new YamlFieldsDocumentationModel();
                yamlFieldsDocumentationModel.setClassFieldName(info.getClassFieldName());
                yamlFieldsDocumentationModel.setYamlFieldName(info.getYamlFieldName());
                yamlFieldsDocumentationModel.setDisplayName(info.getDisplayName());
                yamlFieldsDocumentationModel.setHelpText(info.getHelpText());
                yamlFieldsDocumentationModel.setClazz(info.getClazz());
                yamlFieldsDocumentationModel.setClassNameUsedOnTheField(info.getClazz().getSimpleName());
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
    private void processGenericTypes(Type type, Map<Class<?>, YamlObjectDocumentationModel> visitedObjects) {
        if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type;
            Type[] typeArguments = parameterizedType.getActualTypeArguments();
            for (Type typeArgument : typeArguments) {
                if (typeArgument instanceof Class) {
                    Class<?> genericClass = (Class<?>) typeArgument;
                    if (!isJavaClass(genericClass)) {
                        generateYamlObjectDocumentationModelRecursive(genericClass, visitedObjects);
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

