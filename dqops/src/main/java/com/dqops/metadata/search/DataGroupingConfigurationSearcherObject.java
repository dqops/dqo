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
package com.dqops.metadata.search;

import com.dqops.metadata.groupings.DataGroupingConfigurationSpec;
import com.dqops.metadata.groupings.DataGroupingConfigurationSpecMap;
import org.apache.parquet.Strings;

/**
 * Helper object used when a search visitor is traversing nodes to find target checks to execute, given the data grouping configuration hierarchy tags.
 */
@Deprecated
public class DataGroupingConfigurationSearcherObject {
	private DataGroupingConfigurationSpecMap tableDataGroupingConfigurations;
	private String defaultDataGrouping;

	/**
	 * Retrieves the default data grouping configuration.
	 * @return The default data grouping configuration.
	 */
	public DataGroupingConfigurationSpec getDefaultGroupingConfiguration() {
		if (Strings.isNullOrEmpty(this.defaultDataGrouping) || tableDataGroupingConfigurations == null) {
			return null;
		}

		return tableDataGroupingConfigurations.get(this.defaultDataGrouping);
	}

	/**
	 * Returns a collection (map) of named data grouping configurations defined at a table level.
	 * @return Data grouping configurations map.
	 */
	public DataGroupingConfigurationSpecMap getTableDataGroupingConfigurations() {
		return this.tableDataGroupingConfigurations;
	}

	/**
	 * Stores (remembers) the data grouping configurations mappings collection from a table level.
	 * @param tableDataGroupingConfigurations Data grouping configurations map from the table level.
	 */
	public void setTableDataGroupingConfigurations(DataGroupingConfigurationSpecMap tableDataGroupingConfigurations) {
		this.tableDataGroupingConfigurations = tableDataGroupingConfigurations;
	}

	/**
	 * Returns the name of the default data grouping, retrieved from the table.
	 * @return The name of the default data grouping.
	 */
	public String getDefaultDataGrouping() {
		return defaultDataGrouping;
	}

	/**
	 * Stores the name of the default data grouping, retrieved from the table.
	 * @param defaultDataGrouping The name of the default data grouping.
	 */
	public void setDefaultDataGrouping(String defaultDataGrouping) {
		this.defaultDataGrouping = defaultDataGrouping;
	}
}
