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
package ai.dqo.cli.commands.column.impl;

import ai.dqo.cli.commands.CliOperationStatus;
import ai.dqo.cli.commands.TabularOutputFormat;
import ai.dqo.cli.output.OutputFormatService;
import ai.dqo.cli.terminal.TerminalReader;
import ai.dqo.cli.terminal.TerminalTableWritter;
import ai.dqo.cli.terminal.TerminalWriter;
import ai.dqo.metadata.search.ColumnSearchFilters;
import ai.dqo.metadata.search.HierarchyNodeTreeSearcherImpl;
import ai.dqo.metadata.sources.ColumnSpec;
import ai.dqo.metadata.sources.ConnectionWrapper;
import ai.dqo.metadata.sources.PhysicalTableName;
import ai.dqo.metadata.sources.TableWrapper;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContext;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContextFactory;
import ai.dqo.metadata.traversal.HierarchyNodeTreeWalker;
import ai.dqo.metadata.traversal.HierarchyNodeTreeWalkerImpl;
import ai.dqo.metadata.userhome.UserHome;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import tech.tablesaw.api.*;

import java.util.Collection;

@Service
@Scope("prototype")
public class ColumnServiceImpl implements ColumnService {
	private final UserHomeContextFactory userHomeContextFactory;
	private final TerminalReader terminalReader;
	private final TerminalWriter terminalWriter;
	private final TerminalTableWritter terminalTableWritter;
	private final OutputFormatService outputFormatService;

	@Autowired
	public ColumnServiceImpl(UserHomeContextFactory userHomeContextFactory,
							 TerminalReader terminalReader,
							 TerminalWriter terminalWriter,
							 TerminalTableWritter terminalTableWritter,
							 OutputFormatService outputFormatService) {
		this.userHomeContextFactory = userHomeContextFactory;
		this.terminalReader = terminalReader;
		this.terminalWriter = terminalWriter;
		this.terminalTableWritter = terminalTableWritter;
		this.outputFormatService = outputFormatService;
	}

	/**
	 * Loads a list of columns from a given connection and table.
	 * @param connectionName Connection name.
	 * @param tableName Table name.
	 * @param tabularOutputFormat Tabular output format.
	 * @param dimensions Dimensions filter.
	 * @param labels Labels filter.
	 * @return Cli operation status.
	 */
	@Override
	public CliOperationStatus loadColumns(String connectionName, String tableName, String columnName, TabularOutputFormat tabularOutputFormat, String[] dimensions, String[] labels) {
		CliOperationStatus cliOperationStatus = new CliOperationStatus();

		ColumnSearchFilters columnSearchFilters = new ColumnSearchFilters();
		columnSearchFilters.setColumnName(columnName);
		columnSearchFilters.setSchemaTableName(tableName);
		columnSearchFilters.setConnectionName(connectionName);
		columnSearchFilters.setDimensions(dimensions);
		columnSearchFilters.setLabels(labels);

		HierarchyNodeTreeWalker hierarchyNodeTreeWalker = new HierarchyNodeTreeWalkerImpl();
		HierarchyNodeTreeSearcherImpl hierarchyNodeTreeSearcher = new HierarchyNodeTreeSearcherImpl(hierarchyNodeTreeWalker);

		Collection <ColumnSpec> columnSpecs = hierarchyNodeTreeSearcher.findColumns(userHomeContextFactory.openLocalUserHome().getUserHome(), columnSearchFilters);

		if (columnSpecs.size() == 0) {
			cliOperationStatus.setFailedMessage("There are no columns with this filters!");
			return cliOperationStatus;
		}

		Table resultTable = Table.create().addColumns(
				LongColumn.create("Id"),
				StringColumn.create("Connection name"),
				StringColumn.create("Table name"),
				StringColumn.create("Column name"),
				StringColumn.create("Column type"),
				BooleanColumn.create("Disabled"));

		for (ColumnSpec columnSpec: columnSpecs) {
			Row row = resultTable.appendRow();
			ConnectionWrapper currentConnection = userHomeContextFactory.openLocalUserHome().getUserHome().findConnectionFor(columnSpec.getHierarchyId());
			TableWrapper currentTable = userHomeContextFactory.openLocalUserHome().getUserHome().findTableFor(columnSpec.getHierarchyId());
			row.setLong(0, columnSpec.getHierarchyId().hashCode64());
			row.setString(1, currentConnection.getName());
			row.setString(2, currentTable.getPhysicalTableName().toBaseFileName());
			row.setString(3, columnSpec.getColumnName());
			row.setString(4, columnSpec.getTypeSnapshot().getColumnType());
			if (columnSpec.isDisabled()) {
				row.setBoolean(5, true);
			}
		}
		cliOperationStatus.setSuccess(true);
		switch(tabularOutputFormat) {
			case CSV: {
				cliOperationStatus.setMessage(this.outputFormatService.tableToCsv(resultTable));
				break;
			}
			case JSON: {
				cliOperationStatus.setMessage(this.outputFormatService.tableToJson(resultTable));
				break;
			}
			default: {
				cliOperationStatus.setTable(resultTable);
				break;
			}
		}

		return cliOperationStatus;
	}

	/**
	 * Add a column from a given connection, table and column.
	 * @param connectionName Connection name.
	 * @param tableName Table name.
	 * @param columnName Column name.
	 * @param columnSpec Column spec.
	 * @return Cli operation status.
	 */
	@Override
	public CliOperationStatus addColumn(String connectionName, String tableName, String columnName, ColumnSpec columnSpec) {
		CliOperationStatus cliOperationStatus = new CliOperationStatus();

		UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
		UserHome userHome = userHomeContext.getUserHome();

		ConnectionWrapper connection = userHome.getConnections().getByObjectName(connectionName, true);
		if (connection == null) {
			cliOperationStatus.setFailedMessage("There are no connections with this name");
			return cliOperationStatus;
		}

		PhysicalTableName physicalTableName = PhysicalTableName.fromSchemaTableFilter(tableName);
		TableWrapper table = connection.getTables().getByObjectName(physicalTableName, true);
		if (table == null) {
			cliOperationStatus.setFailedMessage("There are no tables with this name");
			return cliOperationStatus;
		}

		if (table.getSpec().getColumns().get(columnName) != null) {
			cliOperationStatus.setFailedMessage("There are no columns with this name");
			return cliOperationStatus;
		}

		table.getSpec().getColumns().put(columnName, columnSpec);
		userHomeContext.flush();
		cliOperationStatus.setSuccessMessage(String.format("Column %s was successfully added.", columnName));
		return cliOperationStatus;
	}

	/**
	 * Remove a column from a given connection, table and column.
	 * @param connectionName Connection name.
	 * @param tableName Table name.
	 * @param columnName Column name.
	 * @return Cli operation status.
	 */
	@Override
	public CliOperationStatus removeColumn(String connectionName, String tableName, String columnName) {
		CliOperationStatus cliOperationStatus = new CliOperationStatus();

		UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
		UserHome userHome = userHomeContext.getUserHome();

		ColumnSearchFilters columnSearchFilters = new ColumnSearchFilters();
		columnSearchFilters.setColumnName(columnName);
		columnSearchFilters.setSchemaTableName(tableName);
		columnSearchFilters.setConnectionName(connectionName);

		HierarchyNodeTreeWalker hierarchyNodeTreeWalker = new HierarchyNodeTreeWalkerImpl();
		HierarchyNodeTreeSearcherImpl hierarchyNodeTreeSearcher = new HierarchyNodeTreeSearcherImpl(hierarchyNodeTreeWalker);

		Collection <ColumnSpec> columnSpecs = hierarchyNodeTreeSearcher.findColumns(userHome, columnSearchFilters);

		if (columnSpecs.size() == 0) {
			cliOperationStatus.setFailedMessage("There are no columns with this filters!");
			return cliOperationStatus;
		}

		CliOperationStatus listingStatus = loadColumns(connectionName, tableName, columnName, TabularOutputFormat.TABLE, null, null);
		this.terminalTableWritter.writeTable(listingStatus.getTable(), true);
		this.terminalWriter.writeLine("Do You want to remove these " + columnSpecs.size() + " columns?");
		boolean response = this.terminalReader.promptBoolean("Yes or No", false);
		if (!response) {
			cliOperationStatus.setFailedMessage("You deleted 0 columns");
			return cliOperationStatus;
		}

		columnSpecs.forEach(
				spec -> {
					TableWrapper table = userHome.findTableFor(spec.getHierarchyId());
					table.getSpec().getColumns().remove(spec.getColumnName());
					userHomeContext.flush();
				}
		);

		cliOperationStatus.setSuccessMessage(String.format("Successfully removed %d columns.", columnSpecs.size()));
		return cliOperationStatus;
	}

	/**
	 * Update a column from a given connection, table and column.
	 * @param connectionName Connection name.
	 * @param tableName Table name.
	 * @param columnName Column name.
	 * @param columnSpec Column spec.
	 * @return Cli operation status.
	 */
	@Override
	public CliOperationStatus updateColumn(String connectionName, String tableName, String columnName, ColumnSpec columnSpec) {
		CliOperationStatus cliOperationStatus = new CliOperationStatus();

		UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
		UserHome userHome = userHomeContext.getUserHome();

		ColumnSearchFilters columnSearchFilters = new ColumnSearchFilters();
		columnSearchFilters.setColumnName(columnName);
		columnSearchFilters.setSchemaTableName(tableName);
		columnSearchFilters.setConnectionName(connectionName);

		HierarchyNodeTreeWalker hierarchyNodeTreeWalker = new HierarchyNodeTreeWalkerImpl();
		HierarchyNodeTreeSearcherImpl hierarchyNodeTreeSearcher = new HierarchyNodeTreeSearcherImpl(hierarchyNodeTreeWalker);

		Collection <ColumnSpec> columnSpecs = hierarchyNodeTreeSearcher.findColumns(userHome, columnSearchFilters);

		if (columnSpecs.size() == 0) {
			cliOperationStatus.setFailedMessage("There are no columns with this filters!");
			return cliOperationStatus;
		}

		columnSpecs.forEach(
				spec -> {
					if (columnSpec.getTypeSnapshot() != null) {
						spec.setTypeSnapshot(columnSpec.getTypeSnapshot());
						userHomeContext.flush();
					}
				}
		);

		cliOperationStatus.setSuccessMessage(String.format("Successfully updated %d columns.", columnSpecs.size()));
		return cliOperationStatus;
	}

	/**
	 * Update a column from a given connection, table and column.
	 * @param connectionName Connection name.
	 * @param tableName Table name.
	 * @param columnName Column name.
	 * @param newColumnName New column name.
	 * @return Cli operation status.
	 */
	@Override
	public CliOperationStatus renameColumn(String connectionName, String tableName, String columnName, String newColumnName) {
		CliOperationStatus cliOperationStatus = new CliOperationStatus();

		UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
		UserHome userHome = userHomeContext.getUserHome();

		ColumnSearchFilters columnSearchFilters = new ColumnSearchFilters();
		columnSearchFilters.setColumnName(columnName);
		columnSearchFilters.setSchemaTableName(tableName);
		columnSearchFilters.setConnectionName(connectionName);

		HierarchyNodeTreeWalker hierarchyNodeTreeWalker = new HierarchyNodeTreeWalkerImpl();
		HierarchyNodeTreeSearcherImpl hierarchyNodeTreeSearcher = new HierarchyNodeTreeSearcherImpl(hierarchyNodeTreeWalker);

		Collection <ColumnSpec> columnSpecs = hierarchyNodeTreeSearcher.findColumns(userHome, columnSearchFilters);

		if (columnSpecs.size() == 0) {
			cliOperationStatus.setFailedMessage("There are no columns with this filters!");
			return cliOperationStatus;
		}

		if (columnSpecs.size() > 1) {
			cliOperationStatus.setFailedMessage("There are too many columns with this filters!");
			return cliOperationStatus;
		}

		columnSpecs.forEach(
				spec -> {
					TableWrapper table = userHome.findTableFor(spec.getHierarchyId());
					table.getSpec().getColumns().put(newColumnName, spec.clone());
					table.getSpec().getColumns().remove(spec.getColumnName());
					userHomeContext.flush();
				}
		);

		cliOperationStatus.setSuccessMessage(String.format("Successfully renamed column %s.", columnName));
		return cliOperationStatus;
	}

	/**
	 * Disable or enable a column from a given connection, table and column.
	 * @param connectionName Connection name.
	 * @param tableName Table name.
	 * @param columnName Column name.
	 * @param disable logic value determines if we turn on or off check.
	 * @return Cli operation status.
	 */
	@Override
	public CliOperationStatus setDisableTo(String connectionName, String tableName, String columnName, boolean disable) {
		CliOperationStatus cliOperationStatus = new CliOperationStatus();

		UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
		UserHome userHome = userHomeContext.getUserHome();

		ColumnSearchFilters columnSearchFilters = new ColumnSearchFilters();
		columnSearchFilters.setEnabled(false);
		columnSearchFilters.setColumnName(columnName);
		columnSearchFilters.setSchemaTableName(tableName);
		columnSearchFilters.setConnectionName(connectionName);

		HierarchyNodeTreeWalker hierarchyNodeTreeWalker = new HierarchyNodeTreeWalkerImpl();
		HierarchyNodeTreeSearcherImpl hierarchyNodeTreeSearcher = new HierarchyNodeTreeSearcherImpl(hierarchyNodeTreeWalker);

		Collection <ColumnSpec> columnSpecs = hierarchyNodeTreeSearcher.findColumns(userHome, columnSearchFilters);

		if (columnSpecs.size() == 0) {
			cliOperationStatus.setFailedMessage("There are no columns with this filters!");
			return cliOperationStatus;
		}

		boolean isPossible = true;
		for (ColumnSpec spec: columnSpecs) {
			if (spec.isDisabled() == disable) {
				isPossible = false;
			}
		}

		if (columnSpecs.size() == 1 && !isPossible) {
			String message = disable ? "You cannot disable disabled column" : "You cannot enable enabled column";
			cliOperationStatus.setFailedMessage(message);
			return cliOperationStatus;
		}

		columnSpecs.forEach(spec -> {
				spec.setDisabled(disable);
				userHomeContext.flush();
			}
		);

		cliOperationStatus.setSuccessMessage(String.format("Successfully %s %d columns.",
															disable ? "disabled" : "enabled", columnSpecs.size()));
		return cliOperationStatus;
	}
}
