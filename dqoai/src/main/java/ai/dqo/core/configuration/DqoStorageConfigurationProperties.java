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
 * Configuration POJO with the configuration for dqo.ai. Properties are mapped to the "dqo.storage." prefix that are responsible for the file paths where the result files are stored.
 */
@Configuration
@ConfigurationProperties(prefix = "dqo.storage")
@EqualsAndHashCode(callSuper = false)
public class DqoStorageConfigurationProperties implements Cloneable {
    private String sensorReadingsStoragePath;
    private String alertsStoragePath;

    /**
     * Sensor readings storage path.
     * @return Sensor readings storage path.
     */
    public String getSensorReadingsStoragePath() {
        return sensorReadingsStoragePath;
    }

    /**
     * Sets the path where the sensor readings are stored.
     * @param sensorReadingsStoragePath Sensor results storage path.
     */
    public void setSensorReadingsStoragePath(String sensorReadingsStoragePath) {
        this.sensorReadingsStoragePath = sensorReadingsStoragePath;
    }

    /**
     * Rule evaluation results (alerts) storage path.
     * @return Rule evaluation storage path.
     */
    public String getAlertsStoragePath() {
        return alertsStoragePath;
    }

    /**
     * Sets the alert storage path.
     * @param alertsStoragePath Alerts storage path.
     */
    public void setAlertsStoragePath(String alertsStoragePath) {
        this.alertsStoragePath = alertsStoragePath;
    }

    /**
     * Clones the current object.
     * @return Cloned instance.
     */
    @Override
    public DqoStorageConfigurationProperties clone() {
        try {
            return (DqoStorageConfigurationProperties)super.clone();
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
