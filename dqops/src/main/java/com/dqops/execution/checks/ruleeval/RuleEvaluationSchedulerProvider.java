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
