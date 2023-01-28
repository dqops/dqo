package ai.dqo.metadata.fields;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation for sensor parameter fields and rule parameter fields with a sample value.
 * The sample value is used mostly for generating the documentation with examples of YAML files.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface SampleValues {
    /**
     * Array of sample values that could be used on a field.
     * It could be a single value or an array of values. Numeric values should be also specified as strings.
     * @return Array of sample values.
     */
    String[] values() default {};
}
