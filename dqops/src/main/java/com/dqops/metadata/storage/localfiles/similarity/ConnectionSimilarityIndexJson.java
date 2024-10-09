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
package com.dqops.metadata.storage.localfiles.similarity;

import com.dqops.core.filesystem.ApiVersion;
import com.dqops.metadata.similarity.ConnectionSimilarityIndexSpec;
import com.dqops.metadata.storage.localfiles.SpecificationKind;
import com.dqops.utils.reflection.DefaultFieldValue;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

/**
 * Connection similarity index definition file that stores similarity scores of tables within that connection.
 */
public class ConnectionSimilarityIndexJson {
    @JsonPropertyDescription("DQOps YAML schema version")
    @DefaultFieldValue(ApiVersion.CURRENT_API_VERSION)
    private String apiVersion = ApiVersion.CURRENT_API_VERSION;

    @JsonPropertyDescription("File type")
    @DefaultFieldValue("connection_similarity_index")
    private SpecificationKind kind = SpecificationKind.connection_similarity_index;

    @JsonPropertyDescription("Connection similarity index")
    private ConnectionSimilarityIndexSpec spec = new ConnectionSimilarityIndexSpec();

    public ConnectionSimilarityIndexJson() {
    }

    public ConnectionSimilarityIndexJson(ConnectionSimilarityIndexSpec spec) {
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
     * Returns a file index specification.
     * @return File index specification.
     */
    public ConnectionSimilarityIndexSpec getSpec() {
        return spec;
    }

    /**
     * Sets a file index specification.
     * @param spec File index specification.
     */
    public void setSpec(ConnectionSimilarityIndexSpec spec) {
        this.spec = spec;
    }
}
