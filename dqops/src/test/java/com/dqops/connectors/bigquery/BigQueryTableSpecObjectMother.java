/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.connectors.bigquery;

import com.dqops.metadata.sources.PhysicalTableName;
import com.dqops.metadata.sources.TableSpec;

/**
 * Object mother that creates testable (known) tables to the user context.
 * Those tables should exist in the sandbox bigquery instance.
 */
@Deprecated
public class BigQueryTableSpecObjectMother {
    /**
     * Shared dataset in bigquery with testable content.
     */
    public static final String DATASET_NAME = "dqo_test_data_ec2";

    /**
     * Table names.
     */
    public enum TableNames {
        bq_data_types_test,
        numerical_datetime_average_week
    }

    /**
     * Create the bq_data_types_test table spec.
     * @return Table spec.
     */
    public static TableSpec create_bq_data_types_test() {
        TableSpec table = new TableSpec(new PhysicalTableName(DATASET_NAME, TableNames.bq_data_types_test.name()));
        // TODO: we can also add columns

        return table;
    }


    /**
     * Create the numerical_datetime_average_week table spec.
     * @return Table spec.
     */
    public static TableSpec create_numerical_datetime_average_week() {
        TableSpec table = new TableSpec(new PhysicalTableName(DATASET_NAME, TableNames.numerical_datetime_average_week.name()));
        // TODO: we can also add columns

        return table;
    }
}
