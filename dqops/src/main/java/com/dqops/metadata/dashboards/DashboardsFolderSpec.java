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
package com.dqops.metadata.dashboards;

import com.dqops.metadata.basespecs.AbstractSpec;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMap;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.dqops.metadata.id.HierarchyNode;
import com.dqops.metadata.id.HierarchyNodeResultVisitor;
import com.dqops.utils.exceptions.DqoRuntimeException;
import com.dqops.utils.serialization.IgnoreEmptyYamlSerializer;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.StringUtils;

import java.util.LinkedHashMap;
import java.util.Map;
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

    @JsonPropertyDescription("Always shows this schema tree node because it contains standard dashboards. Set the value to false to show this folder only when advanced dashboards are enabled.")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private boolean standard;

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
     * Returns the flag if the folder contains standard dashboard and should be always shown.
     * @return True when the folder contains standard dashboards.
     */
    public boolean isStandard() {
        return standard;
    }

    /**
     * Set the flag to show this folder always, not only when advanced dashboards are enabled.
     * @param standard Folder is shown always.
     */
    public void setStandard(boolean standard) {
        this.setDirtyIf(this.standard != standard);
        this.standard = standard;
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
    public DashboardsFolderSpec deepClone() {
        DashboardsFolderSpec cloned = (DashboardsFolderSpec)super.deepClone();
        return cloned;
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
     * Adds a DQOps Cloud dashboard using a fluent interface.
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
        }};
        this.dashboards.add(dashboardSpec);

        return this;
    }

    /**
     * Adds a DQOps Cloud dashboard using a fluent interface.
     * @param dashboardName Dashboard name.
     * @param url Looker studio dashboard url.
     * @param width Width in pixels.
     * @param height Height in pixels.
     * @param parameters Dictionary of additional parameters.
     * @return Self.
     */
    public DashboardsFolderSpec withDqoCloudDashboard(String dashboardName, String url, Integer width, Integer height,
                                                      LinkedHashMap<String, String> parameters) {
        if (this.dashboards.stream().anyMatch(d -> Objects.equals(d.getDashboardName(), dashboardName))) {
            throw new IllegalStateException("Dashboard name '" + dashboardName + "' was already added");
        }

        DashboardSpec dashboardSpec = new DashboardSpec() {{
            setDashboardName(dashboardName);
            setUrl(url);
            setWidth(width);
            setHeight(height);
            setParameters(parameters);
        }};
        this.dashboards.add(dashboardSpec);

        return this;
    }

    /**
     * Collects all similar dashboards from all subfolders.
     * @param allSimilarDashboardsContainer Target container where the dashboards should be added.
     * @param rootNode Root node, used to resolve folder names.
     */
    public void collectSimilarDashboards(AllSimilarDashboardsContainer allSimilarDashboardsContainer, HierarchyNode rootNode) {
        for (DashboardSpec dashboardSpec : this.getDashboards()) {
            allSimilarDashboardsContainer.addDashboard(dashboardSpec, rootNode);
        }

        this.getFolders().collectSimilarDashboards(allSimilarDashboardsContainer, rootNode);
    }

    /**
     * Creates a copy of this object, copying all dashboards and folders. Expands templated dashboards.
     * Expanding templated dashboards means that when a dashboard has a parameter in the form: "*default_value,value2,value3*", then
     * a dashboard with the parameter value default_value (the first from the list) is used for the dashboard in the root folder, while variants of the dashboard
     * with the parameters from the list are created in a nested folder named after the parameter value.
     * @return New dashboard folder with additional expanded dashboards.
     */
    public DashboardsFolderSpec createExpandedDashboardFolder() {
        DashboardsFolderSpec expandedFolder = new DashboardsFolderSpec(this.folderName);
        expandedFolder.setStandard(this.standard);
        expandedFolder.setFolders(this.getFolders().createExpandedDashboardTree()); // replacing with an expanded list
        expandedFolder.getFolders().setFileLastModified(this.getFolders().getFileLastModified());

        for (DashboardSpec templatedDashboardSpec : this.getDashboards()) {
            LinkedHashMap<String, String> defaultParameters = createDefaultParameters(templatedDashboardSpec);
            DashboardSpec rootDashboard = templatedDashboardSpec.deepClone();
            rootDashboard.setParameters(defaultParameters);
            expandedFolder.getDashboards().add(rootDashboard);

            addExpandedDashboards(expandedFolder, templatedDashboardSpec, defaultParameters);
        }

        return expandedFolder;
    }

    /**
     * Adds expanded dashboards to the target folder <code>expandedFolder</code>.
     * @param targetFolder Target folder where child folders will be created.
     * @param templatedDashboardSpec Templated dashboard that will be expanded.
     * @param defaultParameters A list of default parameters (using just the first parameter value for all other parameters.
     */
    protected void addExpandedDashboards(DashboardsFolderSpec targetFolder, DashboardSpec templatedDashboardSpec, LinkedHashMap<String, String> defaultParameters) {
        if (templatedDashboardSpec.getParameters() == null || templatedDashboardSpec.getParameters().size() == 0) {
            return;
        }

        for (Map.Entry<String, String> templatedDashboardParameterEntry : templatedDashboardSpec.getParameters().entrySet()) {
            String parameterName = templatedDashboardParameterEntry.getKey();
            String templateParameterValue = templatedDashboardParameterEntry.getValue();
            if (!(templateParameterValue.startsWith("$") || templateParameterValue.startsWith("*$")) && !templateParameterValue.endsWith("$")) {
                continue; // not a templated (multi-value) parameter
            }

            boolean multiplicableParameter = templateParameterValue.startsWith("*$");
            if (multiplicableParameter) {
                templateParameterValue = templateParameterValue.substring(1);
            }

            int indexOfEndOfLabel = templateParameterValue.indexOf(':');
            String parameterDisplayName = templateParameterValue.substring(1, indexOfEndOfLabel);
            String parameterValuesListString = templateParameterValue.substring(indexOfEndOfLabel + 1, templateParameterValue.length() - 1);
            int indexOfFirstAlternative = parameterValuesListString.indexOf(',');
            String[] alternativeParameterValues = StringUtils.split(parameterValuesListString.substring(indexOfFirstAlternative + 1), ',');

            String childFolderName = templatedDashboardSpec.getDashboardName() +
                    (templatedDashboardSpec.getDashboardName().contains(" per ") ? ", " : " per ") +
                    parameterDisplayName;
            DashboardsFolderSpec childFolderForExpandedDashboards = targetFolder.getFolders().getOrCreateChildFolder(childFolderName);

            for (String alternativeParameterValue : alternativeParameterValues) {
                LinkedHashMap<String, String> addedDashboardParameters = (LinkedHashMap<String, String>) defaultParameters.clone();
                DashboardSpec alternateDashboard = templatedDashboardSpec.deepClone();
                alternateDashboard.setDashboardName(templatedDashboardSpec.getDashboardName() + " - " + alternativeParameterValue);
                addedDashboardParameters.put(parameterName, alternativeParameterValue);
                alternateDashboard.setParameters(addedDashboardParameters);
                childFolderForExpandedDashboards.getDashboards().add(alternateDashboard);

                if (multiplicableParameter) {
                    DashboardSpec nestedTemplate = templatedDashboardSpec.deepClone();
                    nestedTemplate.setDashboardName(nestedTemplate.getDashboardName() + " (" + alternativeParameterValue + ")");
                    LinkedHashMap<String, String> nestedDefaultParameters = new LinkedHashMap<>(templatedDashboardSpec.getParameters());
                    nestedDefaultParameters.put(parameterName, alternativeParameterValue);
                    nestedTemplate.setParameters(nestedDefaultParameters);

                    addExpandedDashboards(childFolderForExpandedDashboards, nestedTemplate, addedDashboardParameters);
                }
            }
        }
    }

    /**
     * Creates the default parameters for the root dashboards. This dictionary of parameters will be cloned to generate alternative dashboards.
     * @param templatedDashboardSpec Templated dashboard specification.
     * @return Dictionary of parameter values, picking only the first (default) value from the list of alternative parameter values.
     */
    protected LinkedHashMap<String, String> createDefaultParameters(DashboardSpec templatedDashboardSpec) {
        LinkedHashMap<String, String> defaultParameters = new LinkedHashMap<>();

        if (templatedDashboardSpec.getParameters() == null || templatedDashboardSpec.getParameters().size() == 0) {
            return defaultParameters;
        }

        for (Map.Entry<String, String> templatedDashboardParameterEntry : templatedDashboardSpec.getParameters().entrySet()) {
            String parameterName = templatedDashboardParameterEntry.getKey();
            String templateParameterValue = templatedDashboardParameterEntry.getValue();
            if ((templateParameterValue.startsWith("$") || templateParameterValue.startsWith("*$")) && templateParameterValue.endsWith("$")) {
                if (templateParameterValue.startsWith("*")) {
                    templateParameterValue.substring(1);
                }

                int indexOfEndOfLabel = templateParameterValue.indexOf(':');
                if (indexOfEndOfLabel < 0) {
                    throw new DqoRuntimeException("Invalid format of a templated parameter value, must be: $Label:defaultvalue,value2,value3,value4$, but was: " + templateParameterValue);
                }
                String parameterValuesListString = templateParameterValue.substring(indexOfEndOfLabel + 1, templateParameterValue.length() - 1);
                int indexOfFirstAlternative = parameterValuesListString.indexOf(',');
                String firstParameterValue = parameterValuesListString.substring(0, indexOfFirstAlternative < 0 ? parameterValuesListString.length() : indexOfFirstAlternative);
                defaultParameters.put(parameterName, firstParameterValue);
            } else {
                defaultParameters.put(parameterName, templateParameterValue);
            }
        }

        return defaultParameters;
    }

    /**
     * Sorts the dashboards and nested folders alphabetically, by name.
     */
    public void sort() {
        this.folders.sort();
        this.dashboards.sort();
    }

    /**
     * Merges the current folder with the <code>otherFolder</code>, adding or overriding dashboard configuration.
     * @param otherFolder The other dashboard configuration, from the DQOps user home, containing user overwritten dashboards.
     * @return Merged folder that includes current folders and dashboards, merged with the other dashboards.
     */
    public DashboardsFolderSpec merge(DashboardsFolderSpec otherFolder) {
        DashboardsFolderSpec cloned = new DashboardsFolderSpec();
        cloned.setFolderName(this.folderName);
        cloned.setStandard(this.standard || otherFolder.standard);
        if (this.getHierarchyId() != null) {
            cloned.setHierarchyId(this.getHierarchyId());
        }

        cloned.setFolders(this.folders.merge(otherFolder.folders));
        cloned.setDashboards(this.dashboards.merge(otherFolder.dashboards));

        return cloned;
    }
}
