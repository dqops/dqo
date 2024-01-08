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
package com.dqops.metadata.definitions.rules;

import com.dqops.BaseTest;
import com.dqops.metadata.basespecs.InstanceStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class RuleDefinitionListImplTests extends BaseTest {
    private RuleDefinitionListImpl sut;

    @BeforeEach
    void setUp() {
		this.sut = new RuleDefinitionListImpl();
    }

    @Test
    void createNewElement_whenCalled_createsElementWithEmptyModel() {
        RuleDefinitionWrapper wrapper = this.sut.createNewElement("src");
        Assertions.assertEquals("src", wrapper.getRuleName());
        Assertions.assertEquals(InstanceStatus.ADDED, wrapper.getStatus());
        Assertions.assertNotNull(wrapper.getSpec());
        Assertions.assertEquals(0, this.sut.size());
    }

    @Test
    void toList_whenCalled_returnList() {
        RuleDefinitionWrapper wrapper = this.sut.createAndAddNew("src");
        Assertions.assertEquals(1, this.sut.size());
        List<RuleDefinitionWrapper> list = this.sut.toList();
        Assertions.assertNotNull(list);
        Assertions.assertEquals(1, list.size());
        Assertions.assertSame(wrapper, list.get(0));
    }
}
