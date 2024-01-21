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
package com.dqops.execution.sensors.finder;

import com.dqops.BaseTest;
import com.dqops.connectors.ProviderType;
import com.dqops.execution.ExecutionContext;
import com.dqops.metadata.definitions.sensors.ProviderSensorDefinitionWrapper;
import com.dqops.metadata.definitions.sensors.SensorDefinitionWrapper;
import com.dqops.metadata.storage.localfiles.HomeType;
import com.dqops.metadata.storage.localfiles.dqohome.DqoHomeContext;
import com.dqops.metadata.storage.localfiles.dqohome.DqoHomeContextObjectMother;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContext;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContextObjectMother;
import com.dqops.metadata.userhome.UserHome;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SensorDefinitionFindServiceImplTests extends BaseTest {
    private SensorDefinitionFindService sut;

    @BeforeEach
    void setUp() {
		this.sut = new SensorDefinitionFindServiceImpl();
    }

    @Test
    void findProviderSensorDefinition_whenDefinitionPresentInDqoHome_thenReturnsDefinition() {
        UserHomeContext inMemoryFileHomeContext = UserHomeContextObjectMother.createInMemoryFileHomeContext();
        DqoHomeContext dqoHomeContext = DqoHomeContextObjectMother.getRealDqoHomeContext();
        ExecutionContext executionContext = new ExecutionContext(inMemoryFileHomeContext, dqoHomeContext);

        SensorDefinitionFindResult result = this.sut.findProviderSensorDefinition(executionContext, "table/volume/row_count", ProviderType.bigquery);

        Assertions.assertNotNull(result);
        Assertions.assertNotNull(result.getSensorDefinitionSpec());
        Assertions.assertNotNull(result.getProviderSensorDefinitionSpec());
        Assertions.assertNull(result.getSqlTemplateText());
        Assertions.assertNotNull(result.getTemplateFilePath());
        Assertions.assertEquals("[]/table/volume/row_count/bigquery.sql.jinja2", result.getTemplateFilePath().toString());
        Assertions.assertEquals(HomeType.DQO_HOME, result.getHome());
    }

    @Test
    void findProviderSensorDefinition_whenUnknownSensor_thenReturnsNull() {
        UserHomeContext inMemoryFileHomeContext = UserHomeContextObjectMother.createInMemoryFileHomeContext();
        DqoHomeContext dqoHomeContext = DqoHomeContextObjectMother.getRealDqoHomeContext();
        ExecutionContext executionContext = new ExecutionContext(inMemoryFileHomeContext, dqoHomeContext);

        SensorDefinitionFindResult result = this.sut.findProviderSensorDefinition(executionContext, "table/category/missingsensor", ProviderType.bigquery);

        Assertions.assertNull(result);
    }

    @Test
    void findProviderSensorDefinition_whenSensorRedefinedInUserHome_thenReturnsDefinitionFromUserHome() {
        UserHomeContext inMemoryFileHomeContext = UserHomeContextObjectMother.createInMemoryFileHomeContext();
        DqoHomeContext dqoHomeContext = DqoHomeContextObjectMother.getRealDqoHomeContext();
        ExecutionContext executionContext = new ExecutionContext(inMemoryFileHomeContext, dqoHomeContext);
        UserHome userHome = inMemoryFileHomeContext.getUserHome();
        final String sensorName = "table/volume/row_count";
        SensorDefinitionWrapper userSensorDef = userHome.getSensors().createAndAddNew(sensorName);
        ProviderSensorDefinitionWrapper userProviderSensor = userSensorDef.getProviderSensors().createAndAddNew(ProviderType.bigquery);
        Assertions.assertNotNull(userSensorDef.getSpec());
        Assertions.assertNotNull(userProviderSensor.getSpec());
        userProviderSensor.setSqlTemplate("select 1 from tab");

        SensorDefinitionFindResult result = this.sut.findProviderSensorDefinition(executionContext, sensorName, ProviderType.bigquery);

        Assertions.assertNotNull(result);
        Assertions.assertNotNull(result.getSensorDefinitionSpec());
        Assertions.assertNotNull(result.getProviderSensorDefinitionSpec());
        Assertions.assertSame(userSensorDef.getSpec(), result.getSensorDefinitionSpec());
        Assertions.assertSame(userProviderSensor.getSpec(), result.getProviderSensorDefinitionSpec());
        Assertions.assertEquals("select 1 from tab", result.getSqlTemplateText());
        Assertions.assertEquals(ProviderType.bigquery, result.getProviderType());
    }

    @Test
    void findProviderSensorDefinition_whenSensorRedefinedInUserHomeButMissingRequestedProviderImplementation_thenReturnsDefinitionFromDqoHome() {
        UserHomeContext inMemoryFileHomeContext = UserHomeContextObjectMother.createInMemoryFileHomeContext();
        DqoHomeContext dqoHomeContext = DqoHomeContextObjectMother.getRealDqoHomeContext();
        ExecutionContext executionContext = new ExecutionContext(inMemoryFileHomeContext, dqoHomeContext);
        UserHome userHome = inMemoryFileHomeContext.getUserHome();
        final String sensorName = "table/volume/row_count";
        SensorDefinitionWrapper userSensorDef = userHome.getSensors().createAndAddNew(sensorName);
        Assertions.assertNotNull(userSensorDef.getSpec());

        SensorDefinitionFindResult result = this.sut.findProviderSensorDefinition(executionContext, sensorName, ProviderType.bigquery);

        Assertions.assertNotNull(result);
        Assertions.assertNotNull(result.getSensorDefinitionSpec());
        Assertions.assertNotNull(result.getProviderSensorDefinitionSpec());
        Assertions.assertNotSame(userSensorDef.getSpec(), result.getSensorDefinitionSpec());
        Assertions.assertNull(result.getSqlTemplateText());
        Assertions.assertEquals(ProviderType.bigquery, result.getProviderType());
        Assertions.assertEquals("[]/table/volume/row_count/bigquery.sql.jinja2", result.getTemplateFilePath().toString());
        Assertions.assertEquals(HomeType.DQO_HOME, result.getHome());
    }
}
