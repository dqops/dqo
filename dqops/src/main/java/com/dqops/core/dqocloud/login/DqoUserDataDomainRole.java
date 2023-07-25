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

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * DQO user role within a data domain.
 */
public enum DqoUserDataDomainRole {
    /**
     * Editor who can configure and run data quality checks.
     */
    @JsonProperty("ed")
    EDITOR,

    /**
     * The user can run data quality checks, but cannot make changes.
     */
    @JsonProperty("rn")
    RUNNER,

    /**
     * Just a read-only viewer.
     */
    @JsonProperty("vr")
    VIEWER
}
