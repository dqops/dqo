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
package com.dqops.core.dqocloud.apikey;

/**
 * DQO Cloud license type.
 */
public enum DqoCloudLicenseType {
    /**
     * Free (community) user.
     */
    FREE,

    /**
     * Personal instance of one user.
     */
    PERSONAL,

    /**
     * Team instance for a group of users.
     */
    TEAM,

    /**
     * Enterprise instance for bigger teams and additional features on request.
     */
    ENTERPRISE
}
