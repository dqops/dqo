/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
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
