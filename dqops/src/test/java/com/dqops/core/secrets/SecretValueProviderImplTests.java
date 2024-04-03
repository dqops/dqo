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
package com.dqops.core.secrets;

import com.dqops.BaseTest;
import com.dqops.core.filesystem.virtual.FileContent;
import com.dqops.data.local.LocalDqoUserHomePathProviderObjectMother;
import com.dqops.metadata.credentials.SharedCredentialWrapper;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContext;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContextObjectMother;
import com.dqops.metadata.userhome.UserHome;
import com.dqops.utils.BeanFactoryObjectMother;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SecretValueProviderImplTests extends BaseTest {
    private SecretValueProviderImpl sut;
    private SecretValueLookupContext secretValueLookupContext;

    @BeforeEach
    void setUp() {
        BeanFactory beanFactory = BeanFactoryObjectMother.getBeanFactory();
		this.sut = beanFactory.getBean(SecretValueProviderImpl.class);
        secretValueLookupContext = new SecretValueLookupContext(null);
    }

    @Test
    void expandValue_whenNoTokens_thenReturnsWithoutChanges() {
        String expanded = this.sut.expandValue("abc", this.secretValueLookupContext);
        Assertions.assertEquals("abc", expanded);
    }

    @Test
    void expandValue_whenJustEnvironmentVariable_thenReturnsExpanded() {
        String expanded = this.sut.expandValue("${DQO_HOME}", this.secretValueLookupContext);
        Assertions.assertEquals(System.getenv("DQO_HOME"), expanded);
    }

    @Test
    void expandValue_whenEnvironmentVariableWithOtherTest_thenReturnsExpanded() {
        String expanded = this.sut.expandValue("prefix_${DQO_HOME}", this.secretValueLookupContext);
        Assertions.assertEquals("prefix_" + System.getenv("DQO_HOME"), expanded);
    }

    @Test
    void expandValue_whenEnvironmentVariableTwice_thenReturnsExpanded() {
        String expanded = this.sut.expandValue("prefix_${DQO_HOME}_${DQO_HOME}", this.secretValueLookupContext);
        Assertions.assertEquals("prefix_" + System.getenv("DQO_HOME") + "_" + System.getenv("DQO_HOME"), expanded);
    }

    @Test
    void expandValue_whenGcpSecretManagerTokenSnowflakeAccount_thenReturnsSomething() {
        String expanded = this.sut.expandValue("${sm://snowflake-account}", this.secretValueLookupContext);
        Assertions.assertNotNull(expanded);
        Assertions.assertFalse(expanded.startsWith("sm://"));
        Assertions.assertNotEquals("", expanded);
    }

    @Test
    void expandValue_whenSharedSecret_thenReturnsExpandedSharedSecret() {
        UserHomeContext defaultHomeContext = UserHomeContextObjectMother.createInMemoryFileHomeContext();
        UserHome userHome = defaultHomeContext.getUserHome();
        SharedCredentialWrapper sharedSecretWrapper = userHome.getCredentials().createAndAddNew("tested_secret.txt");
        sharedSecretWrapper.setObject(new FileContent("mysecret"));
        defaultHomeContext.flush();

        this.secretValueLookupContext = new SecretValueLookupContext(userHome);
        String expanded = this.sut.expandValue("${credential://tested_secret.txt}", this.secretValueLookupContext);
        Assertions.assertEquals("mysecret", expanded);
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
