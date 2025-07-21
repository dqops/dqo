/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.execution.checks.progress;

import com.dqops.cli.terminal.TablesawDatasetTableModel;
import com.dqops.cli.terminal.TerminalWriter;
import com.dqops.execution.checks.CheckExecutionErrorSummary;
import com.dqops.execution.checks.CheckExecutionSummary;
import com.dqops.metadata.search.CheckSearchFilters;
import com.dqops.utils.serialization.JsonSerializer;
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
        synchronized (this.lock) {
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
}
