/*
 * Copyright © 2021 DQO.ai (support@dqo.ai)
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
