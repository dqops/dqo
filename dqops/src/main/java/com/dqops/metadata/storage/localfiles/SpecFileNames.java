/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.metadata.storage.localfiles;

/**
 * File names used for the spec yaml files.
 */
public final class SpecFileNames {
    /**
     * Data source (connection) spec file name.
     */
    public static final String CONNECTION_SPEC_FILE_NAME_YAML = "connection.dqoconnection.yaml";

    /**
     * Data quality sensor definition file name.
     */
    public static final String SENSOR_SPEC_FILE_NAME_YAML = "sensordefinition.dqosensor.yaml";

    /**
     * Table spec file extension.
     */
    public static final String TABLE_SPEC_FILE_EXT_YAML = ".dqotable.yaml";

    /**
     * File index spec file extension.
     */
    public static final String FILE_INDEX_SPEC_FILE_EXT_JSON = ".dqofidx.json";

    /**
     * Connection similarity index spec file extension.
     */
    public static final String CONNECTION_SIMILARITY_INDEX_SPEC_FILE_EXT_JSON = ".dqocsidx.json";

    /**
     * Provider specific sensor definition file extension.
     */
    public static final String PROVIDER_SENSOR_SPEC_FILE_EXT_YAML = ".dqoprovidersensor.yaml";

    /**
     * Provider specific SQL template file extension.
     */
    public static final String PROVIDER_SENSOR_SQL_TEMPLATE_EXT = ".sql.jinja2";

    /**
     * Provider specific error sampling SQL template file extension.
     */
    public static final String PROVIDER_SENSOR_ERROR_SAMPLING_TEMPLATE_EXT = ".sample.sql.jinja2";

    /**
     * Custom rule definition file extension.
     */
    public static final String CUSTOM_RULE_SPEC_FILE_EXT_YAML = ".dqorule.yaml";

    /**
     * Custom check definition file extension.
     */
    public static final String CUSTOM_CHECK_SPEC_FILE_EXT_YAML = ".dqocheck.yaml";

    /**
     * Default table-level checks pattern file extension.
     */
    public static final String TABLE_DEFAULT_CHECKS_SPEC_FILE_EXT_YAML = ".dqotablepattern.yaml";

    /**
     * Default column-level checks pattern file extension.
     */
    public static final String COLUMN_DEFAULT_CHECKS_SPEC_FILE_EXT_YAML = ".dqocolumnpattern.yaml";

    /**
     * The name of the "default" patterns of default checks for both tables and columns. It is depreciated, because the default file was divided into multiple files.
     */
    @Deprecated
    public static final String DEFAULT_CHECKS_PATTERN_NAME = "default";

    /**
     * Custom module file extension (.py).
     */
    public static final String CUSTOM_RULE_PYTHON_MODULE_FILE_EXT_PY = ".py";

    /**
     * Local settings file.
     */
    public static final String LOCAL_SETTINGS_SPEC_FILE_NAME_YAML = ".localsettings.dqosettings.yaml";

    /**
     * Data quality dashboards definitions file name.
     */
    public static final String DASHBOARDS_SPEC_FILE_NAME_YAML = "dashboardslist.dqodashboards.yaml";

    /**
     * Default monitoring schedules file name.
     */
    public static final String DEFAULT_MONITORING_SCHEDULES_SPEC_FILE_NAME_YAML = "defaultschedules.dqoschedules.yaml";

    /**
     * Default notification configuration file name.
     */
    public static final String DEFAULT_NOTIFICATIONS_FILE_NAME_YAML = "defaultnotifications.dqonotifications.yaml";

}
