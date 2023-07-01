/*
 * Copyright © 2021 DQOps (support@dqops.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
