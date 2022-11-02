package ai.dqo.utils.serialization;

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
