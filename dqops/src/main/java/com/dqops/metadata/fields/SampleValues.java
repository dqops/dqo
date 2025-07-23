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
 * Annotation for sensor parameter fields and rule parameter fields with a sample value.
 * The sample value is used mostly for generating the documentation with examples of YAML files.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface SampleValues {
    /**
     * Array of sample values that could be used on a field.
     * It could be a single value or an array of values. Numeric values should also be specified as strings.
     * @return Array of sample values.
     */
    String[] values() default {};
}
