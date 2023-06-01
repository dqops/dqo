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

import ai.dqo.cli.terminal.TablesawDatasetTableModel;
import ai.dqo.cli.terminal.TerminalWriter;
import ai.dqo.execution.checks.CheckExecutionErrorSummary;
import ai.dqo.execution.checks.CheckExecutionSummary;
import ai.dqo.metadata.search.CheckSearchFilters;
import ai.dqo.utils.serialization.JsonSerializer;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Check execution progress listener that is reporting just the summary information about the progress of running the data quality checks.
 */
public class SummaryCheckExecutionProgressListener extends SilentCheckExecutionProgressListener {
    /**
     * Creates a CLI progress listener using a terminal writer to print out the results.
     * @param terminalWriter Terminal writer.
     * @param jsonSerializer Json serializer.
     */
    @Autowired
    public SummaryCheckExecutionProgressListener(TerminalWriter terminalWriter, JsonSerializer jsonSerializer) {
        super(terminalWriter, jsonSerializer);
    }

    /**
     * Called after all data quality checks were executed.
     *
     * @param event Data quality check execution summary for one batch of checks.
     */
    @Override
    public void onCheckExecutionFinished(CheckExecutionFinishedEvent event) {
        if (this.isShowSummary()) {
            CheckExecutionSummary checkExecutionSummary = event.getCheckExecutionSummary();
            TablesawDatasetTableModel tablesawDatasetTableModel = new TablesawDatasetTableModel(checkExecutionSummary.getSummaryTable());
            this.terminalWriter.writeTable(tablesawDatasetTableModel, true);
            CheckExecutionErrorSummary checkExecutionErrorSummary = checkExecutionSummary.getCheckExecutionErrorSummary();
            if (checkExecutionErrorSummary != null) {
                this.terminalWriter.writeLine(checkExecutionErrorSummary.getSummaryMessage());
            }
        }
    }
}
