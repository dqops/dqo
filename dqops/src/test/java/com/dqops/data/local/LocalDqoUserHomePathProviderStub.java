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
package com.dqops.data.local;

import com.dqops.core.principal.DqoUserIdentity;

import java.nio.file.Path;

/**
 * Stub for the local user home path provider.
 */
public class LocalDqoUserHomePathProviderStub implements LocalDqoUserHomePathProvider {
    private Path localUserHomePath;

    public LocalDqoUserHomePathProviderStub(Path localUserHomePath) {
        this.localUserHomePath = localUserHomePath;
    }

    /**
     * Returns the absolute path to the DQO_USER_HOME folder.
     *
     * @param userIdentity User identity.
     * @return Absolute path to the DQOps user home folder.
     */
    @Override
    public Path getLocalUserHomePath(DqoUserIdentity userIdentity) {
        return this.localUserHomePath;
    }
}
