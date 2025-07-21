/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.cli.commands.settings.impl;

import java.util.ArrayList;

/**
 * Editor finder service.
 */
public interface EditorFinderService {

	String findVSC();

	String findIntelliJ();

	String findEclipse();

	String findPycharm();

	String findTemplateWithDirName(String jetBrainsDirectoryPath, String template);

	ArrayList<EditorInformation> detectEditors();

	ArrayList<EditorInformation> getAllEditors();
}
