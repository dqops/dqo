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
import com.dqops.core.dqocloud.apikey.DqoCloudLicenseType;
import com.dqops.core.dqocloud.apikey.DqoCloudLimit;
import com.dqops.core.dqocloud.login.DqoUserRole;
import com.dqops.core.principal.DqoPermissionGrantedAuthorities;
import com.dqops.core.principal.DqoUserPrincipal;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import lombok.Data;

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
     * The ID of the DQOps Cloud account (tenant).
     */
    @JsonPropertyDescription("DQOps Cloud tenant.")
    private String tenant;

    /**
     * DQOps Cloud license type.
     */
    @JsonPropertyDescription("DQOps Cloud license type.")
    private String licenseType;

    /**
     * The date and time when the trial period of a PERSONAL DQOps license expires and the account is downgraded to a FREE license.
     */
    @JsonPropertyDescription("The date and time when the trial period of a PERSONAL DQOps license expires and the account is downgraded to a FREE license.")
    private String trialPeriodExpiresAt;

    /**
     * Limit of the number of connections that can be synchronized to the DQOps Cloud data quality warehouse.
     */
    @JsonPropertyDescription("Limit of the number of connections that can be synchronized to the DQOps Cloud data quality warehouse.")
    private Integer connectionsLimit;

    /**
     * Limit of the number of users that can be added to a DQOps environment.
     */
    @JsonPropertyDescription("Limit of the number of users that can be added to a DQOps environment.")
    private Integer usersLimit;

    /**
     * Limit of the number of recent months (excluding the current month) that can be synchronized to the DQOps Cloud data quality warehouse.
     */
    @JsonPropertyDescription("Limit of the number of recent months (excluding the current month) that can be synchronized to the DQOps Cloud data quality warehouse.")
    private Integer monthsLimit;

    /**
     * Limit of the number of tables inside each connection that can be synchronized to the DQOps Cloud data quality warehouse.
     */
    @JsonPropertyDescription("Limit of the number of tables inside each connection that can be synchronized to the DQOps Cloud data quality warehouse.")
    private Integer connectionTablesLimit;

    /**
     * Limit of the total number of tables that can be synchronized to the DQOps Cloud data quality warehouse.
     */
    @JsonPropertyDescription("Limit of the total number of tables that can be synchronized to the DQOps Cloud data quality warehouse.")
    private Integer tablesLimit;

    /**
     * Limit of the number of supported concurrent jobs that DQOps can run in parallel on this instance.
     */
    @JsonPropertyDescription("Limit of the number of supported concurrent jobs that DQOps can run in parallel on this instance.")
    private Integer jobsLimit;

    /**
     * Limit of the number of supported data domains in an ENTERPRISE version of DQOps.
     */
    @JsonPropertyDescription("Limit of the number of supported data domains in an ENTERPRISE version of DQOps.")
    private Integer dataDomainsLimit;

    /**
     * User role that limits possible operations that the current user can perform.
     */
    @JsonPropertyDescription("User role that limits possible operations that the current user can perform.")
    private DqoUserRole accountRole;

    /**
     * True when the account has access to the DQOps Cloud's data quality data lake and data warehouse, allowing to synchronize files and use the data quality data warehouse.
     */
    @JsonPropertyDescription("True when the account has access to the DQOps Cloud's data quality data lake and data warehouse, allowing to synchronize files and use the data quality data warehouse.")
    private boolean dataQualityDataWarehouseEnabled;

    /**
     * User is the administrator of the account and can perform security related actions, such as managing users.
     */
    @JsonPropertyDescription("User is the administrator of the account and can perform security related actions, such as managing users.")
    private boolean canManageAccount;

    /**
     * User can view any object and view all results.
     */
    @JsonPropertyDescription("User can view any object and view all results.")
    private boolean canViewAnyObject;

    /**
     * User can start and stop the job scheduler.
     */
    @JsonPropertyDescription("User can start and stop the job scheduler.")
    private boolean canManageScheduler;

    /**
     * User can cancel running jobs.
     */
    @JsonPropertyDescription("User can cancel running jobs.")
    private boolean canCancelJobs;

    /**
     * User can run data quality checks.
     */
    @JsonPropertyDescription("User can run data quality checks.")
    private boolean canRunChecks;

    /**
     * User can delete data quality results.
     */
    @JsonPropertyDescription("User can delete data quality results.")
    private boolean canDeleteData;

    /**
     * User can collect statistics.
     */
    @JsonPropertyDescription("User can collect statistics.")
    private boolean canCollectStatistics;

    /**
     * User can manage data sources: create connections, import tables, change the configuration of connections, tables, columns. Change any settings in the Data Sources section.
     */
    @JsonPropertyDescription("User can manage data sources: create connections, import tables, change the configuration of connections, tables, columns. Change any settings in the Data Sources section.")
    private boolean canManageDataSources;

    /**
     * User can trigger the synchronization with DQOps Cloud.
     */
    @JsonPropertyDescription("User can trigger the synchronization with DQOps Cloud.")
    private boolean canSynchronize;

    /**
     * User can edit comments on connections, tables, columns.
     */
    @JsonPropertyDescription("User can edit comments on connections, tables, columns.")
    private boolean canEditComments;

    /**
     * User can edit labels on connections, tables, columns.
     */
    @JsonPropertyDescription("User can edit labels on connections, tables, columns.")
    private boolean canEditLabels;

    /**
     * User can manage definitions of sensors, rules, checks and the default data quality check configuration that is applied on imported tables.
     */
    @JsonPropertyDescription("User can manage definitions of sensors, rules, checks and the default data quality check configuration that is applied on imported tables.")
    private boolean canManageDefinitions;

    /**
     * User can define table comparison configurations and compare tables.
     */
    @JsonPropertyDescription("User can define table comparison configurations and compare tables.")
    private boolean canCompareTables;

    /**
     * User can manage other users, add users to a multi-user account, change access rights, reset passwords.
     */
    @JsonPropertyDescription("User can manage other users, add users to a multi-user account, change access rights, reset passwords.")
    private boolean canManageUsers;

    /**
     * User can manage shared credentials and view (or download) already defined shared credentials.
     */
    @JsonPropertyDescription("User can manage shared credentials and view (or download) already defined shared credentials.")
    private boolean canManageAndViewSharedCredentials;

    /**
     * User can change his own password in DQOps Cloud, because the DQOps Cloud Pairing API Key is valid and synchronization is enabled.
     */
    @JsonPropertyDescription("User can change his own password in DQOps Cloud, because the DQOps Cloud Pairing API Key is valid and synchronization is enabled.")
    private boolean canChangeOwnPassword;

    /**
     * User can use data domains. Support for data domains requires an ENTERPRISE license of DQOps.
     */
    @JsonPropertyDescription("User can use data domains. Support for data domains requires an ENTERPRISE license of DQOps.")
    private boolean canUseDataDomains;

    /**
     * User can synchronize data to a data catalog. The instance must be configured correctly and the user must have at least an EDITOR role."
     */
    @JsonPropertyDescription("User can synchronize data to a data catalog. The instance must be configured correctly and the user must have at least an EDITOR role.")
    private boolean canSynchronizeToDataCatalog;

    /**
     * The DQOps instance is a paid version with advanced AI anomaly prediction.
     */
    @JsonPropertyDescription("The DQOps instance is a paid version with advanced AI anomaly prediction.")
    private boolean canUseAiAnomalyDetection;

    /**
     * This instance uses federated authentication and the user can log out.
     */
    @JsonPropertyDescription("This instance uses federated authentication and the user can log out.")
    private boolean canLogout;

    /**
     * Creates a user profile model from the API key.
     * @param dqoCloudApiKey DQOps Cloud api key.
     * @param principal Calling user principal.
     * @param syncToDataCatalogPossible True when it is possible to synchronize to a data catalog.
     * @return User profile.
     */
    public static DqoUserProfileModel fromApiKeyAndPrincipal(DqoCloudApiKey dqoCloudApiKey,
                                                             DqoUserPrincipal principal,
                                                             boolean syncToDataCatalogPossible) {
        DqoUserProfileModel model = new DqoUserProfileModel() {{
            setUser(principal.getDataDomainIdentity().getUserName());
            setAccountRole(principal.getAccountRole());
            setCanManageAccount(principal.hasPrivilege(DqoPermissionGrantedAuthorities.MANAGE_ACCOUNT));
            setCanViewAnyObject(principal.hasPrivilege(DqoPermissionGrantedAuthorities.VIEW));
            setCanManageScheduler(principal.hasPrivilege(DqoPermissionGrantedAuthorities.EDIT));
            setCanCancelJobs(principal.hasPrivilege(DqoPermissionGrantedAuthorities.OPERATE));
            setCanRunChecks(principal.hasPrivilege(DqoPermissionGrantedAuthorities.OPERATE));
            setCanDeleteData(principal.hasPrivilege(DqoPermissionGrantedAuthorities.OPERATE));
            setCanCollectStatistics(principal.hasPrivilege(DqoPermissionGrantedAuthorities.OPERATE));
            setCanManageDataSources(principal.hasPrivilege(DqoPermissionGrantedAuthorities.EDIT));
            setCanSynchronize(principal.hasPrivilege(DqoPermissionGrantedAuthorities.OPERATE));
            setCanEditComments(principal.hasPrivilege(DqoPermissionGrantedAuthorities.OPERATE));
            setCanEditLabels(principal.hasPrivilege(DqoPermissionGrantedAuthorities.OPERATE));
            setCanManageDefinitions(principal.hasPrivilege(DqoPermissionGrantedAuthorities.EDIT));
            setCanCompareTables(principal.hasPrivilege(DqoPermissionGrantedAuthorities.OPERATE));
            setCanManageUsers(principal.hasPrivilege(DqoPermissionGrantedAuthorities.MANAGE_ACCOUNT));
            setCanManageAndViewSharedCredentials(principal.hasPrivilege(DqoPermissionGrantedAuthorities.EDIT));
            setCanSynchronizeToDataCatalog(syncToDataCatalogPossible && principal.hasPrivilege(DqoPermissionGrantedAuthorities.EDIT));
        }};

        if (dqoCloudApiKey != null) {
            model.setTenant(dqoCloudApiKey.getApiKeyPayload().getTenantId() + "/" + dqoCloudApiKey.getApiKeyPayload().getTenantGroup());
            model.setLicenseType(dqoCloudApiKey.getApiKeyPayload().getLicenseType() != null ?
                    dqoCloudApiKey.getApiKeyPayload().getLicenseType().toString() : null);
            model.setTrialPeriodExpiresAt(dqoCloudApiKey.getApiKeyPayload().getExpiresAt() != null ?
                    dqoCloudApiKey.getApiKeyPayload().getExpiresAt().toString() : null);
            model.setConnectionsLimit(dqoCloudApiKey.getApiKeyPayload().getLimits().get(DqoCloudLimit.CONNECTIONS_LIMIT));
            model.setUsersLimit(dqoCloudApiKey.getApiKeyPayload().getLimits().get(DqoCloudLimit.USERS_LIMIT));
            model.setMonthsLimit(dqoCloudApiKey.getApiKeyPayload().getLimits().get(DqoCloudLimit.MONTHS_LIMIT));
            model.setConnectionTablesLimit(dqoCloudApiKey.getApiKeyPayload().getLimits().get(DqoCloudLimit.CONNECTION_TABLES_LIMIT));
            model.setTablesLimit(dqoCloudApiKey.getApiKeyPayload().getLimits().get(DqoCloudLimit.TABLES_LIMIT));
            model.setJobsLimit(dqoCloudApiKey.getApiKeyPayload().getLimits().get(DqoCloudLimit.JOBS_LIMIT));
            model.setDataDomainsLimit(dqoCloudApiKey.getApiKeyPayload().getLimits().get(DqoCloudLimit.DATA_DOMAINS_LIMIT));
            model.setDataQualityDataWarehouseEnabled(dqoCloudApiKey.getApiKeyPayload().getDataQualityDataWarehouse() == null ||
                    dqoCloudApiKey.getApiKeyPayload().getDataQualityDataWarehouse() == true);
            model.setCanChangeOwnPassword(true);
            model.setCanUseDataDomains(dqoCloudApiKey.getApiKeyPayload().getLicenseType() == DqoCloudLicenseType.ENTERPRISE);
            model.setCanUseAiAnomalyDetection(dqoCloudApiKey.getApiKeyPayload().getLicenseType() != DqoCloudLicenseType.FREE &&
                    dqoCloudApiKey.getApiKeyPayload().getExpiresAt() == null);
            model.setCanLogout(principal.getUserTokenPayload() != null);
        } else {
            model.setTenant("Standalone");
            model.setLicenseType(DqoCloudLicenseType.FREE.name());
            model.setJobsLimit(1);
            model.setCanChangeOwnPassword(false);
        }

        return model;
    }

    /**
     * Creates a user's profile model for FREE user, not authenticated to DQOps Cloud.
     * @return Empty profile.
     */
    public static DqoUserProfileModel createFreeUserModel() {
        return new DqoUserProfileModel() {{
            setAccountRole(DqoUserRole.ADMIN);
            setCanManageAccount(false);
            setCanViewAnyObject(true);
            setCanManageScheduler(true);
            setCanCancelJobs(true);
            setCanRunChecks(true);
            setCanDeleteData(true);
            setCanCollectStatistics(true);
            setCanManageDataSources(true);
            setCanSynchronize(false);
            setCanEditComments(true);
            setCanEditLabels(true);
            setCanManageDefinitions(true);
            setCanCompareTables(true);
            setCanManageUsers(false);
            setCanManageAndViewSharedCredentials(true);
            setDataQualityDataWarehouseEnabled(false);
            setTenant("Standalone");
            setLicenseType(DqoCloudLicenseType.FREE.name());
            setJobsLimit(1);
        }};
    }
}
