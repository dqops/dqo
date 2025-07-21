/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
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
