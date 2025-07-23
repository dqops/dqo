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

import com.dqops.BaseTest;
import com.dqops.utils.exceptions.DqoRuntimeException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Mono;

import java.util.concurrent.CompletableFuture;

@SpringBootTest
public class CompletableFutureRunnerTests extends BaseTest {
    @Test
    void supplyAsync_whenOperationReturnsValue_thenReturnsIt() {
        Object expected = new Object();
        CompletableFuture<Object> completableFuture = CompletableFutureRunner.supplyAsync(() -> expected);
        Object result = completableFuture.join();
        Assertions.assertSame(expected, result);
    }

    @Test
    void supplyAsync_whenOperationThrowsException_thenRethrowsException() {
        CompletableFuture<Object> completableFuture = CompletableFutureRunner.supplyAsync(() -> {
            throw new DqoRuntimeException();
        });
        Assertions.assertThrows(Exception.class, () -> {
            Object result = completableFuture.join();
        });
    }

    @Test
    void supplyAsync_whenOperationWaitsForMono_thenReturnsResult() {
        Object expected = new Object();
        CompletableFuture<Object> completableFuture = CompletableFutureRunner.supplyAsync(() -> {
            Mono<Object> mono = Mono.fromCallable(() -> expected);
            return mono.block();
        });
        Object result = completableFuture.join();
        Assertions.assertSame(expected, result);
    }
}
