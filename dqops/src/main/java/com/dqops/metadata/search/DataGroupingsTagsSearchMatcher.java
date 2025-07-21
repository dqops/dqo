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

import java.util.Arrays;

/**
 * Data grouping tags search matcher for CLI commands.
 */
public class DataGroupingsTagsSearchMatcher {
	/**
	 * Returns a boolean value if filters fit spec.
	 * @param level Data stream level value.
	 * @param dataGroupingConfiguration DimensionsConfigurationSpec.
	 * @return Boolean value if filters fit spec.
	 */
	public static boolean matchDataGroupingConfigurationTag(String level, DataGroupingConfigurationSpec dataGroupingConfiguration) {
		if (level == null || dataGroupingConfiguration == null) {
			return true;
		}
		if(dataGroupingConfiguration.getLevel1() == null || dataGroupingConfiguration.getLevel2() == null || dataGroupingConfiguration.getLevel3() == null ||
				dataGroupingConfiguration.getLevel4() == null || dataGroupingConfiguration.getLevel5() == null || dataGroupingConfiguration.getLevel6() == null ||
				dataGroupingConfiguration.getLevel7() == null || dataGroupingConfiguration.getLevel8() == null || dataGroupingConfiguration.getLevel9() == null) {
			return true;
		}
		if (level.equals(dataGroupingConfiguration.getLevel1().getTag()) ||
				StringPatternComparer.matchSearchPattern(dataGroupingConfiguration.getLevel1().getTag(), level)) {
			return true;
		}
		if (level.equals(dataGroupingConfiguration.getLevel2().getTag()) ||
				StringPatternComparer.matchSearchPattern(dataGroupingConfiguration.getLevel2().getTag(), level)) {
			return true;
		}
		if (level.equals(dataGroupingConfiguration.getLevel3().getTag()) ||
				StringPatternComparer.matchSearchPattern(dataGroupingConfiguration.getLevel3().getTag(), level)) {
			return true;
		}
		if (level.equals(dataGroupingConfiguration.getLevel4().getTag()) ||
				StringPatternComparer.matchSearchPattern(dataGroupingConfiguration.getLevel4().getTag(), level)) {
			return true;
		}
		if (level.equals(dataGroupingConfiguration.getLevel5().getTag()) ||
				StringPatternComparer.matchSearchPattern(dataGroupingConfiguration.getLevel5().getTag(), level)) {
			return true;
		}
		if (level.equals(dataGroupingConfiguration.getLevel6().getTag()) ||
				StringPatternComparer.matchSearchPattern(dataGroupingConfiguration.getLevel6().getTag(), level)) {
			return true;
		}
		if (level.equals(dataGroupingConfiguration.getLevel7().getTag()) ||
				StringPatternComparer.matchSearchPattern(dataGroupingConfiguration.getLevel7().getTag(), level)) {
			return true;
		}
		if (level.equals(dataGroupingConfiguration.getLevel8().getTag()) ||
				StringPatternComparer.matchSearchPattern(dataGroupingConfiguration.getLevel8().getTag(), level)) {
			return true;
		}
		if (level.equals(dataGroupingConfiguration.getLevel9().getTag()) ||
				StringPatternComparer.matchSearchPattern(dataGroupingConfiguration.getLevel9().getTag(), level)) {
			return true;
		}
		return false;
	}

	/**
	 * Returns a boolean value if filters fit spec.
	 * @param columnSearchFilters Column search filters.
	 * @param dataGroupingConfigurationSpec Data grouping configuration spec.
	 * @return Boolean value if filters fit spec.
	 */
	static boolean matchAllColumnDataGroupingTags(ColumnSearchFilters columnSearchFilters, DataGroupingConfigurationSpec dataGroupingConfigurationSpec) {
		String[] tags = columnSearchFilters.getTags();
		if (tags == null) {
			return true;
		}
		return Arrays.stream(tags).allMatch(tag -> matchDataGroupingConfigurationTag(tag, dataGroupingConfigurationSpec));
	}

	/**
	 * Returns a boolean value if filters fit spec.
	 * @param checkSearchFilters Check search filters.
	 * @param dataGroupingConfigurationSpec Data streams mapping spec.
	 * @return Boolean value if filters fit spec.
	 */
	static boolean matchAllCheckDataStreamsMapping(CheckSearchFilters checkSearchFilters, DataGroupingConfigurationSpec dataGroupingConfigurationSpec) {
		String[] tags = checkSearchFilters.getTags();
		if (tags == null) {
			return true;
		}
		return Arrays.stream(tags).allMatch(tag -> matchDataGroupingConfigurationTag(tag, dataGroupingConfigurationSpec));
	}

	/**
	 * Returns a boolean value if filters fit spec.
	 * @param tags Array of required tags.
	 * @param dataGroupingConfigurationSpec Data grouping configuration spec.
	 * @return Boolean value if filters fit spec.
	 */
	static boolean matchAllRequiredTags(String[] tags, DataGroupingConfigurationSpec dataGroupingConfigurationSpec) {
		if (tags == null) {
			return true;
		}
		return Arrays.stream(tags).allMatch(dimension -> matchDataGroupingConfigurationTag(dimension, dataGroupingConfigurationSpec));
	}
}
