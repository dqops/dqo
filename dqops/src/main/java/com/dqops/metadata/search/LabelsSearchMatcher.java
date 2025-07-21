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

import com.dqops.metadata.labels.LabelSetSpec;

/**
 * Labels matcher for CLI commands.
 */
public class LabelsSearchMatcher {
	/**
	 * Returns a boolean value if labels contains searched label.
	 * @param label Label search pattern.
	 * @param labelSetSpec Label set spec.
	 * @return Boolean value if labels contains searched label.
	 */
	private static boolean containsPattern(String label, LabelSetSpec labelSetSpec) {
		if (label == null) {
			return true;
		}

		if (labelSetSpec == null || labelSetSpec.size() == 0) {
			return false;
		}

		if (labelSetSpec.contains(label)) {
			return true;
		}

		for (String labelSpec : labelSetSpec) {
			if (StringPatternComparer.matchSearchPattern(labelSpec, label)) {
				return true;
			}
		}

		return false;
	}
	/**
	 * Returns a boolean value if filters fit spec.
	 * @param connectionSearchFilters Connection search filters.
	 * @param labels Label set spec.
	 * @return Boolean value if filters fit spec.
	 */
	public static boolean matchConnectionLabels(ConnectionSearchFilters connectionSearchFilters, LabelSetSpec labels) {
		String[] filterLabels = connectionSearchFilters.getLabels();
		if (filterLabels == null) {
			return true;
		}
		for (String label : filterLabels) {
			if (!containsPattern(label, labels)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Returns a boolean value if filters fit spec.
	 * @param tableSearchFilters Table search filters.
	 * @param labels Label set spec.
	 * @return Boolean value if filters fit spec.
	 */
	public static boolean matchTableLabels(TableSearchFilters tableSearchFilters, LabelSetSpec labels) {
		String[] filterLabels = tableSearchFilters.getLabels();
		if (filterLabels == null) {
			return true;
		}
		for (String label : filterLabels) {
			if (!containsPattern(label, labels)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Returns a boolean value if filters fit spec.
	 * @param columnSearchFilters Column search filters.
	 * @param labels Label set spec.
	 * @return Boolean value if filters fit spec.
	 */
	public static boolean matchColumnLabels(ColumnSearchFilters columnSearchFilters, LabelSetSpec labels) {
		String[] filterLabels = columnSearchFilters.getLabels();
		if (filterLabels == null) {
			return true;
		}
		for (String label : filterLabels) {
			if (!containsPattern(label, labels)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Returns a boolean value if filters fit spec.
	 * @param checkSearchFilters Check search filters.
	 * @param labels Label set spec.
	 * @return Boolean value if filters fit spec.
	 */
	public static boolean matchCheckLabels(CheckSearchFilters checkSearchFilters, LabelSetSpec labels) {
		String[] filterLabels = checkSearchFilters.getLabels();
		if (filterLabels == null) {
			return true;
		}
		for (String label : filterLabels) {
			if (!containsPattern(label, labels)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Returns a boolean value if filters fit spec.
	 * @param requiredLabels Array of required labels.
	 * @param labels Label set spec.
	 * @return Boolean value if filters fit spec.
	 */
	public static boolean hasAllLabels(String[] requiredLabels, LabelSetSpec labels) {
		if (requiredLabels == null) {
			return true;
		}
		if (labels == null && requiredLabels.length > 0) {
			return false;
		}
		for (String label : requiredLabels) {
			if (!containsPattern(label, labels)) {
				return false;
			}
		}
		return true;
	}
}
