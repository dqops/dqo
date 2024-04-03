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

package com.dqops.rest.models.comparison;

import com.dqops.BaseTest;
import com.dqops.checks.CheckType;
import com.dqops.checks.column.checkspecs.comparison.*;
import com.dqops.checks.column.profiling.ColumnComparisonProfilingChecksSpec;
import com.dqops.checks.column.profiling.ColumnProfilingCheckCategoriesSpec;
import com.dqops.checks.table.checkspecs.comparison.TableComparisonRowCountMatchCheckSpec;
import com.dqops.checks.table.profiling.TableComparisonProfilingChecksSpec;
import com.dqops.metadata.comparisons.TableComparisonConfigurationSpec;
import com.dqops.metadata.comparisons.TableComparisonGroupingColumnsPairSpec;
import com.dqops.metadata.groupings.DataGroupingConfigurationSpec;
import com.dqops.metadata.id.HierarchyId;
import com.dqops.metadata.sources.ColumnSpec;
import com.dqops.metadata.sources.PhysicalTableName;
import com.dqops.metadata.sources.TableSpec;
import com.dqops.rules.comparison.MaxDiffPercentRule0ParametersSpec;
import com.dqops.rules.comparison.MaxDiffPercentRule1ParametersSpec;
import com.dqops.rules.comparison.MaxDiffPercentRule5ParametersSpec;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TableComparisonModelTests extends BaseTest {
    private TableComparisonModel sut;
    private TableSpec comparedTableSpec;
    private TableComparisonConfigurationSpec tableComparisonConfigurationSpec;
    private TableSpec referencedTableSpec;

    @BeforeEach
    void setUp() {
        this.sut = new TableComparisonModel();
        this.comparedTableSpec = new TableSpec();
        this.comparedTableSpec.setHierarchyId(HierarchyId.makeHierarchyIdForTable("conn1",
                new PhysicalTableName("schema1", "comp_table")));
        this.referencedTableSpec = new TableSpec();
        this.referencedTableSpec.setHierarchyId(HierarchyId.makeHierarchyIdForTable("refconn",
                new PhysicalTableName("refsch", "reftab")));
        this.comparedTableSpec.getColumns().put("col1", new ColumnSpec());
        this.comparedTableSpec.getColumns().put("col2", new ColumnSpec());
        this.comparedTableSpec.getGroupings().put("mygrouping", new DataGroupingConfigurationSpec());
        this.tableComparisonConfigurationSpec = new TableComparisonConfigurationSpec();
        this.tableComparisonConfigurationSpec.setReferenceTableConnectionName("refconn");
        this.tableComparisonConfigurationSpec.setReferenceTableSchemaName("refsch");
        this.tableComparisonConfigurationSpec.setReferenceTableName("reftab");
        this.comparedTableSpec.getTableComparisons().put("reftable", tableComparisonConfigurationSpec);
        this.sut.setTableComparisonConfigurationName("reftable");
    }

    @Test
    void fromTableSpec_whenTableGivenAndProfilingChecksEmpty_thenCreatesModel() {
        this.tableComparisonConfigurationSpec.getGroupingColumns().add(new TableComparisonGroupingColumnsPairSpec("col1", "ref_col1"));
        TableComparisonModel sut = TableComparisonModel.fromTableSpec(this.comparedTableSpec, this.referencedTableSpec,
                "reftable", CheckType.profiling, null, true);
        Assertions.assertEquals(2, sut.getColumns().size());
        Assertions.assertNotNull(sut.getDefaultCompareThresholds());
        Assertions.assertEquals("reftable", sut.getTableComparisonConfigurationName());
        Assertions.assertNull(sut.getCompareRowCount());
        Assertions.assertEquals(1, sut.getGroupingColumns().size());
        Assertions.assertEquals("col1", sut.getGroupingColumns().get(0).getComparedTableColumnName());
        Assertions.assertEquals("ref_col1", sut.getGroupingColumns().get(0).getReferenceTableColumnName());
    }

    @Test
    void fromTableSpec_whenTableGivenAndHasRowCountCheckConfigured_thenReturnsThresholdsForRowCount() {
        TableComparisonProfilingChecksSpec tableComparisons = new TableComparisonProfilingChecksSpec();
        this.comparedTableSpec.getProfilingChecks().getComparisons().put("reftable", tableComparisons);
        TableComparisonRowCountMatchCheckSpec rowCountMatch = new TableComparisonRowCountMatchCheckSpec();
        tableComparisons.setProfileRowCountMatch(rowCountMatch);
        rowCountMatch.setWarning(new MaxDiffPercentRule0ParametersSpec(2.5));
        rowCountMatch.setError(new MaxDiffPercentRule1ParametersSpec(3.5));
        rowCountMatch.setFatal(new MaxDiffPercentRule5ParametersSpec(4.5));

        TableComparisonModel sut = TableComparisonModel.fromTableSpec(this.comparedTableSpec, this.referencedTableSpec,
                "reftable", CheckType.profiling, null, true);
        Assertions.assertNotNull(sut.getCompareRowCount());
        Assertions.assertEquals(2.5, sut.getCompareRowCount().getWarningDifferencePercent());
        Assertions.assertEquals(3.5, sut.getCompareRowCount().getErrorDifferencePercent());
        Assertions.assertEquals(4.5, sut.getCompareRowCount().getFatalDifferencePercent());
    }

    @Test
    void fromTableSpec_whenMatchingColumnInReferencedTableNotFound_thenReferenceColumnNameNull() {
        this.referencedTableSpec.getColumns().put("somethingelse", new ColumnSpec());

        TableComparisonModel sut = TableComparisonModel.fromTableSpec(this.comparedTableSpec, this.referencedTableSpec,
                "reftable", CheckType.profiling, null, true);

        ColumnComparisonModel columnComparisonModel = sut.getColumns().get(0);
        Assertions.assertEquals("col1", columnComparisonModel.getComparedColumnName());
        Assertions.assertEquals(null, columnComparisonModel.getReferenceColumnName());
    }

    @Test
    void fromTableSpec_whenColumnInReferencedTableFoundWithFullCase_thenReturnsItAsSuggestedMatch() {
        this.referencedTableSpec.getColumns().put("col1", new ColumnSpec());

        TableComparisonModel sut = TableComparisonModel.fromTableSpec(this.comparedTableSpec, this.referencedTableSpec,
                "reftable", CheckType.profiling, null, true);

        ColumnComparisonModel columnComparisonModel = sut.getColumns().get(0);
        Assertions.assertEquals("col1", columnComparisonModel.getComparedColumnName());
        Assertions.assertEquals("col1", columnComparisonModel.getReferenceColumnName());
    }

    @Test
    void fromTableSpec_whenColumnInReferencedTableFoundButDifferentCase_thenReturnsItAsSuggestedMatch() {
        this.referencedTableSpec.getColumns().put("COL1", new ColumnSpec());

        TableComparisonModel sut = TableComparisonModel.fromTableSpec(this.comparedTableSpec, this.referencedTableSpec,
                "reftable", CheckType.profiling, null, true);

        ColumnComparisonModel columnComparisonModel = sut.getColumns().get(0);
        Assertions.assertEquals("col1", columnComparisonModel.getComparedColumnName());
        Assertions.assertEquals("COL1", columnComparisonModel.getReferenceColumnName());
    }

    @Test
    void fromTableSpec_whenColumnInReferencedTableNotFoundExactlyButSimilarColumnFound_thenReturnsItAsSuggestedMatch() {
        this.referencedTableSpec.getColumns().put("COL1_fk", new ColumnSpec());

        TableComparisonModel sut = TableComparisonModel.fromTableSpec(this.comparedTableSpec, this.referencedTableSpec,
                "reftable", CheckType.profiling, null, true);

        ColumnComparisonModel columnComparisonModel = sut.getColumns().get(0);
        Assertions.assertEquals("col1", columnComparisonModel.getComparedColumnName());
        Assertions.assertEquals("COL1_fk", columnComparisonModel.getReferenceColumnName());
    }

    @Test
    void fromTableSpec_whenTableGivenWithColumn_thenReturnsThresholdsForColumn() {
        TableComparisonProfilingChecksSpec tableComparisons = new TableComparisonProfilingChecksSpec();
        this.comparedTableSpec.getProfilingChecks().getComparisons().put("reftable", tableComparisons);
        TableComparisonRowCountMatchCheckSpec rowCountMatch = new TableComparisonRowCountMatchCheckSpec();
        tableComparisons.setProfileRowCountMatch(rowCountMatch);
        rowCountMatch.setWarning(new MaxDiffPercentRule0ParametersSpec(2.5));
        rowCountMatch.setError(new MaxDiffPercentRule1ParametersSpec(3.5));
        rowCountMatch.setFatal(new MaxDiffPercentRule5ParametersSpec(4.5));

        ColumnSpec columnSpec = this.comparedTableSpec.getColumns().get("col1");
        columnSpec.setProfilingChecks(new ColumnProfilingCheckCategoriesSpec());
        ColumnComparisonProfilingChecksSpec columnComparison = new ColumnComparisonProfilingChecksSpec();
        columnComparison.setReferenceColumn("refcol1");
        columnComparison.setProfileMinMatch(new ColumnComparisonMinMatchCheckSpec() {{
            setWarning(new MaxDiffPercentRule0ParametersSpec(1.5));
        }});
        columnComparison.setProfileMaxMatch(new ColumnComparisonMaxMatchCheckSpec() {{
            setError(new MaxDiffPercentRule1ParametersSpec(2.5));
        }});
        columnComparison.setProfileSumMatch(new ColumnComparisonSumMatchCheckSpec() {{
            setFatal(new MaxDiffPercentRule5ParametersSpec(3.5));
        }});
        columnComparison.setProfileMeanMatch(new ColumnComparisonMeanMatchCheckSpec() {{
            setWarning(new MaxDiffPercentRule0ParametersSpec(4.5));
        }});
        columnComparison.setProfileNullCountMatch(new ColumnComparisonNullCountMatchCheckSpec() {{
            setError(new MaxDiffPercentRule1ParametersSpec(5.5));
        }});
        columnComparison.setProfileNotNullCountMatch(new ColumnComparisonNotNullCountMatchCheckSpec() {{
            setFatal(new MaxDiffPercentRule5ParametersSpec(6.5));
        }});
        columnSpec.getProfilingChecks().getComparisons().put("reftable", columnComparison);

        TableComparisonModel sut = TableComparisonModel.fromTableSpec(this.comparedTableSpec, this.referencedTableSpec,
                "reftable", CheckType.profiling, null, true);
        ColumnComparisonModel columnComparisonModel = sut.getColumns().get(0);
        Assertions.assertEquals("col1", columnComparisonModel.getComparedColumnName());
        Assertions.assertEquals("refcol1", columnComparisonModel.getReferenceColumnName());
        Assertions.assertEquals(1.5, columnComparisonModel.getCompareMin().getWarningDifferencePercent());
        Assertions.assertEquals(2.5, columnComparisonModel.getCompareMax().getErrorDifferencePercent());
        Assertions.assertEquals(3.5, columnComparisonModel.getCompareSum().getFatalDifferencePercent());
        Assertions.assertEquals(4.5, columnComparisonModel.getCompareMean().getWarningDifferencePercent());
        Assertions.assertEquals(5.5, columnComparisonModel.getCompareNullCount().getErrorDifferencePercent());
        Assertions.assertEquals(6.5, columnComparisonModel.getCompareNotNullCount().getFatalDifferencePercent());
    }

    @Test
    void copyToTableSpec_whenRowCountConfiguredAndNotConfiguredInTable_thenCreatesCheckConfiguration() {
        this.sut.setCompareRowCount(new CompareThresholdsModel(1.5, 2.5, 3.5));

        this.sut.copyToTableSpec(this.comparedTableSpec, "reftable", CheckType.profiling, null);

        TableComparisonProfilingChecksSpec tableComparisonChecks = this.comparedTableSpec.getProfilingChecks().getComparisons().get("reftable");
        Assertions.assertNotNull(tableComparisonChecks);
        Assertions.assertNotNull(tableComparisonChecks.getProfileRowCountMatch());
        Assertions.assertEquals(1.5, tableComparisonChecks.getProfileRowCountMatch().getWarning().getMaxDiffPercent());
        Assertions.assertEquals(2.5, tableComparisonChecks.getProfileRowCountMatch().getError().getMaxDiffPercent());
        Assertions.assertEquals(3.5, tableComparisonChecks.getProfileRowCountMatch().getFatal().getMaxDiffPercent());
    }

    @Test
    void copyToTableSpec_whenRowCountNotConfiguredInModelButWasConfiguredAsCheck_thenRemovesCheck() {
        TableComparisonProfilingChecksSpec tableComparisons = new TableComparisonProfilingChecksSpec();
        this.comparedTableSpec.getProfilingChecks().getComparisons().put("reftable", tableComparisons);
        TableComparisonRowCountMatchCheckSpec rowCountMatch = new TableComparisonRowCountMatchCheckSpec();
        tableComparisons.setProfileRowCountMatch(rowCountMatch);
        rowCountMatch.setWarning(new MaxDiffPercentRule0ParametersSpec(2.5));

        this.sut.copyToTableSpec(this.comparedTableSpec, "reftable", CheckType.profiling, null);

        TableComparisonProfilingChecksSpec tableComparisonChecks = this.comparedTableSpec.getProfilingChecks().getComparisons().get("reftable");
        Assertions.assertNotNull(tableComparisonChecks);
        Assertions.assertNull(tableComparisonChecks.getProfileRowCountMatch());
    }

    @Test
    void copyToTableSpec_whenRowCountConfiguredConfiguredWarningInModelButCheckWasConfiguredAsError_thenReconfiguresRules() {
        TableComparisonProfilingChecksSpec tableComparisons = new TableComparisonProfilingChecksSpec();
        this.comparedTableSpec.getProfilingChecks().getComparisons().put("reftable", tableComparisons);
        TableComparisonRowCountMatchCheckSpec rowCountMatch = new TableComparisonRowCountMatchCheckSpec();
        tableComparisons.setProfileRowCountMatch(rowCountMatch);
        rowCountMatch.setWarning(new MaxDiffPercentRule0ParametersSpec(2.5));

        this.sut.setCompareRowCount(new CompareThresholdsModel(null, 2.5, null));
        this.sut.copyToTableSpec(this.comparedTableSpec, "reftable", CheckType.profiling, null);

        TableComparisonProfilingChecksSpec tableComparisonChecks = this.comparedTableSpec.getProfilingChecks().getComparisons().get("reftable");
        Assertions.assertNotNull(tableComparisonChecks);
        Assertions.assertNotNull(tableComparisonChecks.getProfileRowCountMatch());
        Assertions.assertNull(tableComparisonChecks.getProfileRowCountMatch().getWarning());
        Assertions.assertEquals(2.5, tableComparisonChecks.getProfileRowCountMatch().getError().getMaxDiffPercent());
        Assertions.assertNull(tableComparisonChecks.getProfileRowCountMatch().getFatal());
    }

    @Test
    void copyToTableSpec_whenMinConfiguredInModelButNotInTableChecks_thenConfiguresCheck() {
        ColumnComparisonModel columnComparisonModel = new ColumnComparisonModel() {{
            setComparedColumnName("col1");
            setReferenceColumnName("refcol111");
            setCompareMin(new CompareThresholdsModel(1.5, null, null));
            setCompareMax(new CompareThresholdsModel(2.5, null, null));
            setCompareSum(new CompareThresholdsModel(3.5, null, null));
            setCompareMean(new CompareThresholdsModel(4.5, null, null));
            setCompareNullCount(new CompareThresholdsModel(5.5, null, null));
            setCompareNotNullCount(new CompareThresholdsModel(6.5, null, null));
        }};
        this.sut.getColumns().add(columnComparisonModel);
        this.sut.copyToTableSpec(this.comparedTableSpec, "reftable", CheckType.profiling, null);

        ColumnSpec columnSpec = this.comparedTableSpec.getColumns().get("col1");
        ColumnProfilingCheckCategoriesSpec profilingChecks = columnSpec.getProfilingChecks();
        ColumnComparisonProfilingChecksSpec columnComparison = profilingChecks.getComparisons().get("reftable");
        Assertions.assertNotNull(columnComparison);
        Assertions.assertEquals("refcol111", columnComparison.getReferenceColumn());
        Assertions.assertNotNull(columnComparison.getProfileMinMatch());
        Assertions.assertEquals(1.5, columnComparison.getProfileMinMatch().getWarning().getMaxDiffPercent());
        Assertions.assertEquals(2.5, columnComparison.getProfileMaxMatch().getWarning().getMaxDiffPercent());
        Assertions.assertEquals(3.5, columnComparison.getProfileSumMatch().getWarning().getMaxDiffPercent());
        Assertions.assertEquals(4.5, columnComparison.getProfileMeanMatch().getWarning().getMaxDiffPercent());
        Assertions.assertEquals(5.5, columnComparison.getProfileNullCountMatch().getWarning().getMaxDiffPercent());
        Assertions.assertEquals(6.5, columnComparison.getProfileNotNullCountMatch().getWarning().getMaxDiffPercent());
    }
}
