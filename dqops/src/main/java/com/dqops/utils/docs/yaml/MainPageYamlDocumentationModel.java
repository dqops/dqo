/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.utils.docs.yaml;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class MainPageYamlDocumentationModel {
    /**
     * File header.
     */
    private String header;

    /**
     * Description of the file.
     */
    private String helpText;

    private List<MainPageYamlDocumentationModel> subpages = new ArrayList<>();

    private List<YamlSuperiorObjectDocumentationModel> superiorObjects = new ArrayList<>();
}
