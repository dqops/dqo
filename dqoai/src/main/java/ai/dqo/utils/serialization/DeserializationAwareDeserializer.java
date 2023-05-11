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
package ai.dqo.utils.serialization;

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
