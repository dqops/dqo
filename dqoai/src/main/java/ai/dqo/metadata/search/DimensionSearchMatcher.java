package ai.dqo.metadata.search;

import ai.dqo.metadata.groupings.DimensionsConfigurationSpec;

import java.util.Arrays;

/**
 * Dimension search matcher for CLI commands.
 */
public class DimensionSearchMatcher {
	/**
	 * Returns a boolean value if filters fit spec.
	 * @param dimension Dimension.
	 * @param dimensions DimensionsConfigurationSpec.
	 * @return Boolean value if filters fit spec.
	 */
	public static boolean matchDimension(String dimension, DimensionsConfigurationSpec dimensions) {
		if (dimension == null || dimensions == null) {
			return true;
		}
		if(dimensions.getDimension1() == null || dimensions.getDimension2() == null || dimensions.getDimension3() == null ||
				dimensions.getDimension4() == null || dimensions.getDimension5() == null || dimensions.getDimension6() == null ||
				dimensions.getDimension7() == null || dimensions.getDimension8() == null || dimensions.getDimension9() == null) {
			return true;
		}
		if (dimension.equals(dimensions.getDimension1().getStaticValue()) ||
				StringPatternComparer.matchSearchPattern(dimensions.getDimension1().getStaticValue(), dimension)) {
			return true;
		}
		if (dimension.equals(dimensions.getDimension2().getStaticValue()) ||
				StringPatternComparer.matchSearchPattern(dimensions.getDimension2().getStaticValue(), dimension)) {
			return true;
		}
		if (dimension.equals(dimensions.getDimension3().getStaticValue()) ||
				StringPatternComparer.matchSearchPattern(dimensions.getDimension3().getStaticValue(), dimension)) {
			return true;
		}
		if (dimension.equals(dimensions.getDimension4().getStaticValue()) ||
				StringPatternComparer.matchSearchPattern(dimensions.getDimension4().getStaticValue(), dimension)) {
			return true;
		}
		if (dimension.equals(dimensions.getDimension5().getStaticValue()) ||
				StringPatternComparer.matchSearchPattern(dimensions.getDimension5().getStaticValue(), dimension)) {
			return true;
		}
		if (dimension.equals(dimensions.getDimension6().getStaticValue()) ||
				StringPatternComparer.matchSearchPattern(dimensions.getDimension6().getStaticValue(), dimension)) {
			return true;
		}
		if (dimension.equals(dimensions.getDimension7().getStaticValue()) ||
				StringPatternComparer.matchSearchPattern(dimensions.getDimension7().getStaticValue(), dimension)) {
			return true;
		}
		if (dimension.equals(dimensions.getDimension8().getStaticValue()) ||
				StringPatternComparer.matchSearchPattern(dimensions.getDimension8().getStaticValue(), dimension)) {
			return true;
		}
		if (dimension.equals(dimensions.getDimension9().getStaticValue()) ||
				StringPatternComparer.matchSearchPattern(dimensions.getDimension9().getStaticValue(), dimension)) {
			return true;
		}
		return false;
	}

	/**
	 * Returns a boolean value if filters fit spec.
	 * @param connectionSearchFilters Connection search filters.
	 * @param dimensionSpec Dimensions configuration spec.
	 * @return Boolean value if filters fit spec.
	 */
	static boolean matchAllConnectionDimensions(ConnectionSearchFilters connectionSearchFilters, DimensionsConfigurationSpec dimensionSpec) {
		String[] dimensions = connectionSearchFilters.getDimensions();
		if (dimensions == null) {
			return true;
		}
		return Arrays.stream(dimensions).allMatch(dimension -> matchDimension(dimension, dimensionSpec));
	}

	/**
	 * Returns a boolean value if filters fit spec.
	 * @param tableSearchFilters Table search filters.
	 * @param dimensionSpec Dimensions configuration spec.
	 * @return Boolean value if filters fit spec.
	 */
	static boolean matchAllTableDimensions(TableSearchFilters tableSearchFilters, DimensionsConfigurationSpec dimensionSpec) {
		String[] dimensions = tableSearchFilters.getDimensions();
		if (dimensions == null) {
			return true;
		}
		return Arrays.stream(dimensions).allMatch(dimension -> matchDimension(dimension, dimensionSpec));
	}

	/**
	 * Returns a boolean value if filters fit spec.
	 * @param columnSearchFilters Column search filters.
	 * @param dimensionSpec Dimensions configuration spec.
	 * @return Boolean value if filters fit spec.
	 */
	static boolean matchAllColumnDimensions(ColumnSearchFilters columnSearchFilters, DimensionsConfigurationSpec dimensionSpec) {
		String[] dimensions = columnSearchFilters.getDimensions();
		if (dimensions == null) {
			return true;
		}
		return Arrays.stream(dimensions).allMatch(dimension -> matchDimension(dimension, dimensionSpec));
	}

	/**
	 * Returns a boolean value if filters fit spec.
	 * @param checkSearchFilters Check search filters.
	 * @param dimensionSpec Dimensions configuration spec.
	 * @return Boolean value if filters fit spec.
	 */
	static boolean matchAllCheckDimensions(CheckSearchFilters checkSearchFilters, DimensionsConfigurationSpec dimensionSpec) {
		String[] dimensions = checkSearchFilters.getDimensions();
		if (dimensions == null) {
			return true;
		}
		return Arrays.stream(dimensions).allMatch(dimension -> matchDimension(dimension, dimensionSpec));
	}
}
