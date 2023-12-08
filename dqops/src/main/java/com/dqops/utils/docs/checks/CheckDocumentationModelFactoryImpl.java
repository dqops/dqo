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
package com.dqops.utils.docs.checks;

import com.dqops.checks.AbstractCheckSpec;
import com.dqops.checks.AbstractRootChecksContainerSpec;
import com.dqops.checks.CheckTarget;
import com.dqops.checks.CheckType;
import com.dqops.checks.comparison.AbstractColumnComparisonCheckCategorySpec;
import com.dqops.checks.comparison.AbstractComparisonCheckCategorySpec;
import com.dqops.checks.comparison.AbstractComparisonCheckCategorySpecMap;
import com.dqops.connectors.ProviderDialectSettings;
import com.dqops.connectors.ProviderType;
import com.dqops.connectors.bigquery.BigQueryParametersSpec;
import com.dqops.connectors.bigquery.BigQueryProviderDialectSettings;
import com.dqops.connectors.mysql.MysqlParametersSpec;
import com.dqops.connectors.mysql.MysqlProviderDialectSettings;
import com.dqops.connectors.oracle.OracleParametersSpec;
import com.dqops.connectors.oracle.OracleProviderDialectSettings;
import com.dqops.connectors.postgresql.PostgresqlParametersSpec;
import com.dqops.connectors.postgresql.PostgresqlProviderDialectSettings;
import com.dqops.connectors.presto.PrestoProviderDialectSettings;
import com.dqops.connectors.redshift.RedshiftParametersSpec;
import com.dqops.connectors.redshift.RedshiftProviderDialectSettings;
import com.dqops.connectors.snowflake.SnowflakeParametersSpec;
import com.dqops.connectors.snowflake.SnowflakeProviderDialectSettings;
import com.dqops.connectors.spark.SparkParametersSpec;
import com.dqops.connectors.spark.SparkProviderDialectSettings;
import com.dqops.connectors.sqlserver.SqlServerParametersSpec;
import com.dqops.connectors.sqlserver.SqlServerProviderDialectSettings;
import com.dqops.connectors.trino.TrinoProviderDialectSettings;
import com.dqops.core.principal.UserDomainIdentity;
import com.dqops.execution.checks.EffectiveSensorRuleNames;
import com.dqops.execution.sensors.SensorExecutionRunParameters;
import com.dqops.execution.sensors.finder.SensorDefinitionFindResult;
import com.dqops.execution.sqltemplates.rendering.JinjaTemplateRenderParameters;
import com.dqops.execution.sqltemplates.rendering.JinjaTemplateRenderService;
import com.dqops.metadata.comparisons.TableComparisonConfigurationSpec;
import com.dqops.metadata.comparisons.TableComparisonConfigurationSpecMap;
import com.dqops.metadata.comparisons.TableComparisonGroupingColumnsPairSpec;
import com.dqops.metadata.definitions.sensors.ProviderSensorDefinitionWrapper;
import com.dqops.metadata.definitions.sensors.SensorDefinitionWrapper;
import com.dqops.metadata.groupings.DataGroupingConfigurationSpec;
import com.dqops.metadata.groupings.DataGroupingConfigurationSpecMap;
import com.dqops.metadata.groupings.DataGroupingDimensionSpec;
import com.dqops.metadata.id.HierarchyNode;
import com.dqops.metadata.search.CheckSearchFilters;
import com.dqops.metadata.sources.*;
import com.dqops.metadata.storage.localfiles.HomeType;
import com.dqops.metadata.storage.localfiles.sources.TableYaml;
import com.dqops.metadata.userhome.UserHomeImpl;
import com.dqops.services.check.mapping.ModelToSpecCheckMappingService;
import com.dqops.services.check.mapping.models.CheckContainerModel;
import com.dqops.services.check.mapping.models.CheckModel;
import com.dqops.services.check.mapping.models.QualityCategoryModel;
import com.dqops.services.check.matching.SimilarCheckMatchingService;
import com.dqops.services.check.matching.SimilarCheckModel;
import com.dqops.services.check.matching.SimilarChecksContainer;
import com.dqops.services.check.matching.SimilarChecksGroup;
import com.dqops.utils.docs.LinkageStore;
import com.dqops.utils.docs.ProviderTypeModel;
import com.dqops.utils.docs.rules.RuleDocumentationModelFactory;
import com.dqops.utils.docs.sensors.SensorDocumentationModel;
import com.dqops.utils.docs.sensors.SensorDocumentationModelFactory;
import com.dqops.utils.exceptions.DqoRuntimeException;
import com.dqops.utils.serialization.YamlSerializer;
import com.github.therapi.runtimejavadoc.ClassJavadoc;
import com.github.therapi.runtimejavadoc.CommentFormatter;
import com.github.therapi.runtimejavadoc.RuntimeJavadoc;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;
import java.util.*;

/**
 * Check documentation model factory. Creates documentation objects for each check.
 */
public class CheckDocumentationModelFactoryImpl implements CheckDocumentationModelFactory {
    private static final String COMPARISON_NAME = "compare_to_source_of_truth_table";

    private static final Map<String, String> TABLE_CATEGORY_HELP = new LinkedHashMap<>() {{
        put("volume", "Evaluates the overall quality of the table by verifying the number of rows.");
        put("timeliness", "Assesses the freshness and staleness of data, as well as data ingestion delay and reload lag for partitioned data.");
        put("accuracy", "Compares the tested table with another (reference) table.");
        put("sql", "Validate data against user-defined SQL queries at the table level. Checks in this group allow for validation that the set percentage of rows passed a custom SQL expression or that the custom SQL expression is not outside the set range.");
        put("availability", "Checks whether the table is accessible and available for use.");
        put("anomaly", "Detects anomalous (unexpected) changes and outliers in the time series of data quality results collected over a period of time.");
        put("schema", "Detects schema drifts such as columns added, removed, reordered or the data types of columns have changed.");
    }};

    private static final Map<String, String> COLUMN_CATEGORY_HELP = new LinkedHashMap<>() {{
        put("nulls", "Checks for the presence of null or missing values in a column.");
        put("numeric", "Validates that the data in a numeric column is in the expected format or within predefined ranges.");
        put("strings", "Validates that the data in a string column match the expected format or pattern.");
        put("uniqueness", "Counts the number or percent of duplicate or unique values in a column.");
        put("datetime", "Validates that the data in a date or time column is in the expected format and within predefined ranges.");
        put("pii", "Checks for the presence of sensitive or personally identifiable information (PII) in a column such as email, phone, zip code, IP4 and IP6 addresses.");
        put("sql", "Validate data against user-defined SQL queries at the column level. Checks in this group allows to validate that the set percentage of rows passed a custom SQL expression or that the custom SQL expression is not outside the set range.");
        put("bool", "Calculates the percentage of data in a Boolean format.");
        put("integrity", "Checks the referential integrity of a column against a column in another table.");
        put("anomaly", "Detects anomalous (unexpected) changes and outliers in the time series of data quality results collected over a period of time.");
        put("schema", "Detects schema drifts such as a column is missing or the data type has changed.");
    }};

    private static final CommentFormatter commentFormatter = new CommentFormatter();

    private SimilarCheckMatchingService similarCheckMatchingService;
    private SensorDocumentationModelFactory sensorDocumentationModelFactory;
    private RuleDocumentationModelFactory ruleDocumentationModelFactory;
    private ModelToSpecCheckMappingService modelToSpecCheckMappingService;
    private YamlSerializer yamlSerializer;
    private JinjaTemplateRenderService jinjaTemplateRenderService;
    private final LinkageStore<Class<?>> linkageStore;

    /**
     * Creates a check documentation service.
     *
     * @param similarCheckMatchingService     Service that finds all similar checks that share the same sensor and rule.
     * @param sensorDocumentationModelFactory Sensor documentation factory for generating the documentation for the sensor, maybe we want to pick some information about the sensor.
     * @param ruleDocumentationModelFactory   Rule documentation factory for generating the documentation for the sensor, maybe we want to pick some information about the rule.
     * @param modelToSpecCheckMappingService  UI check model to specification adapter that can generate a sample usage for us.
     * @param yamlSerializer                  Yaml serializer, used to render the table yaml files with a sample usage.
     * @param jinjaTemplateRenderService      Jinja template rendering service. Used to render how the SQL template will be filled for the given parameters.
     * @param linkageStore
     */
    public CheckDocumentationModelFactoryImpl(SimilarCheckMatchingService similarCheckMatchingService,
                                              SensorDocumentationModelFactory sensorDocumentationModelFactory,
                                              RuleDocumentationModelFactory ruleDocumentationModelFactory,
                                              ModelToSpecCheckMappingService modelToSpecCheckMappingService,
                                              YamlSerializer yamlSerializer,
                                              JinjaTemplateRenderService jinjaTemplateRenderService,
                                              LinkageStore<Class<?>> linkageStore) {
        this.similarCheckMatchingService = similarCheckMatchingService;
        this.sensorDocumentationModelFactory = sensorDocumentationModelFactory;
        this.ruleDocumentationModelFactory = ruleDocumentationModelFactory;
        this.modelToSpecCheckMappingService = modelToSpecCheckMappingService;
        this.yamlSerializer = yamlSerializer;
        this.jinjaTemplateRenderService = jinjaTemplateRenderService;
        this.linkageStore = linkageStore;
    }

    /**
     * Creates a column specification with a label.
     * @param label Label to add to the column.
     * @return Column specification.
     */
    private ColumnSpec createColumnWithLabel(String label) {
        ColumnSpec columnSpec = new ColumnSpec();
        LabelSetSpec labels = new LabelSetSpec();
        labels.add(label);
        columnSpec.setLabels(labels);
        return columnSpec;
    }

    /**
     * Creates a table specification with one sample table and the timestamp columns configured.
     * @param addAnalyzedColumn Boolean value - add the analyzed column.
     * @return Table specification.
     */
    private TableSpec createTableSpec(boolean addAnalyzedColumn) {
        TableSpec tableSpec = new TableSpec();
        tableSpec.setPhysicalTableName(new PhysicalTableName("target_schema", "target_table"));

        if (addAnalyzedColumn) {
            ColumnSpec columnSpec = createColumnWithLabel("This is the column that is analyzed for data quality issues");
            tableSpec.getColumns().put("target_column", columnSpec);
        }

        tableSpec.getColumns().put("col_event_timestamp", createColumnWithLabel("optional column that stores the timestamp when the event/transaction happened"));
        tableSpec.getColumns().put("col_inserted_at", createColumnWithLabel("optional column that stores the timestamp when row was ingested"));
        TimestampColumnsSpec timestampColumns = tableSpec.getTimestampColumns();
        timestampColumns.setEventTimestampColumn("col_event_timestamp");
        timestampColumns.setIngestionTimestampColumn("col_inserted_at");

        return tableSpec;
    }

    /**
     * Create a list of check documentation models for table level checks. Each category contains a list of similar checks to be documented on the same page.
     * @return Documentation for each check category on a table level.
     */
    @Override
    public List<CheckCategoryDocumentationModel> makeDocumentationForTableChecks() {
        UserHomeImpl userHome = new UserHomeImpl(UserDomainIdentity.LOCAL_INSTANCE_ADMIN_IDENTITY);
        ConnectionWrapper connectionWrapper = userHome.getConnections().createAndAddNew("<target_connection>");
        TableWrapper tableWrapper = connectionWrapper.getTables().createAndAddNew(new PhysicalTableName("<target_schema>", "<target_table>"));
        TableSpec tableSpec = createTableSpec(false);
        tableWrapper.setSpec(tableSpec);

        SimilarChecksContainer similarTableChecks = this.similarCheckMatchingService.findSimilarTableChecks();
        Map<String, Collection<SimilarChecksGroup>> checksPerGroup = similarTableChecks.getChecksPerGroup();
        List<CheckCategoryDocumentationModel> resultList = buildDocumentationForChecks(checksPerGroup, TABLE_CATEGORY_HELP, tableSpec, CheckTarget.table);

        resultList.sort(Comparator.comparing(CheckCategoryDocumentationModel::getCategoryName));

        return resultList;
    }

    /**
     * Create a list of check documentation models for column level checks. Each category contains a list of similar checks to be documented on the same page.
     * @return Documentation for each check category on a column level.
     */
    @Override
    public List<CheckCategoryDocumentationModel> makeDocumentationForColumnChecks() {
        UserHomeImpl userHome = new UserHomeImpl(UserDomainIdentity.LOCAL_INSTANCE_ADMIN_IDENTITY);
        ConnectionWrapper connectionWrapper = userHome.getConnections().createAndAddNew("<target_connection>");
        TableWrapper tableWrapper = connectionWrapper.getTables().createAndAddNew(new PhysicalTableName("<target_schema>", "<target_table>"));
        TableSpec tableSpec = createTableSpec(true);
        tableWrapper.setSpec(tableSpec);

        SimilarChecksContainer similarTableChecks = this.similarCheckMatchingService.findSimilarColumnChecks();
        Map<String, Collection<SimilarChecksGroup>> checksPerGroup = similarTableChecks.getChecksPerGroup();
        List<CheckCategoryDocumentationModel> resultList = buildDocumentationForChecks(checksPerGroup, COLUMN_CATEGORY_HELP, tableSpec, CheckTarget.column);

        resultList.sort(Comparator.comparing(CheckCategoryDocumentationModel::getCategoryName));

        return resultList;
    }

    /**
     * Builds the documentation model for a given list of checks.
     * @param checksPerGroup  Dictionary of checks, grouped by a category.
     * @param categoryHelpMap Dictionary to find documentation for each category.
     * @param tableSpec Table specification that will be used to generate a YAML example.
     * @param target Target name for the check, it is one of "table" or "column".
     * @return List of category documentation models.
     */
    @NotNull
    public List<CheckCategoryDocumentationModel> buildDocumentationForChecks(
            Map<String, Collection<SimilarChecksGroup>> checksPerGroup,
            Map<String, String> categoryHelpMap,
            TableSpec tableSpec,
            CheckTarget target) {
        List<CheckCategoryDocumentationModel> resultList = new ArrayList<>();

        for (Map.Entry<String, Collection<SimilarChecksGroup>> similarChecksInGroup : checksPerGroup.entrySet()) {
            CheckCategoryDocumentationModel categoryDocumentationModel = new CheckCategoryDocumentationModel();
            String categoryName = similarChecksInGroup.getKey();
            categoryDocumentationModel.setTarget(target.name());
            categoryDocumentationModel.setCategoryName(categoryName);
            categoryDocumentationModel.setCategoryHelp(categoryHelpMap.get(categoryName));

            for (SimilarChecksGroup similarChecksGroup : similarChecksInGroup.getValue()) {
                SimilarChecksDocumentationModel similarChecksDocumentationModel = new SimilarChecksDocumentationModel();
                SimilarCheckModel firstCheckModel = similarChecksGroup.getSimilarChecks().get(0);
                similarChecksDocumentationModel.setCategory(firstCheckModel.getCategory()); // the category of the first check, the other similar checks should be in the same category
                similarChecksDocumentationModel.setTarget(target.name());
                String firstCheckName = firstCheckModel.getCheckModel().getCheckName();
                if (firstCheckName.startsWith("profile_")) {
                    firstCheckName = firstCheckName.substring("profile_".length());
                } else if ((firstCheckName.startsWith("daily_partition_"))) {
                    firstCheckName = firstCheckName.substring("daily_partition_".length());
                } else if ((firstCheckName.startsWith("monthly_partition_"))) {
                    firstCheckName = firstCheckName.substring("monthly_partition_".length());
                } else if ((firstCheckName.startsWith("daily_"))) {
                    firstCheckName = firstCheckName.substring("daily_".length());
                } else if ((firstCheckName.startsWith("monthly_"))) {
                    firstCheckName = firstCheckName.substring("monthly_".length());
                }

                similarChecksDocumentationModel.setPrimaryCheckName(firstCheckName.replace('_', ' '));

                ClassJavadoc checkClassJavadoc = RuntimeJavadoc.getJavadoc(firstCheckModel.getCheckModel().getCheckSpec().getClass());
                if (checkClassJavadoc != null) {
                    if (checkClassJavadoc.getComment() != null) {
                        String formattedClassComment = commentFormatter.format(checkClassJavadoc.getComment());
                        similarChecksDocumentationModel.setCheckSpecClassJavaDoc(formattedClassComment);
                    }
                }

                SensorDocumentationModel sensorDocumentation = this.sensorDocumentationModelFactory.createSensorDocumentation(
                        firstCheckModel.getCheckModel().getSensorParametersSpec());
                similarChecksDocumentationModel.setSensor(sensorDocumentation);
                similarChecksDocumentationModel.setRule(this.ruleDocumentationModelFactory.createRuleDocumentation(
                        firstCheckModel.getCheckModel().getRule().findFirstNotNullRule().getRuleParametersSpec()));

                for (SimilarCheckModel similarCheckModel : similarChecksGroup.getSimilarChecks()) {
                    CheckDocumentationModel checkDocumentationModel = buildCheckDocumentationModel(similarCheckModel, tableSpec, sensorDocumentation);
                    similarChecksDocumentationModel.getAllChecks().add(checkDocumentationModel);
                }

                categoryDocumentationModel.getCheckGroups().add(similarChecksDocumentationModel);
                Class<?> checkSpecClass = similarChecksDocumentationModel.getAllChecks().stream().findAny().get().getCheckModel().getCheckSpec().getClass();
                this.linkageStore.put(checkSpecClass, Path.of(
                        "/docs","checks",
                        target.name(), categoryName,
                        similarChecksDocumentationModel.getPrimaryCheckName().replace(' ', '-')
                ));
            }

            resultList.add(categoryDocumentationModel);
        }
        return resultList;
    }

    /**
     * Builds documentation for a single check.
     * @param similarCheckModel Similar check model.
     * @param tableSpec Table specification that will be used to generate a YAML example.
     * @param sensorDocumentation Sensor documentation, useful to render the SQL.
     * @return Documentation for a single check.
     */
    public CheckDocumentationModel buildCheckDocumentationModel(SimilarCheckModel similarCheckModel,
                                                                TableSpec tableSpec,
                                                                SensorDocumentationModel sensorDocumentation) {
        CheckDocumentationModel checkDocumentationModel = new CheckDocumentationModel();
        CheckModel checkModel = similarCheckModel.getCheckModel();
        checkDocumentationModel.setCheckName(checkModel.getCheckName());
        checkDocumentationModel.setCheckType(similarCheckModel.getCheckType().getDisplayName());
        checkDocumentationModel.setTimeScale(similarCheckModel.getTimeScale() != null ? similarCheckModel.getTimeScale().name() : null);
        checkDocumentationModel.setQualityDimension(similarCheckModel.getCheckModel().getQualityDimension());
        checkDocumentationModel.setCheckHelp(checkModel.getHelpText());
        checkDocumentationModel.setCheckModel(checkModel);
        String checkCategoryName = similarCheckModel.getCategory();
        checkDocumentationModel.setCategory(checkCategoryName);

        ClassJavadoc checkClassJavadoc = RuntimeJavadoc.getJavadoc(checkModel.getCheckSpec().getClass());
        if (checkClassJavadoc != null) {
            if (checkClassJavadoc.getComment() != null) {
                String formattedClassComment = commentFormatter.format(checkClassJavadoc.getComment());
                checkDocumentationModel.setCheckSpecClassJavaDoc(formattedClassComment);
            }
        }

        TableSpec trimmedTableSpec = tableSpec.trim();
        trimmedTableSpec.setGroupings(new DataGroupingConfigurationSpecMap());
        ColumnSpec columnSpec = trimmedTableSpec.getColumns().getAt(0);
        for (int i = 0; i < trimmedTableSpec.getColumns().size(); i++) {
            trimmedTableSpec.getColumns().getAt(i).setLabels(tableSpec.getColumns().getAt(i).getLabels().deepClone());
        }

        AbstractRootChecksContainerSpec checkRootContainer =
            (similarCheckModel.getCheckTarget() == CheckTarget.table) ?
                trimmedTableSpec.getTableCheckRootContainer(similarCheckModel.getCheckType(), similarCheckModel.getTimeScale(), true) :
                columnSpec.getColumnCheckRootContainer(similarCheckModel.getCheckType(), similarCheckModel.getTimeScale(), true);
        if (similarCheckModel.getCheckTarget() == CheckTarget.table) {
            trimmedTableSpec.setTableCheckRootContainer(checkRootContainer);
        }
        else {
            columnSpec.setColumnCheckRootContainer(checkRootContainer);
        }

        if (checkRootContainer.getCheckType() == CheckType.partitioned) {
            trimmedTableSpec.getColumns().put("date_column", createColumnWithLabel("date or datetime column used as a daily or monthly partitioning key, dates (and times) are truncated to a day or a month by the sensor's query for partitioned checks"));
            trimmedTableSpec.getTimestampColumns().setPartitionByColumn("date_column");
        }

        CheckContainerModel allChecksModel = new CheckContainerModel();
        QualityCategoryModel uiCategoryModel;
        TableComparisonConfigurationSpec tableComparisonConfigurationSpec = null;
        if (Objects.equals(checkCategoryName, AbstractComparisonCheckCategorySpecMap.COMPARISONS_CATEGORY_NAME)) {
            uiCategoryModel = new QualityCategoryModel(AbstractComparisonCheckCategorySpecMap.COMPARISONS_CATEGORY_NAME + "/" + COMPARISON_NAME);
            uiCategoryModel.setComparisonName(COMPARISON_NAME);

            tableComparisonConfigurationSpec = new TableComparisonConfigurationSpec();
            tableComparisonConfigurationSpec.setReferenceTableConnectionName("<source_of_truth_connection_name>");
            tableComparisonConfigurationSpec.setReferenceTableSchemaName("<source_of_truth_schema_name>");
            tableComparisonConfigurationSpec.setReferenceTableName("<source_of_truth_table_name>");

            trimmedTableSpec.setTableComparisons(new TableComparisonConfigurationSpecMap());
            trimmedTableSpec.getTableComparisons().put(COMPARISON_NAME, tableComparisonConfigurationSpec);
        } else {
            uiCategoryModel = new QualityCategoryModel(checkCategoryName);
        }
        CheckModel sampleCheckModel = checkModel.cloneForUpdate();
        sampleCheckModel.setConfigured(true);
        if (sampleCheckModel.getRule().getError() != null) {
            sampleCheckModel.getRule().getError().setConfigured(true);
        }
        if (sampleCheckModel.getRule().getWarning() != null) {
            sampleCheckModel.getRule().getWarning().setConfigured(true);
        }
        if (sampleCheckModel.getRule().getFatal() != null) {
            sampleCheckModel.getRule().getFatal().setConfigured(true);
        }
        sampleCheckModel.applySampleValues();
        uiCategoryModel.getChecks().add(sampleCheckModel);
        allChecksModel.getCategories().add(uiCategoryModel);
        this.modelToSpecCheckMappingService.updateCheckContainerSpec(allChecksModel, checkRootContainer, trimmedTableSpec);

        HierarchyNode checkCategoryContainer = checkRootContainer.getChild(checkCategoryName);
        if (checkCategoryContainer == null) {
            System.err.println("Sorry but check root container: " + checkRootContainer.getClass().getName() + " has no category " + checkCategoryName);
        }

        if (checkCategoryContainer instanceof AbstractComparisonCheckCategorySpecMap) {
            AbstractComparisonCheckCategorySpecMap<? extends AbstractComparisonCheckCategorySpec> comparisonCategoryMap =
                    (AbstractComparisonCheckCategorySpecMap<? extends AbstractComparisonCheckCategorySpec>)checkCategoryContainer;
            AbstractComparisonCheckCategorySpec comparisonCheckCategorySpec = comparisonCategoryMap.getAt(0);
            checkCategoryContainer = comparisonCheckCategorySpec;
            uiCategoryModel.setComparisonName(comparisonCheckCategorySpec.getComparisonName());

            if (comparisonCheckCategorySpec instanceof AbstractColumnComparisonCheckCategorySpec) {
                AbstractColumnComparisonCheckCategorySpec columnComparisonCheckCategorySpec =
                        (AbstractColumnComparisonCheckCategorySpec)comparisonCheckCategorySpec;
                columnComparisonCheckCategorySpec.setReferenceColumn("source_of_truth_column_name");
            }

            trimmedTableSpec.getColumns().put("country", createColumnWithLabel("column used as the first grouping key for calculating aggregated values used for the table comparison"));
            trimmedTableSpec.getColumns().put("state", createColumnWithLabel("column used as the first grouping key for calculating aggregated values used for the table comparison"));
            tableComparisonConfigurationSpec.getGroupingColumns().add(new TableComparisonGroupingColumnsPairSpec() {{
                setComparedTableColumnName("country");
                setReferenceTableColumnName("country_column_name_on_reference_table");
            }});
            tableComparisonConfigurationSpec.getGroupingColumns().add(new TableComparisonGroupingColumnsPairSpec() {{
                setComparedTableColumnName("state");
                setReferenceTableColumnName("state_column_name_on_reference_table");
            }});
        }

        AbstractCheckSpec<?, ?, ?, ?> checkSpec = (AbstractCheckSpec<?, ?, ?, ?>) checkCategoryContainer.getChild(checkModel.getCheckName());
        if (checkSpec == null) {
            System.err.println("Sorry but check category container: " + checkCategoryContainer.getClass().getName() + " has no check named " + checkModel.getCheckName());
        }

        TableYaml tableYaml = new TableYaml(trimmedTableSpec);
        String yamlSample = this.yamlSerializer.serialize(tableYaml);
        checkDocumentationModel.setSampleYaml(yamlSample);

        checkDocumentationModel.setCheckSample(createCheckSample(yamlSample, similarCheckModel, checkDocumentationModel));

        Comparator<CheckProviderRenderedSqlDocumentationModel> checkProviderRenderedSqlDocumentationModelComparator =
                Comparator.comparing(model -> model.getProviderTypeModel().getProviderTypeDisplayName().toLowerCase());

        List<CheckProviderRenderedSqlDocumentationModel> providerSamples = generateProviderSamples(trimmedTableSpec, checkSpec, checkRootContainer, sensorDocumentation);
        providerSamples.sort(checkProviderRenderedSqlDocumentationModelComparator);
        checkDocumentationModel.setProviderTemplates(providerSamples);

        if (tableComparisonConfigurationSpec == null && similarCheckModel.getCheckModel().isSupportsGrouping()) {
            trimmedTableSpec.getColumns().put("country", createColumnWithLabel("column used as the first grouping key"));
            trimmedTableSpec.getColumns().put("state", createColumnWithLabel("column used as the second grouping key"));
            DataGroupingConfigurationSpec groupingConfigurationSpec = new DataGroupingConfigurationSpec();
            groupingConfigurationSpec.setLevel1(DataGroupingDimensionSpec.createForColumn("country"));
            groupingConfigurationSpec.setLevel2(DataGroupingDimensionSpec.createForColumn("state"));
            String groupingName = "group_by_country_and_state";
            trimmedTableSpec.getGroupings().put(groupingName, groupingConfigurationSpec);
            trimmedTableSpec.setDefaultGroupingName(groupingName);

            TableYaml tableYamlWithDataGroupings = new TableYaml(trimmedTableSpec);
            String yamlSampleWithDataGroupings = this.yamlSerializer.serialize(tableYamlWithDataGroupings);
            checkDocumentationModel.setSampleYamlWithDataStreams(yamlSampleWithDataGroupings);
            checkDocumentationModel.setSplitSampleYamlWithDataStreams(splitStringByEndOfLine(yamlSampleWithDataGroupings));
            createMarksForDataGroupings(checkDocumentationModel, yamlSampleWithDataGroupings);

            List<CheckProviderRenderedSqlDocumentationModel> providerSamplesDataStream = generateProviderSamples(trimmedTableSpec, checkSpec, checkRootContainer, sensorDocumentation);
            providerSamplesDataStream.sort(checkProviderRenderedSqlDocumentationModelComparator);
            checkDocumentationModel.setProviderTemplatesDataStreams(providerSamplesDataStream);
        }


        // TODO: generate sample CLI commands
        // TODO: in the future, we can also show the generated JSON for the "run sensors" rest rest api job and cli command to enable this sensor

        return checkDocumentationModel;
    }

    /**
     * Divides string to list of string, looks for phrase, assign position of markers and extract
     * data quality check to separate list.
     * It's necessary for show in docs structure of data quality check and to highlight data quality
     * check in yaml sample in documentation.
     * @param yamlSample Yaml template.
     * @param similarCheckModel Similar check model.
     * @param checkDocumentationModel Check documentation model.
     * @return Data quality check sample.
     */
    private List<String> createCheckSample(String yamlSample, SimilarCheckModel similarCheckModel, CheckDocumentationModel checkDocumentationModel) {
        List<String> checkSample = new ArrayList<>();
        boolean isCheckSection = false;
        String profilingBeginMarker = "profiling_checks:";
        String monitoringBeginMarker = "monitoring_checks:";
        String partitionedBeginMarker = "partitioned_checks:";
        String checkEndMarker = "";
        if (similarCheckModel.getCheckTarget() == CheckTarget.table) {
            checkEndMarker = "  columns:";
        } else if (similarCheckModel.getCheckTarget() == CheckTarget.column) {
            checkEndMarker = "      labels:";
        }

        List<String> splitYaml = List.of(yamlSample.split("\\r?\\n|\\r"));
        for (int i = 0; i <= splitYaml.size(); i++) {
            if (splitYaml.get(i).contains(profilingBeginMarker) || splitYaml.get(i).contains(monitoringBeginMarker) || splitYaml.get(i).contains(partitionedBeginMarker)) {
                isCheckSection = true;
                checkDocumentationModel.setCheckSampleBeginLine(i + 1);
            }
            if (splitYaml.get(i).contains(checkEndMarker)) {
                isCheckSection = false;
                checkDocumentationModel.setCheckSampleEndLine(i);
                break;
            }
            if (isCheckSection) {
                checkSample.add(splitYaml.get(i));
            }
        }
        return checkSample;
    }

    /**
     * Divides string to list of string, looks for phrase and assign position of element.
     * It's necessary for highlight data stream in yaml sample in documentation.
     * @param checkDocumentationModel Check documentation model.
     * @param yamlSampleWithDataGroupings Yaml template.
     */
    private void createMarksForDataGroupings(CheckDocumentationModel checkDocumentationModel, String yamlSampleWithDataGroupings) {

        List<String> splitYaml = List.of(yamlSampleWithDataGroupings.split("\\r?\\n|\\r"));

        for (int i = 0; i <= splitYaml.size(); i++) {
            String currentLine = splitYaml.get(i);
            if (currentLine.contains("default_grouping_name:")) {
                int firstSectionBeginMarker = i + 1; // +1 because line in documentation is numerating from 1
                int firstSectionEndMarker = findFirstIndex(
                        indexOfLineWithText(splitYaml, "table_comparisons:"),
                        indexOfLineWithText(splitYaml, "_checks:"));

                checkDocumentationModel.setFirstSectionBeginMarker(firstSectionBeginMarker);
                checkDocumentationModel.setFirstSectionEndMarker(firstSectionEndMarker);
            } else if (currentLine.contains("country:")) {
                int secondSectionBeginMarker = i + 1; // +1 because line in documentation is numerating from 1
                int secondSectionEndMarker = secondSectionBeginMarker + 5; // +5 because first data stream section includes 5 lines

                checkDocumentationModel.setSecondSectionBeginMarker(secondSectionBeginMarker);
                checkDocumentationModel.setSecondSectionEndMarker(secondSectionEndMarker);

                break;
            }
        }
    }

    /**
     * Finds the smallest value that is not -1.
     * @param lineIndexes List of line indexes to find the minimum.
     * @return Index of the line.
     */
    private int findFirstIndex(int... lineIndexes) {
        int minIndex = -1;
        for (int i = 0; i < lineIndexes.length; i++) {
            int lineIndex = lineIndexes[i];
            if (lineIndex < 0) {
                continue;
            }

            if (minIndex < 0) {
                minIndex = lineIndex;
            } else if (minIndex > lineIndex) {
                minIndex = lineIndex;
            }
        }

        return minIndex;
    }

    /**
     * Finds the 0-based index of the first line that contains the given text or returns -1 when the text was not found.
     * @param lines List with lines.
     * @param text Text to find.
     * @return 0-based line index or -1 when not found.
     */
    private int indexOfLineWithText(List<String> lines, String text) {
        for (int i = 0; i < lines.size(); i++) {
            if (lines.get(i).contains(text)) {
                return i;
            }
        }

        return -1;
    }

    /**
     * Renders provider specific sensors for one check.
     * @param tableSpec Table specification.
     * @param checkSpec Check specification with filled sensor parameters that are using the sample data.
     * @param checkRootContainer Check container.
     * @param sensorDocumentation Sensor documentation to find the templates.
     * @return List of rendered SQLs for each supported provider.
     */
    protected List<CheckProviderRenderedSqlDocumentationModel> generateProviderSamples(
            TableSpec tableSpec, AbstractCheckSpec<?,?,?,?> checkSpec, AbstractRootChecksContainerSpec checkRootContainer, SensorDocumentationModel sensorDocumentation) {
        List<CheckProviderRenderedSqlDocumentationModel> results = new ArrayList<>();

        SensorDefinitionWrapper sensorDefinitionWrapper = sensorDocumentation.getDefinition();
        for (ProviderSensorDefinitionWrapper providerSensorDefinitionWrapper : sensorDefinitionWrapper.getProviderSensors()) {
            ProviderType providerType = providerSensorDefinitionWrapper.getProvider();

            CheckProviderRenderedSqlDocumentationModel providerDocModel = new CheckProviderRenderedSqlDocumentationModel();
            providerDocModel.setProviderTypeModel(ProviderTypeModel.fromProviderType(providerType));
            String sqlTemplate = providerSensorDefinitionWrapper.getSqlTemplate();
            if (sqlTemplate != null) {
                providerDocModel.setJinjaTemplate(sqlTemplate);
                providerDocModel.setListOfJinjaTemplate(splitStringByEndOfLine(sqlTemplate));

                SensorDefinitionFindResult sensorDefinitionFindResult = new SensorDefinitionFindResult(sensorDefinitionWrapper.getSpec(),
                        providerSensorDefinitionWrapper.getSpec(), sqlTemplate, providerSensorDefinitionWrapper.getSqlTemplateLastModified(),
                        providerType, HomeType.DQO_HOME, null);

                ConnectionSpec connectionSpec = new ConnectionSpec();
                connectionSpec.setBigquery(new BigQueryParametersSpec() {{
                    setSourceProjectId("your-google-project-id");
                }});
                connectionSpec.setSnowflake(new SnowflakeParametersSpec() {{
                    setDatabase("your_snowflake_database");
                }});
                connectionSpec.setPostgresql(new PostgresqlParametersSpec() {{
                    setDatabase("your_postgresql_database");
                }});
                connectionSpec.setRedshift(new RedshiftParametersSpec() {{
                    setDatabase("your_redshift_database");
                }});
                connectionSpec.setSqlserver(new SqlServerParametersSpec() {{
                    setDatabase("your_sql_server_database");
                }});
                connectionSpec.setMysql(new MysqlParametersSpec() {{
                    setDatabase("your_my_sql_database");
                }});
                connectionSpec.setOracle(new OracleParametersSpec() {{
                    setDatabase("your_oracle_database");
                }});
                connectionSpec.setSpark(new SparkParametersSpec() {{
                    setSchema("your_spark_schema");
                }});
                connectionSpec.setProviderType(providerType);

                ProviderDialectSettings providerDialectSettings = getProviderDialectSettings(providerType);

                EffectiveSensorRuleNames effectiveSensorRuleNames = new EffectiveSensorRuleNames(
                        sensorDocumentation.getSensorName(), null);
                SensorExecutionRunParameters sensorRunParameters = new SensorExecutionRunParameters(
                        connectionSpec,
                        tableSpec,
                        tableSpec.getColumns().getAt(0),
                        checkSpec,
                        null,
                        effectiveSensorRuleNames,
                        checkRootContainer.getCheckType(),
                        checkRootContainer.getTimeSeriesConfiguration(tableSpec),
                        null,
                        tableSpec.getDefaultDataGroupingConfiguration(),
                        null,
                        null,
                        checkSpec.getParameters(),
                        providerDialectSettings,
                        new CheckSearchFilters(),
                        1000,
                        true
                );

                JinjaTemplateRenderParameters templateRenderParameters = JinjaTemplateRenderParameters.createFromTrimmedObjects(
                        sensorRunParameters, sensorDefinitionFindResult);
                try {
                    String renderedTemplate = this.jinjaTemplateRenderService.renderTemplate(sqlTemplate, templateRenderParameters);
                    providerDocModel.setRenderedTemplate(renderedTemplate);
                    providerDocModel.setListOfRenderedTemplate(splitStringByEndOfLine(renderedTemplate));

                    results.add(providerDocModel);
                }
                catch (Exception ex) {
                    System.err.println("Failed to render a sample SQL for check " + checkSpec.getCheckName() + " for provider: " + providerType + ", error: " + ex.getMessage());
                }
            }
        }

        return results;
    }


    /**
     * Creates a provider specific default provider settings that describe how the provider renders quoted identifiers or other objects.
     * @param providerType Provider type.
     * @return Provider dialect settings.
     */
    protected ProviderDialectSettings getProviderDialectSettings(ProviderType providerType) {
        switch (providerType) {
            case bigquery:
                return new BigQueryProviderDialectSettings();
            case snowflake:
                return new SnowflakeProviderDialectSettings();
            case postgresql:
                return new PostgresqlProviderDialectSettings();
            case redshift:
                return new RedshiftProviderDialectSettings();
            case sqlserver:
                return new SqlServerProviderDialectSettings();
            case presto:
                return new PrestoProviderDialectSettings();
            case trino:
                return new TrinoProviderDialectSettings();
            case mysql:
                return new MysqlProviderDialectSettings();
            case oracle:
                return new OracleProviderDialectSettings();
            case spark:
                return new SparkProviderDialectSettings();
            default:
                throw new DqoRuntimeException("Missing configuration of the dialect settings for the provider " + providerType + ", please add it here");
        }
    }

    /**
     * Create list of split string templates by end of line.
     * @param template Template.
     * @return List of split string templates by end of line.
     */
    private List<String> splitStringByEndOfLine(String template) {
        return List.of(template.split("\\r?\\n|\\r"));
    }
}
