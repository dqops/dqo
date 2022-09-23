package ai.dqo.metadata.search;

import ai.dqo.metadata.sources.LabelSetSpec;

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
}
