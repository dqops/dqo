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
package com.dqops.metadata.storage.localfiles.userhome;

import com.dqops.core.principal.UserDomainIdentity;

/**
 * Creates a user come context and loads the home model from the file system.
 */
public interface UserHomeContextFactory {
    /**
     * Opens a local home context, loads the files from the local file system.
     * @param userDomainIdentity User identity that identifies the user for whom we are opening the user home and the data domain for which we are opening the DQOps user home.
     * @param readOnly Make the context read-only.
     * @return User home context with an active user home model that is backed by the local home file system.
     */
    UserHomeContext openLocalUserHome(UserDomainIdentity userDomainIdentity, boolean readOnly);
}
