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
package ai.dqo.metadata.sources;

import ai.dqo.core.secrets.SecretValueProvider;
import ai.dqo.metadata.basespecs.AbstractSpec;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMap;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMapImpl;
import ai.dqo.metadata.id.HierarchyNodeResultVisitor;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;

import java.util.Objects;

/**
 * Settings specification.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class SettingsSpec extends AbstractSpec implements Cloneable {
	private static final ChildHierarchyNodeFieldMapImpl<SettingsSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractSpec.FIELDS) {
		{
		}
	};

	@JsonPropertyDescription("Editor name spec (VSC, Eclipse, Intellj)")
	private String editorName;

	@JsonPropertyDescription("Editor path on user's computer")
	private String editorPath;

	@JsonPropertyDescription("Api key")
	private String apiKey;

	/**
	 * Default constructor.
	 */
	public SettingsSpec() {
	}

	/**
	 * Editor name constructor.
	 */
	public SettingsSpec(String editorName) {
		this.editorName = editorName;
	}

	/**
	 * Returns an editor name.
	 * @return Editor name.
	 */
	public String getEditorName() {
		return this.editorName;
	}

	/**
	 * Sets an editor name.
	 * @param editorName Editor name.
	 */
	public void setEditorName(String editorName) {
		setDirtyIf(!Objects.equals(this.editorName, editorName));
		this.editorName = editorName;
	}

	/**
	 * Returns an editor path.
	 * @return Editor path.
	 */
	public String getEditorPath() {
		return this.editorPath;
	}

	/**
	 * Sets an editor path.
	 * @param editorPath Editor path.
	 */
	public void setEditorPath(String editorPath) {
		setDirtyIf(!Objects.equals(this.editorPath, editorPath));
		this.editorPath = editorPath;
	}

	/**
	 * Returns an api key.
	 * @return Api key.
	 */
	public String getApiKey() {
		return this.apiKey;
	}

	/**
	 * Sets an api key.
	 * @param apiKey Api key.
	 */
	public void setApiKey(String apiKey) {
		setDirtyIf(!Objects.equals(this.apiKey, apiKey));
		this.apiKey = apiKey;
	}

	/**
	 * Returns the child map on the spec class with all fields.
	 *
	 * @return Return the field map.
	 */
	@Override
	protected ChildHierarchyNodeFieldMap getChildMap() {
		return FIELDS;
	}

	/**
	 * Creates and returns a deep copy of this object.
	 */
	@Override
	public SettingsSpec clone() {
		try {
			SettingsSpec cloned = (SettingsSpec) super.clone();
			return cloned;
		}
		catch (CloneNotSupportedException ex) {
			throw new RuntimeException("Object cannot be cloned", ex);
		}
	}

	/**
	 * Creates a trimmed and expanded version of the object without unwanted properties, but with all variables like ${ENV_VAR} expanded.
	 * @return Trimmed and expanded version of this object.
	 */
	public SettingsSpec expandAndTrim(SecretValueProvider secretValueProvider) {
		try {
			SettingsSpec cloned = (SettingsSpec) super.clone();

			return cloned;
		}
		catch (CloneNotSupportedException ex) {
			throw new RuntimeException("Object cannot be cloned", ex);
		}
	}

	@Override
	public <P, R> R visit(HierarchyNodeResultVisitor<P, R> visitor, P parameter) {
		return visitor.accept(this, parameter);
	}
}
