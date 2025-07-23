/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.execution.sqltemplates.rendering;

import com.dqops.BaseTest;
import com.dqops.connectors.ProviderDialectSettingsObjectMother;
import com.dqops.connectors.ProviderType;
import com.dqops.core.configuration.DqoConfigurationProperties;
import com.dqops.core.configuration.DqoConfigurationPropertiesObjectMother;
import com.dqops.core.configuration.DqoPythonConfigurationProperties;
import com.dqops.core.configuration.DqoPythonConfigurationPropertiesObjectMother;
import com.dqops.core.jobqueue.concurrency.ParallelJobLimitProviderStub;
import com.dqops.data.readouts.factory.SensorReadoutsColumnNames;
import com.dqops.execution.sensors.TimeWindowFilterParameters;
import com.dqops.metadata.definitions.sensors.ProviderSensorDefinitionSpec;
import com.dqops.metadata.definitions.sensors.SensorDefinitionSpec;
import com.dqops.metadata.groupings.DataGroupingConfigurationSpec;
import com.dqops.metadata.timeseries.TimeSeriesConfigurationSpec;
import com.dqops.metadata.sources.ColumnSpec;
import com.dqops.metadata.sources.ConnectionSpec;
import com.dqops.metadata.sources.PhysicalTableName;
import com.dqops.metadata.sources.TableSpec;
import com.dqops.sensors.table.volume.TableVolumeRowCountSensorParametersSpec;
import com.dqops.utils.python.PythonCallerServiceImpl;
import com.dqops.utils.python.PythonVirtualEnvService;
import com.dqops.utils.python.PythonVirtualEnvServiceObjectMother;
import com.dqops.utils.serialization.JsonSerializerImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.LinkedHashSet;

@SpringBootTest
public class JinjaTemplateRenderServiceImplTests extends BaseTest {
    private JinjaTemplateRenderServiceImpl sut;
    private JinjaTemplateRenderParameters renderParameters;
    private TableSpec table;

    @BeforeEach
    void setUp() {
        DqoConfigurationProperties dqoConfigurationProperties = DqoConfigurationPropertiesObjectMother.getDefaultCloned();
        DqoPythonConfigurationProperties pythonConfigurationProperties = DqoPythonConfigurationPropertiesObjectMother.getDefaultCloned();
        PythonVirtualEnvService pythonVirtualEnvService = PythonVirtualEnvServiceObjectMother.getDefault();
        PythonCallerServiceImpl pythonCallerService = new PythonCallerServiceImpl(
                dqoConfigurationProperties, pythonConfigurationProperties, new JsonSerializerImpl(), pythonVirtualEnvService, new ParallelJobLimitProviderStub(8));
		this.sut = new JinjaTemplateRenderServiceImpl(pythonCallerService, pythonConfigurationProperties);
        table = new TableSpec();
        table.setPhysicalTableName(new PhysicalTableName("schema1", "table1"));
        this.renderParameters = new JinjaTemplateRenderParameters(
                new ConnectionSpec(),
                table,
                new ColumnSpec(),
                null,
                new TableVolumeRowCountSensorParametersSpec(),
                TimeSeriesConfigurationSpec.createCurrentTimeMilliseconds(),
                new TimeWindowFilterParameters(),
                new DataGroupingConfigurationSpec(),
                new SensorDefinitionSpec(),
                new ProviderSensorDefinitionSpec(),
                ProviderDialectSettingsObjectMother.getDialectForProvider(ProviderType.bigquery),
                SensorReadoutsColumnNames.ACTUAL_VALUE_COLUMN_NAME,
                SensorReadoutsColumnNames.EXPECTED_VALUE_COLUMN_NAME,
                new LinkedHashSet<>(),
                null
        );
    }

    @Test
    void renderTemplate_whenFakeTemplateWithoutParameters_thenRendersTemplate() {
        String result = this.sut.renderTemplate("select 1", this.renderParameters);
        Assertions.assertEquals("select 1", result);
    }

    @Test
    void renderTemplate_whenSensorReferencingTable_thenRendersTemplateWithFilledField() {
        String result = this.sut.renderTemplate("select 1 from {{ target_table.table_name }}", this.renderParameters);
        Assertions.assertEquals("select 1 from table1", result);
    }
}
