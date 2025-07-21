/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
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
