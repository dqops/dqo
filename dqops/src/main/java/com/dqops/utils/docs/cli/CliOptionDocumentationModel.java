/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.utils.docs.cli;

import lombok.Data;

import java.util.List;

/**
 * Documentation model for a single parameter (option) of a CLI command.
 */
@Data
public class CliOptionDocumentationModel {
    /**
     * Array of all option names (shorter, longer).
     */
    private List<String> names;

    /**
     * Option description.
     */
    private String[] description;

    /**
     * The option is required.
     */
    private boolean required;

    /**
     * Array of accepted values.
     */
    private String[] acceptedValues;
}
