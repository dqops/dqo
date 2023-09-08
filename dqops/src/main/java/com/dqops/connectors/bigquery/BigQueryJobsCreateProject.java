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
package com.dqops.connectors.bigquery;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Enumeration of the method of selecting the big query source project that will be used for running the BigQuery job.
 * The user must have bigquery.jobs.create permission on the project that is selected by this enumeration.
 */
public enum BigQueryJobsCreateProject {
    /**
     * Start BigQuery jobs on the source project.
     */
    @JsonProperty("run_on_source_project")
    run_on_source_project,

    /**
     * Run BigQuery jobs on the project identified as the default project in the default credentials.
     */
    @JsonProperty("run_on_default_project_from_credentials")
    run_on_default_project_from_credentials,

    /**
     * Run BigQuery jobs on the project provided in the billing_project_id field.
     */
    @JsonProperty("run_on_selected_billing_project_id")
    run_on_selected_billing_project_id
}
