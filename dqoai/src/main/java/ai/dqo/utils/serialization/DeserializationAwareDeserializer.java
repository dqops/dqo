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
