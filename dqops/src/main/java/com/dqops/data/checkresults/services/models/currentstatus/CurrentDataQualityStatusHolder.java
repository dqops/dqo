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

package com.dqops.data.checkresults.services.models.currentstatus;

import java.time.Instant;
import java.util.Map;

/**
 * Interface implemented by the table and column level current data quality status, provides access
 * to the service getter of the dictionary that returns data quality check statuses keyed by the check name, but on a table or column levels.
 */
public interface CurrentDataQualityStatusHolder {
    Integer getHighestSeverityLevel();
    void setHighestSeverityLevel(Integer highestSeverityLevel);
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
