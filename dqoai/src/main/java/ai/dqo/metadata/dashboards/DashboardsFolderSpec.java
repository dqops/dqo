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
package ai.dqo.metadata.dashboards;

import ai.dqo.metadata.basespecs.AbstractSpec;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMap;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMapImpl;
import ai.dqo.metadata.id.HierarchyNodeResultVisitor;
import ai.dqo.utils.serialization.IgnoreEmptyYamlSerializer;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.EqualsAndHashCode;

import java.util.Objects;
import java.util.function.Consumer;

/**
 * Description of a folder with multiple dashboards or other folders.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class DashboardsFolderSpec extends AbstractSpec implements Cloneable {
    private static final ChildHierarchyNodeFieldMapImpl<DashboardsFolderSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractSpec.FIELDS) {
        {
            put("dashboards", o -> o.dashboards);
            put("folders", o -> o.folders);
        }
    };

    public DashboardsFolderSpec() {
    }

    /**
     * Creates a new dashboard folder given a folder name.
     * @param folderName Folder name.
     */
    public DashboardsFolderSpec(String folderName) {
        this.folderName = folderName;
    }

    @JsonPropertyDescription("Folder name")
    private String folderName;

    @JsonPropertyDescription("List of data quality dashboard at this level.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private DashboardListSpec dashboards = new DashboardListSpec();

    @JsonPropertyDescription("List of data quality dashboard folders at this level.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private DashboardsFolderListSpec folders = new DashboardsFolderListSpec();

    /**
     * Returns the folder name.
     * @return Folder name.
     */
    public String getFolderName() {
        return folderName;
    }

    /**
     * Sets the folder name.
     * @param folderName Folder name.
     */
    public void setFolderName(String folderName) {
        this.setDirtyIf(!Objects.equals(this.folderName, folderName));
        this.folderName = folderName;
    }

    /**
     * Returns a list of dashboards in this folder.
     * @return List of dashboards in this folder.
     */
    public DashboardListSpec getDashboards() {
        return dashboards;
    }

    /**
     * Sets a list of dashboards in this folder.
     * @param dashboards List of dashboards.
     */
    public void setDashboards(DashboardListSpec dashboards) {
        setDirtyIf(!Objects.equals(this.dashboards, dashboards));
        this.dashboards = dashboards;
        propagateHierarchyIdToField(dashboards, "dashboards");
    }

    /**
     * Returns a list dashboard child folders.
     * @return List of dashboard child folders.
     */
    public DashboardsFolderListSpec getFolders() {
        return folders;
    }

    /**
     * Sets a new list of child folders.
     * @param folders List of child folders.
     */
    public void setFolders(DashboardsFolderListSpec folders) {
        setDirtyIf(!Objects.equals(this.folders, folders));
        this.folders = folders;
        propagateHierarchyIdToField(folders, "folders");
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
     * Calls a visitor (using a visitor design pattern) that returns a result.
     *
     * @param visitor   Visitor instance.
     * @param parameter Additional parameter that will be passed back to the visitor.
     */
    @Override
    public <P, R> R visit(HierarchyNodeResultVisitor<P, R> visitor, P parameter) {
        return visitor.accept(this, parameter);
    }

    /**
     * Creates and returns a copy of this object.
     */
    @Override
    public DashboardsFolderSpec clone() {
        try {
            DashboardsFolderSpec cloned = (DashboardsFolderSpec)super.clone();
            if (cloned.dashboards != null) {
                cloned.dashboards = cloned.dashboards.clone();
            }
            if (cloned.folders != null) {
                cloned.folders = cloned.folders.clone();
            }
            return cloned;
        }
        catch (CloneNotSupportedException ex) {
            throw new RuntimeException("Object cannot be cloned.");
        }
    }

    /**
     * Adds a child folder using a fluent interface.
     * @param folderName Child folder name.
     * @param childFolderConfigurer Configurer that accepts the child folder and can add other folders or dashboards.
     * @return Self - for fluent continuation.
     */
    public DashboardsFolderSpec withFolder(String folderName, Consumer<DashboardsFolderSpec> childFolderConfigurer) {
        if (this.folders.stream().anyMatch(f -> Objects.equals(f.getFolderName(), folderName))) {
            throw new IllegalStateException("Folder name '" + folderName + "' was already added");
        }

        DashboardsFolderSpec childFolder = new DashboardsFolderSpec();
        childFolder.setFolderName(folderName);
        if (childFolderConfigurer != null) {
            childFolderConfigurer.accept(childFolder);
        }
        this.folders.add(childFolder);

        return this;
    }

    /**
     * Adds a DQO Cloud dashboard using a fluent interface.
     * @param dashboardName Dashboard name.
     * @param url Looker studio dashboard url.
     * @param width Width in pixels.
     * @param height Height in pixels.
     * @return Self.
     */
    public DashboardsFolderSpec withDqoCloudDashboard(String dashboardName, String url, Integer width, Integer height) {
        if (this.dashboards.stream().anyMatch(d -> Objects.equals(d.getDashboardName(), dashboardName))) {
            throw new IllegalStateException("Dashboard name '" + dashboardName + "' was already added");
        }

        DashboardSpec dashboardSpec = new DashboardSpec() {{
            setDashboardName(dashboardName);
            setUrl(url);
            setWidth(width);
            setHeight(height);
            setDqoCloudCredentials(true);
        }};
        this.dashboards.add(dashboardSpec);

        return this;
    }
}
