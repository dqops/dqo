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

package com.dqops.core.catalogsync;

import com.dqops.core.configuration.DqoIntegrationsConfigurationProperties;
import com.dqops.core.principal.DqoUserPrincipal;
import com.dqops.core.principal.DqoUserPrincipalProvider;
import com.dqops.data.checkresults.models.currentstatus.TableCurrentDataQualityStatusModel;
import com.dqops.data.checkresults.statuscache.DomainConnectionTableKey;
import com.dqops.metadata.settings.SettingsWrapper;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContext;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContextFactory;
import com.dqops.utils.http.OutboundHttpCallQueue;
import com.dqops.utils.http.OutboundHttpMessage;
import com.dqops.utils.serialization.JsonSerializer;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.apache.parquet.Strings;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Data catalog synchronization service that sends data quality health statuses to a rest api that will upload it into a data catalog.
 */
@Component
public class DataCatalogHealthSendServiceImpl implements DataCatalogHealthSendService {
    private final UserHomeContextFactory userHomeContextFactory;
    private final DqoUserPrincipalProvider userPrincipalProvider;
    private final DqoIntegrationsConfigurationProperties dqoIntegrationsConfigurationProperties;
    private final OutboundHttpCallQueue outboundHttpCallQueue;
    private final JsonSerializer jsonSerializer;
    private final Object lock = new Object();
    private final Map<DomainConnectionTableKey, TableCurrentDataQualityStatusModel> currentSendBatch = new LinkedHashMap<>();
    private final Map<DomainConnectionTableKey, TableCurrentDataQualityStatusModel> nextSendBatch = new LinkedHashMap<>();
    private List<String> notificationUrls;

    /**
     * Dependency injection constructor.
     * @param userHomeContextFactory User home context to load the list of notification urls.
     * @param userPrincipalProvider User principal provider to get the principal of the root data domain.
     * @param dqoIntegrationsConfigurationProperties Configuration parameters for this service.
     * @param outboundHttpCallQueue HTTP call queue.
     * @param jsonSerializer Json serializer.
     */
    @Autowired
    public DataCatalogHealthSendServiceImpl(
            UserHomeContextFactory userHomeContextFactory,
            DqoUserPrincipalProvider userPrincipalProvider,
            DqoIntegrationsConfigurationProperties dqoIntegrationsConfigurationProperties,
            OutboundHttpCallQueue outboundHttpCallQueue,
            JsonSerializer jsonSerializer) {
        this.userHomeContextFactory = userHomeContextFactory;
        this.userPrincipalProvider = userPrincipalProvider;
        this.dqoIntegrationsConfigurationProperties = dqoIntegrationsConfigurationProperties;
        this.outboundHttpCallQueue = outboundHttpCallQueue;
        this.jsonSerializer = jsonSerializer;
    }

    /**
     * Sends a serialized data quality status JSON message to a webhook that will load it to a data catalog.
     * @param tableKey Table key.
     * @param dataQualityStatusModel Data quality status model.
     */
    @Override
    public void sendTableQualityStatusToCatalog(DomainConnectionTableKey tableKey, TableCurrentDataQualityStatusModel dataQualityStatusModel) {
        List<String> notificationUrlCopy;
        synchronized (this.lock) {
            notificationUrlCopy = this.notificationUrls;
        }

        if (notificationUrlCopy == null) {
            invalidateUrlList();
        }

        synchronized (this.lock) {
            if (this.notificationUrls.isEmpty()) {
                return;
            }
        }

        synchronized (this.lock) {
            this.currentSendBatch.remove(tableKey); // table status is under changes, do not send it in the next batch yet
            this.nextSendBatch.put(tableKey, dataQualityStatusModel);
        }
    }

    /**
     * Called by a scheduler - moves the operations up from the next batch to the current batch, and sends values in teh current batch.
     */
    @Scheduled(fixedRate = 10, timeUnit = TimeUnit.SECONDS)
    public void moveSendQueueUpOnSchedule() {
        List<TableCurrentDataQualityStatusModel> currentTableStatusesToSent = null;
        List<String> notificationList = null;
        synchronized (this.lock) {
            currentTableStatusesToSent = this.currentSendBatch.values().stream().collect(Collectors.toList());
            this.currentSendBatch.clear();

            for (Map.Entry<DomainConnectionTableKey, TableCurrentDataQualityStatusModel> nextBatchKeyValue : this.nextSendBatch.entrySet()) {
                this.currentSendBatch.put(nextBatchKeyValue.getKey(), nextBatchKeyValue.getValue());
            }

            this.nextSendBatch.clear();
            notificationList = this.notificationUrls;
        }

        for (TableCurrentDataQualityStatusModel dataQualityStatusModel : currentTableStatusesToSent) {
            String serializedModel = this.jsonSerializer.serialize(dataQualityStatusModel);

            for (String healthApiUrl : notificationList) {
                this.outboundHttpCallQueue.sendMessage(new OutboundHttpMessage(healthApiUrl, serializedModel));
            }
        }
    }

    /**
     * Checks if the instance is configured to support synchronization with a data catalog.
     *
     * @return True when synchronization is possible.
     */
    @Override
    public boolean isSynchronizationSupported() {
        List<String> notificationUrlCopy;
        synchronized (this.lock) {
            notificationUrlCopy = this.notificationUrls;
        }

        if (notificationUrlCopy == null) {
            invalidateUrlList();
        }

        synchronized (this.lock) {
            return !this.notificationUrls.isEmpty();
        }
    }

    /**
     * Notifies the send service to update its list of target urls from the instance's local settings.
     */
    @Override
    public void invalidateUrlList() {
        List<String> newUrlList = new ArrayList<>();

        if (!Strings.isNullOrEmpty(dqoIntegrationsConfigurationProperties.getTableHealthWebhookUrls())) {
            String[] urlsFromParameters = StringUtils.split(dqoIntegrationsConfigurationProperties.getTableHealthWebhookUrls(), ',');
            newUrlList.addAll(Lists.newArrayList(urlsFromParameters));
        }

        DqoUserPrincipal localInstanceAdminPrincipal = this.userPrincipalProvider.createLocalInstanceAdminPrincipal();
        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(localInstanceAdminPrincipal.getDataDomainIdentity(), true);
        SettingsWrapper settingsWrapper = userHomeContext.getUserHome().getSettings();
        if (settingsWrapper != null && settingsWrapper.getSpec() != null && settingsWrapper.getSpec().getDataCatalogUrls() != null) {
            newUrlList.addAll(settingsWrapper.getSpec().getDataCatalogUrls());
        }

        synchronized (this.lock) {
            this.notificationUrls = newUrlList;
        }
    }
}
