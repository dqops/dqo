/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.metadata.fields;

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
public @interface ControlDisplayHint {
    /**
     * Additional information about the type of visual (control) that should display the field in UI.
     * @return Additional display hint.
     */
    DisplayHint value();
}
