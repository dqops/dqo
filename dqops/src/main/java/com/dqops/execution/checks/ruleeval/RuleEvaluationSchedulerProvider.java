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
import reactor.core.scheduler.Scheduler;

/**
 * A provider that returns a custom reactor job scheduler that is limited by the number of CPUs, to avoid running too many jobs that run Python jobs.
 */
public interface RuleEvaluationSchedulerProvider extends DisposableBean {
    /**
     * Returns the scheduler instance.
     *
     * @return Scheduler instance.
     */
    Scheduler getScheduler();
}
