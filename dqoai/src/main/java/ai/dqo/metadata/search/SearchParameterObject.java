package ai.dqo.metadata.search;

import ai.dqo.metadata.id.HierarchyNode;

import java.util.ArrayList;
import java.util.List;

public class SearchParameterObject {
	private List<HierarchyNode> nodes;
	private DimensionSearcherObject dimensionSearcherObject;
	private LabelsSearcherObject labelsSearcherObject;

	public SearchParameterObject(List<HierarchyNode> nodes, DimensionSearcherObject dimensionSearcherObject, LabelsSearcherObject labelsSearcherObject) {
		this.nodes = nodes;
		this.dimensionSearcherObject = dimensionSearcherObject;
		this.labelsSearcherObject = labelsSearcherObject;
	}

	public SearchParameterObject(List<HierarchyNode> nodes) {
		this.nodes = nodes;
		this.dimensionSearcherObject = new DimensionSearcherObject();
		this.labelsSearcherObject = new LabelsSearcherObject();
	}

	public SearchParameterObject() {
		this(new ArrayList<>());
	}

	public List<HierarchyNode> getNodes() {
		return nodes;
	}

	public void setNodes(List<HierarchyNode> nodes) {
		this.nodes = nodes;
	}

	public DimensionSearcherObject getDimensionSearcherObject() {
		return dimensionSearcherObject;
	}

	public void setDimensionSearcherObject(DimensionSearcherObject dimensionSearcher) {
		this.dimensionSearcherObject = dimensionSearcher;
	}

	public LabelsSearcherObject getLabelsSearcherObject() {
		return labelsSearcherObject;
	}

	public void setLabelsSearcherObject(LabelsSearcherObject labelsSearcherObject) {
		this.labelsSearcherObject = labelsSearcherObject;
	}
}
