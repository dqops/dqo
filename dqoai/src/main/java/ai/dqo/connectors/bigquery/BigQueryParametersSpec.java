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
package ai.dqo.connectors.bigquery;

import ai.dqo.connectors.ConnectionProviderSpecificParameters;
import ai.dqo.core.secrets.SecretValueProvider;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMap;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMapImpl;
import ai.dqo.metadata.sources.BaseProviderParametersSpec;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;
import picocli.CommandLine;

import java.util.Objects;

/**
 * BigQuery connection parameters.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class BigQueryParametersSpec extends BaseProviderParametersSpec implements Cloneable,
        ConnectionProviderSpecificParameters {
    private static final ChildHierarchyNodeFieldMapImpl<BigQueryParametersSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(BaseProviderParametersSpec.FIELDS) {
        {
        }
    };

    @CommandLine.Option(names = {"--bigquery-source-project-id"}, description = "Bigquery source GCP project id. This is the project that has datasets that will be imported.")
    @JsonPropertyDescription("Source GCP project ID. This is the project that has datasets that will be imported.")
    private String sourceProjectId;

    @CommandLine.Option(names = {"--bigquery-billing-project-id"}, description = "Bigquery billing GCP project id. This is the project used as the default GCP project. The calling user must have a bigquery.jobs.create permission in this project.")
    @JsonPropertyDescription("Billing GCP project ID. This is the project used as the default GCP project. The calling user must have a bigquery.jobs.create permission in this project.")
    private String billingProjectId;

    @CommandLine.Option(names = {"--bigquery-authentication-mode"}, description = "Bigquery authentication mode.")
    @JsonPropertyDescription("Authentication mode to the Google Cloud.")
    private BigQueryAuthenticationMode authenticationMode;

    @CommandLine.Option(names = {"--bigquery-json-key-content"}, description = "Bigquery service account key content as JSON.")
    @JsonPropertyDescription("JSON key content. Use an environment variable that contains the content of the key as ${KEY_ENV} or a name of a secret in the GCP Secret Manager: ${sm://key-secret-name}. Requires the authentication-mode: json_key_content.")
    private String jsonKeyContent;

    @CommandLine.Option(names = {"--bigquery-json-key-path"}, description = "Path to a GCP service account key JSON file used to authenticate to Bigquery.")
    @JsonPropertyDescription("A path to the JSON key file. Requires the authentication-mode: json_key_path.")
    private String jsonKeyPath;

    @CommandLine.Option(names = {"--bigquery-quota-project-id"}, description = "Bigquery quota GCP project id.")
    @JsonPropertyDescription("Quota GCP project ID.")
    private String quotaProjectId;

    /**
     * Returns the source GCP Project id.
     * @return Source GCP project id.
     */
    public String getSourceProjectId() {
        return sourceProjectId;
    }

    /**
     * Sets the source GCP project id.
     * @param sourceProjectId Source GCP project id.
     */
    public void setSourceProjectId(String sourceProjectId) {
		this.setDirtyIf(!Objects.equals(this.sourceProjectId, sourceProjectId));
        this.sourceProjectId = sourceProjectId;
    }

    /**
     * Returns the billing GCP Project id.
     * @return Billing GCP project id.
     */
    public String getBillingProjectId() {
        return billingProjectId;
    }

    /**
     * Sets the billing GCP project id.
     * @param billingProjectId Billing GCP project id.
     */
    public void setBillingProjectId(String billingProjectId) {
        this.setDirtyIf(!Objects.equals(this.billingProjectId, billingProjectId));
        this.billingProjectId = billingProjectId;
    }

    /**
     * Returns the authentication mode.
     * @return Authentication mode.
     */
    public BigQueryAuthenticationMode getAuthenticationMode() {
        return authenticationMode;
    }

    /**
     * Sets the authentication mode.
     * @param authenticationMode Authentication mode.
     */
    public void setAuthenticationMode(BigQueryAuthenticationMode authenticationMode) {
		this.setDirtyIf(!Objects.equals(this.authenticationMode, authenticationMode));
        this.authenticationMode = authenticationMode;
    }

    /**
     * Returns the content of a JSON key.
     * @return JSON key content.
     */
    public String getJsonKeyContent() {
        return jsonKeyContent;
    }

    /**
     * Sets the JSON key content.
     * @param jsonKeyContent JSON key content.
     */
    public void setJsonKeyContent(String jsonKeyContent) {
		this.setDirtyIf(!Objects.equals(this.jsonKeyContent, jsonKeyContent));
        this.jsonKeyContent = jsonKeyContent;
    }

    /**
     * Returns the path to a file with a JSON key.
     * @return Path to the JSON key.
     */
    public String getJsonKeyPath() {
        return jsonKeyPath;
    }

    /**
     * Sets the path to a JSON key.
     * @param jsonKeyPath Path to a JSON key.
     */
    public void setJsonKeyPath(String jsonKeyPath) {
		this.setDirtyIf(!Objects.equals(this.jsonKeyPath, jsonKeyPath));
        this.jsonKeyPath = jsonKeyPath;
    }

    /**
     * Gets the optional quota project id.
     * @return  Quota project id.
     */
    public String getQuotaProjectId() {
        return quotaProjectId;
    }

    /**
     * Sets the quota project id.
     * @param quotaProjectId Quota project id.
     */
    public void setQuotaProjectId(String quotaProjectId) {
		this.setDirtyIf(!Objects.equals(this.quotaProjectId, quotaProjectId));
        this.quotaProjectId = quotaProjectId;
    }

    /**
     * Returns the child map on the spec class with all fields.
     *
     * @return Return the field map.
     */
    @Override
    protected ChildHierarchyNodeFieldMap getChildMap() {
        return FIELDS;
    }

    /**
     * Creates and returns a deep copy of this object.
     */
    @Override
    public BigQueryParametersSpec clone() {
        try {
            BigQueryParametersSpec cloned = (BigQueryParametersSpec)super.clone();
            return cloned;
        }
        catch (CloneNotSupportedException ex) {
            throw new RuntimeException("Object cannot be cloned", ex);
        }
    }

    /**
     * Creates a trimmed and expanded version of the object without unwanted properties, but with all variables like ${ENV_VAR} expanded.
     * @return Trimmed and expanded version of this object.
     */
    public BigQueryParametersSpec expandAndTrim(SecretValueProvider secretValueProvider) {
        try {
            BigQueryParametersSpec cloned = (BigQueryParametersSpec) super.clone();
            cloned.sourceProjectId = secretValueProvider.expandValue(cloned.sourceProjectId);
            cloned.billingProjectId = secretValueProvider.expandValue(cloned.billingProjectId);
            cloned.jsonKeyContent = secretValueProvider.expandValue(cloned.jsonKeyContent);
            cloned.jsonKeyPath = secretValueProvider.expandValue(cloned.jsonKeyPath);
            cloned.quotaProjectId = secretValueProvider.expandValue(cloned.quotaProjectId);

            return cloned;
        }
        catch (CloneNotSupportedException ex) {
            throw new RuntimeException("Object cannot be cloned", ex);
        }
    }

    /**
     * Checks if the object is a default value, so it would be rendered as an empty node. We want to skip it and not render it to YAML.
     * The implementation of this interface method should check all object's fields to find if at least one of them has a non-default value or is not null, so it should be rendered.
     *
     * @return true when the object has the default values only and should not be rendered to YAML, false when it should be rendered.
     */
    @Override
    public boolean isDefault() {
        if (!super.isDefault()) {
            return false;
        }

        return this.authenticationMode == null &&
                this.sourceProjectId == null &&
				this.billingProjectId == null &&
                this.quotaProjectId == null &&
				this.jsonKeyContent == null &&
				this.jsonKeyPath == null;
    }

    /**
     * Returns a database name. Used only for the CLI connection list command to return the GCP project name.
     *
     * @return Database name.
     */
    @JsonIgnore
    @Override
    public String getDatabase() {
        return this.sourceProjectId;
    }
}
