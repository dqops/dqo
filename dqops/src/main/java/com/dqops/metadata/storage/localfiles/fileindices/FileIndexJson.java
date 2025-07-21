/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.metadata.storage.localfiles.fileindices;

import com.dqops.core.filesystem.ApiVersion;
import com.dqops.metadata.fileindices.FileIndexSpec;
import com.dqops.metadata.storage.localfiles.SpecificationKind;
import com.dqops.utils.reflection.DefaultFieldValue;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

/**
 * File index definition file that defines a tree of files that were synchronized with the DQOps Cloud.
 */
public class FileIndexJson {
    @JsonPropertyDescription("DQOps YAML schema version")
    @DefaultFieldValue(ApiVersion.CURRENT_API_VERSION)
    private String apiVersion = ApiVersion.CURRENT_API_VERSION;

    @JsonPropertyDescription("File type")
    @DefaultFieldValue("file_index")
    private SpecificationKind kind = SpecificationKind.file_index;

    @JsonPropertyDescription("File index")
    private FileIndexSpec spec = new FileIndexSpec();

    public FileIndexJson() {
    }

    public FileIndexJson(FileIndexSpec spec) {
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
    public FileIndexSpec getSpec() {
        return spec;
    }

    /**
     * Sets a file index specification.
     * @param spec File index specification.
     */
    public void setSpec(FileIndexSpec spec) {
        this.spec = spec;
    }
}
