package ai.dqo.metadata.search;

import ai.dqo.metadata.groupings.DimensionsConfigurationSpec;

public class DimensionSearcherObject {
	private DimensionsConfigurationSpec connectionDimension;
	private DimensionsConfigurationSpec tableDimension;
	private DimensionsConfigurationSpec columnDimension;

	public DimensionsConfigurationSpec getConnectionDimension() {
		return this.connectionDimension;
	}

	public void setConnectionDimension(DimensionsConfigurationSpec connectionDimension) {
		this.connectionDimension = connectionDimension;
	}

	public DimensionsConfigurationSpec getTableDimension() {
		return this.tableDimension;
	}

	public void setTableDimension(DimensionsConfigurationSpec tableDimension) {
		this.tableDimension = tableDimension;
	}

	public DimensionsConfigurationSpec getColumnDimension() {
		return this.columnDimension;
	}

	public void setColumnDimension(DimensionsConfigurationSpec columnDimension) {
		this.columnDimension = columnDimension;
	}
}
