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

/**
 * User home context local cache, used by the cli command completers to speed up parsing.
 */
public interface UserHomeContextCache {
    /**
     * Notifies the factory that a cached copy of a user home context should be invalidated because a change was written to the user home.
     */
    void invalidateCache();

    /**
     * Returns a cached user home context for the CLI user.
     * @return Cached user home context.
     */
    UserHomeContext getCachedLocalUserHome();

    /**
     * Attaches a reference to a DQOps user home context factory used by this cache.
     * @param userHomeContextFactory User home context factory.
     */
    void setUserHomeContextFactory(UserHomeContextFactory userHomeContextFactory);
}
