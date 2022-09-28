package ai.dqo.metadata.fields;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation applied on fields of sensor parameters and rule threshold parameters. Configures the UI control type to be used to display the field.
 * It is useful mostly for the string fields that will contain a column name.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ControlType {
    /**
     * Control type to be used to display the field in the UI.
     * @return Display control type.
     */
    ParameterDataType value();
}
