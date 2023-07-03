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
import com.dqops.connectors.ProviderType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ProviderSensorDefinitionWrapperImplTests extends BaseTest {
    private ProviderSensorDefinitionWrapper sut;

    @BeforeEach
    void setUp() {
		this.sut = new ProviderSensorDefinitionWrapperImpl();
    }

    @Test
    void setProvider_whenSet_returnProvider() {
		this.sut.setProvider(ProviderType.bigquery);
        Assertions.assertEquals(ProviderType.bigquery, this.sut.getProvider());
    }

    @Test
    void setSwlTemplate_whenSet_returnSqlTemplate() {
		this.sut.setSqlTemplate("test");
        Assertions.assertEquals("test", this.sut.getSqlTemplate());
    }
}
