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

package ai.dqo.core.jobqueue;

import ai.dqo.core.jobqueue.monitoring.DqoJobQueueMonitoringServiceObjectMother;
import ai.dqo.utils.BeanFactoryObjectMother;
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
