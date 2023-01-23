package ai.dqo.data.ruleresults.services;

import ai.dqo.checks.AbstractRootChecksContainerSpec;
import ai.dqo.checks.CheckTimeScale;
import ai.dqo.data.errors.factory.ErrorsColumnNames;
import ai.dqo.data.errors.snapshot.ErrorsSnapshot;
import ai.dqo.data.errors.snapshot.ErrorsSnapshotFactory;
import ai.dqo.data.readouts.factory.SensorReadoutsColumnNames;
import ai.dqo.data.ruleresults.factory.RuleResultsColumnNames;
import ai.dqo.data.ruleresults.services.models.CheckResultStatus;
import ai.dqo.data.ruleresults.services.models.CheckResultsOverviewDataModel;
import ai.dqo.data.ruleresults.snapshot.RuleResultsSnapshot;
import ai.dqo.data.ruleresults.snapshot.RuleResultsSnapshotFactory;
import ai.dqo.metadata.id.HierarchyId;
import ai.dqo.metadata.sources.PhysicalTableName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.tablesaw.api.*;
import tech.tablesaw.selection.Selection;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Service that returns data from the check results.
 */
@Service
public class RuleResultsDataServiceImpl implements RuleResultsDataService {
    private RuleResultsSnapshotFactory ruleResultsSnapshotFactory;
    private ErrorsSnapshotFactory errorsSnapshotFactory;

    @Autowired
    public RuleResultsDataServiceImpl(RuleResultsSnapshotFactory ruleResultsSnapshotFactory,
                                      ErrorsSnapshotFactory errorsSnapshotFactory) {
        this.ruleResultsSnapshotFactory = ruleResultsSnapshotFactory;
        this.errorsSnapshotFactory = errorsSnapshotFactory;
    }

    /**
     * Retrieves the overall status of the recent check executions for the given root checks container (group of checks).
     * @param rootChecksContainerSpec Root checks container.
     * @param loadParameters Load parameters.
     * @return Overview of the check recent results.
     */
    @Override
    public CheckResultsOverviewDataModel[] readMostRecentCheckStatuses(AbstractRootChecksContainerSpec rootChecksContainerSpec,
                                                                       CheckResultsOverviewParameters loadParameters) {
        Map<Long, CheckResultsOverviewDataModel> resultMap = new LinkedHashMap<>();
        HierarchyId checksContainerHierarchyId = rootChecksContainerSpec.getHierarchyId();
        String connectionName = checksContainerHierarchyId.getConnectionName();
        PhysicalTableName physicalTableName = checksContainerHierarchyId.getPhysicalTableName();

        Table ruleResultsTable = loadRuleResults(loadParameters, connectionName, physicalTableName);
        Table errorsTable = loadErrorsNormalizedToResults(loadParameters, connectionName, physicalTableName);
        Table combinedTable = errorsTable != null ?
                (ruleResultsTable != null ? errorsTable.append(ruleResultsTable) : errorsTable) :
                ruleResultsTable;

        if (combinedTable == null) {
            return new CheckResultsOverviewDataModel[0]; // empty array
        }

        Table filteredTable = filterTableToRootChecksContainer(rootChecksContainerSpec, combinedTable);
        Table sortedTable = filteredTable.sortDescendingOn(
                SensorReadoutsColumnNames.EXECUTED_AT_COLUMN_NAME, // most recent execution first
                SensorReadoutsColumnNames.TIME_PERIOD_COLUMN_NAME, // then the most recent reading (for partitioned checks) when many partitions were captured
                RuleResultsColumnNames.SEVERITY_COLUMN_NAME); // second on the highest severity first on that time period

        int rowCount = sortedTable.rowCount();
        DateTimeColumn timePeriodColumn = sortedTable.dateTimeColumn(SensorReadoutsColumnNames.TIME_PERIOD_COLUMN_NAME);
        IntColumn severityColumn = sortedTable.intColumn(RuleResultsColumnNames.SEVERITY_COLUMN_NAME);
        StringColumn dataStreamColumn = sortedTable.stringColumn(SensorReadoutsColumnNames.DATA_STREAM_NAME_COLUMN_NAME);
        LongColumn checkHashColumn = sortedTable.longColumn(SensorReadoutsColumnNames.CHECK_HASH_COLUMN_NAME);
        StringColumn checkCategoryColumn = sortedTable.stringColumn(SensorReadoutsColumnNames.CHECK_CATEGORY_COLUMN_NAME);
        StringColumn checkNameColumn = sortedTable.stringColumn(SensorReadoutsColumnNames.CHECK_NAME_COLUMN_NAME);
        for (int i = 0; i < rowCount ; i++) {
            LocalDateTime timePeriod = timePeriodColumn.get(i);
            Integer severity = severityColumn.get(i);
            String dataStreamName = dataStreamColumn.get(i);
            Long checkHash = checkHashColumn.get(i);

            CheckResultsOverviewDataModel checkResultsOverviewDataModel = resultMap.get(checkHash);
            if (checkResultsOverviewDataModel == null) {
                String checkCategory = checkCategoryColumn.get(i);
                String checkName = checkNameColumn.get(i);
                checkResultsOverviewDataModel = new CheckResultsOverviewDataModel() {{
                    setCheckCategory(checkCategory);
                    setCheckName(checkName);
                    setCheckHash(checkHash);
                }};
                resultMap.put(checkHash, checkResultsOverviewDataModel);
            }

            checkResultsOverviewDataModel.appendResult(timePeriod, severity, dataStreamName, loadParameters.getResultsCount());
        }

        resultMap.values().forEach(m -> m.reverseLists());

        return resultMap.values().toArray(CheckResultsOverviewDataModel[]::new);
    }

    /**
     * Filters the results to only the results for the target object.
     * @param rootChecksContainerSpec Root checks container to identify the parent column, check type and time scale.
     * @param combinedTable Source table to be filtered.
     * @return Filtered table.
     */
    protected Table filterTableToRootChecksContainer(AbstractRootChecksContainerSpec rootChecksContainerSpec,
                                                     Table combinedTable) {
        String columnName = rootChecksContainerSpec.getHierarchyId().getColumnName(); // nullable
        String checkType = rootChecksContainerSpec.getCheckType().getDisplayName();
        CheckTimeScale timeScale = rootChecksContainerSpec.getCheckTimeScale();

        Selection rowSelection = combinedTable.stringColumn(SensorReadoutsColumnNames.CHECK_TYPE_COLUMN_NAME).isEqualTo(checkType);

        if (timeScale != null) {
            StringColumn timeGradientColumn = combinedTable.stringColumn(SensorReadoutsColumnNames.TIME_GRADIENT_COLUMN_NAME);
            rowSelection = rowSelection.and(timeGradientColumn.isEqualTo(timeScale.name()));
        }

        StringColumn columnNameColumn = combinedTable.stringColumn(SensorReadoutsColumnNames.COLUMN_NAME_COLUMN_NAME);
        rowSelection = rowSelection.and((columnName != null) ? columnNameColumn.isEqualTo(columnName) : columnNameColumn.isMissing());

        Table filteredTable = combinedTable.where(rowSelection);
        return filteredTable;
    }

    /**
     * Loads all errors and normalizes them to match the column names loaded from the rule results.
     * Adds a fake "severity" column with a value 4, so it is higher even then a fatal severity data quality error.
     * @param loadParameters Load parameters.
     * @param connectionName Connection name.
     * @param physicalTableName Physical table name.
     * @return Errors table or null when no data found.
     */
    protected Table loadErrorsNormalizedToResults(CheckResultsOverviewParameters loadParameters, String connectionName, PhysicalTableName physicalTableName) {
        ErrorsSnapshot errorsSnapshot = this.errorsSnapshotFactory.createReadOnlySnapshot(connectionName,
                physicalTableName, ErrorsColumnNames.COLUMN_NAMES_FOR_ERRORS_OVERVIEW);
        errorsSnapshot.ensureMonthsAreLoaded(loadParameters.getStartMonth(), loadParameters.getEndMonth());
        Table allErrors = errorsSnapshot.getAllData();
        if (allErrors == null) {
            return null;
        }

        IntColumn severityColumn = IntColumn.create(RuleResultsColumnNames.SEVERITY_COLUMN_NAME, allErrors.rowCount());
        severityColumn.setMissingTo(CheckResultStatus.execution_error.getSeverity()); // severity 0,1,2,3 are success,warning,error,fatal, so a processing error with severity 4 will sort ahead of other severities (processing errors are more severe for the overview)
        allErrors.addColumns(severityColumn);

        return allErrors;
    }

    /**
     * Loads rule results.
     * @param loadParameters Load parameters.
     * @param connectionName Connection name.
     * @param physicalTableName Physical table name.
     * @return Table with all rule results or null when no data found.
     */
    protected Table loadRuleResults(CheckResultsOverviewParameters loadParameters, String connectionName, PhysicalTableName physicalTableName) {
        RuleResultsSnapshot ruleResultsSnapshot = this.ruleResultsSnapshotFactory.createReadOnlySnapshot(connectionName,
                physicalTableName, RuleResultsColumnNames.COLUMN_NAMES_FOR_RESULTS_OVERVIEW);
        ruleResultsSnapshot.ensureMonthsAreLoaded(loadParameters.getStartMonth(), loadParameters.getEndMonth());
        Table ruleResultsData = ruleResultsSnapshot.getAllData();
        return ruleResultsData;
    }
}
