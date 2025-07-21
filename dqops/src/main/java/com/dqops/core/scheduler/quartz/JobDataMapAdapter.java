/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.core.scheduler.quartz;

import com.dqops.metadata.scheduling.CronScheduleSpec;
import org.quartz.JobDataMap;

/**
 * Job data map adapter that can read and write common properties that are stored as a job parameter for a Quartz job.
 * Complex objects cannot be stored directly in the job data map, so this class will serialize and deserialize them from JSON.
 */
public interface JobDataMapAdapter {
    /**
     * Stores the schedule in the job data map as a serialized JSON.
     * @param dataMap Target data map to store the schedule.
     * @param monitoringSchedule Schedule specification to store.
     */
    void setSchedule(JobDataMap dataMap, CronScheduleSpec monitoringSchedule);

    /**
     * Retrieves a serialized schedule specification from the job data map.
     * @param jobDataMap Job data map to read.
     * @return Deserialized monitoring schedule specification.
     */
    CronScheduleSpec getSchedule(JobDataMap jobDataMap);

    /**
     * Sets the name of the data domain on which this job is executed. Selects the DQOps data domain specific user home.
     * @param jobDataMap Data map to store the data domain name.
     * @param dataDomain Data domain name to store.
     */
    void setDataDomain(JobDataMap jobDataMap, String dataDomain);

    /**
     * Retrieves the data domain name on which the job is executed. Selects the DQOps data domain specific user home.
     * @param jobDataMap Job data map to retrieve the value.
     * @return Data domain name.
     */
    String getDataDomain(JobDataMap jobDataMap);
}
