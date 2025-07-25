/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.core.dqocloud.users;

import com.dqops.cloud.rest.model.DqoUserModel;
import com.dqops.core.dqocloud.login.DqoUserRole;
import com.dqops.core.principal.UserDomainIdentity;
import com.dqops.metadata.settings.domains.LocalDataDomainSpec;
import com.dqops.utils.docs.generators.SampleStringsRegistry;
import com.dqops.utils.docs.generators.SampleValueFactory;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.*;

/**
 * DQOps user model - identifies a user in a multi-user DQOps deployment and the user's roles.
 */
@ApiModel(value = "DqoUserRolesModel", description = "DQOps user model - identifies a user in a multi-user DQOps deployment and the user's roles.")
@Data
public class DqoUserRolesModel {
    /**
     * User's email that identifies the user.
     */
    @JsonPropertyDescription("User's email that identifies the user.")
    private String email;

    /**
     * Account role.
     */
    @JsonPropertyDescription("User role at the whole account level. This role is applicable to all data domains.")
    private DqoUserRole accountRole;

    /**
     * User roles within each data domain. Data domains are supported in an ENTERPRISE version of DQOps and they are managed by the SaaS components of DQOps Cloud.
     */
    @JsonPropertyDescription("User roles within each data domain. Data domains are supported in an ENTERPRISE version of DQOps and they are managed by the SaaS components of DQOps Cloud.")
    private Map<String, DqoUserRole> dataDomainRoles = new LinkedHashMap<>();

    /**
     * Converts the local user model to the cloud user model.
     * @return Cloud user model.
     */
    public DqoUserModel toCloudUserModel() {
        DqoUserModel dqoCloudUserModel = new DqoUserModel();
        dqoCloudUserModel.setEmail(this.getEmail());
        dqoCloudUserModel.setAccountRole(this.getAccountRole().convertToApiAccountRoleEnum());

        if (this.getDataDomainRoles() != null) {
            LinkedHashMap<String, DqoUserModel.InnerEnum> cloudDataDomainRoles = new LinkedHashMap<>();
            for (Map.Entry<String, DqoUserRole> localDomainRolePair : this.getDataDomainRoles().entrySet()) {
                if (localDomainRolePair.getValue() != DqoUserRole.NONE) {
                    String domainName = localDomainRolePair.getKey();
                    if (Objects.equals(domainName, UserDomainIdentity.ROOT_DOMAIN_ALTERNATE_NAME)) {
                        domainName = UserDomainIdentity.ROOT_DATA_DOMAIN;
                    }
                    cloudDataDomainRoles.put(domainName, localDomainRolePair.getValue().convertToApiDomainRoleEnum());
                }
            }

            dqoCloudUserModel.setDataDomainRoles(cloudDataDomainRoles);
        }

        return dqoCloudUserModel;
    }

    /**
     * Creates a local user model from a cloud user model.
     * @param cloudUserModel Cloud (data provider) user model.
     * @param allDataDomains All data domains, even those that the user has no rights. Pass null when the instance is not supporting data domains.
     * @return Local user model.
     */
    public static DqoUserRolesModel createFromCloudModel(DqoUserModel cloudUserModel, Collection<LocalDataDomainSpec> allDataDomains) {
        DqoUserRolesModel localUserModel = new DqoUserRolesModel() {{
            setEmail(cloudUserModel.getEmail());
            setAccountRole(DqoUserRole.convertFromApiAccountRoleEnum(cloudUserModel.getAccountRole()));
        }};

        if (allDataDomains != null) {
            LinkedHashMap<String, DqoUserRole> localDataDomains = new LinkedHashMap<>();
            if (cloudUserModel.getDataDomainRoles() != null) {
                for (Map.Entry<String, DqoUserModel.InnerEnum> cloudDomainRolePair : cloudUserModel.getDataDomainRoles().entrySet()) {
                    String cloudDomainName = cloudDomainRolePair.getKey();
                    String displaySafeDataDomainName = Objects.equals(cloudDomainName, UserDomainIdentity.ROOT_DATA_DOMAIN) ?
                            UserDomainIdentity.ROOT_DOMAIN_ALTERNATE_NAME : cloudDomainName;

                    localDataDomains.put(displaySafeDataDomainName, DqoUserRole.convertFromApiDomainRole(cloudDomainRolePair.getValue()));
                }
            }

            for (LocalDataDomainSpec localDataDomainSpec : allDataDomains) {
                String localDomainName = localDataDomainSpec.getDataDomainName();

                if (cloudUserModel.getDataDomainRoles() != null && cloudUserModel.getDataDomainRoles().containsKey(
                        Objects.equals(localDomainName, UserDomainIdentity.ROOT_DOMAIN_ALTERNATE_NAME) ? UserDomainIdentity.ROOT_DATA_DOMAIN : localDomainName)) {
                    continue; // already mapped
                }

                // add missing data domains with no access rights
                String displaySafeDataDomainName = Objects.equals(localDomainName, UserDomainIdentity.ROOT_DATA_DOMAIN) ?
                        UserDomainIdentity.ROOT_DOMAIN_ALTERNATE_NAME : localDomainName;

                localDataDomains.put(displaySafeDataDomainName, DqoUserRole.NONE);
            }

            localUserModel.setDataDomainRoles(localDataDomains);
        }

        return localUserModel;
    }

    /**
     * Documentation sample factory.
     */
    public static class DqoUserRolesModelSampleFactory implements SampleValueFactory<DqoUserRolesModel> {
        @Override
        public DqoUserRolesModel createSample() {
            return new DqoUserRolesModel() {{
                setEmail(SampleStringsRegistry.getEmail());
                setAccountRole(DqoUserRole.OPERATOR);
                setDataDomainRoles(new LinkedHashMap<>() {{
                    put(UserDomainIdentity.ROOT_DOMAIN_ALTERNATE_NAME, DqoUserRole.EDITOR);
                    put("datalake", DqoUserRole.EDITOR);
                }});
            }};
        }
    }
}
