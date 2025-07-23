/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.utils.docs.generators;

import com.dqops.metadata.fields.ParameterDataType;
import com.dqops.utils.docs.DocumentationReflectionService;
import com.dqops.utils.reflection.ClassInfo;
import com.dqops.utils.reflection.ObjectDataType;
import com.google.common.collect.Streams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class ParsedSampleObjectFactoryImpl implements ParsedSampleObjectFactory {
    private final DocumentationReflectionService reflectionService;

    @Autowired
    public ParsedSampleObjectFactoryImpl(DocumentationReflectionService reflectionService) {
        this.reflectionService = reflectionService;
    }

    @Override
    public ParsedSampleObject parseSampleObject(Object sampleObject) {
        Class<?> sourceClass = sampleObject.getClass();

        TypeModel sourceTypeModel = reflectionService.getObjectsTypeModel(sourceClass, _s -> null);
        ParsedSampleObject parsedSampleObject = new ParsedSampleObject();
        parsedSampleObject.setSource(sampleObject);
        parsedSampleObject.setDataType(sourceTypeModel.getDataType());

        if (sourceTypeModel.getDataType() == ParameterDataType.object_type) {
            switch (sourceTypeModel.getObjectDataType()) {
                case object_type:
                    ClassInfo classInfo = reflectionService.getClassInfoForClass(sourceClass);
                    parsedSampleObject.setClassInfo(classInfo);
                    break;

                case list_type:
                    Iterable<?> iterable = (Iterable<?>) sampleObject;
                    List<ParsedSampleObject> parsedSampleObjectList = Streams.stream(iterable.iterator())
                            .map(this::parseSampleObject)
                            .collect(Collectors.toList());
                    parsedSampleObject.setListElements(parsedSampleObjectList);
                    break;

                case map_type:
                    Map<String, ?> keyMap = (Map<String, ?>) sampleObject;
                    Map<String, ParsedSampleObject> parsedSampleObjectMap = keyMap.entrySet().stream()
                            .map(entry -> Map.entry(entry.getKey(), parseSampleObject(entry.getValue())))
                            .collect(Collectors.toMap(
                                    Map.Entry::getKey,
                                    Map.Entry::getValue,
                                    (key, value) -> value,
                                    LinkedHashMap::new));
                    parsedSampleObject.setMapElements(parsedSampleObjectMap);
                    break;
            }
        }

        return parsedSampleObject;
    }
}
