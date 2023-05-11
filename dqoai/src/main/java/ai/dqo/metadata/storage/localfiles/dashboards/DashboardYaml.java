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
package ai.dqo.metadata.storage.localfiles.dashboards;

import ai.dqo.core.filesystem.ApiVersion;
import ai.dqo.metadata.dashboards.DashboardsFolderListSpec;
import ai.dqo.metadata.storage.localfiles.SpecificationKind;

/**
 * Data quality dashboard definition YAML schema for a data quality dashboards list specification.
 */
public class DashboardYaml {

    private String apiVersion = ApiVersion.CURRENT_API_VERSION;
    private SpecificationKind kind = SpecificationKind.DASHBOARDS;
    private DashboardsFolderListSpec spec = new DashboardsFolderListSpec();

    DashboardYaml() {
    }

    public DashboardYaml(DashboardsFolderListSpec spec) {
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
     * Returns a data quality dashboard list configuration specification.
     * @return Data quality dashboard list configuration specification.
     */
    public DashboardsFolderListSpec getSpec() {
        return spec;
    }

    /**
     * Sets a data quality dashboard list configuration specification.
     * @param spec Data quality dashboard list configuration specification.
     */
    public void setSpec(DashboardsFolderListSpec spec) {
        this.spec = spec;
    }
}
