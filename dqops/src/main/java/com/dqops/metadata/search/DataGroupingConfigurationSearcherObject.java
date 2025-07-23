/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
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
