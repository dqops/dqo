/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.utils.docs.checks;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Container object with a list of all checks.
 */
@Data
public class MainPageCheckDocumentationModel {
    /**
     * File header.
     */
    private String header;

    /**
     * Description of the file.
     */
    private String helpText;

    /**
     * Targets in provided checks.
     */
    private List<String> checkTargets = new ArrayList<>();

    /**
     * List of all checks.
     */
    private List<CheckCategoryDocumentationModel> checks = new ArrayList<>();
}
