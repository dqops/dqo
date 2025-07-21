/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.cli.commands.impl;

import java.util.concurrent.Callable;

/**
 * Root DQOps shell runner. Separated into a different class to avoid circular dependencies in IoC.
 */
public interface DqoShellRunnerService extends Callable<Integer> {
}
