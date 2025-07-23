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

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.deser.std.DelegatingDeserializer;

import java.io.IOException;

/**
 * Delegating deserializer that supports only classes that implement the {@link DeserializationAware} interface
 * and want to be notified when the whole object was deserialized.
 */
public class DeserializationAwareDeserializer extends DelegatingDeserializer {
    public DeserializationAwareDeserializer(JsonDeserializer<?> defaultDeserializer) {
        super(defaultDeserializer);
    }

    @Override
    protected JsonDeserializer<?> newDelegatingInstance(JsonDeserializer<?> jsonDeserializer) {
        return new DeserializationAwareDeserializer(jsonDeserializer);
    }

    @Override
    public Object deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        Object deserializedInstance = super.deserialize(p, ctxt);
        DeserializationAware deserializationAwareInstance = (DeserializationAware) deserializedInstance;
        deserializationAwareInstance.onDeserialized();
        return deserializedInstance;
    }
}
