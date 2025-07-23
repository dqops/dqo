/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.metadata.groupings;

/**
 * Object mother for {@link DataGroupingConfigurationSpec}.
 */
public class DataGroupingConfigurationSpecObjectMother {
    /**
     * Creates a data grouping configuration specification with one level.
     * @param level1 Data grouping dimension level 1.
     * @return Data grouping configuration configuration.
     */
    public static DataGroupingConfigurationSpec create(DataGroupingDimensionSpec level1) {
        DataGroupingConfigurationSpec dataGroupingConfigurationSpec = new DataGroupingConfigurationSpec() {{
			setLevel1(level1);
        }};
        return dataGroupingConfigurationSpec;
    }

    /**
     * Creates a data grouping configuration specification with two levels.
     * @param level1 Level 1.
     * @param level2 Level 2.
     * @return Data grouping configuration.
     */
    public static DataGroupingConfigurationSpec create(DataGroupingDimensionSpec level1, DataGroupingDimensionSpec level2) {
        DataGroupingConfigurationSpec dataGroupingConfigurationSpec = new DataGroupingConfigurationSpec() {{
			setLevel1(level1);
			setLevel2(level2);
        }};
        return dataGroupingConfigurationSpec;
    }

    /**
     * Creates a data grouping configuration with three levels.
     * @param level1 Level 1.
     * @param level2 Level 2.
     * @param level3 Level 3.
     * @return Data grouping configuration configuration.
     */
    public static DataGroupingConfigurationSpec create(DataGroupingDimensionSpec level1, DataGroupingDimensionSpec level2, DataGroupingDimensionSpec level3) {
        DataGroupingConfigurationSpec dataGroupingConfigurationSpec = new DataGroupingConfigurationSpec() {{
			setLevel1(level1);
			setLevel2(level2);
			setLevel3(level3);
        }};
        return dataGroupingConfigurationSpec;
    }

    /**
     * Creates a data grouping configuration specification with four levels.
     * @param level1 Level 1.
     * @param level2 Level 2.
     * @param level3 Level 3.
     * @param level4 Level 4.
     * @return Data grouping configuration configuration.
     */
    public static DataGroupingConfigurationSpec create(DataGroupingDimensionSpec level1, DataGroupingDimensionSpec level2, DataGroupingDimensionSpec level3, DataGroupingDimensionSpec level4) {
        DataGroupingConfigurationSpec dataGroupingConfigurationSpec = new DataGroupingConfigurationSpec() {{
			setLevel1(level1);
			setLevel2(level2);
			setLevel3(level3);
			setLevel4(level4);
        }};
        return dataGroupingConfigurationSpec;
    }
}
