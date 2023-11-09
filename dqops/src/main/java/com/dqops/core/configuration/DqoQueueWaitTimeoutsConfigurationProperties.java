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
