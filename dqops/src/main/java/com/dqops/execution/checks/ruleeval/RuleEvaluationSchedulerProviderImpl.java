/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
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
