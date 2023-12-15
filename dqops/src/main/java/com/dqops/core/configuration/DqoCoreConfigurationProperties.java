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
package com.dqops.core.configuration;

import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration POJO with the configuration for the dqo core.
 */
@Configuration
@ConfigurationProperties(prefix = "dqo.core")
@EqualsAndHashCode(callSuper = false)
public class DqoCoreConfigurationProperties implements Cloneable {
    private boolean printStackTrace;
    private long lockWaitTimeoutSeconds = 15 * 60;
    private boolean logMultipleLocking = true;

    /**
     * Prints the stack trace.
     * @return Stack trace is printed when a commands fails.
     */
    public boolean isPrintStackTrace() {
        return printStackTrace;
    }

    /**
     * Enables printing the stack trace when a command fails.
     * @param printStackTrace Print stack trace.
     */
    public void setPrintStackTrace(boolean printStackTrace) {
        this.printStackTrace = printStackTrace;
    }

    /**
     * Returns the timeout in seconds to acquire a lock on shared resources.
     * @return Timeout in seconds.
     */
    public long getLockWaitTimeoutSeconds() {
        return lockWaitTimeoutSeconds;
    }

    /**
     * Sets the timeout in seconds for waiting to acquire a shared read lock or an exclusive write lock.
     * @param lockWaitTimeoutSeconds Timeout in seconds.
     */
    public void setLockWaitTimeoutSeconds(long lockWaitTimeoutSeconds) {
        this.lockWaitTimeoutSeconds = lockWaitTimeoutSeconds;
    }

    /**
     * Returns true if the system will count reader/writer locks obtained for each thread and will report when a thread requests another lock while already having some other locks.
     * @return True when the system should detect multiple locking.
     */
    public boolean isLogMultipleLocking() {
        return logMultipleLocking;
    }

    /**
     * Enables detection of obtaining multiple locks per thread.
     * @param logMultipleLocking True when multiple lock detection is enabled.
     */
    public void setLogMultipleLocking(boolean logMultipleLocking) {
        this.logMultipleLocking = logMultipleLocking;
    }

    /**
     * Creates a clone of the object.
     * @return Cloned instance.
     */
    @Override
    public DqoCoreConfigurationProperties clone() {
        try {
            DqoCoreConfigurationProperties cloned = (DqoCoreConfigurationProperties) super.clone();
            return cloned;
        }
        catch (CloneNotSupportedException ex) {
            throw new RuntimeException("Cannot clone object", ex);
        }
    }
}
