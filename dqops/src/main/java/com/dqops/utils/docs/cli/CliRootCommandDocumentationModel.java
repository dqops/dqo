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

import java.util.ArrayList;
import java.util.List;

/**
 * Documentation model about one single CLI command.
 */
@Data
public class CliRootCommandDocumentationModel {
    /**
     * The name of the first command.
     */
    private String rootCommandName;

    /**
     * The header of the root command.
     */
    private String rootCommandHeader;

    /**
     * The description of the root command.
     */
    private String rootCommandDescription;

    /**
     * List of commands.
     */
    private List<CliCommandDocumentationModel> commands = new ArrayList<>();
}
