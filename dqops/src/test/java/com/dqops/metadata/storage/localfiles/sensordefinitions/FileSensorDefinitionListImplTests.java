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
package com.dqops.metadata.storage.localfiles.sensordefinitions;

import com.dqops.BaseTest;
import com.dqops.metadata.basespecs.InstanceStatus;
import com.dqops.metadata.definitions.sensors.SensorDefinitionList;
import com.dqops.metadata.definitions.sensors.SensorDefinitionSpec;
import com.dqops.metadata.definitions.sensors.SensorDefinitionWrapper;
import com.dqops.metadata.definitions.sensors.SensorDefinitionWrapperImpl;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContext;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContextObjectMother;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Iterator;
import java.util.List;

@SpringBootTest
public class FileSensorDefinitionListImplTests extends BaseTest {
    private FileSensorDefinitionListImpl sut;
    private UserHomeContext homeContext;

    @BeforeEach
    void setUp() {
		homeContext = UserHomeContextObjectMother.createTemporaryFileHomeContext(true);
		this.sut = (FileSensorDefinitionListImpl) homeContext.getUserHome().getSensors();
    }

    @Test
    void createNewElement_whenCalled_createsElementWithEmptyModel() {
        SensorDefinitionWrapperImpl wrapper = this.sut.createNewElement("test");
        Assertions.assertEquals("test", wrapper.getName());
        Assertions.assertEquals(InstanceStatus.ADDED, wrapper.getStatus());
        Assertions.assertNotNull(wrapper.getSpec());
    }

    @Test
    void toList_whenCalled_returnList() {
        SensorDefinitionWrapper wrapper = this.sut.createAndAddNew("test");
        Assertions.assertEquals(1, this.sut.size());
        List<SensorDefinitionWrapper> list = this.sut.toList();
        Assertions.assertNotNull(list);
        Assertions.assertEquals(1, list.size());
        Assertions.assertSame(wrapper, list.get(0));
    }

    @Test
    void createAndAddNew_whenNewSourceAddedAndFlushed_thenIsSaved() {
        SensorDefinitionWrapper wrapper = this.sut.createAndAddNew("src1");
        SensorDefinitionSpec model = wrapper.getSpec();
		homeContext.flush();

        UserHomeContext homeContext2 = UserHomeContextObjectMother.createTemporaryFileHomeContext(false);
        SensorDefinitionList definitionList2 = homeContext2.getUserHome().getSensors();
        SensorDefinitionWrapper wrapper2 = definitionList2.getByObjectName("src1", true);
        Assertions.assertNotEquals(null, wrapper2);
    }

    @Test
    void flush_whenExistingSourceLoadedModifiedAndFlushed_thenIsSaved() {
		this.sut.createAndAddNew("src1");
		homeContext.flush();

        SensorDefinitionSpec spec2 = new SensorDefinitionSpec();

        UserHomeContext homeContext2 = UserHomeContextObjectMother.createTemporaryFileHomeContext(false);
        SensorDefinitionList definitionList2 = homeContext2.getUserHome().getSensors();
        SensorDefinitionWrapper wrapper2 = definitionList2.getByObjectName("src1", true);
        wrapper2.setSpec(spec2);
        homeContext2.flush();

        UserHomeContext homeContext3 = UserHomeContextObjectMother.createTemporaryFileHomeContext(false);
        SensorDefinitionList list3 = homeContext3.getUserHome().getSensors();
        SensorDefinitionWrapper wrapper3 = list3.getByObjectName("src1", true);
        Assertions.assertEquals(false, wrapper3.getSpec().isDirty());
    }

    @Test
    void iterator_whenDefinitionAdded_thenReturnsDefinition() {
		this.sut.createAndAddNew("src3");
		homeContext.flush();

        UserHomeContext homeContext2 = UserHomeContextObjectMother.createTemporaryFileHomeContext(false);
        SensorDefinitionList sut2 = homeContext2.getUserHome().getSensors();
        Iterator<SensorDefinitionWrapper> iterator = sut2.iterator();
        Assertions.assertTrue(iterator.hasNext());
        SensorDefinitionWrapper wrapperLoaded = iterator.next();
        Assertions.assertNotNull(wrapperLoaded);
    }
}
