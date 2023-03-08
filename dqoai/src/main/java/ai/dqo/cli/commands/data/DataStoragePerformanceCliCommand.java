/*
 * Copyright © 2023 DQO.ai (support@dqo.ai)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ai.dqo.cli.commands.data;

import ai.dqo.cli.commands.BaseCommand;
import ai.dqo.cli.commands.ICommand;
import ai.dqo.cli.terminal.TerminalWriter;
import ai.dqo.data.ruleresults.factory.RuleResultsColumnNames;
import ai.dqo.data.ruleresults.factory.RuleResultsTableFactory;
import ai.dqo.data.ruleresults.models.RuleResultsFragmentFilter;
import ai.dqo.data.ruleresults.services.RuleResultsDeleteService;
import ai.dqo.data.ruleresults.snapshot.RuleResultsSnapshot;
import ai.dqo.data.ruleresults.snapshot.RuleResultsSnapshotFactory;
import ai.dqo.data.storage.LoadedMonthlyPartition;
import ai.dqo.metadata.search.TableSearchFilters;
import ai.dqo.metadata.sources.PhysicalTableName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import picocli.CommandLine;
import tech.tablesaw.api.*;

import java.time.LocalDate;

/**
 * Development level command for performing parquet write performance tests.
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@CommandLine.Command(name = "perf", description = "Run performance tests of writing parquet files. To be used only for development purposes.")
public class DataStoragePerformanceCliCommand extends BaseCommand implements ICommand {
    private TerminalWriter terminalWriter;
    private RuleResultsTableFactory ruleResultsTableFactory;
    private RuleResultsSnapshotFactory ruleResultsSnapshotFactory;
    private RuleResultsDeleteService ruleResultsDeleteService;

    public DataStoragePerformanceCliCommand() {
    }

    @Autowired
    public DataStoragePerformanceCliCommand(TerminalWriter terminalWriter,
                                            RuleResultsTableFactory ruleResultsTableFactory,
                                            RuleResultsSnapshotFactory ruleResultsSnapshotFactory,
                                            RuleResultsDeleteService ruleResultsDeleteService) {
        this.terminalWriter = terminalWriter;
        this.ruleResultsTableFactory = ruleResultsTableFactory;
        this.ruleResultsSnapshotFactory = ruleResultsSnapshotFactory;
        this.ruleResultsDeleteService = ruleResultsDeleteService;
    }

    @CommandLine.Option(names = {"-r", "--repeats"}, description = "The number of repeats (write file, delete file... and another retry: write, delete)",
                        defaultValue = "1")
    private int repeats = 1;

    @CommandLine.Option(names = {"-c", "--row-count"}, description = "The number of rows",
            defaultValue = "1")
    private int rowCount = 1;

    /**
     * Performs one iteration of the performance test: create parquet file, delete file.
     */
    private void runOneIteration(LocalDate monthDate, Table newRows) {
        RuleResultsSnapshot snapshot = this.ruleResultsSnapshotFactory.createSnapshot("perftestdelete", new PhysicalTableName("perfschema", "perftest"));
        LoadedMonthlyPartition monthPartition = snapshot.getMonthPartition(monthDate, false);
        Table targetTable = snapshot.getTableDataChanges().getNewOrChangedRows();
        targetTable.append(newRows);

        snapshot.save();

        RuleResultsFragmentFilter deleteFilter = new RuleResultsFragmentFilter();
        TableSearchFilters tableSearchFilters = new TableSearchFilters();
        tableSearchFilters.setConnectionName("perftestdelete");
        tableSearchFilters.setSchemaTableName("perfschema.perftest");
        deleteFilter.setTableSearchFilters(tableSearchFilters);
        deleteFilter.setDateStart(monthDate);
        deleteFilter.setDateEnd(monthDate.plusMonths(1));
        this.ruleResultsDeleteService.deleteSelectedRuleResultsFragment(deleteFilter);
    }

    private void generateRows(LocalDate monthDate, Table targetTable) {
        StringColumn idColumn = targetTable.stringColumn(RuleResultsColumnNames.ID_COLUMN_NAME);
        DoubleColumn actualValueColumn = targetTable.doubleColumn(RuleResultsColumnNames.ACTUAL_VALUE_COLUMN_NAME);
        DateTimeColumn timePeriodColumn = targetTable.dateTimeColumn(RuleResultsColumnNames.TIME_PERIOD_COLUMN_NAME);

        for (int i = 0; i < this.rowCount; i++) {
            Row row = targetTable.appendRow();
            int rowIndex = row.getRowNumber();
            idColumn.set(rowIndex, "id" + i);
            actualValueColumn.set(rowIndex, 100.0);
            timePeriodColumn.set(rowIndex, monthDate.atStartOfDay().plusDays(i % 20)); // stay in the same month
        }
    }

    /**
     * Runs performance tests of writing parquet files.
     *
     * @return 0
     * @throws Exception if unable to perform the action
     */
    @Override
    public Integer call() throws Exception {
        LocalDate monthDate = LocalDate.of(2023, 03, 01);
        Table targetTable = this.ruleResultsTableFactory.createEmptyRuleResultsTable("rules");
        generateRows(monthDate, targetTable);

        long startMillis = System.currentTimeMillis();

        for (int i = 0; i < this.repeats; i++) {
            runOneIteration(monthDate, targetTable);
        }

        long endMillis = System.currentTimeMillis();
        long elapsedMillis = endMillis - startMillis;

        this.terminalWriter.writeLine("Performed " + this.repeats + " repeats, writing " + this.rowCount + " rows per repeat, time: " +
                elapsedMillis + " ms");

        return 0;
    }
}
