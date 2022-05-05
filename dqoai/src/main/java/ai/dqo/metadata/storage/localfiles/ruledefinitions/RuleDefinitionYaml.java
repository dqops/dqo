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
package ai.dqo.metadata.storage.localfiles.ruledefinitions;

import ai.dqo.core.filesystem.ApiVersion;
import ai.dqo.metadata.definitions.rules.RuleDefinitionSpec;
import ai.dqo.metadata.storage.localfiles.SpecificationKind;

/**
 * Custom rule specification that describes the configuration of a python module with the rule code (additional parameters).
 */
public class RuleDefinitionYaml {
    private String apiVersion = ApiVersion.CURRENT_API_VERSION;
    private SpecificationKind kind = SpecificationKind.RULE;
    private RuleDefinitionSpec spec = new RuleDefinitionSpec();

    public RuleDefinitionYaml() {
    }

    public RuleDefinitionYaml(RuleDefinitionSpec spec) {
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
     * Returns a custom rule definition specification.
     * @return Custom rule definition specification.
     */
    public RuleDefinitionSpec getSpec() {
        return spec;
    }

    /**
     * Sets a custom rule definition specification.
     * @param spec Custom rule definition specification.
     */
    public void setSpec(RuleDefinitionSpec spec) {
        this.spec = spec;
    }
}
