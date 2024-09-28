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
