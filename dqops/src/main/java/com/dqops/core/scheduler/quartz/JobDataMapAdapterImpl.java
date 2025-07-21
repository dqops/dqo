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

import com.dqops.core.configuration.DqoUserConfigurationProperties;
import com.dqops.metadata.scheduling.CronScheduleSpec;
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
    public void setSchedule(JobDataMap dataMap, CronScheduleSpec monitoringSchedule) {
        String jsonString = this.jsonSerializer.serialize(monitoringSchedule);
        dataMap.put(KEY_SCHEDULE, jsonString);
    }

    /**
     * Retrieves a serialized schedule specification from the job data map.
     * @param jobDataMap Job data map to read.
     * @return Deserialized monitoring schedule specification.
     */
    public CronScheduleSpec getSchedule(JobDataMap jobDataMap) {
        if (jobDataMap == null || !jobDataMap.containsKey(KEY_SCHEDULE)) {
            return null;
        }

        String scheduleJson = jobDataMap.getString(KEY_SCHEDULE);
        CronScheduleSpec monitoringSchedule = this.jsonSerializer.deserialize(scheduleJson, CronScheduleSpec.class);
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
