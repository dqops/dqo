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

package com.dqops.utils.exceptions;

/**
 * Helper method that runs a given runnable silently, without throwing any exceptions.
 */
public final class RunSilently {
    /**
     * Silently runs a runnable that could throw an exception.
     * @param runnable Runnable to call.
     *
     * @return Exception that was throw or null when no exception was raised.
     */
    public static Throwable run(RunnableWithException runnable) {
        try {
            runnable.run();
            return null;
        }
        catch (Throwable t) {
            // does nothing, eats the exception
            return t;
        }
    }
}
