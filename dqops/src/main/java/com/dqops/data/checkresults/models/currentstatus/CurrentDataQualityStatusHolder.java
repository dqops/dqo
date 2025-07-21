/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.data.checkresults.models.currentstatus;

import com.dqops.rules.RuleSeverityLevel;

import java.time.Instant;
import java.util.Map;

/**
 * Interface implemented by the table and column level current data quality status, provides access
 * to the service getter of the dictionary that returns data quality check statuses keyed by the check name, but on a table or column levels.
 */
public interface CurrentDataQualityStatusHolder {
    RuleSeverityLevel getCurrentSeverity();
    void setCurrentSeverity(RuleSeverityLevel currentSeverity);

    RuleSeverityLevel getHighestHistoricalSeverity();
    void setHighestHistoricalSeverity(RuleSeverityLevel highestHistoricalSeverity);

    Instant getLastCheckExecutedAt();
    void setLastCheckExecutedAt(Instant lastCheckExecutedAt);

    int getExecutedChecks();
    void setExecutedChecks(int executedChecks);

    int getValidResults();
    void setValidResults(int validResults);

    int getWarnings();
    void setWarnings(int warnings);

    int getErrors();
    void setErrors(int errors);

    int getFatals();
    void setFatals(int fatals);

    int getExecutionErrors();
    void setExecutionErrors(int executionErrors);

    Map<String, CheckCurrentDataQualityStatusModel> getChecks();
    void setChecks(Map<String, CheckCurrentDataQualityStatusModel> checks);
}
