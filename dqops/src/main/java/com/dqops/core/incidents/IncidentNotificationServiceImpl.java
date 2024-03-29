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
package com.dqops.core.incidents;

import com.dqops.core.principal.UserDomainIdentity;
import com.dqops.execution.ExecutionContext;
import com.dqops.execution.ExecutionContextFactory;
import com.dqops.metadata.incidents.ConnectionIncidentGroupingSpec;
import com.dqops.metadata.incidents.IncidentWebhookNotificationsSpec;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContext;
import com.dqops.metadata.incidents.defaultnotifications.DefaultIncidentWebhookNotificationsWrapper;
import com.dqops.metadata.userhome.UserHome;
import com.dqops.utils.http.SharedHttpClientProvider;
import com.dqops.utils.serialization.JsonSerializer;
import io.netty.buffer.Unpooled;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHeaders;
import org.apache.parquet.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.netty.http.client.HttpClient;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * Incident notification service that sends notifications when new data quality incidents are detected.
 */
@Component
@Slf4j
public class IncidentNotificationServiceImpl implements IncidentNotificationService {
    private SharedHttpClientProvider sharedHttpClientProvider;
    private JsonSerializer jsonSerializer;
    private ExecutionContextFactory executionContextFactory;

    /**
     * Creates an incident notification service.
     *
     * @param sharedHttpClientProvider Shared http client provider that manages the HTTP connection pooling.
     * @param jsonSerializer           Json serializer.
     * @param executionContextFactory  Execution context factory.
     */
    @Autowired
    public IncidentNotificationServiceImpl(SharedHttpClientProvider sharedHttpClientProvider,
                                           JsonSerializer jsonSerializer,
                                           ExecutionContextFactory executionContextFactory) {
        this.sharedHttpClientProvider = sharedHttpClientProvider;
        this.jsonSerializer = jsonSerializer;
        this.executionContextFactory = executionContextFactory;
    }

    /**
     * Sends new incident notifications to the notification targets (webhooks) specified in the incident grouping configuration.
     *
     * @param newMessages      List of new data quality incidents that will be sent as a payload.
     * @param incidentGrouping Incident grouping that identifies the notification target (where to send the notifications).
     * @param userIdentity     User identity that specifies the data domain where the webhooks are defined.
     */
    @Override
    public void sendNotifications(List<IncidentNotificationMessage> newMessages,
                                  ConnectionIncidentGroupingSpec incidentGrouping,
                                  UserDomainIdentity userIdentity) {
        IncidentWebhookNotificationsSpec webhooksSpec = prepareWebhooks(incidentGrouping, userIdentity);
        Mono<Void> finishedSendMono = sendAllNotifications(newMessages, webhooksSpec);
        finishedSendMono.subscribe(); // starts a background task (fire-and-forget), running on reactor
    }

    /**
     * Sends all notifications, one by one.
     * @param newMessages Messages with new data quality incidents.
     * @param webhooksSpec Webhook specification.
     * @return Awaitable Mono object.
     */
    protected Mono<Void> sendAllNotifications(List<IncidentNotificationMessage> newMessages, IncidentWebhookNotificationsSpec webhooksSpec) {
        Mono<Void> allNotificationsSent = Flux.fromIterable(newMessages)
                .filter(message -> !Strings.isNullOrEmpty(webhooksSpec.getWebhookUrlForStatus(message.getStatus())))
                .map(message -> new MessageWebhookPair(message, webhooksSpec.getWebhookUrlForStatus(message.getStatus())))
                .filter(messageUrlPair -> !Strings.isNullOrEmpty(messageUrlPair.getWebhookUrl()))
                .flatMap(messageUrlPair -> sendNotification(messageUrlPair))
                .onErrorContinue((Throwable ex, Object msg) -> {
                    log.error("Failed to send notification to webhook: " +
                            msg + ", error: " + ex.getMessage(), ex);
                })
                .subscribeOn(Schedulers.parallel())
                .then();

        return allNotificationsSent;
    }

    /**
     * Sets a single notification with one incident.
     * @param messageWebhookPair Incident notification payload and webhook url pair.
     * @return Mono that returns the target webhook url.
     */
    protected Mono<MessageWebhookPair> sendNotification(MessageWebhookPair messageWebhookPair) {
        HttpClient httpClient = this.sharedHttpClientProvider.getHttp11SharedClient();
        String serializedJsonMessage = this.jsonSerializer.serialize(messageWebhookPair.getIncidentNotificationMessage());
        byte[] messageBytes = serializedJsonMessage.getBytes(StandardCharsets.UTF_8);

        Mono<Void> responseSent = httpClient
                .headers(httpHeaders -> {
                    httpHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE + "; charset=utf-8");
                    httpHeaders.add(HttpHeaders.CONTENT_LENGTH, messageBytes.length);
                })
                .post()
                .uri(messageWebhookPair.getWebhookUrl())
                .send(Mono.fromCallable(() -> Unpooled.wrappedBuffer(messageBytes)))
                .response()
                .then();

        return responseSent.retry(3).thenReturn(messageWebhookPair);
    }

    /**
     * Returns a combined list of web hooks, combining the default notification channels with the notification settings on a connection.
     * @param incidentGrouping Incident grouping and notification settings from a connection.
     * @param userIdentity User identity that also specifies the data domain where the webhooks are defined.
     * @return Effective notification settings with webhooks that could be the default values.
     */
    protected IncidentWebhookNotificationsSpec prepareWebhooks(ConnectionIncidentGroupingSpec incidentGrouping,
                                                               UserDomainIdentity userIdentity){
        ExecutionContext executionContext = executionContextFactory.create(userIdentity);
        UserHomeContext userHomeContext = executionContext.getUserHomeContext();
        UserHome userHome = userHomeContext.getUserHome();
        DefaultIncidentWebhookNotificationsWrapper defaultWebhooksWrapper = userHome.getDefaultNotificationWebhook();
        IncidentWebhookNotificationsSpec defaultWebhooks = defaultWebhooksWrapper.getSpec();
        if (defaultWebhooks == null) {
            defaultWebhooks = new IncidentWebhookNotificationsSpec();
        }

        if (incidentGrouping == null || incidentGrouping.getWebhooks() == null){
            return defaultWebhooks.deepClone();
        } else {
            return incidentGrouping.getWebhooks().combineWithDefaults(defaultWebhooks);
        }
    }

}
