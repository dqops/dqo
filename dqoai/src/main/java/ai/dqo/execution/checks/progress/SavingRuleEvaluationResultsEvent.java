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
package ai.dqo.execution.checks.progress;

import ai.dqo.data.ruleresults.snapshot.RuleResultsSnapshot;
import ai.dqo.metadata.sources.TableSpec;

/**
 * Progress event raised before rule evaluation results are saved.
 */
public class SavingRuleEvaluationResultsEvent extends CheckExecutionProgressEvent {
    private final TableSpec tableSpec;
    private final RuleResultsSnapshot ruleResultsSnapshot;

    /**
     * Creates a progress event.
     *
     * @param tableSpec                     Target table.
     * @param ruleResultsSnapshot           Rule evaluation results (snapshot).
     */
    public SavingRuleEvaluationResultsEvent(TableSpec tableSpec, RuleResultsSnapshot ruleResultsSnapshot) {
        this.tableSpec = tableSpec;
        this.ruleResultsSnapshot = ruleResultsSnapshot;
    }

    /**
     * Returns a target table spec.
     *
     * @return Target table.
     */
    public TableSpec getTableSpec() {
        return tableSpec;
    }

    /**
     * Rule evaluation results that will be saved.
     *
     * @return Rule evaluation results.
     */
    public RuleResultsSnapshot getRuleResultsSnapshot() {
        return ruleResultsSnapshot;
    }
}
