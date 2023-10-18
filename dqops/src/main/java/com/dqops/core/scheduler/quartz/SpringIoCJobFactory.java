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
package com.dqops.core.scheduler.quartz;

import org.quartz.Job;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.spi.JobFactory;
import org.quartz.spi.TriggerFiredBundle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Custom job factory for quartz that will instantiate job class instances using IoC (Spring).
 */
@Component
public class SpringIoCJobFactory implements JobFactory {
    private static final Logger LOG = LoggerFactory.getLogger(SpringIoCJobFactory.class);

    private BeanFactory beanFactory;

    /**
     * Constructor for the job factory.
     * @param beanFactory The default instance of the Spring bean factory.
     */
    @Autowired
    public SpringIoCJobFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    /**
     * Creates a job instance using IoC container.
     * @param triggerFiredBundle Trigger and job details.
     * @param scheduler Scheduler.
     * @return Job instance created from the job factory.
     * @throws SchedulerException
     */
    @Override
    public Job newJob(TriggerFiredBundle triggerFiredBundle, Scheduler scheduler) throws SchedulerException {
        Class<? extends Job> jobClass = triggerFiredBundle.getJobDetail().getJobClass();
        try {
            Job jobInstance = this.beanFactory.getBean(jobClass);
            return jobInstance;
        }
        catch (Exception ex) {
            LOG.error("Cannot create an instance of a job " + jobClass.getCanonicalName(), ex);
            throw new SchedulerException("Cannot create an instance of a job " + jobClass.getCanonicalName(), ex);
        }
    }
}
