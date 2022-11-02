package ai.dqo.utils.serialization;

/**
 * Interface implemented by classes that are deserialized by a custom deserializer {@link }
 */
public interface DeserializationAware {
    /**
     * Called after the object was deserialized from JSON or YAML.
     */
    void onDeserialized();
}
