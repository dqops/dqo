/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.core.jobqueue;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Job type that identifies a job by type.
 */
public enum DqoJobType {
    run_checks,
    run_checks_on_table,
    collect_statistics,
    collect_scheduled_statistics,
    collect_statistics_on_table,
    collect_error_samples,
    collect_error_samples_on_table,
    queue_thread_shutdown,
    synchronize_folder,
    synchronize_multiple_folders,
    run_scheduled_checks_cron,
    import_schema,
    import_tables,
    auto_import_tables,
    delete_stored_data,
    repair_stored_data,
}
