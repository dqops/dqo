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

package com.dqops.execution.checks.ruleeval;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

/**
 * A provider that returns a custom reactor job scheduler that is limited by the number of CPUs, to avoid running too many jobs that run Python jobs.
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class RuleEvaluationSchedulerProviderImpl implements RuleEvaluationSchedulerProvider {
    private final Scheduler scheduler;

    /**
     * Default constructor that creates a scheduler.
     */
    @Autowired
    public RuleEvaluationSchedulerProviderImpl() {
        int availableProcessors = Runtime.getRuntime().availableProcessors();
        this.scheduler = Schedulers.newBoundedElastic(availableProcessors, Integer.MAX_VALUE, "dqops-rule-runners", 30, true);
    }

    /**
     * Called by Spring infrastructure when the object is disposed and the applications shuts down.
     * @throws Exception
     */
    @Override
    public void destroy() throws Exception {
        this.scheduler.dispose();
    }

    /**
     * Returns the scheduler instance.
     * @return Scheduler instance.
     */
    @Override
    public Scheduler getScheduler() {
        return scheduler;
    }
}
