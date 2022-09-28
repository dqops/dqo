package ai.dqo.utils.reflection;

/**
 * Field reflection service that returns cached objects used to access fields on a class.
 */
public interface ReflectionService {
    /**
     * Returns a cached reflection info for a given class type.
     *
     * @param targetClass Target class type to introspect for fields.
     * @return Class info with a list of fields that will be serialized to YAML.
     */
    ClassInfo getClassInfoForClass(Class<?> targetClass);
}
