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

package ai.dqo.execution.checks;

import ai.dqo.execution.checks.ruleeval.RuleEvaluationResult;

/**
 * Object that stores statistics of the count of executed checks, failed checks, execution errors, etc.
 */
public class TableChecksExecutionStatistics {
    private int executedChecksCount = 0;
    private int sensorReadoutsCount = 0;
    private int passedRulesCount = 0;
    private int warningIssuesCount = 0;
    private int errorIssuesCount = 0;
    private int fatalIssuesCount = 0;
    private int sensorExecutionErrorsCount = 0;
    private int ruleExecutionErrorsCount = 0;

    /**
     * Returns the count of executed checks (even if they failed to execute).
     * @return Count of checks that were executed (even if failed).
     */
    public int getExecutedChecksCount() {
        return executedChecksCount;
    }

    /**
     * Increments the executed checks count by a given increment <code>increment</code>.
     * @param increment The increment value to increase the current count.
     */
    public void incrementExecutedChecksCount(int increment) {
        this.executedChecksCount += increment;
    }

    /**
     * Returns the count of sensor readouts that were captured by sensors.
     * @return Total count of sensor readouts that were captured.
     */
    public int getSensorReadoutsCount() {
        return sensorReadoutsCount;
    }

    /**
     * Increments the sensor readouts count by a given increment <code>increment</code>.
     * @param increment The increment value to increase the current count.
     */
    public void incrementSensorReadoutsCount(int increment) {
        this.sensorReadoutsCount += increment;
    }

    /**
     * Returns the count of sensor readouts that passed the validation by data quality rules (severity level is 0).
     * @return Count of fully passed checks.
     */
    public int getPassedRulesCount() {
        return passedRulesCount;
    }

    /**
     * Increments the passed rules count by a given increment <code>increment</code>.
     * @param increment The increment value to increase the current count.
     */
    public void incrementPassedRulesCount(int increment) {
        this.passedRulesCount += increment;
    }

    /**
     * Returns the count of sensor readouts that failed the quality rule with a warning (1) severity level.
     * @return Count of checks that failed as a warning severity level.
     */
    public int getWarningIssuesCount() {
        return warningIssuesCount;
    }

    /**
     * Increments the warning issues count by a given increment <code>increment</code>.
     * @param increment The increment value to increase the current count.
     */
    public void incrementWarningIssuesCount(int increment) {
        this.warningIssuesCount += increment;
    }

    /**
     * Returns the count of sensor readouts that failed the quality rule with a error (2) severity level.
     * @return Count of checks that failed as an error severity level.
     */
    public int getErrorIssuesCount() {
        return errorIssuesCount;
    }

    /**
     * Increments the error issues count by a given increment <code>increment</code>.
     * @param increment The increment value to increase the current count.
     */
    public void incrementErrorIssuesCount(int increment) {
        this.errorIssuesCount += increment;
    }

    /**
     * Returns the count of sensor readouts that failed the quality rule with a fatal (3) severity level.
     * @return Count of checks that failed as a fatal severity level.
     */
    public int getFatalIssuesCount() {
        return fatalIssuesCount;
    }

    /**
     * Increments the fatal issues count by a given increment <code>increment</code>.
     * @param increment The increment value to increase the current count.
     */
    public void incrementFatalIssuesCount(int increment) {
        this.fatalIssuesCount += increment;
    }

    /**
     * Count of sensors that failed to execute, either because the jinja2 template was invalid or the query failed to execute on the data source.
     * @return Count of check execution errors that failed when the sensors were executed.
     */
    public int getSensorExecutionErrorsCount() {
        return sensorExecutionErrorsCount;
    }

    /**
     * Increments the sensor execution errors count by a given increment <code>increment</code>.
     * @param increment The increment value to increase the current count.
     */
    public void incrementSensorExecutionErrorsCount(int increment) {
        this.sensorExecutionErrorsCount += increment;
    }

    /**
     * Count of rule execution errors. This is how many sensor readouts failed to be evaluated by a python rule, because it failed with an execution exception.
     * A rule that finishes to execute and returns a severity warning is not an execution error.
     * @return Count of rules that failed to execute due to some coding issues.
     */
    public int getRuleExecutionErrorsCount() {
        return ruleExecutionErrorsCount;
    }

    /**
     * Increments the rule execution errors count by a given increment <code>increment</code>.
     * @param increment The increment value to increase the current count.
     */
    public void incrementRuleExecutionErrorsCount(int increment) {
        this.ruleExecutionErrorsCount += increment;
    }

    /**
     * Increments rule result counts from a rule evaluation result object.
     * @param ruleEvaluationResult Rule evaluation result with the rule results that should be counted by the rule severity level.
     */
    public void addRuleEvaluationResults(RuleEvaluationResult ruleEvaluationResult) {
        passedRulesCount += ruleEvaluationResult.getSeverityColumn().isLessThanOrEqualTo(1).size();  // passed checks are severity 0 (passed) and 1 (warnings)
        warningIssuesCount += ruleEvaluationResult.getSeverityColumn().isEqualTo(1).size();
        errorIssuesCount += ruleEvaluationResult.getSeverityColumn().isEqualTo(2).size();
        fatalIssuesCount += ruleEvaluationResult.getSeverityColumn().isEqualTo(3).size();
    }

    /**
     * Returns true if there are any failed rules, that means any sensor readout did not pass a warnings, error or a fatal severity rule.
     * @return True when there are any check results that failed a rule evaluation and were considered as data quality issues.
     */
    public boolean hasAnyFailedRules() {
        return warningIssuesCount > 0 || errorIssuesCount > 0 || fatalIssuesCount > 0;
    }

    /**
     * Creates a string representation of the object for debugging purposes.
     * @return String representation of the object.
     */
    @Override
    public String toString() {
        return "TableChecksExecutionStatistics{" +
                "executedChecksCount=" + executedChecksCount +
                ", sensorReadoutsCount=" + sensorReadoutsCount +
                ", passedRulesCount=" + passedRulesCount +
                ", warningIssuesCount=" + warningIssuesCount +
                ", errorIssuesCount=" + errorIssuesCount +
                ", fatalIssuesCount=" + fatalIssuesCount +
                ", sensorExecutionErrorsCount=" + sensorExecutionErrorsCount +
                ", ruleExecutionErrorsCount=" + ruleExecutionErrorsCount +
                '}';
    }
}
