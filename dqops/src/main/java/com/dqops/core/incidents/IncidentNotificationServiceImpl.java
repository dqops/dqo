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

import com.dqops.core.incidents.email.EmailSender;
import com.dqops.core.incidents.email.EmailSenderProvider;
import com.dqops.core.principal.UserDomainIdentity;
import com.dqops.execution.ExecutionContext;
import com.dqops.execution.ExecutionContextFactory;
import com.dqops.metadata.incidents.ConnectionIncidentGroupingSpec;
import com.dqops.metadata.incidents.IncidentNotificationSpec;
import com.dqops.metadata.incidents.defaultnotifications.DefaultIncidentNotificationsWrapper;
import com.dqops.metadata.settings.SmtpServerConfigurationSpec;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContext;
import com.dqops.metadata.userhome.UserHome;
import com.dqops.utils.http.SharedHttpClientProvider;
import com.dqops.utils.serialization.JsonSerializer;
import io.netty.buffer.Unpooled;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHeaders;
import org.apache.parquet.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
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
    private EmailSenderProvider emailSenderProvider;
    private final IncidentNotificationMessageMarkdownFormatter incidentNotificationMessageMarkdownFormatter;
    private final IncidentNotificationHtmlMessageFormatter incidentNotificationHtmlMessageFormatter;


    /**
     * Creates an incident notification service.
     *
     * @param sharedHttpClientProvider                     Shared http client provider that manages the HTTP connection pooling.
     * @param jsonSerializer                               Json serializer.
     * @param executionContextFactory                      Execution context factory.
     * @param incidentNotificationMessageMarkdownFormatter
     * @param incidentNotificationHtmlMessageFormatter
     */
    @Autowired
    public IncidentNotificationServiceImpl(SharedHttpClientProvider sharedHttpClientProvider,
                                           JsonSerializer jsonSerializer,
                                           ExecutionContextFactory executionContextFactory,
                                           EmailSenderProvider emailSenderProvider,
                                           IncidentNotificationMessageMarkdownFormatter incidentNotificationMessageMarkdownFormatter,
                                           IncidentNotificationHtmlMessageFormatter incidentNotificationHtmlMessageFormatter) {
        this.sharedHttpClientProvider = sharedHttpClientProvider;
        this.jsonSerializer = jsonSerializer;
        this.executionContextFactory = executionContextFactory;
        this.emailSenderProvider = emailSenderProvider;
        this.incidentNotificationMessageMarkdownFormatter = incidentNotificationMessageMarkdownFormatter;
        this.incidentNotificationHtmlMessageFormatter = incidentNotificationHtmlMessageFormatter;
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
        IncidentNotificationSpec webhooksSpec = prepareWebhooks(incidentGrouping, userIdentity);
        Mono<Void> finishedSendMono = sendAllNotifications(newMessages, webhooksSpec, userIdentity);
        finishedSendMono.subscribe(); // starts a background task (fire-and-forget), running on reactor
    }

    /**
     * Sends all notifications, one by one.
     * @param newMessages Messages with new data quality incidents.
     * @param notificationSpec Webhook specification.
     * @param userIdentity     User identity that specifies the data domain where the webhooks are defined.
     * @return Awaitable Mono object.
     */
    protected Mono<Void> sendAllNotifications(List<IncidentNotificationMessage> newMessages,
                                              IncidentNotificationSpec notificationSpec,
                                              UserDomainIdentity userIdentity) {
        Mono<Void> allNotificationsSent = Flux.fromIterable(newMessages)
                .filter(message -> !Strings.isNullOrEmpty(notificationSpec.getNotificationAddressForStatus(message.getStatus())))
                .map(message -> new MessageAddressPair(message, notificationSpec.getNotificationAddressForStatus(message.getStatus())))
                .filter(messageAddressPair -> !Strings.isNullOrEmpty(messageAddressPair.getNotificationAddress()))
                .flatMap(messageAddressPair -> {
                    // todo: messageAddressPair might contains comma separated addresses
                    if(messageAddressPair.getNotificationAddress().contains("@")){
                        String incidentText = incidentNotificationHtmlMessageFormatter.prepareText(messageAddressPair.getIncidentNotificationMessage());
                        messageAddressPair.getIncidentNotificationMessage().setText(incidentText);

                        return sendEmailNotification(messageAddressPair, userIdentity);
                    }

                    String incidentText = incidentNotificationMessageMarkdownFormatter.prepareText(messageAddressPair.getIncidentNotificationMessage());
                    messageAddressPair.getIncidentNotificationMessage().setText(incidentText);

                    return sendWebhookNotification(messageAddressPair);
                })
                .onErrorContinue((Throwable ex, Object msg) -> {
                    log.error("Failed to send notification to address(es): " +
                            msg + ", error: " + ex.getMessage(), ex);
                })
                .subscribeOn(Schedulers.parallel())
                .then();

        return allNotificationsSent;
    }

    /**
     * Sets a single notification with one incident.
     * @param messageAddressPair Incident notification payload and webhook url pair.
     * @return Mono that returns the target webhook url.
     */
    protected Mono<MessageAddressPair> sendWebhookNotification(MessageAddressPair messageAddressPair) {
        HttpClient httpClient = this.sharedHttpClientProvider.getHttp11SharedClient();
        String serializedJsonMessage = this.jsonSerializer.serialize(messageAddressPair.getIncidentNotificationMessage());
        byte[] messageBytes = serializedJsonMessage.getBytes(StandardCharsets.UTF_8);

        Mono<Void> responseSent = httpClient
                .headers(httpHeaders -> {
                    httpHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE + "; charset=utf-8");
                    httpHeaders.add(HttpHeaders.CONTENT_LENGTH, messageBytes.length);
                })
                .post()
                .uri(messageAddressPair.getNotificationAddress())
                .send(Mono.fromCallable(() -> Unpooled.wrappedBuffer(messageBytes)))
                .response()
                .then();

        return responseSent.retry(3).thenReturn(messageAddressPair);
    }

    /**
     * Sets a single notification with one incident.
     * @param messageAddressPair Incident notification payload and email address pair.
     * @return Mono that returns the target email address.
     */
    protected Mono<MessageAddressPair> sendEmailNotification(MessageAddressPair messageAddressPair,
                                                             UserDomainIdentity userIdentity) {
        SmtpServerConfigurationSpec smtpServerConfigurationSpec = loadSmtpServerConfiguration(userIdentity);
        JavaMailSender javaMailSender = emailSenderProvider.configureJavaMailSender(smtpServerConfigurationSpec);

        Mono<Void> responseSent = Mono.fromCallable(() -> {
                    try {
                        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
                        simpleMailMessage.setFrom(EmailSender.EMAIL_SENDER_FROM);
                        simpleMailMessage.setTo(messageAddressPair.getNotificationAddress());
                        simpleMailMessage.setSubject("subject here");
                        simpleMailMessage.setText("test1");
                        javaMailSender.send(simpleMailMessage);
                        return simpleMailMessage;
                    } catch (Exception e) {
                        log.error(e.getMessage());
                        throw new RuntimeException(e);
                    }
                }
        ).then();
        return responseSent.retry(3).thenReturn(messageAddressPair);
    }

    /**
     * Returns a combined list of web hooks, combining the default notification channels with the notification settings on a connection.
     * @param incidentGrouping Incident grouping and notification settings from a connection.
     * @param userIdentity User identity that also specifies the data domain where the webhooks are defined.
     * @return Effective notification settings with webhooks that could be the default values.
     */
    protected IncidentNotificationSpec prepareWebhooks(ConnectionIncidentGroupingSpec incidentGrouping,
                                                       UserDomainIdentity userIdentity){
        ExecutionContext executionContext = executionContextFactory.create(userIdentity);
        UserHomeContext userHomeContext = executionContext.getUserHomeContext();
        UserHome userHome = userHomeContext.getUserHome();
        DefaultIncidentNotificationsWrapper defaultWebhooksWrapper = userHome.getDefaultIncidentNotifications();
        IncidentNotificationSpec defaultWebhooks = defaultWebhooksWrapper.getSpec();
        if (defaultWebhooks == null) {
            defaultWebhooks = new IncidentNotificationSpec();
        }

        if (incidentGrouping == null || incidentGrouping.getWebhooks() == null){
            return defaultWebhooks.deepClone();
        } else {
            return incidentGrouping.getWebhooks().combineWithDefaults(defaultWebhooks);
        }
    }

    private SmtpServerConfigurationSpec loadSmtpServerConfiguration(UserDomainIdentity userIdentity){
        ExecutionContext executionContext = executionContextFactory.create(userIdentity);
        UserHomeContext userHomeContext = executionContext.getUserHomeContext();
        UserHome userHome = userHomeContext.getUserHome();
        SmtpServerConfigurationSpec serverConfiguration;
        if (userHome.getSettings() == null || userHome.getSettings().getSpec() == null
                || userHome.getSettings().getSpec().getSmtpServerConfiguration() == null) {
            serverConfiguration = new SmtpServerConfigurationSpec();
        }
        else {
            serverConfiguration = userHome.getSettings().getSpec().getSmtpServerConfiguration();
        }

        return serverConfiguration;
    }

}
