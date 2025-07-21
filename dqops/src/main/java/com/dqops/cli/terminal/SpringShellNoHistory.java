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

import org.jline.reader.impl.history.DefaultHistory;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * A fix to stop saving Spring Shell log (spring-shell.log) which we don't use.
 */
@Component
public class SpringShellNoHistory extends DefaultHistory {
    /**
     * Does nothing, just captures saving the spring-shell.log.
     * @throws IOException
     */
    @Override
    public void save() throws IOException {
    }
}
