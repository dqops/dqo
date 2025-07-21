/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
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
