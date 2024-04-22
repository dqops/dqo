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
		if (label == null || labelSetSpec == null || labelSetSpec.size() == 0) {
			return true;
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
		for (String label : requiredLabels) {
			if (!containsPattern(label, labels)) {
				return false;
			}
		}
		return true;
	}
}
