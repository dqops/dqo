/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.core.jobqueue.monitoring;

import com.dqops.utils.BeanFactoryObjectMother;
import org.springframework.beans.factory.BeanFactory;

/**
 * Object mother for {@link com.dqops.core.jobqueue.monitoring.DqoJobQueueMonitoringService}
 */
public class DqoJobQueueMonitoringServiceObjectMother {
    /**
     * Returns the default job queue monitoring service.
     * @return Default job queue monitoring service.
     */
    public static DqoJobQueueMonitoringService getDefault() {
        BeanFactory beanFactory = BeanFactoryObjectMother.getBeanFactory();
        return beanFactory.getBean(DqoJobQueueMonitoringService.class);
    }

    /**
     * Ensures that the dqo job queue monitoring service is started.
     */
    public static void ensureJobQueueMonitoringServiceStarted() {
        DqoJobQueueMonitoringService jobQueueMonitoringService = getDefault();
        jobQueueMonitoringService.start();
    }
}
