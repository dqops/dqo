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
                            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
                    parsedSampleObject.setMapElements(parsedSampleObjectMap);
                    break;
            }
        }

        return parsedSampleObject;
    }
}
