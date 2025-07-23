/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.core.dqocloud.login;

import com.dqops.cloud.rest.model.DqoUserModel;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * DQOps user role within a data domain or a whole account level.
 */
public enum DqoUserRole {
    /**
     * Administrator (owner) of the account who can manage users and perform all actions.
     */
    @JsonProperty("admin")
    ADMIN(1),

    /**
     * Editor who can configure and run data quality checks.
     */
    @JsonProperty("editor")
    EDITOR(2),

    /**
     * The user can run data quality checks, but cannot make changes.
     */
    @JsonProperty("operator")
    OPERATOR(3),

    /**
     * Just a read-only viewer.
     */
    @JsonProperty("viewer")
    VIEWER(4),

    /**
     * No access rights role.
     */
    @JsonProperty("none")
    NONE(5);

    private int priority;

    DqoUserRole(int priority) {
        this.priority = priority;
    }

    /**
     * Role priority (role strength). Smaller value means a role with more permissions.
     * @return Role priority. 1 is the most powerful role (ADMIN).
     */
    public int getPriority() {
        return priority;
    }

    /**
     * Converts an account role from a client generated from DQOps Cloud Swagger.
     * @param accountRole Account role enum from the code generated from swagger.
     * @return DQOps account role.
     */
    public static DqoUserRole convertFromApiAccountRoleEnum(DqoUserModel.AccountRoleEnum accountRole) {
        if (accountRole == null) {
            return null;
        }

        switch (accountRole) {
            case ADMIN:
                return ADMIN;

            case EDITOR:
                return EDITOR;

            case OPERATOR:
                return OPERATOR;

            case VIEWER:
                return VIEWER;

            case NONE:
                return NONE;

            default:
                throw new IllegalArgumentException("Account role " + accountRole + " not supported");
        }
    }

    /**
     * Converts the account role to a role defined in the DQOps Cloud api.
     * @return DQOps Cloud API role.
     */
    public DqoUserModel.AccountRoleEnum convertToApiAccountRoleEnum() {
        switch (this) {
            case ADMIN:
                return DqoUserModel.AccountRoleEnum.ADMIN;

            case EDITOR:
                return DqoUserModel.AccountRoleEnum.EDITOR;

            case OPERATOR:
                return DqoUserModel.AccountRoleEnum.OPERATOR;

            case VIEWER:
                return DqoUserModel.AccountRoleEnum.VIEWER;

            case NONE:
                return DqoUserModel.AccountRoleEnum.NONE;

            default:
                throw new IllegalArgumentException("Account role " + this + " not supported");
        }
    }

    /**
     * Converts a data domain role from a client generated from DQOps Cloud Swagger.
     * @param domainRole Data domain role enum from the code generated from swagger.
     * @return DQOps local data domain role.
     */
    public static DqoUserRole convertFromApiDomainRole(DqoUserModel.InnerEnum domainRole) {
        if (domainRole == null) {
            return null;
        }

        switch (domainRole) {
            case ADMIN:
                return ADMIN;

            case EDITOR:
                return EDITOR;

            case OPERATOR:
                return OPERATOR;

            case VIEWER:
                return VIEWER;

            case NONE:
                return NONE;

            default:
                throw new IllegalArgumentException("Data domain role " + domainRole + " not supported");
        }
    }

    /**
     * Converts the domain role to a domain role defined in the DQOps Cloud API.
     * @return DQOps Cloud API data domain role.
     */
    public DqoUserModel.InnerEnum convertToApiDomainRoleEnum() {
        switch (this) {
            case ADMIN:
                return DqoUserModel.InnerEnum.ADMIN;

            case EDITOR:
                return DqoUserModel.InnerEnum.EDITOR;

            case OPERATOR:
                return DqoUserModel.InnerEnum.OPERATOR;

            case VIEWER:
                return DqoUserModel.InnerEnum.VIEWER;

            case NONE:
                return DqoUserModel.InnerEnum.NONE;

            default:
                throw new IllegalArgumentException("Domain role " + this + " not supported");
        }
    }

    /**
     * Returns the most powerful role out of two roles to compare. Usually one is the account level role and the second is the data domain level role.
     * @param r1 First role to compare.
     * @param r2 Second role to compare.
     * @return One of the roles, that gives more permissions.
     */
    public static DqoUserRole strongest(DqoUserRole r1, DqoUserRole r2) {
        if (r1 == null) {
            return r2;
        }

        if (r2 == null) {
            return r1;
        }

        if (r1.priority <= r2.priority) {
            return r1;
        }

        return r2;
    }
}
