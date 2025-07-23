/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.utils.docs.client.operations.examples.serialization;

import com.dqops.metadata.fields.ParameterDataType;
import com.dqops.utils.docs.client.operations.examples.PathParameterFillerUtility;
import com.dqops.utils.docs.generators.ParsedSampleObject;
import com.dqops.utils.docs.generators.ParsedSampleObjectFactory;
import com.dqops.utils.reflection.ClassInfo;
import com.dqops.utils.reflection.FieldInfo;
import com.dqops.utils.reflection.ObjectDataType;
import com.dqops.utils.string.StringCaseFormat;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.Instant;
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
            if (parsedSource.getSource().getClass().equals(Instant.class)) {
                return "'" + parsedSource.getSource().toString() + "'";
            }
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
                return source.getClass().getSimpleName() + "." + sourceEnum.name().toUpperCase();

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
                serializedFields.add(serializedField.replace(newLine, newLine + indent));
            }

            StringBuilder serializedObject = new StringBuilder();
            String javaSimpleClassName = PathParameterFillerUtility.getJavaSimpleClassName(sourceClassInfo.getReflectedClass());
            serializedObject.append(javaSimpleClassName)
                    .append("(");
            if (serializedFields.size() > 1 || serializedFields.stream().anyMatch(f -> f.contains("\n"))) {
                serializedObject.append(newLine).append(indent)
                        .append(String.join("," + newLine + indent, serializedFields))
                        .append(newLine);
            } else {
                serializedFields.forEach(serializedObject::append);
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
