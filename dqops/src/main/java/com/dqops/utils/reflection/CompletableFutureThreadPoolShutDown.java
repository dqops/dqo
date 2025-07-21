/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.utils.reflection;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * This helper class retrieves an instance of a ScheduledThreadPoolExecutor from the CompletableFuture.
 * We use it to stop the thread pool when the process exits.
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
@Lazy(false)
public class CompletableFutureThreadPoolShutDown implements DisposableBean {
    /**
     * Stops the thread pool used internally inside the completable future.
     * @throws Exception
     */
    @Override
    public void destroy() {
        try {
            Class<CompletableFuture> completableFutureClass = CompletableFuture.class;
            Optional<Class<?>> delayerClassOpt = Arrays.stream(completableFutureClass.getDeclaredClasses())
                    .filter(clazz -> Objects.equals(clazz.getSimpleName(), "Delayer"))
                    .findFirst();

            Class<?> delayerClass = delayerClassOpt.get();
            Field delayerField = delayerClass.getDeclaredField("delayer");
            delayerField.setAccessible(true);
            ScheduledThreadPoolExecutor poolExecutor = (ScheduledThreadPoolExecutor) delayerField.get(null);
            if (poolExecutor != null) {
                poolExecutor.setContinueExistingPeriodicTasksAfterShutdownPolicy(true);
                poolExecutor.setExecuteExistingDelayedTasksAfterShutdownPolicy(false);
                poolExecutor.setRemoveOnCancelPolicy(true);
                poolExecutor.shutdown();
            }
        }
        catch (Exception ex) {
            System.out.println("Failed to stop the thread pool for the CompletableFuture, exception: " + ex.getMessage());
            System.out.println("You may need to start java with an extra parameter: --add-opens java.base/java.util.concurrent=ALL-UNNAMED");
        }
    }
}
