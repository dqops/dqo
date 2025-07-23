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

import com.dqops.utils.docs.ProviderTypeModel;
import lombok.Data;

import java.util.List;

/**
 * Documentation model that shows how a particular provider would render the check (sensor) SQL, using the parameters given.
 */
@Data
public class CheckProviderRenderedSqlDocumentationModel {
    /**
     * Provider type model.
     */
    private ProviderTypeModel providerTypeModel;

    /**
     * Jinja2 template content.
     */
    private String jinjaTemplate;

    /**
     * The SQL that would be executed on the target database.
     */
    private String renderedTemplate;

    /**
     * Jinja2 template content.
     */
    private List<String> listOfJinjaTemplate;

    /**
     * The SQL that would be executed on the target database.
     */
    private List<String> listOfRenderedTemplate;
}
