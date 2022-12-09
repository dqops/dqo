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
package ai.dqo.execution;

import ai.dqo.metadata.storage.localfiles.dqohome.DqoHomeContext;
import ai.dqo.metadata.storage.localfiles.dqohome.DqoHomeContextObjectMother;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContext;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContextObjectMother;

/**
 * Check execution context object mother.
 */
public class CheckExecutionContextObjectMother {
    /**
     * Create a check execution context with an empty, in-memory home context and a real dqo context.
     * @return Check execution context.
     */
    public static ExecutionContext createWithInMemoryUserContext() {
        UserHomeContext userHomeContext = UserHomeContextObjectMother.createInMemoryFileHomeContext();
        DqoHomeContext dqoHomeContext = DqoHomeContextObjectMother.getRealDqoHomeContext();
        ExecutionContext executionContext = new ExecutionContext(userHomeContext, dqoHomeContext);
        return executionContext;
    }

    /**
     * Create a check execution context with a testable user home context and a real dqo context.
     * @param recreateTemporaryHomeFolder Recreate (clean) the testable user home context.
     * @return Check execution context.
     */
    public static ExecutionContext createTestableUserContext(boolean recreateTemporaryHomeFolder) {
        UserHomeContext userHomeContext = UserHomeContextObjectMother.createTemporaryFileHomeContext(recreateTemporaryHomeFolder);
        DqoHomeContext dqoHomeContext = DqoHomeContextObjectMother.getRealDqoHomeContext();
        ExecutionContext executionContext = new ExecutionContext(userHomeContext, dqoHomeContext);
        return executionContext;
    }
}
