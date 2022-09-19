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
package ai.dqo;

import ai.dqo.data.ParquetSupport;
import ai.dqo.metadata.storage.localfiles.userhome.LocalUserHomeCreatorObjectMother;
import ai.dqo.utils.BeanFactoryObjectMother;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

/**
 * Base class for unit tests. All test classes must extend this class DIRECTLY (with no intermediate classes in the class hierarchy).
 */
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
@ActiveProfiles("test")
@Tag("unittest")
public abstract class BaseTest {
    @Autowired
    private BeanFactory beanFactory;

    /**
     * Called before each test.
     * This method should be overridden in derived super classes (test classes), but remember to add {@link BeforeEach} annotation in a derived test class. JUnit5 demands it.
     * @throws Throwable
     */
    @BeforeEach
    protected void setUp() throws Throwable {
        BeanFactoryObjectMother.setBeanFactory(beanFactory); // let object mothers use the bean factory without propagating too many object instances
        ParquetSupport.ensureInitialized();
        LocalUserHomeCreatorObjectMother.initializeDefaultDqoUserHomeSilentlyOnce();
        // to be extended in the future when the need appears
    }

    /**
     * Called after each test.
     * This method should be overriden in derived super classes (test classes), but remember to add @AfterEach in a derived test class. JUnit5 demands it.
     * @throws Throwable
     */
    @AfterEach
    protected void tearDown() throws Throwable  {
        // to be extended in the future when the need appears
    }
}
