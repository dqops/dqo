/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.core.configuration;

import com.dqops.core.jobqueue.DqoJobType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration POJO with the configuration for DQOps. Properties are mapped to the "dqo.queue.wait-timeouts" prefix that are responsible for the configuration of
 * the default rest api timeouts when a job is started using the rest api with the wait=true option and no timeout provided by the client.
 */
@Configuration
@ConfigurationProperties(prefix = "dqo.queue.wait-timeouts")
@EqualsAndHashCode(callSuper = false)
@Data
public class DqoQueueWaitTimeoutsConfigurationProperties implements Cloneable {
    /**
     * Wait timeout for the "run checks" job.
     */
    private long runChecks = 120L;

    /**
     * Wait timeout for the "collect statistics" job.
     */
    private long collectStatistics = 120L;

    /**
     * Wait timeout for the "import tables" job.
     */
    private long importTables = 120L;

    /**
     * Wait timeout for the "delete stored data" job.
     */
    private long deleteStoredData = 120L;

    /**
     * Wait timeout for the "synchronize multiple folders" job.
     */
    private long synchronizeMultipleFolders = 120L;

    /**
     * The default wait timeout for any kind of job.
     */
    private long defaultWaitTimeout = 120L;

    /**
     * Clones the current object.
     * @return
     */
    @Override
    public DqoQueueWaitTimeoutsConfigurationProperties clone() {
        try {
            return (DqoQueueWaitTimeoutsConfigurationProperties)super.clone();
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Retrieves a default job wait timeout for a given type of job.
     * @param jobType Job type.
     * @return Wait timeout in seconds.
     */
    public long getWaitTimeForJobType(DqoJobType jobType) {
        switch (jobType) {
            case run_checks:
            case run_checks_on_table:
            case run_scheduled_checks_cron:
                return this.runChecks;

            case collect_statistics:
            case collect_statistics_on_table:
                return this.collectStatistics;

            case delete_stored_data:
                return this.deleteStoredData;

            case import_schema:
            case import_tables:
                return this.importTables;

            default:
                return this.defaultWaitTimeout;
        }
    }
}
