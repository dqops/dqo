/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.metadata.storage.localfiles.columndefaultpatterns;

import com.dqops.core.filesystem.ApiVersion;
import com.dqops.metadata.policies.column.ColumnQualityPolicySpec;
import com.dqops.metadata.storage.localfiles.SpecificationKind;
import com.dqops.utils.reflection.DefaultFieldValue;
import com.dqops.utils.serialization.InvalidYamlStatusHolder;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

/**
 * The configuration of a data quality policy at a column level, containing data quality checks that are applied on columns that match a search pattern.
 */
public class ColumnLevelDataQualityPolicyYaml implements InvalidYamlStatusHolder {
    @JsonPropertyDescription("DQOps YAML schema version")
    @DefaultFieldValue(ApiVersion.CURRENT_API_VERSION)
    private String apiVersion = ApiVersion.CURRENT_API_VERSION;

    @JsonPropertyDescription("File type")
    @DefaultFieldValue("default_column_checks")
    private SpecificationKind kind = SpecificationKind.default_column_checks;

    @JsonPropertyDescription("The specification (configuration) of the column-level data quality policy with checks that are applied on columns matching a pattern")
    private ColumnQualityPolicySpec spec = new ColumnQualityPolicySpec();

    @JsonIgnore
    private String yamlParsingError;

    public ColumnLevelDataQualityPolicyYaml() {
    }

    public ColumnLevelDataQualityPolicyYaml(ColumnQualityPolicySpec spec) {
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
     * Returns a configuration of default data observability checks for tables matching a pattern.
     * @return Default checks for tables.
     */
    public ColumnQualityPolicySpec getSpec() {
        return spec;
    }

    /**
     * Sets a configuration of default table-level checks applied for tables matching a pattern.
     * @param spec Default checks for tables.
     */
    public void setSpec(ColumnQualityPolicySpec spec) {
        this.spec = spec;
    }

    /**
     * Sets a value that indicates that the YAML file deserialized into this object has a parsing error.
     *
     * @param yamlParsingError YAML parsing error.
     */
    @Override
    public void setYamlParsingError(String yamlParsingError) {
        if (this.spec != null) {
            this.spec.setYamlParsingError(yamlParsingError);
        }
        this.yamlParsingError = yamlParsingError;
    }

    /**
     * Returns the YAML parsing error that was captured.
     *
     * @return YAML parsing error.
     */
    @Override
    public String getYamlParsingError() {
        return this.yamlParsingError;
    }
}
