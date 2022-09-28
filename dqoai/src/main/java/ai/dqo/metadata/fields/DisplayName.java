package ai.dqo.metadata.fields;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation applied on fields of sensor parameters and rule threshold parameters, to configure label (display text) used in the UI to display the control.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface DisplayName {
    /**
     * Display name used to display the field in UI.
     * @return Display name.
     */
    String value();
}
