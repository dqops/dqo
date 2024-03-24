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

package com.dqops.services.locking;

import com.dqops.utils.exceptions.DqoRuntimeException;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Supplier;

/**
 * An object used as a lock for a target object in the REST API.
 */
public final class RestApiObjectLock {
    /**
     * The timeout of holding a lock.
     */
    public static final long LOCK_TIMEOUT_SECONDS = 30;

    private final Object target;
    private final Lock lock = new ReentrantLock();


    /**
     * Creates a lock over a given object.
     * @param target Target object.
     */
    public RestApiObjectLock(Object target) {
        this.target = target;
    }

    /**
     * Returns the target object on which the lock works.
     * @return Target object.
     */
    public Object getTarget() {
        return target;
    }

    /**
     * Runs the given code inside a lock.
     * @param runnable Runnable lambda to call.
     */
    public void runSynchronized(Runnable runnable) {
        try {
            if (!this.lock.tryLock(LOCK_TIMEOUT_SECONDS, TimeUnit.SECONDS)) {
                throw new DqoRuntimeException("Exceeded the time to wait for a REST API lock on the object " + this.target.toString());
            }

            try {
                runnable.run();
            }
            finally {
                this.lock.unlock();
            }
        }
        catch (InterruptedException ex) {
            throw new DqoRuntimeException("Waiting on a lock was interrupted", ex);
        }
    }

    /**
     * Run a code that returns a result (a lambda) within a lock on the target object.
     * @param supplier Lambda to call.
     * @return Returned object from the supplier.
     * @param <T> Returned object type.
     */
    public <T> T callSynchronized(Supplier<T> supplier) {
        try {
            if (!this.lock.tryLock(LOCK_TIMEOUT_SECONDS, TimeUnit.SECONDS)) {
                throw new DqoRuntimeException("Exceeded the time to wait for a REST API lock on the object " + this.target.toString());
            }

            try {
                return supplier.get();
            }
            finally {
                this.lock.unlock();
            }
        }
        catch (InterruptedException ex) {
            throw new DqoRuntimeException("Waiting on a lock was interrupted", ex);
        }
    }

    @Override
    public String toString() {
        return "ObjectLockWrapper{" +
                "target=" + target +
                '}';
    }
}
