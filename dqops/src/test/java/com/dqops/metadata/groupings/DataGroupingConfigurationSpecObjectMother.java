/*
 * Copyright © 2021 DQOps (support@dqops.com)
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
