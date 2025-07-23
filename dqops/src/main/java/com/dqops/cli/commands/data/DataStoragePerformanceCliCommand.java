/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.cli.commands.data;

import com.dqops.cli.commands.BaseCommand;
import com.dqops.cli.commands.ICommand;
import com.dqops.cli.terminal.TerminalWriter;
import com.dqops.core.principal.UserDomainIdentity;
import com.dqops.data.checkresults.factory.CheckResultsColumnNames;
import com.dqops.data.checkresults.factory.CheckResultsTableFactory;
import com.dqops.data.checkresults.models.CheckResultsFragmentFilter;
import com.dqops.data.checkresults.services.CheckResultsDeleteService;
import com.dqops.data.checkresults.snapshot.CheckResultsSnapshot;
import com.dqops.data.checkresults.snapshot.CheckResultsSnapshotFactory;
import com.dqops.data.storage.LoadedMonthlyPartition;
import com.dqops.metadata.search.TableSearchFilters;
import com.dqops.metadata.sources.PhysicalTableName;
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
    private CheckResultsTableFactory checkResultsTableFactory;
    private CheckResultsSnapshotFactory checkResultsSnapshotFactory;
    private CheckResultsDeleteService checkResultsDeleteService;

    public DataStoragePerformanceCliCommand() {
    }

    @Autowired
    public DataStoragePerformanceCliCommand(TerminalWriter terminalWriter,
                                            CheckResultsTableFactory checkResultsTableFactory,
                                            CheckResultsSnapshotFactory checkResultsSnapshotFactory,
                                            CheckResultsDeleteService checkResultsDeleteService) {
        this.terminalWriter = terminalWriter;
        this.checkResultsTableFactory = checkResultsTableFactory;
        this.checkResultsSnapshotFactory = checkResultsSnapshotFactory;
        this.checkResultsDeleteService = checkResultsDeleteService;
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
        UserDomainIdentity userIdentity = UserDomainIdentity.LOCAL_INSTANCE_ADMIN_IDENTITY;
        CheckResultsSnapshot snapshot = this.checkResultsSnapshotFactory.createSnapshot("perftestdelete", new PhysicalTableName("perfschema", "perftest"), userIdentity);
        LoadedMonthlyPartition monthPartition = snapshot.getMonthPartition(monthDate, false);
        Table targetTable = snapshot.getTableDataChanges().getNewOrChangedRows();
        targetTable.append(newRows);

        snapshot.save();

        CheckResultsFragmentFilter deleteFilter = new CheckResultsFragmentFilter();
        TableSearchFilters tableSearchFilters = new TableSearchFilters();
        tableSearchFilters.setConnection("perftestdelete");
        tableSearchFilters.setFullTableName("perfschema.perftest");
        deleteFilter.setTableSearchFilters(tableSearchFilters);
        deleteFilter.setDateStart(monthDate);
        deleteFilter.setDateEnd(monthDate.plusMonths(1));
        this.checkResultsDeleteService.deleteSelectedCheckResultsFragment(deleteFilter, userIdentity);
    }

    private void generateRows(LocalDate monthDate, Table targetTable) {
        StringColumn idColumn = targetTable.stringColumn(CheckResultsColumnNames.ID_COLUMN_NAME);
        DoubleColumn actualValueColumn = targetTable.doubleColumn(CheckResultsColumnNames.ACTUAL_VALUE_COLUMN_NAME);
        DateTimeColumn timePeriodColumn = targetTable.dateTimeColumn(CheckResultsColumnNames.TIME_PERIOD_COLUMN_NAME);

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
        Table targetTable = this.checkResultsTableFactory.createEmptyCheckResultsTable("rules");
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
