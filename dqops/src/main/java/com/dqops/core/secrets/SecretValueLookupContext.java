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

package com.dqops.core.secrets;

import com.dqops.metadata.userhome.UserHome;

/**
 * Context object passed to the secret value. Provides access to the user home, to use shared credentials.
 */
public class SecretValueLookupContext {
    private UserHome userHome;

    /**
     * Creates a secret value lookup context.
     * @param userHome User home.
     */
    public SecretValueLookupContext(UserHome userHome) {
        this.userHome = userHome;
    }

    /**
     * User home - to look up shared credentials.
     * @return User home.
     */
    public UserHome getUserHome() {
        return userHome;
    }
}
