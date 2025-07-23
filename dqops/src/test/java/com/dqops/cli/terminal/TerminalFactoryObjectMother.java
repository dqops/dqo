/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.cli.terminal;

import com.dqops.utils.BeanFactoryObjectMother;

/**
 * Create a terminal factory instance.
 */
public class TerminalFactoryObjectMother {
    /**
     * Returns the default terminal factory that returns a real terminal reader and writer.
     * @return Default terminal factory.
     */
    public static TerminalFactory getDefault() {
        return BeanFactoryObjectMother.getBeanFactory().getBean(TerminalFactory.class);
    }
}
