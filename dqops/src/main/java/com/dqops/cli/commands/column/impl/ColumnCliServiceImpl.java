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
package com.dqops.cli.commands.column.impl;

import com.dqops.cli.commands.CliOperationStatus;
import com.dqops.cli.commands.TabularOutputFormat;
import com.dqops.cli.output.OutputFormatService;
import com.dqops.cli.terminal.TerminalFactory;
import com.dqops.cli.terminal.TerminalTableWritter;
import com.dqops.core.jobqueue.PushJobResult;
import com.dqops.core.principal.DqoUserPrincipal;
import com.dqops.core.principal.DqoUserPrincipalProvider;
import com.dqops.data.models.DeleteStoredDataResult;
import com.dqops.metadata.search.ColumnSearchFilters;
import com.dqops.metadata.search.HierarchyNodeTreeSearcherImpl;
import com.dqops.metadata.sources.ColumnSpec;
import com.dqops.metadata.sources.ConnectionWrapper;
import com.dqops.metadata.sources.PhysicalTableName;
import com.dqops.metadata.sources.TableWrapper;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContext;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContextFactory;
import com.dqops.metadata.traversal.HierarchyNodeTreeWalker;
import com.dqops.metadata.traversal.HierarchyNodeTreeWalkerImpl;
import com.dqops.metadata.userhome.UserHome;
import com.dqops.services.metadata.ColumnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import tech.tablesaw.api.*;

import java.util.*;
import java.util.concurrent.ExecutionException;

@Service
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ColumnCliServiceImpl implements ColumnCliService {
	private final ColumnService columnService;
	private final UserHomeContextFactory userHomeContextFactory;
	private TerminalFactory terminalFactory;
	private final TerminalTableWritter terminalTableWritter;
	private final OutputFormatService outputFormatService;
	private final DqoUserPrincipalProvider userPrincipalProvider;

	@Autowired
	public ColumnCliServiceImpl(ColumnService columnService,
								UserHomeContextFactory userHomeContextFactory,
								TerminalFactory terminalFactory,
								TerminalTableWritter terminalTableWritter,
								OutputFormatService outputFormatService,
								DqoUserPrincipalProvider userPrincipalProvider) {
		this.columnService = columnService;
		this.userHomeContextFactory = userHomeContextFactory;
		this.terminalFactory = terminalFactory;
		this.terminalTableWritter = terminalTableWritter;
		this.outputFormatService = outputFormatService;
		this.userPrincipalProvider = userPrincipalProvider;
	}

	/**
	 * Loads a list of columns from a given connection and table.
	 * @param connectionName Connection name.
	 * @param tableName Table name.
	 * @param tabularOutputFormat Tabular output format.
	 * @param tags Dimensions filter.
	 * @param labels Labels filter.
	 * @return CLI operation status.
	 */
	@Override
	public CliOperationStatus loadColumns(String connectionName,
										  String tableName,
										  String columnName,
										  TabularOutputFormat tabularOutputFormat,
										  String[] tags,
										  String[] labels) {
		CliOperationStatus cliOperationStatus = new CliOperationStatus();

		ColumnSearchFilters columnSearchFilters = new ColumnSearchFilters();
		columnSearchFilters.setColumnName(columnName);
		columnSearchFilters.setSchemaTableName(tableName);
		columnSearchFilters.setConnectionName(connectionName);
		columnSearchFilters.setTags(tags);
		columnSearchFilters.setLabels(labels);

		HierarchyNodeTreeWalker hierarchyNodeTreeWalker = new HierarchyNodeTreeWalkerImpl();
		HierarchyNodeTreeSearcherImpl hierarchyNodeTreeSearcher = new HierarchyNodeTreeSearcherImpl(hierarchyNodeTreeWalker);

		DqoUserPrincipal userPrincipal = this.userPrincipalProvider.getLocalUserPrincipal();
		UserHomeContext userHomeContext = userHomeContextFactory.openLocalUserHome(userPrincipal.getDomainIdentity());
		UserHome userHome = userHomeContext.getUserHome();
		Collection <ColumnSpec> columnSpecs = hierarchyNodeTreeSearcher.findColumns(
				userHome, columnSearchFilters);

		if (columnSpecs.isEmpty()) {
			setColumnsNotFoundStatusMessage(cliOperationStatus);
			return cliOperationStatus;
		}

		Table resultTable = Table.create().addColumns(
				LongColumn.create("Id"),
				TextColumn.create("Connection name"),
				TextColumn.create("Table name"),
				TextColumn.create("Column name"),
				TextColumn.create("Column type"),
				BooleanColumn.create("Disabled"));

		for (ColumnSpec columnSpec: columnSpecs) {
			Row row = resultTable.appendRow();
			ConnectionWrapper currentConnection = userHome.findConnectionFor(columnSpec.getHierarchyId());
			TableWrapper currentTable = userHome.findTableFor(columnSpec.getHierarchyId());
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
	 * @return CLI operation status.
	 */
	@Override
	public CliOperationStatus addColumn(String connectionName, String tableName, String columnName, ColumnSpec columnSpec) {
		CliOperationStatus cliOperationStatus = new CliOperationStatus();

		DqoUserPrincipal userPrincipal = this.userPrincipalProvider.getLocalUserPrincipal();
		UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(userPrincipal.getDomainIdentity());
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
	 * @param principal Principal that will be used to run the job.
	 * @return CLI operation status.
	 */
	@Override
	public CliOperationStatus removeColumn(String connectionName, String tableName, String columnName, DqoUserPrincipal principal) {
		CliOperationStatus cliOperationStatus = new CliOperationStatus();

		DqoUserPrincipal userPrincipal = this.userPrincipalProvider.getLocalUserPrincipal();
		UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(userPrincipal.getDomainIdentity());
		UserHome userHome = userHomeContext.getUserHome();

		ColumnSearchFilters columnSearchFilters = new ColumnSearchFilters();
		columnSearchFilters.setColumnName(columnName);
		columnSearchFilters.setSchemaTableName(tableName);
		columnSearchFilters.setConnectionName(connectionName);

		HierarchyNodeTreeWalker hierarchyNodeTreeWalker = new HierarchyNodeTreeWalkerImpl();
		HierarchyNodeTreeSearcherImpl hierarchyNodeTreeSearcher = new HierarchyNodeTreeSearcherImpl(hierarchyNodeTreeWalker);

		Collection<ColumnSpec> columnSpecs = hierarchyNodeTreeSearcher.findColumns(userHome, columnSearchFilters);

		if (columnSpecs.size() == 0) {
			setColumnsNotFoundStatusMessage(cliOperationStatus);
			return cliOperationStatus;
		}

		CliOperationStatus listingStatus = loadColumns(connectionName, tableName, columnName, TabularOutputFormat.TABLE, null, null);
		this.terminalTableWritter.writeTable(listingStatus.getTable(), true);
		this.terminalFactory.getWriter().writeLine("Do you want to remove these " + columnSpecs.size() + " columns?");
		boolean response = this.terminalFactory.getReader().promptBoolean("Yes or No", false);
		if (!response) {
			cliOperationStatus.setFailedMessage("Delete operation cancelled.");
			return cliOperationStatus;
		}

		List<PushJobResult<DeleteStoredDataResult>> backgroundJobs = this.columnService.deleteColumns(
				this.convertColumnSpecsToHierarchyMapping(userHome, columnSpecs), principal);

		try {
			for (PushJobResult<DeleteStoredDataResult> job : backgroundJobs) {
				job.getFinishedFuture().get();
			}
		} catch (InterruptedException e) {
			cliOperationStatus.setSuccessMessage(String.format("Removed %d columns.", columnSpecs.size())
					+ " Deleting results for these columns has been cancelled."
			);
			return cliOperationStatus;
		} catch (ExecutionException e) {
			cliOperationStatus.setSuccessMessage(String.format("Removed %d columns.", columnSpecs.size())
					+ " An exception occurred while deleting results for these columns."
			);
			return cliOperationStatus;
		}

		cliOperationStatus.setSuccessMessage(String.format("Successfully removed %d columns.", columnSpecs.size()));
		return cliOperationStatus;
	}

	/**
	 * Converts an iterable of column specs into a connection -> table -> column mapping,
	 * provided user home for read operations.
	 * @param userHome    User home used for read operations on the hierarchy tree.
	 * @param columnSpecs Iterable of column specs.
	 * @return Hierarchy mapping of column specs wrt the connection level.
	 */
	protected Map<String, Map<PhysicalTableName, Iterable<String>>> convertColumnSpecsToHierarchyMapping(
			UserHome userHome, Iterable<ColumnSpec> columnSpecs) {
		Map<String, Map<PhysicalTableName, List<String>>> connectionToTableToColumnsMapping = new HashMap<>();
		for (ColumnSpec columnSpec: columnSpecs) {
			TableWrapper tableWrapper = userHome.findTableFor(columnSpec.getHierarchyId());

			String connectionName = userHome.findConnectionFor(tableWrapper.getHierarchyId()).getName();
			if (!connectionToTableToColumnsMapping.containsKey(connectionName)) {
				connectionToTableToColumnsMapping.put(connectionName, new HashMap<>());
			}

			Map<PhysicalTableName, List<String>> tableToColumnsMapping = connectionToTableToColumnsMapping.get(connectionName);
			PhysicalTableName tableName = tableWrapper.getPhysicalTableName();
			if (!tableToColumnsMapping.containsKey(tableName)) {
				tableToColumnsMapping.put(tableName, new ArrayList<>());
			}
			tableToColumnsMapping.get(tableName).add(columnSpec.getColumnName());
		}

		Map<String, Map<PhysicalTableName, Iterable<String>>> result = new HashMap<>();
		for (Map.Entry<String, Map<PhysicalTableName, List<String>>> connectionMapping: connectionToTableToColumnsMapping.entrySet()) {
			// Convert Map<PhysicalTableName, List<String>> to Map<PhysicalTableName, Iterable<String>>.
			result.put(connectionMapping.getKey(), new HashMap<>(connectionMapping.getValue()));
		}

		return result;
	}

	/**
	 * Update a column from a given connection, table and column.
	 * @param connectionName Connection name.
	 * @param tableName Table name.
	 * @param columnName Column name.
	 * @param columnSpec Column spec.
	 * @return CLI operation status.
	 */
	@Override
	public CliOperationStatus updateColumn(String connectionName, String tableName, String columnName, ColumnSpec columnSpec) {
		CliOperationStatus cliOperationStatus = new CliOperationStatus();

		DqoUserPrincipal userPrincipal = this.userPrincipalProvider.getLocalUserPrincipal();
		UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(userPrincipal.getDomainIdentity());
		UserHome userHome = userHomeContext.getUserHome();

		ColumnSearchFilters columnSearchFilters = new ColumnSearchFilters();
		columnSearchFilters.setColumnName(columnName);
		columnSearchFilters.setSchemaTableName(tableName);
		columnSearchFilters.setConnectionName(connectionName);

		HierarchyNodeTreeWalker hierarchyNodeTreeWalker = new HierarchyNodeTreeWalkerImpl();
		HierarchyNodeTreeSearcherImpl hierarchyNodeTreeSearcher = new HierarchyNodeTreeSearcherImpl(hierarchyNodeTreeWalker);

		Collection <ColumnSpec> columnSpecs = hierarchyNodeTreeSearcher.findColumns(userHome, columnSearchFilters);

		if (columnSpecs.size() == 0) {
			setColumnsNotFoundStatusMessage(cliOperationStatus);
			return cliOperationStatus;
		}

		columnSpecs.forEach(
				spec -> {
					if (columnSpec.getTypeSnapshot() != null) {
						spec.setTypeSnapshot(columnSpec.getTypeSnapshot());
					}
					if (columnSpec.getSqlExpression() != null) {
						spec.setSqlExpression(columnSpec.getSqlExpression());
					}
				}
		);

		userHomeContext.flush();

		cliOperationStatus.setSuccessMessage(String.format("Successfully updated %d columns.", columnSpecs.size()));
		return cliOperationStatus;
	}

	/**
	 * Update a column from a given connection, table and column.
	 * @param connectionName Connection name.
	 * @param tableName Table name.
	 * @param columnName Column name.
	 * @param newColumnName New column name.
	 * @return CLI operation status.
	 */
	@Override
	public CliOperationStatus renameColumn(String connectionName, String tableName, String columnName, String newColumnName) {
		CliOperationStatus cliOperationStatus = new CliOperationStatus();

		DqoUserPrincipal userPrincipal = this.userPrincipalProvider.getLocalUserPrincipal();
		UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(userPrincipal.getDomainIdentity());
		UserHome userHome = userHomeContext.getUserHome();

		ColumnSearchFilters columnSearchFilters = new ColumnSearchFilters();
		columnSearchFilters.setColumnName(columnName);
		columnSearchFilters.setSchemaTableName(tableName);
		columnSearchFilters.setConnectionName(connectionName);

		HierarchyNodeTreeWalker hierarchyNodeTreeWalker = new HierarchyNodeTreeWalkerImpl();
		HierarchyNodeTreeSearcherImpl hierarchyNodeTreeSearcher = new HierarchyNodeTreeSearcherImpl(hierarchyNodeTreeWalker);

		Collection <ColumnSpec> columnSpecs = hierarchyNodeTreeSearcher.findColumns(userHome, columnSearchFilters);

		if (columnSpecs.size() == 0) {
			setColumnsNotFoundStatusMessage(cliOperationStatus);
			return cliOperationStatus;
		}

		if (columnSpecs.size() > 1) {
			cliOperationStatus.setFailedMessage("There are too many columns with these filters!");
			return cliOperationStatus;
		}

		columnSpecs.forEach(
				spec -> {
					TableWrapper table = userHome.findTableFor(spec.getHierarchyId());
					table.getSpec().getColumns().put(newColumnName, spec.deepClone());
					table.getSpec().getColumns().remove(spec.getColumnName());
				}
		);
		userHomeContext.flush();

		cliOperationStatus.setSuccessMessage(String.format("Successfully renamed column %s.", columnName));
		return cliOperationStatus;
	}

	/**
	 * Disable or enable a column from a given connection, table and column.
	 * @param connectionName Connection name.
	 * @param tableName Table name.
	 * @param columnName Column name.
	 * @param disable logic value determines if we turn on or off check.
	 * @return CLI operation status.
	 */
	@Override
	public CliOperationStatus setDisableTo(String connectionName, String tableName, String columnName, boolean disable) {
		CliOperationStatus cliOperationStatus = new CliOperationStatus();

		DqoUserPrincipal userPrincipal = this.userPrincipalProvider.getLocalUserPrincipal();
		UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(userPrincipal.getDomainIdentity());
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
			setColumnsNotFoundStatusMessage(cliOperationStatus);
			return cliOperationStatus;
		}

		boolean isPossible = true;
		for (ColumnSpec spec: columnSpecs) {
			if (spec.isDisabled() == disable) {
				isPossible = false;
			}
		}

		if (columnSpecs.size() == 1 && !isPossible) {
			String message = disable ? "You cannot disable a disabled column" : "You cannot enable an enabled column";
			cliOperationStatus.setFailedMessage(message);
			return cliOperationStatus;
		}

		columnSpecs.forEach(spec -> {
				spec.setDisabled(disable);
			}
		);
		userHomeContext.flush();

		cliOperationStatus.setSuccessMessage(String.format("Successfully %s %d columns.",
															disable ? "disabled" : "enabled", columnSpecs.size()));
		return cliOperationStatus;
	}

	/**
	 * Sets a shared "columns not found" message on CLI operation status.
	 * @param cliOperationStatus CLI operation status to modify.
	 */
	protected static void setColumnsNotFoundStatusMessage(CliOperationStatus cliOperationStatus) {
		cliOperationStatus.setFailedMessage("There are no columns with these filters!");
	}
}
