/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.utils.serialization;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Marker interface applied to specification objects that should not be rendered to YAML because they contain default values
 * and the rendered YAML will be a mix of YAML and JSON (a node will look like:  node_name: {} )
 */
@JsonIgnoreProperties("default")
public interface YamlNotRenderWhenDefault {
    /**
     * Checks if the object is a default value, so it would be rendered as an empty node. We want to skip it and not render it to YAML.
     * The implementation of this interface method should check all object's fields to find if at least one of them has a non-default value or is not null, so it should be rendered.
     * @return true when the object has the default values only and should not be rendered to YAML, false when it should be rendered.
     */
    boolean isDefault();
}
