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
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHeaders;
import org.apache.parquet.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;

/**
 * Incident notification service that sends notifications when new data quality incidents are detected.
 */
@Component
@Slf4j
public class IncidentNotificationServiceImpl implements IncidentNotificationService {
    @Autowired
    public IncidentNotificationServiceImpl() {
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
     * @param newIncidentNotificationMessage New incident notification payload.
     * @param webhookUrl Target webhook url.
     * @return Mono that returns the target webhook url.
     */
    protected Mono<String> sendNotification(IncidentNotificationMessage newIncidentNotificationMessage, String webhookUrl) {
        WebClient webClient = WebClient.builder()
                .baseUrl(webhookUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();

        WebClient.ResponseSpec responseSpec = webClient.method(HttpMethod.POST)
                .body(Mono.just(newIncidentNotificationMessage), IncidentNotificationMessage.class)
                .retrieve();

        Mono<ResponseEntity<Void>> responseEntityMono = responseSpec.toBodilessEntity();
        return responseEntityMono.retry(3).thenReturn(webhookUrl);
    }
}
