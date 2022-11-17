/*
 * Copyright © 2021 DQO.ai (support@dqo.ai)
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
package ai.dqo.metadata.storage.localfiles.userhome;

/**
 * Testable stub that returns a provided user home context.
 */
public class UserHomeContextFactoryStub implements UserHomeContextFactory {
    private UserHomeContext userHomeContext;

    /**
     * Create a user home context factory stub that will always return the same user come context.
     * @param userHomeContext User home context to return.
     */
    public UserHomeContextFactoryStub(UserHomeContext userHomeContext) {
        this.userHomeContext = userHomeContext;
    }

    /**
     * Opens a local home context, loads the files from the local file system.
     *
     * @return User home context with an active user home model that is backed by the local home file system.
     */
    @Override
    public UserHomeContext openLocalUserHome() {
        return this.userHomeContext;
    }
}
