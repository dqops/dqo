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

import com.dqops.data.checkresults.models.currentstatus.TableCurrentDataQualityStatusModel;
import com.dqops.data.checkresults.statuscache.DomainConnectionTableKey;

/**
 * Data catalog synchronization service that sends data quality health statuses to a rest api that will upload it into a data catalog.
 */
public interface DataCatalogHealthSendService {
    /**
     * Sends a serialized data quality status JSON message to a webhook that will load it to a data catalog.
     *
     * @param tableKey               Table key.
     * @param dataQualityStatusModel Data quality status model.
     */
    void sendTableQualityStatusToCatalog(DomainConnectionTableKey tableKey, TableCurrentDataQualityStatusModel dataQualityStatusModel);

    /**
     * Checks if the instance is configured to support synchronization with a data catalog.
     * @return True when synchronization is possible.
     */
    boolean isSynchronizationSupported();

    /**
     * Notifies the send service to update its list of target urls from the instance's local settings.
     */
    void invalidateUrlList();
}
