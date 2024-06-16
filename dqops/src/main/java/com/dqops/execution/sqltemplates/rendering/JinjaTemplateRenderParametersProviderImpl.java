package com.dqops.execution.sqltemplates.rendering;

import com.dqops.connectors.duckdb.DuckdbFilesFormatType;
import com.dqops.connectors.duckdb.DuckdbParametersSpec;
import com.dqops.execution.ExecutionContext;
import com.dqops.execution.sensors.SensorExecutionRunParameters;
import com.dqops.execution.sensors.finder.SensorDefinitionFindResult;
import com.dqops.metadata.sources.ConnectionSpec;
import com.dqops.metadata.sources.PhysicalTableName;
import com.dqops.metadata.sources.TableSpec;
import com.dqops.metadata.sources.TableWrapper;
import com.dqops.metadata.sources.fileformat.FileFormatSpec;
import com.dqops.metadata.sources.fileformat.FileFormatSpecProvider;
import com.dqops.metadata.timeseries.TimeSeriesMode;
import com.dqops.metadata.userhome.UserHome;
import com.dqops.sensors.AbstractSensorParametersSpec;
import com.dqops.sensors.ReferencedTableParameters;
import com.dqops.services.metadata.TableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JinjaTemplateRenderParametersProviderImpl implements JinjaTemplateRenderParametersProvider {

    private final TableService tableService;

    @Autowired
    public JinjaTemplateRenderParametersProviderImpl(TableService tableService) {
        this.tableService = tableService;
    }

    /**
     * Creates a template render parameters using trimmed spec objects copied from the parameters.
     * Specification object trimming means that we make a clone of a table specification, but we are removing configuration of other checks to make the result json smaller.
     * @param sensorRunParameters Sensor run parameters with connection, table, column, sensor parameters to copy (with trimming).
     * @param sensorDefinitions Sensor definition specifications for the sensor itself and its provider sensor definition.
     * @return Jinja template render parameters that will be forwarded to Jinja2.
     */
    public JinjaTemplateRenderParameters createFromTrimmedObjects(ExecutionContext executionContext,
                                                                  SensorExecutionRunParameters sensorRunParameters,
                                                                  SensorDefinitionFindResult sensorDefinitions) {



        TableSpec tableSpec = sensorRunParameters.getTable();
        ConnectionSpec connectionSpec = sensorRunParameters.getConnection().trim();
        JinjaTemplateRenderParameters result = new JinjaTemplateRenderParameters()
        {{
            setConnection(connectionSpec);
            setTable(tableSpec.trim());
            setTargetTable(tableSpec.getPhysicalTableName());
            setColumn(sensorRunParameters.getColumn() != null ? sensorRunParameters.getColumn().trim() : null);
            setColumnName(sensorRunParameters.getColumn() != null ? sensorRunParameters.getColumn().getColumnName() : null);
            setParameters(sensorRunParameters.getSensorParameters());
            setEffectiveTimeSeries(sensorRunParameters.getTimeSeries() == null || sensorRunParameters.getTimeSeries().getMode() == TimeSeriesMode.current_time ? null : sensorRunParameters.getTimeSeries());
            setEffectiveDataGroupings(sensorRunParameters.getDataGroupings() != null ? sensorRunParameters.getDataGroupings().truncateToColumns() : null);
            setSensorDefinition(sensorDefinitions.getSensorDefinitionSpec().trim());
            setProviderSensorDefinition(sensorDefinitions.getProviderSensorDefinitionSpec().trim());
            setDialectSettings(sensorRunParameters.getDialectSettings());
            setEffectiveTimeWindowFilter(sensorRunParameters.getTimeWindowFilter());
            setActualValueAlias(sensorRunParameters.getActualValueAlias());
            setExpectedValueAlias(sensorRunParameters.getExpectedValueAlias());
            setAdditionalFilters(sensorRunParameters.getAdditionalFilters());
            setErrorSampling(sensorRunParameters.getErrorSamplingRenderParameters());  // when not null, error sampling is used
        }};

        DuckdbParametersSpec duckdbParametersSpec = sensorRunParameters.getConnection().getDuckdb();
        if (duckdbParametersSpec != null && duckdbParametersSpec.getFilesFormatType() != null && executionContext != null) {
            DuckdbFilesFormatType duckdbFilesFormatType = duckdbParametersSpec.getFilesFormatType();
            FileFormatSpec fileFormatSpec = FileFormatSpecProvider.resolveFileFormat(duckdbParametersSpec, tableSpec);
            if (duckdbFilesFormatType != null && fileFormatSpec != null && !fileFormatSpec.getFilePaths().isEmpty()) {
                result.setTableFromFiles(fileFormatSpec.buildTableOptionsString(duckdbParametersSpec, tableSpec));
            }

            if(sensorRunParameters.getSensorParameters() instanceof ReferencedTableParameters){
                String referencedTable = ((ReferencedTableParameters)sensorRunParameters.getSensorParameters()).getReferencedTable();
                String fullReferencedTableName = referencedTable.contains(".")
                        ? referencedTable
                        : tableSpec.getPhysicalTableName().getSchemaName() + "." + referencedTable;
                PhysicalTableName referencedPhysicalTableName = PhysicalTableName.fromSchemaTableFilter(fullReferencedTableName);

                UserHome userHome = executionContext.getUserHomeContext().getUserHome();
                TableWrapper tableWrapper = tableService.getTable(
                        userHome,
                        tableSpec.getHierarchyId().getConnectionName(),
                        referencedPhysicalTableName);

                FileFormatSpec referencedTableFileFormatSpec = FileFormatSpecProvider.resolveFileFormat(
                        duckdbParametersSpec, tableWrapper.getSpec());
                if (duckdbFilesFormatType != null && fileFormatSpec != null && !referencedTableFileFormatSpec.getFilePaths().isEmpty()) {
                    ReferencedTableParameters sensorParametersCloned = (ReferencedTableParameters) sensorRunParameters.getSensorParameters().deepClone();
                    String tableFile = referencedTableFileFormatSpec.buildTableOptionsString(duckdbParametersSpec, tableWrapper.getSpec());
                    sensorParametersCloned.setReferencedTable(tableFile);
                    result.setParameters((AbstractSensorParametersSpec)sensorParametersCloned);
                }
            }
        }

        return result;
    }

}
