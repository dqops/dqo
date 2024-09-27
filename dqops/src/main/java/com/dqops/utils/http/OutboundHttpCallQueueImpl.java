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

package com.dqops.utils.http;

import com.dqops.core.configuration.DqoNotificationsRestConfigurationProperties;
import com.dqops.data.checkresults.statuscache.TableStatusCache;
import io.netty.buffer.Unpooled;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHeaders;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;
import reactor.core.scheduler.Schedulers;
import reactor.netty.http.client.HttpClient;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * A fire-and-forget service for calling REST API in one way to send notifications (webhook calls).
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
@Slf4j
public class OutboundHttpCallQueueImpl implements OutboundHttpCallQueue, InitializingBean, DisposableBean {
    public static int SUBSCRIBER_BACKPRESSURE_BUFFER_SIZE = 1000000; // the number of awaiting operations in the backpressure buffer (queue)
    private boolean started;
    private Sinks.Many<OutboundHttpMessage> messageQueueSink;
    private Disposable subscription;
    private final Object lock = new Object();
    private final List<OutboundHttpMessage> messagesToRetryNextTurn = new ArrayList<>();
    private final List<OutboundHttpMessage> messagesToRetryOnSchedule = new ArrayList<>();
    private final SharedHttpClientProvider sharedHttpClientProvider;
    private final DqoNotificationsRestConfigurationProperties dqoNotificationsRestConfigurationProperties;

    /**
     * Default injection container.
     * @param sharedHttpClientProvider REST API client factory.
     * @param dqoNotificationsRestConfigurationProperties Configuration properties.
     */
    @Autowired
    public OutboundHttpCallQueueImpl(
            SharedHttpClientProvider sharedHttpClientProvider,
            DqoNotificationsRestConfigurationProperties dqoNotificationsRestConfigurationProperties) {
        this.sharedHttpClientProvider = sharedHttpClientProvider;
        this.dqoNotificationsRestConfigurationProperties = dqoNotificationsRestConfigurationProperties;
    }

    /**
     * Creates a failure handler with a new duration.
     * @return Failure handler.
     */
    protected Sinks.EmitFailureHandler createFailureHandler() {
        return Sinks.EmitFailureHandler.busyLooping(Duration.ofSeconds(
                this.dqoNotificationsRestConfigurationProperties.getPublishBusyLoopingDurationSeconds()));
    }

    /**
     * Scheduled operation called by Spring framework. It checks if there are any messages to be retried and sends these messages.
     */
    @Scheduled(fixedRate = 30, timeUnit = TimeUnit.SECONDS)
    public void scheduleMessageRetries() {
        OutboundHttpMessage[] messagesForRetry = null;
        synchronized (this.lock) {
            messagesForRetry = this.messagesToRetryOnSchedule.toArray(OutboundHttpMessage[]::new);
            this.messagesToRetryOnSchedule.clear();
            this.messagesToRetryOnSchedule.addAll(this.messagesToRetryNextTurn);
            this.messagesToRetryNextTurn.clear();
        }

        for (OutboundHttpMessage outboundHttpMessage : messagesForRetry) {
            sendMessage(outboundHttpMessage); // requeue to be sent again
        }
    }

    /**
     * Adds a message to a queue. Messages will be picked by a sending engine and sent to the target URL.
     * In case of errors, the call service will reply these messages.
     * @param outboundHttpMessage Outbound http message.
     */
    @Override
    public void sendMessage(OutboundHttpMessage outboundHttpMessage) {
        try {
            if (outboundHttpMessage.getRemainingRetries() == null) {
                outboundHttpMessage.setRemainingRetries(this.dqoNotificationsRestConfigurationProperties.getMaxRetries());
            }
            this.messageQueueSink.emitNext(outboundHttpMessage, createFailureHandler());
        }
        catch (Exception ex) {
            log.error("Failed to queue an outbound HTTP call message, error: " + ex.getMessage(), ex);
        }
    }

    /**
     * Make a REST API call to an outbound service.
     * @param outboundHttpMessage Outbound message to send.
     * @return Mono returned after the call, for both positive and negative calls.
     */
    public Mono<Void> onMakeOutboundCall(OutboundHttpMessage outboundHttpMessage) {
        HttpClient httpClient = this.sharedHttpClientProvider.getHttp11SharedClient();
        byte[] messageBytes = outboundHttpMessage.getMessage().getBytes(StandardCharsets.UTF_8);

        HttpClient headers = httpClient
                .headers(httpHeaders -> {
                    httpHeaders.add(HttpHeaders.CONTENT_TYPE, outboundHttpMessage.getMimeType());
                    httpHeaders.add(HttpHeaders.CONTENT_LENGTH, messageBytes.length);
                });

        HttpClient.RequestSender requestSender =
                Objects.equals(outboundHttpMessage.getMethod(), "POST") ? headers.post() :
                Objects.equals(outboundHttpMessage.getMethod(), "PUT") ? headers.put() :
                        null; // intentional..

        Mono<Void> responseSent = requestSender
                .uri(outboundHttpMessage.getUrl())
                .send((request, outbound) -> {
                    request.responseTimeout(Duration.ofSeconds(this.dqoNotificationsRestConfigurationProperties.getResponseTimeoutSeconds()));
                    return outbound.send(Mono.fromCallable(() -> Unpooled.wrappedBuffer(messageBytes)));
                })
                .response()
                .onErrorComplete(error -> {
                    log.warn("Failed to send a notification message to url " + outboundHttpMessage.getUrl() + ", error: " + error.getMessage(), error);

                    if (outboundHttpMessage.getRemainingRetries() != null && outboundHttpMessage.getRemainingRetries() > 0) {
                        outboundHttpMessage.setRemainingRetries(outboundHttpMessage.getRemainingRetries() - 1);

                        synchronized (this.lock) {
                            this.messagesToRetryNextTurn.add(outboundHttpMessage);
                        }
                    }

                    return true;
                })
                .then();

        return responseSent;
    }

    /**
     * Initialization operation.
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        if (this.started) {
            return;
        }

        this.started = true;

        this.messageQueueSink = Sinks.many().multicast().onBackpressureBuffer();
        Flux<List<OutboundHttpMessage>> requestLoadFlux = this.messageQueueSink.asFlux()
                .onBackpressureBuffer(SUBSCRIBER_BACKPRESSURE_BUFFER_SIZE)
                .buffer(Duration.ofMillis(TableStatusCache.BATCH_COLLECTION_TIMEOUT_MS));  // wait 50 millis, maybe multiple file system updates are made, like changing multiple parquet files... we want to merge all file changes
        int concurrency = this.dqoNotificationsRestConfigurationProperties.getMaxParallelCalls();
        this.subscription = requestLoadFlux.subscribeOn(Schedulers.boundedElastic())
                .flatMap(list -> Flux.fromIterable(list)) // single thread forwarder
                .parallel(concurrency)
                .flatMap(outboundHttpMessage -> {
                    return onMakeOutboundCall(outboundHttpMessage);
                })
                .subscribe();
    }

    /**
     * Destruction operation called when the service is being stopped.
     * @throws Exception
     */
    @Override
    public void destroy() throws Exception {
        if (!this.started) {
            return;
        }

        try {
            this.started = false;
            Sinks.EmitFailureHandler emitFailureHandler = Sinks.EmitFailureHandler.busyLooping(Duration.ofSeconds(
                    this.dqoNotificationsRestConfigurationProperties.getPublishBusyLoopingDurationSeconds()));
            this.messageQueueSink.emitComplete(emitFailureHandler);
            this.subscription.dispose();
        }
        catch (Exception ex) {
            log.error("Failed to stop the notification call service, error: " + ex.getMessage(), ex);
        }
    }
}
