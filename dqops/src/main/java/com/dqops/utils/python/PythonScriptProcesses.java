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

package com.dqops.utils.python;

import java.util.Stack;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * A container of Python processes that are running a single Python script.
 */
public class PythonScriptProcesses {
    private final Stack<StreamingPythonProcess> availableProcesses = new Stack<>();
    private final AtomicInteger runningProcesses = new AtomicInteger();

    /**
     * Returns a stack of running processes.
     * @return A stack of running processes.
     */
    public Stack<StreamingPythonProcess> getAvailableProcesses() {
        return this.availableProcesses;
    }

    /**
     * Returns the current count of running processes.
     * @return Count of running proceses.
     */
    public int getRunningProcesses() {
        return this.runningProcesses.get();
    }

    /**
     * Increments the number of running processes if it is below the accepted MaxDOP. Returns true if one more process was accepted because it up to the MaxDOP limit,
     * or returns false when the counter was not increased because it is already equal or above MaxDOP.
     * @param maxDop The maximum accepted DOP (number of parallel processes).
     * @return True if another process is allowed to run, false when the MaxDOP was achieved.
     */
    public boolean incrementRunningProcesses(int maxDop) {
        while (true) {
            int currentProcessCount = this.runningProcesses.get();

            if (currentProcessCount < maxDop) {
                if (this.runningProcesses.compareAndSet(currentProcessCount, currentProcessCount + 1)) {
                    return true;
                }
            } else {
                return false;
            }
        }
    }

    /**
     * Decrements the count of running processes.
     * @return The count of running processes after the change.
     */
    public int decrementRunningProcesses() {
        return this.runningProcesses.decrementAndGet();
    }
}
