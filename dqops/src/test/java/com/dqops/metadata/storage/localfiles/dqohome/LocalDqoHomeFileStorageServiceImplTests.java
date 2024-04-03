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
package com.dqops.metadata.storage.localfiles.dqohome;

import com.dqops.BaseTest;
import com.dqops.utils.BeanFactoryObjectMother;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.file.Path;

@SpringBootTest
public class LocalDqoHomeFileStorageServiceImplTests extends BaseTest {
    private LocalDqoHomeFileStorageServiceImpl sut;

    @BeforeEach
    void setUp(){
		this.sut = (LocalDqoHomeFileStorageServiceImpl) BeanFactoryObjectMother.getBeanFactory().getBean(LocalDqoHomeFileStorageService.class);
    }

    @Test
    void getHome_whenDefaultDqoHomeStorageServiceCreated_thenReturnsDQO_HOME() {
        String dqo_home = Path.of(System.getenv("DQO_HOME")).toAbsolutePath().normalize().toString().replace('\\', '/');
        Assertions.assertEquals(dqo_home, this.sut.getHomeRootDirectory().replace('\\', '/'));
    }
}
