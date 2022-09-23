package ai.dqo.metadata.search;

import ai.dqo.metadata.sources.LabelSetSpec;

public class LabelsSearcherObject {
	private LabelSetSpec connectionLabels;
	private LabelSetSpec tableLabels;
	private LabelSetSpec columnLabels;

	public LabelSetSpec getConnectionLabels() {
		return this.connectionLabels;
	}

	public void setConnectionLabels(LabelSetSpec connectionLabels) {
		this.connectionLabels = connectionLabels;
	}

	public LabelSetSpec getTableLabels() {
		return this.tableLabels;
	}

	public void setTableLabels(LabelSetSpec tableLabels) {
		this.tableLabels = tableLabels;
	}

	public LabelSetSpec getColumnLabels() {
		return this.columnLabels;
	}

	public void setColumnLabels(LabelSetSpec columnLabels) {
		this.columnLabels = columnLabels;
	}
}
