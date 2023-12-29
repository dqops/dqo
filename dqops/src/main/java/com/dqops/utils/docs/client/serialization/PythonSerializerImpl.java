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
package com.dqops.utils.docs.client.serialization;

import com.dqops.metadata.fields.ParameterDataType;
import com.dqops.utils.docs.generators.ParsedSampleObject;
import com.dqops.utils.docs.generators.ParsedSampleObjectFactory;
import com.dqops.utils.reflection.ClassInfo;
import com.dqops.utils.reflection.FieldInfo;
import com.dqops.utils.reflection.ObjectDataType;
import com.dqops.utils.string.StringCaseFormat;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Python serializer.
 */
@Component
public class PythonSerializerImpl implements PythonSerializer {
    private static final String indent = "\t";
    private static final String newLine = "\n";

    private final ParsedSampleObjectFactory parsedSampleObjectFactory;

    public PythonSerializerImpl(ParsedSampleObjectFactory parsedSampleObjectFactory) {
        this.parsedSampleObjectFactory = parsedSampleObjectFactory;
    }

    /**
     * Serializes an object as a Python snippet creating a new instance of this object.
     * @param source Source object to be serialized.
     * @return Object serialized as a Python snippet.
     */
    @Override
    public String serialize(Object source) {
        String prettyPrint = serializePrettyPrint(source);
        String collapsed = prettyPrint.replaceAll(",[ \n\t]+", ", ")
                .replaceAll("\n[ \t]*", "");
        return collapsed;
    }

    /**
     * Serializes an object as a Python snippet creating a new instance of this object, using a pretty print writer.
     * @param source Source object to be serialized.
     * @return Object serialized as a Python snippet, formatted.
     */
    @Override
    public String serializePrettyPrint(Object source) {
        ParsedSampleObject parsedSource = parsedSampleObjectFactory.parseSampleObject(source);
        return serializeParsedObjectPrettyPrint(parsedSource);
    }

    private static String convertJavaVariableNameToPythonName(String javaVariableName) {
        return StringCaseFormat.LOWER_CAMEL.to(StringCaseFormat.LOWER_UNDERSCORE_SEPARATE_NUMBER, javaVariableName);
    }

    private String serializeParsedObjectPrettyPrint(ParsedSampleObject parsedSource) {
        if (parsedSource.getDataType() == ParameterDataType.object_type) {
            return serializeComplexParsedObjectPrettyPrint(parsedSource);
        } else {
            return serializeSimpleParsedObjectPrettyPrint(parsedSource);
        }
    }

    private String serializeSimpleParsedObjectPrettyPrint(ParsedSampleObject parsedSource) {
        Object source = parsedSource.getSource();

        switch (parsedSource.getDataType()) {
            case object_type:
                throw new IllegalArgumentException(parsedSource.getSource().getClass().getName()
                        + " is of type "
                        + parsedSource.getDataType());

            case integer_list_type:
                List<?> integerslist = (List<?>) source;
                List<String> integerElementsList = integerslist.stream()
                        .map(Object::toString)
                        .collect(Collectors.toList());
                return "[" + String.join(", ", integerElementsList) + "]";

            case string_list_type:
                List<?> listSource = (List<?>) source;
                List<String> elementsList = listSource.stream()
                        .map(Object::toString)
                        .map(s -> String.format("'%s'", s))
                        .collect(Collectors.toList());
                return "[" + String.join(", ", elementsList) + "]";

            case column_name_type:
            case string_type:
                String stringSource = (String) source;
                return "'" + stringSource + "'";

            case enum_type:
                Enum<?> sourceEnum = (Enum<?>) source;
                return source.getClass().getSimpleName() + "." + sourceEnum.name();

            case boolean_type:
                Boolean sourceBoolean = (Boolean) source;
                return sourceBoolean ? "True" : "False";

            case date_type:
            case datetime_type:
                // TODO: serialize date/time to Python.
                return "Some date/time value: [" + source.toString() + "]";

            default:
                return source.toString();
        }
    }

    private String serializeComplexParsedObjectPrettyPrint(ParsedSampleObject parsedSource) {
        if (parsedSource.getDataType() != ParameterDataType.object_type) {
            throw new IllegalArgumentException(parsedSource.getSource().getClass().getName()
                    + " is of type "
                    + parsedSource.getDataType());
        }

        if (parsedSource.getClassInfo() != null) {
            ClassInfo sourceClassInfo = parsedSource.getClassInfo();

            List<String> serializedFields = new ArrayList<>();
            for (FieldInfo fieldInfo : sourceClassInfo.getFields()) {
                Method fieldGetter = fieldInfo.getGetterMethod();
                if (fieldGetter == null) {
                    continue;
                }
                
                String serializedFieldValue;
                try {
                    Object fieldValue = fieldGetter.invoke(parsedSource.getSource());
                    if (fieldValue == null) {
                        continue;
                    }

                    serializedFieldValue = serializePrettyPrint(fieldValue);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    continue;
                }

                String fieldPythonName = convertJavaVariableNameToPythonName(fieldInfo.getClassFieldName());
                String serializedField = fieldPythonName + "=" + serializedFieldValue;
                serializedFields.add(indent + serializedField.replace(newLine, newLine + indent));
            }

            StringBuilder serializedObject = new StringBuilder();
            serializedObject.append(sourceClassInfo.getReflectedClass().getSimpleName())
                    .append("(");
            if (serializedFields.size() <= 1) {
                serializedFields.forEach(serializedObject::append);
            } else {
                serializedObject.append(newLine)
                        .append(String.join("," + newLine, serializedFields))
                        .append(newLine);
            }

            serializedObject.append(")");
            return serializedObject.toString();
        }

        if (parsedSource.getListElements() != null) {
            List<String> elementsList = parsedSource.getListElements().stream()
                    .map(this::serializeParsedObjectPrettyPrint)
                    .map(s -> indent + s.replace(newLine, newLine + indent))
                    .collect(Collectors.toList());
            return "[" + newLine
                    + String.join("," + newLine, elementsList) + newLine
                    + "]";
        }

        if (parsedSource.getMapElements() != null) {
            Map<String, ParsedSampleObject> elementsMap = parsedSource.getMapElements();
            List<String> serializedEntries = elementsMap.entrySet().stream()
                    .map(entry -> "'" + entry.getKey() + "': " + serializeParsedObjectPrettyPrint(entry.getValue()))
                    .map(s -> indent + s.replace(newLine, newLine + indent))
                    .collect(Collectors.toList());
            return "{" + newLine
                    + String.join("," + newLine, serializedEntries) + newLine
                    + "}";
        }

        throw new IllegalArgumentException(parsedSource.getSource().getClass().getName()
                + " doesn't fit any of the known " + ObjectDataType.class.getSimpleName() + "s");
    }
}
