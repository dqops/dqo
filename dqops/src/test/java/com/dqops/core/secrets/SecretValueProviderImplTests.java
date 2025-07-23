/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
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
