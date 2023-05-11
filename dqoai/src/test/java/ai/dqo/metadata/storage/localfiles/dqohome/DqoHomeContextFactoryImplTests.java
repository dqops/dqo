/*
 * Copyright © 2021 DQO.ai (support@dqo.ai)
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
package ai.dqo.metadata.storage.localfiles.dqohome;

import ai.dqo.BaseTest;
import ai.dqo.metadata.dqohome.DqoHome;
import ai.dqo.utils.BeanFactoryObjectMother;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DqoHomeContextFactoryImplTests extends BaseTest {
    private DqoHomeContextFactoryImpl sut;

    @BeforeEach
    void setUp() {
		this.sut = (DqoHomeContextFactoryImpl) BeanFactoryObjectMother.getBeanFactory().getBean(DqoHomeContextFactory.class);
    }

    @Test
    void openLocalDqoHome_whenCalled_thenOpensDqoHomeInstance() {
        DqoHomeContext dqoHomeContext = this.sut.openLocalDqoHome();
        Assertions.assertNotNull(dqoHomeContext);
        DqoHome dqoHome = dqoHomeContext.getDqoHome();
        Assertions.assertNotNull(dqoHome);
        Assertions.assertNotNull(dqoHome.getSensors());
        Assertions.assertNotNull(dqoHome.getRules());
        Assertions.assertNotNull(dqoHome.getDashboards());
    }
}
