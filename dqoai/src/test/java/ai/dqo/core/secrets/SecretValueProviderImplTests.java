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
package ai.dqo.core.secrets;

import ai.dqo.BaseTest;
import ai.dqo.utils.BeanFactoryObjectMother;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SecretValueProviderImplTests extends BaseTest {
    private SecretValueProviderImpl sut;

    /**
     * Called before each test.
     * This method should be overridden in derived super classes (test classes), but remember to add {@link BeforeEach} annotation in a derived test class. JUnit5 demands it.
     *
     * @throws Throwable
     */
    @Override
    @BeforeEach
    protected void setUp() throws Throwable {
        super.setUp();
        BeanFactory beanFactory = BeanFactoryObjectMother.getBeanFactory();
		this.sut = beanFactory.getBean(SecretValueProviderImpl.class);
    }

    @Test
    void expandValue_whenNoTokens_thenReturnsWithoutChanges() {
        String expanded = this.sut.expandValue("abc");
        Assertions.assertEquals("abc", expanded);
    }

    @Test
    void expandValue_whenJustEnvironmentVariable_thenReturnsExpanded() {
        String expanded = this.sut.expandValue("${DQO_HOME}");
        Assertions.assertEquals(System.getenv("DQO_HOME"), expanded);
    }

    @Test
    void expandValue_whenEnvironmentVariableWithOtherTest_thenReturnsExpanded() {
        String expanded = this.sut.expandValue("prefix_${DQO_HOME}");
        Assertions.assertEquals("prefix_" + System.getenv("DQO_HOME"), expanded);
    }

    @Test
    void expandValue_whenEnvironmentVariableTwice_thenReturnsExpanded() {
        String expanded = this.sut.expandValue("prefix_${DQO_HOME}_${DQO_HOME}");
        Assertions.assertEquals("prefix_" + System.getenv("DQO_HOME") + "_" + System.getenv("DQO_HOME"), expanded);
    }

    @Test
    void expandValue_whenGcpSecretManagerTokenSnowflakeAccount_thenReturnsSomething() {
        String expanded = this.sut.expandValue("${sm://snowflake-account}");
        Assertions.assertNotNull(expanded);
        Assertions.assertFalse(expanded.startsWith("sm://"));
        Assertions.assertNotEquals("", expanded);
    }

//    @Test
//    @Ignore("Spring Cloud Does not support defaults")
//    void expandValue_whenGcpSecretManagerTokenHasNotFoundSecretButItHasDefault_thenReturnsDefault() {
//        String expanded = this.sut.expandValue("${sm://missing-secret:default_value}");
//        Assertions.assertNotNull(expanded);
//        Assertions.assertFalse(expanded.startsWith("sm://"));
//        Assertions.assertEquals("default_value", expanded);
//    }
//
//    @Test
//    @Ignore("Spring Cloud Does not support defaults")
//    void expandValue_whenGcpSecretNotFoundAndDefaultValueIsAlsoEnvVarExpression_thenReturnsExpanded() {
//        String expanded = this.sut.expandValue("${sm://missing-secret:${DQO_HOME}}");
//        Assertions.assertEquals(System.getenv("DQO_HOME"), expanded);
//    }
}
