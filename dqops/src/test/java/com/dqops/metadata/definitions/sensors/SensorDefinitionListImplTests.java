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
package com.dqops.metadata.definitions.sensors;

import com.dqops.BaseTest;
import com.dqops.metadata.basespecs.InstanceStatus;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContext;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContextObjectMother;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Iterator;
import java.util.List;

@SpringBootTest
public class SensorDefinitionListImplTests extends BaseTest {
    private SensorDefinitionListImpl sut;
    private UserHomeContext homeContext;

    @BeforeEach
    void setUp() {
		homeContext = UserHomeContextObjectMother.createTemporaryFileHomeContext(true);
		this.sut = (SensorDefinitionListImpl) homeContext.getUserHome().getSensors();
    }

    @Test
    void createNewElement_whenCalled_createsElementWithEmptyModel() {
        int sutSize = this.sut.size();
        SensorDefinitionWrapper wrapper = this.sut.createNewElement("src");
        Assertions.assertEquals("src", wrapper.getName());
        Assertions.assertEquals(InstanceStatus.ADDED, wrapper.getStatus());
        Assertions.assertNotNull(wrapper.getSpec());
        Assertions.assertEquals(sutSize, this.sut.size());
    }

    @Test
    void toList_whenCalled_returnList() {
        SensorDefinitionWrapper wrapper = this.sut.createAndAddNew("src");
        Assertions.assertEquals(1, this.sut.size());
        List<SensorDefinitionWrapper> list = this.sut.toList();
        Assertions.assertNotNull(list);
        Assertions.assertEquals(1, list.size());
        Assertions.assertSame(wrapper, list.get(0));
    }

    @Test
    void createAndAddNew_whenNewSourceAddedAndFlushed_thenIsSaved() {
		this.sut.createAndAddNew("src1");
		homeContext.flush();

        UserHomeContext homeContext2 = UserHomeContextObjectMother.createTemporaryFileHomeContext(false);
        SensorDefinitionList sources2 = homeContext2.getUserHome().getSensors();
        SensorDefinitionWrapper wrapper2 = sources2.getByObjectName("src1", true);
        Assertions.assertNotEquals(null, wrapper2);
    }

    //TODO remake placeholder
    @Test
    void flush_whenExistingSourceLoadedModifiedAndFlushed_thenIsSaved() {
		this.sut.createAndAddNew("src1");
		homeContext.flush();

        UserHomeContext homeContext2 = UserHomeContextObjectMother.createTemporaryFileHomeContext(false);
        SensorDefinitionList sources2 = homeContext2.getUserHome().getSensors();
        SensorDefinitionWrapper wrapper2 = sources2.getByObjectName("src1", true);
        wrapper2.getSpec();
        homeContext2.flush();

        UserHomeContext homeContext3 = UserHomeContextObjectMother.createTemporaryFileHomeContext(false);
        SensorDefinitionList sources3 = homeContext3.getUserHome().getSensors();
        SensorDefinitionWrapper wrapper3 = sources3.getByObjectName("src1", true);
        Assertions.assertNotEquals(null, wrapper3);
    }

    //TODO remake placeholder
    @Test
    void iterator_whenConnectionAdded_thenReturnsConnection() {
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
