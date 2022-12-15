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
package ai.dqo.core.jobqueue;

import ai.dqo.utils.exceptions.DqoRuntimeException;

/**
 * Exception thrown when it was not possible to retrieve a result from a job that was pushed to a job queue.
 * It could be caused by an {@link InterruptedException} or an {@link java.util.concurrent.ExecutionException}.
 */
public class DqoQueueJobExecutionException extends DqoRuntimeException {
    /**
     * Constructs a new runtime exception with the specified detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     */
    public DqoQueueJobExecutionException(String message) {
        super(message);
    }

    public DqoQueueJobExecutionException(Throwable cause) {
        super(cause);
    }
}
