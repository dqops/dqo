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

import com.dqops.core.configuration.DqoUserConfigurationProperties;
import com.dqops.metadata.scheduling.MonitoringScheduleSpec;
import com.dqops.utils.serialization.JsonSerializer;
import org.quartz.JobDataMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Job data map adapter that can read and write common properties that are stored as a job parameter for a Quartz job.
 * Complex objects cannot be stored directly in the job data map, so this class will serialize and deserialize them from JSON.
 */
@Component
public class JobDataMapAdapterImpl implements JobDataMapAdapter {
    /**
     * Key used in the {@see JsonDataMap} to store a serialized {@see MonitoringScheduleSpec}
     */
    public static final String KEY_SCHEDULE = "schedule";

    /**
     *Key used in the {@see JsonDataMap} to store the data domain name.
     */
    public static final String KEY_DATA_DOMAIN = "domain";

    private JsonSerializer jsonSerializer;
    private DqoUserConfigurationProperties dqoUserConfigurationProperties;

    /**
     * Creates a job data map adapter using a DQOps JSON serializer.
     * @param jsonSerializer DQOps JSON serializer.
     * @param dqoUserConfigurationProperties DQOps user configuration parameters.
     */
    @Autowired
    public JobDataMapAdapterImpl(JsonSerializer jsonSerializer,
                                 DqoUserConfigurationProperties dqoUserConfigurationProperties) {
        this.jsonSerializer = jsonSerializer;
        this.dqoUserConfigurationProperties = dqoUserConfigurationProperties;
    }

    /**
     * Stores the schedule in the job data map as a serialized JSON.
     * @param dataMap Target data map to store the schedule.
     * @param monitoringSchedule Schedule specification to store.
     */
    public void setSchedule(JobDataMap dataMap, MonitoringScheduleSpec monitoringSchedule) {
        String jsonString = this.jsonSerializer.serialize(monitoringSchedule);
        dataMap.put(KEY_SCHEDULE, jsonString);
    }

    /**
     * Retrieves a serialized schedule specification from the job data map.
     * @param jobDataMap Job data map to read.
     * @return Deserialized monitoring schedule specification.
     */
    public MonitoringScheduleSpec getSchedule(JobDataMap jobDataMap) {
        if (jobDataMap == null || !jobDataMap.containsKey(KEY_SCHEDULE)) {
            return null;
        }

        String scheduleJson = jobDataMap.getString(KEY_SCHEDULE);
        MonitoringScheduleSpec monitoringSchedule = this.jsonSerializer.deserialize(scheduleJson, MonitoringScheduleSpec.class);
        return monitoringSchedule;
    }

    /**
     * Sets the name of the data domain on which this job is executed. Selects the DQOps data domain specific user home.
     *
     * @param jobDataMap    Data map to store the data domain name.
     * @param dataDomain Data domain name to store.
     */
    @Override
    public void setDataDomain(JobDataMap jobDataMap, String dataDomain) {
        jobDataMap.put(KEY_DATA_DOMAIN, dataDomain);
    }

    /**
     * Retrieves the data domain name on which the job is executed. Selects the DQOps data domain specific user home.
     *
     * @param jobDataMap Job data map to retrieve the value.
     * @return Data domain name.
     */
    @Override
    public String getDataDomain(JobDataMap jobDataMap) {
        if (jobDataMap == null || !jobDataMap.containsKey(KEY_DATA_DOMAIN)) {
            return this.dqoUserConfigurationProperties.getDefaultDataDomain();
        }

        return jobDataMap.getString(KEY_DATA_DOMAIN);
    }
}
