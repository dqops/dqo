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
