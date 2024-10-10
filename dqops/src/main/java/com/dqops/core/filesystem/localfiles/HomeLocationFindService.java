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
package com.dqops.core.filesystem.localfiles;

import com.dqops.core.principal.UserDomainIdentity;
import com.dqops.metadata.storage.localfiles.HomeType;

/**
 * Simple service that returns the location of the user home or the DQO_HOME system home.
 */
public interface HomeLocationFindService {
    String DQO_USER_HOME_MARKER_NAME = ".DQO_USER_HOME";

    /**
     * Returns an absolute path to the user home.
     * @param userDomainIdentity User home identity to identify the data domain.
     * @return Absolute path to the user home. May return null if the user home is not enabled.
     */
    String getUserHomePath(UserDomainIdentity userDomainIdentity);

    /**
     * Returns an absolute path to the root user home.
     * @return Absolute path to the user home. May return null if the user home is not enabled.
     */
    String getRootUserHomePath();

    /**
     * Returns an absolute path to the dqo home.
     * @return Absolute path to the DQO_HOME.
     */
    String getDqoHomePath();

    /**
     * Returns the absolute path to a home of choice (user home or DQO_HOME).
     * @param homeType Home type (user home or dqo system home).
     * @param userDomainIdentity User domain identity to identify the data domain when the user home is requested.
     * @return Absolute path to home.
     */
    String getHomePath(HomeType homeType, UserDomainIdentity userDomainIdentity);
}
