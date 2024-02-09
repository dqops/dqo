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
