/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops;

import com.dqops.testutils.BeforeAllIntegrationTestExtension;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ActiveProfiles;

/**
 * Base class for integration tests. All test classes must extend this class DIRECTLY (with no intermediate classes in the class hierarchy).
 */
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
@ActiveProfiles("test")
@Tag("integrationtest")
@ExtendWith(BeforeAllIntegrationTestExtension.class)
public abstract class BaseIntegrationTest {
}
