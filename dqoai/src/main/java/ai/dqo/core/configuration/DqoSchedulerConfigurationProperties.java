/*
 * Copyright © 2021 DQO.ai (support@dqo.ai)
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
package ai.dqo.core.configuration;

import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration POJO with the configuration for DQO that configure the job scheduler. Properties are mapped to the "dqo.scheduler." prefix.
 */
@Configuration
@ConfigurationProperties(prefix = "dqo.scheduler")
@EqualsAndHashCode(callSuper = false)
public class DqoSchedulerConfigurationProperties implements Cloneable {
    private String scanMetadataCronSchedule;

    /**
     * Returns the default cron expression used to scan the metadata for new schedules to run data quality checks.
     * @return Cron expression used to scan the metadata.
     */
    public String getScanMetadataCronSchedule() {
        return scanMetadataCronSchedule;
    }

    /**
     * Sets the scan metadata cron schedule.
     * @param scanMetadataCronSchedule Cron schedule used to scan the metadata.
     */
    public void setScanMetadataCronSchedule(String scanMetadataCronSchedule) {
        this.scanMetadataCronSchedule = scanMetadataCronSchedule;
    }

    @Override
    public DqoSchedulerConfigurationProperties clone() {
        try {
            return (DqoSchedulerConfigurationProperties)super.clone();
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
