/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.connectors.bigquery;

/**
 * Enumeration of the method of selecting the big query source project that will be used for running the BigQuery job.
 * The user must have bigquery.jobs.create permission on the project that is selected by this enumeration.
 */
public enum BigQueryJobsCreateProject {
    /**
     * Start BigQuery jobs on the source project.
     */
    create_jobs_in_source_project,

    /**
     * Run BigQuery jobs on the project identified as the default project in the default credentials.
     */
    create_jobs_in_default_project_from_credentials,

    /**
     * Run BigQuery jobs on the project provided in the billing_project_id field.
     */
    create_jobs_in_selected_billing_project_id
}
