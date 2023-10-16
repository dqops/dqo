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
    ADMIN,

    /**
     * Editor who can configure and run data quality checks.
     */
    @JsonProperty("editor")
    EDITOR,

    /**
     * The user can run data quality checks, but cannot make changes.
     */
    @JsonProperty("operator")
    OPERATOR,

    /**
     * Just a read-only viewer.
     */
    @JsonProperty("viewer")
    VIEWER,

    /**
     * No access rights role.
     */
    @JsonProperty("none")
    NONE;

    /**
     * Converts an account role from a client generated from DQOps Cloud Swagger.
     * @param accountRole Account role enum from the code generated from swagger.
     * @return DQOps account role.
     */
    public static DqoUserRole convertFromApiEnum(DqoUserModel.AccountRoleEnum accountRole) {
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
    public DqoUserModel.AccountRoleEnum convertToApiEnum() {
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
}
