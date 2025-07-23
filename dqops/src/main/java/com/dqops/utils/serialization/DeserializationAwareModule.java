/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.utils.serialization;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.deser.BeanDeserializerModifier;
import com.fasterxml.jackson.databind.module.SimpleModule;

/**
 * Jackson module that adds a custom deserializer that notifies objects when they were deserialized.
 */
public class DeserializationAwareModule extends SimpleModule {
    public DeserializationAwareModule() {
        this.setDeserializerModifier(new BeanDeserializerModifier() {
            @Override
            public JsonDeserializer<?> modifyDeserializer(DeserializationConfig config,
                                                          BeanDescription beanDesc, JsonDeserializer<?> defaultDeserializer) {
                if (DeserializationAware.class.isAssignableFrom(beanDesc.getBeanClass())) {
                    return new DeserializationAwareDeserializer(defaultDeserializer);
                }
                return defaultDeserializer;
            }
        });
    }
}
