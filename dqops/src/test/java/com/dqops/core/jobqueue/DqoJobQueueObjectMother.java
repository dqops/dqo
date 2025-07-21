/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.core.jobqueue;

import com.dqops.core.jobqueue.monitoring.DqoJobQueueMonitoringServiceObjectMother;
import com.dqops.utils.BeanFactoryObjectMother;
import org.springframework.beans.factory.BeanFactory;

/**
 * Object mother for retrieving and starting a job queue.
 */
public class DqoJobQueueObjectMother {
    /**
     * Returns the default (singleteon) instance of the job queue.
     * @return Job queue instance.
     */
    public static DqoJobQueue getDefaultJobQueue() {
        BeanFactory beanFactory = BeanFactoryObjectMother.getBeanFactory();
        return beanFactory.getBean(DqoJobQueue.class);
    }

    /**
     * Returns the default (singleton) instance of the parent job queue that supports only parent jobs.
     * @return Default instance of the job queue for parent jobs.
     */
    public static ParentDqoJobQueue getDefaultParentJobQueue() {
        BeanFactory beanFactory = BeanFactoryObjectMother.getBeanFactory();
        return beanFactory.getBean(ParentDqoJobQueue.class);
    }

    /**
     * Ensures that the job queue is started.
     */
    public static void ensureJobQueueIsStarted() {
        DqoJobQueueMonitoringServiceObjectMother.ensureJobQueueMonitoringServiceStarted();

        DqoJobQueue defaultJobQueue = getDefaultJobQueue();
        defaultJobQueue.start();

        ParentDqoJobQueue defaultParentJobQueue = getDefaultParentJobQueue();
        defaultParentJobQueue.start();
    }
}
