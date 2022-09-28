package ai.dqo.utils.reflection;

import java.util.ArrayList;
import java.util.List;

/**
 * Describes all fields used in YAML serialization in a class, their file names, YAML snake_case names, descriptions,
 * expected UI control types.
 */
public class ClassInfo {
    private Class<?> reflectedClass;
    private List<FieldInfo> fields = new ArrayList<>();

    /**
     * Creates a class reflection info, storing the class type.
     * @param reflectedClass Target class type.
     */
    public ClassInfo(Class<?> reflectedClass) {
        this.reflectedClass = reflectedClass;
    }

    /**
     * Returns the Java class of the reflected class, whose fields are described.
     * @return Java class of the reflected type.
     */
    public Class<?> getReflectedClass() {
        return reflectedClass;
    }

    /**
     * Returns an editable list of fields found on the class.
     * @return List of reflected fields.
     */
    public List<FieldInfo> getFields() {
        return fields;
    }
}
