/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.utils.threading;

import reactor.core.scheduler.Schedulers;

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

/**
 * A custom runner that starts a background call and returns a completable future.
 * It is an alternative to the default CompletableFutureRunner.supplyAsync() method which uses a fixed thread pool
 * that is calculated from the number of threads. On a 1-core VM, it would have only one thread which would block.
 *
 * This alternative implementation uses a bounded elastic thread pool from Project Reactor, which automatically scales
 * the number of threads and has a thread count limit as 10x the number of CPU cores.
 */
public final class CompletableFutureRunner {
    /**
     * Starts an asynchronous operation on a bounded thread pool that is autoscaling and uses 10x up to 10x the number of CPU cores.
     * This type of operations is usable for blocking operations, such as calls from the REST Api controllers.
     * @param callback Callback (supplier) to call.
     * @return Completable future to wait for.
     * @param <T> Result type.
     */
    public static <T> CompletableFuture<T> supplyAsync(Supplier<T> callback) {
        CompletableFuture<T> completableFuture = new CompletableFuture<>();

        Schedulers.boundedElastic().schedule(() -> {
            try {
                if (completableFuture.isCancelled()) {
                    completableFuture.complete(null);
                    return;
                }

                T result = callback.get();
                completableFuture.complete(result);
            } catch (Throwable ex) {
                completableFuture.completeExceptionally(ex);
            }
        });

        return completableFuture;
    }
}
