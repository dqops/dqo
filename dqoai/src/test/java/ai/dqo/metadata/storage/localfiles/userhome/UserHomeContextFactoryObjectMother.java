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
 * User home context factory object mother that creates a testable user home context factory
 * that uses a predefined user home context (in-memory or file based).
 */
public final class UserHomeContextFactoryObjectMother {
    /**
     * Creates a user home context factory that always returns an in-memory user context.
     * @return Returns an in-memory user context.
     */
    public static UserHomeContextFactory createWithInMemoryContext() {
        UserHomeContext inMemoryFileHomeContext = UserHomeContextObjectMother.createInMemoryFileHomeContext();
        return new UserHomeContextFactoryStub(inMemoryFileHomeContext);
    }

    /**
     * Creates a user home context factory that creates an empty, file-based user context in a temporary folder.
     * @return Returns a stub factory that returns an empty file based user context.
     */
    public static UserHomeContextFactory createWithEmptyTemporaryContext() {
        UserHomeContext inMemoryFileHomeContext = UserHomeContextObjectMother.createTemporaryFileHomeContext(true);
        return new UserHomeContextFactoryStub(inMemoryFileHomeContext);
    }
}
