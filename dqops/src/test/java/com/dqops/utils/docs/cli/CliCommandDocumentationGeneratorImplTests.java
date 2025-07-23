/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.utils.docs.cli;

import com.dqops.BaseTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class CliCommandDocumentationGeneratorImplTests extends BaseTest {
    private CliCommandDocumentationGeneratorImpl sut;

    @BeforeEach
    void setUp() {
        this.sut = new CliCommandDocumentationGeneratorImpl(new CliCommandDocumentationModelFactoryImpl());
    }

    @Test
    void createCommandModels_whenCalled_generatesModelsForAllCliCommands() {
        List<CliRootCommandDocumentationModel> commandModels = this.sut.createCommandModels();
        Assertions.assertTrue(commandModels.size() > 1);
    }
}
