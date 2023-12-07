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
package com.dqops.execution;

import com.dqops.core.principal.DqoUserPrincipal;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContext;

/**
 * Execution context factory. Opens the dqo home context and the user home context.
 */
public interface ExecutionContextFactory {
    /**
     * Creates a new execution context by opening the user home context and the dqo system home context.
     * @param principal Calling user principal.
     * @return Check execution context.
     */
    ExecutionContext create(DqoUserPrincipal principal);

    /**
     * Creates a new execution context by using a given user context.
     * @param userHomeContext User home context.
     * @return A new execution context.
     */
    ExecutionContext create(UserHomeContext userHomeContext);
}
