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
