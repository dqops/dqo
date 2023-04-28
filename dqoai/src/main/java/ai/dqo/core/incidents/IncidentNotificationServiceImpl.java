/*
 * Copyright © 2021 DQO.ai (support@dqo.ai)
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
package ai.dqo.core.incidents;

import ai.dqo.metadata.incidents.IncidentGroupingSpec;
import ai.dqo.metadata.incidents.IncidentWebhookNotificationsSpec;
import ai.dqo.utils.http.SharedHttpClientProvider;
import ai.dqo.utils.serialization.JsonSerializer;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
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
import reactor.netty.ByteBufFlux;
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

    /**
     * Creates an incident notification service.
     * @param sharedHttpClientProvider Shared http client provider that manages the HTTP connection pooling.
     * @param jsonSerializer Json serializer.
     */
    @Autowired
    public IncidentNotificationServiceImpl(SharedHttpClientProvider sharedHttpClientProvider,
                                           JsonSerializer jsonSerializer) {
        this.sharedHttpClientProvider = sharedHttpClientProvider;
        this.jsonSerializer = jsonSerializer;
    }

    /**
     * Sends new incident notifications to the notification targets (webhooks) specified in the incident grouping configuration.
     *
     * @param newMessages      List of new data quality incidents that will be sent as a payload.
     * @param incidentGrouping Incident grouping that identifies the notification target (where to send the notifications).
     */
    @Override
    public void sendNotifications(List<IncidentNotificationMessage> newMessages, IncidentGroupingSpec incidentGrouping) {
        if (incidentGrouping == null || incidentGrouping.getWebhooks() == null) {
            return;
        }

        Mono<Void> finishedSendMono = sendAllNotifications(newMessages, incidentGrouping);
        finishedSendMono.subscribe(); // starts a background task (fire-and-forget), running on reactor
    }

    /**
     * Sends all notifications, one by one.
     * @param newMessages Messages with new data quality incidents.
     * @param incidentGrouping Incident grouping configuration with the webhook.
     * @return Awaitable Mono object.
     */
    protected Mono<Void> sendAllNotifications(List<IncidentNotificationMessage> newMessages, IncidentGroupingSpec incidentGrouping) {
        final IncidentWebhookNotificationsSpec webhooksSpec = incidentGrouping.getWebhooks();

        Mono<Void> allNotificationsSent = Flux.fromIterable(newMessages)
                .filter(message -> !Strings.isNullOrEmpty(webhooksSpec.getWebhookUrlForStatus(message.getStatus())))
                .flatMap(message -> sendNotification(message, webhooksSpec.getWebhookUrlForStatus(message.getStatus())))
                .onErrorContinue((Throwable ex, Object msg) -> {
                    log.error("Failed to send notification to webhook: " +
                            msg.toString() + ", message: " + ex.getMessage(), ex);
                })
                .subscribeOn(Schedulers.parallel())
                .then();

        return allNotificationsSent;
    }

    /**
     * Sets a single notification with one incident.
     * @param incidentNotificationMessage Incident notification payload.
     * @param webhookUrl Target webhook url.
     * @return Mono that returns the target webhook url.
     */
    protected Mono<String> sendNotification(IncidentNotificationMessage incidentNotificationMessage, String webhookUrl) {
        HttpClient httpClient = this.sharedHttpClientProvider.getHttp11SharedClient();
        String serializedJsonMessage = this.jsonSerializer.serialize(incidentNotificationMessage);
        byte[] messageBytes = serializedJsonMessage.getBytes(StandardCharsets.UTF_8);

        Mono<Void> responseSent = httpClient
                .headers(httpHeaders -> {
                    httpHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE + "; charset=utf-8");
                    httpHeaders.add(HttpHeaders.CONTENT_LENGTH, messageBytes.length);
                })
                .post()
                .uri(webhookUrl)
                .send(Mono.fromCallable(() -> Unpooled.wrappedBuffer(messageBytes)))
                .response()
                .then();

        return responseSent.retry(3).thenReturn(webhookUrl);
    }
}
