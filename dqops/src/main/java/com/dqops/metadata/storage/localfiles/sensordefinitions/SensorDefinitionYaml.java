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
package com.dqops.metadata.storage.localfiles.sensordefinitions;

import com.dqops.core.filesystem.ApiVersion;
import com.dqops.metadata.definitions.sensors.SensorDefinitionSpec;
import com.dqops.metadata.storage.localfiles.SpecificationKind;

/**
 * Data quality sensor definition YAML schema for a data quality sensor specification.
 */
public class SensorDefinitionYaml {
    private String apiVersion = ApiVersion.CURRENT_API_VERSION;
    private SpecificationKind kind = SpecificationKind.SENSOR;
    private SensorDefinitionSpec spec = new SensorDefinitionSpec();

    public SensorDefinitionYaml() {
    }

    public SensorDefinitionYaml(SensorDefinitionSpec spec) {
        this.spec = spec;
    }

    /**
     * Current api version. The current version is dqo/v1
     * @return Current api version.
     */
    public String getApiVersion() {
        return apiVersion;
    }

    /**
     * Sets the api version for the serialized file.
     * @param apiVersion Api version.
     */
    public void setApiVersion(String apiVersion) {
        this.apiVersion = apiVersion;
    }

    /**
     * Returns the YAML file kind.
     * @return Yaml file kind.
     */
    public SpecificationKind getKind() {
        return kind;
    }

    /**
     * Sets YAML file kind.
     * @param kind YAML file kind.
     */
    public void setKind(SpecificationKind kind) {
        this.kind = kind;
    }

    /**
     * Returns a data quality sensor configuration specification.
     * @return Data quality sensor configuration specification.
     */
    public SensorDefinitionSpec getSpec() {
        return spec;
    }

    /**
     * Sets a data quality sensor configuration specification.
     * @param spec Data quality sensor configuration specification.
     */
    public void setSpec(SensorDefinitionSpec spec) {
        this.spec = spec;
    }
}
