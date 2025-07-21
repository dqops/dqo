/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
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
