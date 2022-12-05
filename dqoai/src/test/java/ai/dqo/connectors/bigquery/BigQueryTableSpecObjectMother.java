/*
 * Copyright © 2021 DQO.ai (support@dqo.ai)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ai.dqo.connectors.bigquery;

import ai.dqo.metadata.groupings.TimeSeriesConfigurationSpec;
import ai.dqo.metadata.sources.TableSpec;
import ai.dqo.metadata.sources.TableTargetSpec;

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
        TableSpec table = new TableSpec(new TableTargetSpec(DATASET_NAME, TableNames.bq_data_types_test.name()));
        // TODO: we can also add columns

        return table;
    }


    /**
     * Create the numerical_datetime_average_week table spec.
     * @return Table spec.
     */
    public static TableSpec create_numerical_datetime_average_week() {
        TableSpec table = new TableSpec(new TableTargetSpec(DATASET_NAME, TableNames.numerical_datetime_average_week.name()));
        // TODO: we can also add columns

        return table;
    }
}
