/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.core.filesystem.virtual;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Folder kind type. Identifies a type of a folder in the file system tree.
 */
public enum FolderKind {
    /**
     * Unknown (unspecified) folder kind.
     */
    @JsonProperty("unknown")
    UNKNOWN,

    /**
     * Foreign folder that was probably created by the user and is not tracked.
     */
    @JsonProperty("foreign")
    FOREIGN,  // not our tracked folder, a user has put his own folders

    /**
     * Home folder.
     */
    @JsonProperty("home")
    HOME,

    /**
     * Checks folder.
     */
    @JsonProperty("checks")
    CHECKS,

    /**
     * Subfolder in the "checks" folder. This folder holds a single check definition.
     */
    @JsonProperty("checks_subfolder")
    CHECK_SUBFOLDER,

    /**
     * Sources folder.
     */
    @JsonProperty("sources")
    SOURCES,

    /**
     * Single source.
     */
    @JsonProperty("source")
    SOURCE,

    /**
     * Subfolder in a source folder (user created).
     */
    @JsonProperty("source_subfolder")
    SOURCE_SUBFOLDER,

    /**
     * Custom sensor definition folder.
     */
    @JsonProperty("sensors")
    SENSORS,

    /**
     * Custom rules definition folder.
     */
    @JsonProperty("rules")
    RULES,

    /**
     * Subfolder in the custom "rules" folder.
     */
    @JsonProperty("rules_subfolder")
    RULES_SUBFOLDER,

    /**
     * Shared settings folder.
     */
    @JsonProperty("settings")
    SETTINGS,

    /**
     * Subfolder in the shared settings folder.
     */
    @JsonProperty("settings_subfolder")
    SETTINGS_SUBFOLDER,

    /**
     * Shared credentials folder.
     */
    @JsonProperty("credentials")
    CREDENTIALS,

    /**
     * Subfolder in the credentials folder.
     */
    @JsonProperty("credentials_subfolder")
    CREDENTIALS_SUBFOLDER,

    /**
     * Dictionaries folder.
     */
    @JsonProperty("dictionaries")
    DICTIONARIES,

    /**
     * Subfolder in the dictionaries folder.
    */
    @JsonProperty("dictionaries_subfolder")
    DICTIONARIES_SUBFOLDER,

    /**
     * Patterns folder.
     */
    @JsonProperty("patterns")
    PATTERNS,

    /**
     * Subfolder in the patterns folder.
     */
    @JsonProperty("patterns_subfolder")
    PATTERNS_SUBFOLDER
}

