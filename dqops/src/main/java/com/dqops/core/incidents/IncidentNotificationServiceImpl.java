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

import com.dqops.core.configuration.SmtpServerConfigurationProperties;
import com.dqops.core.incidents.email.EmailSender;
import com.dqops.core.incidents.email.EmailSenderProvider;
import com.dqops.core.incidents.message.IncidentNotificationHtmlMessageFormatter;
import com.dqops.core.incidents.message.IncidentNotificationMessage;
import com.dqops.core.incidents.message.IncidentNotificationMessageMarkdownFormatter;
import com.dqops.core.incidents.message.MessageAddressPair;
import com.dqops.core.principal.UserDomainIdentity;
import com.dqops.data.incidents.factory.IncidentStatus;
import com.dqops.execution.ExecutionContext;
import com.dqops.execution.ExecutionContextFactory;
import com.dqops.metadata.incidents.ConnectionIncidentGroupingSpec;
import com.dqops.metadata.incidents.FilteredNotificationSpec;
import com.dqops.metadata.incidents.IncidentNotificationSpec;
import com.dqops.metadata.incidents.NotificationFilterSpec;
import com.dqops.metadata.incidents.defaultnotifications.DefaultIncidentNotificationsWrapper;
import com.dqops.metadata.settings.SmtpServerConfigurationSpec;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContext;
import com.dqops.metadata.userhome.UserHome;
import com.dqops.utils.http.SharedHttpClientProvider;
import com.dqops.utils.serialization.JsonSerializer;
import io.netty.buffer.Unpooled;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpHeaders;
import org.apache.parquet.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.netty.http.client.HttpClient;

import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

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
    private final SmtpServerConfigurationProperties smtpServerConfigurationProperties;

    /**
     * Creates an incident notification service.
     *
     * @param sharedHttpClientProvider                     Shared http client provider that manages the HTTP connection pooling.
     * @param jsonSerializer                               Json serializer.
     * @param executionContextFactory                      Execution context factory.
     * @param incidentNotificationMessageMarkdownFormatter Incident notification message formatter that creates a markdown formatted message.
     * @param incidentNotificationHtmlMessageFormatter     Incident notification message formatter that creates an HTML formatted message.
     * @param smtpServerConfigurationProperties            SMTP
     */
    @Autowired
    public IncidentNotificationServiceImpl(SharedHttpClientProvider sharedHttpClientProvider,
                                           JsonSerializer jsonSerializer,
                                           ExecutionContextFactory executionContextFactory,
                                           EmailSenderProvider emailSenderProvider,
                                           IncidentNotificationMessageMarkdownFormatter incidentNotificationMessageMarkdownFormatter,
                                           IncidentNotificationHtmlMessageFormatter incidentNotificationHtmlMessageFormatter,
                                           SmtpServerConfigurationProperties smtpServerConfigurationProperties) {
        this.sharedHttpClientProvider = sharedHttpClientProvider;
        this.jsonSerializer = jsonSerializer;
        this.executionContextFactory = executionContextFactory;
        this.emailSenderProvider = emailSenderProvider;
        this.incidentNotificationMessageMarkdownFormatter = incidentNotificationMessageMarkdownFormatter;
        this.incidentNotificationHtmlMessageFormatter = incidentNotificationHtmlMessageFormatter;
        this.smtpServerConfigurationProperties = smtpServerConfigurationProperties;
    }

    /**
     * Sends new incident notifications to the notification targets addresses specified in the incident grouping configuration.
     *
     * @param newMessages      List of new data quality incidents that will be sent as a payload.
     * @param incidentGrouping Incident grouping that identifies the notification target (where to send the notifications).
     * @param userIdentity     User identity that specifies the data domain where the addresses are defined.
     */
    @Override
    public void sendNotifications(List<IncidentNotificationMessage> newMessages,
                                  ConnectionIncidentGroupingSpec incidentGrouping,
                                  UserDomainIdentity userIdentity) {
        IncidentNotificationSpec incidentNotificationSpec = prepareIncidentNotifications(incidentGrouping, userIdentity);
        Mono<Void> finishedSendMono = sendAllNotifications(newMessages, incidentNotificationSpec, userIdentity);
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
                .map(message -> {
                    List<Map.Entry<String, FilteredNotificationSpec>> filteredNotifications = notificationSpec.getFilteredNotificationMap()
                            .entrySet().stream()
                            .filter(stringFilteredNotificationSpecEntry -> {
                                        NotificationFilterSpec filter = stringFilteredNotificationSpecEntry.getValue().getNotificationFilter();

                                        return message.getConnection().equals(filter.getConnection()) &&
                                               message.getSchema().equals(filter.getSchema()) &&
                                               message.getTable().equals(filter.getTable()) &&
                                               message.getTablePriority().equals(filter.getTablePriority()) &&
                                               message.getDataGroupName().equals(filter.getDataGroupName()) &&
                                               message.getQualityDimension().equals(filter.getQualityDimension()) &&
                                               message.getCheckCategory().equals(filter.getCheckCategory()) &&
                                               message.getCheckType().equals(filter.getCheckType()) &&
                                               message.getCheckName().equals(filter.getCheckName()) &&
                                               filter.getHighestSeverity().equals(message.getHighestSeverity());
                                    }
                            ).sorted(Comparator.comparing(value -> value.getValue().getPriority())).collect(Collectors.toList()); // todo: reversed

                    List<FilteredNotificationSpec> filteredNotificationsList = filteredNotifications.stream().map(Map.Entry::getValue).collect(Collectors.toList());
                    List<FilteredNotificationSpec> filteredNotificationsToSend = new ArrayList<>();
                    for (int i = 0; i < filteredNotificationsList.size(); i++) {
                        FilteredNotificationSpec filteredNotification = filteredNotificationsList.get(i);
                        filteredNotificationsToSend.add(filteredNotification);
                        if (!filteredNotification.getProcessAdditionalFilters()) {
                            break;
                        }
                    }

                    if (!filteredNotificationsToSend.isEmpty()) {

                        List<String> compoundAddressesList = filteredNotificationsToSend.stream().map(filteredNotificationSpec -> {
                            IncidentNotificationSpec notificationTarget = filteredNotificationSpec.getNotificationTarget();
                            return notificationTarget.getNotificationAddressForStatus(message.getStatus()) != null
                                    ? notificationTarget.getNotificationAddressForStatus(message.getStatus())
                                    : "";
                        }).collect(Collectors.toList());

                        String addressesString = StringUtils.join(compoundAddressesList, ',');

                        List<String> addresses = addressesString.contains(",")
                                ? Arrays.stream(addressesString.split(",")).map(String::trim).collect(Collectors.toList())
                                : List.of(addressesString);

                        return addresses.stream().map(address -> new MessageAddressPair(message, address)).collect(Collectors.toList());
                    }

                    // todo: send it when list of filteredNotification is full of entries with processAdditionalFilters

                    String addressesString = notificationSpec.getNotificationAddressForStatus(message.getStatus()) != null
                            ? notificationSpec.getNotificationAddressForStatus(message.getStatus())
                            : "";

                    List<String> addresses = addressesString.contains(",")
                            ? Arrays.stream(addressesString.split(",")).map(String::trim).collect(Collectors.toList())
                            : List.of(addressesString);

                    return addresses.stream().map(address -> new MessageAddressPair(message, address)).collect(Collectors.toList());
                })
                .flatMap(Flux::fromIterable)
                .filter(messageAddressPair -> !Strings.isNullOrEmpty(notificationSpec.getNotificationAddressForStatus(messageAddressPair.getIncidentNotificationMessage().getStatus())))
                .filter(messageAddressPair -> !Strings.isNullOrEmpty(messageAddressPair.getNotificationAddress()))
                .flatMap(messageAddressPair -> {

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
                        MimeMessage simpleMailMessage = javaMailSender.createMimeMessage();
                        String messageTemplate = (messageAddressPair.getIncidentNotificationMessage().getStatus().equals(IncidentStatus.open))
                                ? "New incident detected in %s table."
                                : "The incident in %s table has been detected.";
                        String subjectMessage = String.format(messageTemplate,
                                messageAddressPair.getIncidentNotificationMessage().getConnection()
                                        + "." + messageAddressPair.getIncidentNotificationMessage().getTable());
                        simpleMailMessage.setSubject(subjectMessage);
                        MimeMessageHelper helper;
                        helper = new MimeMessageHelper(simpleMailMessage, true);
                        helper.setFrom(String.valueOf(new InternetAddress(EmailSender.EMAIL_SENDER_FROM_EMAIL, EmailSender.EMAIL_SENDER_FROM_NAME)));
                        helper.setTo(messageAddressPair.getNotificationAddress());
                        helper.setText(messageAddressPair.getIncidentNotificationMessage().getText(), true);
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
     * @return Effective notification settings with addresses that could be the default values.
     */
    protected IncidentNotificationSpec prepareIncidentNotifications(ConnectionIncidentGroupingSpec incidentGrouping,
                                                                    UserDomainIdentity userIdentity){
        ExecutionContext executionContext = executionContextFactory.create(userIdentity);
        UserHomeContext userHomeContext = executionContext.getUserHomeContext();
        UserHome userHome = userHomeContext.getUserHome();
        DefaultIncidentNotificationsWrapper defaultIncidentNotificationsWrapper = userHome.getDefaultIncidentNotifications();
        IncidentNotificationSpec defaultIncidentNotifications = defaultIncidentNotificationsWrapper.getSpec();
        if (defaultIncidentNotifications == null) {
            defaultIncidentNotifications = new IncidentNotificationSpec();
        }

        if (incidentGrouping == null || incidentGrouping.getIncidentNotification() == null){
            return defaultIncidentNotifications.deepClone();
        } else {
            return incidentGrouping.getIncidentNotification().combineWithDefaults(defaultIncidentNotifications);
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

            String host = smtpServerConfigurationProperties.getHost();
            if(host != null && !host.isEmpty()){
                smtpServerConfigurationProperties.setHost(host);
            }
            String port = smtpServerConfigurationProperties.getPort();
            if(port != null && !port.isEmpty()){
                smtpServerConfigurationProperties.setPort(port);
            }
            Boolean useSsl = smtpServerConfigurationProperties.getUseSsl();
            if(useSsl != null){
                smtpServerConfigurationProperties.setUseSsl(useSsl);
            }
            String username = smtpServerConfigurationProperties.getUsername();
            if(username != null && !username.isEmpty()){
                smtpServerConfigurationProperties.setUsername(username);
            }
            String password = smtpServerConfigurationProperties.getPassword();
            if(password != null && !password.isEmpty()){
                smtpServerConfigurationProperties.setPassword(password);
            }
        }
        else {
            serverConfiguration = userHome.getSettings().getSpec().getSmtpServerConfiguration();
        }

        return serverConfiguration;
    }

}
