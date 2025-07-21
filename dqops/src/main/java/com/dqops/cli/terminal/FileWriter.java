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

import com.dqops.cli.commands.CliOperationStatus;

public interface FileWriter {
	/**
	 * Writes string content to a file
	 * @param content String content.
	 * @return Cli operation status.
	 */
	CliOperationStatus writeStringToFile(String content);
}
