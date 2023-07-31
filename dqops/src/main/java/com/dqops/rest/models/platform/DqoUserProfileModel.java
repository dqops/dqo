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
package com.dqops.rest.models.platform;

import com.dqops.core.dqocloud.apikey.DqoCloudApiKey;
import com.dqops.core.dqocloud.apikey.DqoCloudLimit;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.time.Instant;

/**
 * The model that describes the current user and his access rights.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@ApiModel(value = "DqoUserProfileModel", description = "The model that describes the current user and his access rights.")
public class DqoUserProfileModel {
    /**
     * The user email.
     */
    @JsonPropertyDescription("User email.")
    private String user;

    /**
     * The ID of the DQO Cloud account (tenant).
     */
    @JsonPropertyDescription("DQO Cloud tenant.")
    private String tenant;

    /**
     * DQO Cloud license type.
     */
    @JsonPropertyDescription("DQO Cloud license type.")
    private String licenseType;

    /**
     * The date and time when the trial period of a PERSONAL DQO license expires and the account is downgraded to a FREE license.
     */
    @JsonPropertyDescription("The date and time when the trial period of a PERSONAL DQO license expires and the account is downgraded to a FREE license.")
    private String trialPeriodExpiresAt;

    /**
     * Limit of the number of connections that could be synchronized to the DQO Cloud data quality warehouse.
     */
    @JsonPropertyDescription("Limit of the number of connections that could be synchronized to the DQO Cloud data quality warehouse.")
    private Integer connectionsLimit;

    /**
     * Limit of the number of users that could be added to a DQO environment.
     */
    @JsonPropertyDescription("Limit of the number of users that could be added to a DQO environment.")
    private Integer usersLimit;

    /**
     * Limit of the number of recent months (excluding the current month) that could be synchronized to the DQO Cloud data quality warehouse.
     */
    @JsonPropertyDescription("Limit of the number of recent months (excluding the current month) that could be synchronized to the DQO Cloud data quality warehouse.")
    private Integer monthsLimit;

    /**
     * Limit of the number of tables inside each connection that could be synchronized to the DQO Cloud data quality warehouse.
     */
    @JsonPropertyDescription("Limit of the number of tables inside each connection that could be synchronized to the DQO Cloud data quality warehouse.")
    private Integer connectionTablesLimit;

    /**
     * Limit of the total number of tables that could be synchronized to the DQO Cloud data quality warehouse.
     */
    @JsonPropertyDescription("Limit of the total number of tables that could be synchronized to the DQO Cloud data quality warehouse.")
    private Integer tablesLimit;

    /**
     * Limit of the number of supported concurrent jobs that DQO can run in parallel on this instance.
     */
    @JsonPropertyDescription("Limit of the number of supported concurrent jobs that DQO can run in parallel on this instance.")
    private Integer jobsLimit;

    /**
     * Creates a user profile model from the API key.
     * @param dqoCloudApiKey DQO cloud api key.
     * @return User profile.
     */
    public static DqoUserProfileModel fromApiKey(DqoCloudApiKey dqoCloudApiKey) {
        DqoUserProfileModel model = new DqoUserProfileModel() {{
            setUser(dqoCloudApiKey.getApiKeyPayload().getSubject());
            setTenant(dqoCloudApiKey.getApiKeyPayload().getTenantId() + "/" + dqoCloudApiKey.getApiKeyPayload().getTenantGroup());
            setLicenseType(dqoCloudApiKey.getApiKeyPayload().getLicenseType());
            setTrialPeriodExpiresAt(dqoCloudApiKey.getApiKeyPayload().getExpiresAt().toString());
            setConnectionsLimit(dqoCloudApiKey.getApiKeyPayload().getLimits().get(DqoCloudLimit.CONNECTIONS_LIMIT));
            setUsersLimit(dqoCloudApiKey.getApiKeyPayload().getLimits().get(DqoCloudLimit.USERS_LIMIT));
            setMonthsLimit(dqoCloudApiKey.getApiKeyPayload().getLimits().get(DqoCloudLimit.MONTHS_LIMIT));
            setConnectionTablesLimit(dqoCloudApiKey.getApiKeyPayload().getLimits().get(DqoCloudLimit.CONNECTION_TABLES_LIMIT));
            setTablesLimit(dqoCloudApiKey.getApiKeyPayload().getLimits().get(DqoCloudLimit.TABLES_LIMIT));
            setJobsLimit(dqoCloudApiKey.getApiKeyPayload().getLimits().get(DqoCloudLimit.JOBS_LIMIT));
        }};
        return model;
    }
}
