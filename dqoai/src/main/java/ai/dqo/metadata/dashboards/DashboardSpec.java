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
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;

import java.util.Objects;

/**
 * Description of a single dashboard that is available in the platform.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class DashboardSpec extends AbstractSpec implements Cloneable {
    private static final ChildHierarchyNodeFieldMapImpl<DashboardSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractSpec.FIELDS) {
        {
        }
    };

    @JsonPropertyDescription("Dashboard name")
    private String dashboardName;

    @JsonPropertyDescription("Dashboard url")
    private String url;

    @JsonPropertyDescription("Dashboard width (px)")
    private Integer width;

    @JsonPropertyDescription("Dashboard height (px)")
    private Integer height;


    @JsonPropertyDescription("The dashboard is a DQO Cloud dashboard (Looker Studio) that requires DQO Cloud credentials to access the private data quality data lake")
    private boolean dqoCloudCredentials = true;

    /**
     * Returns the name of the dashboard.
     * @return Dashboard name.
     */
    public String getDashboardName() {
        return dashboardName;
    }

    /**
     * Sets the name of the dashboard.
     * @param dashboardName Dashboard name.
     */
    public void setDashboardName(String dashboardName) {
        this.setDirtyIf(!Objects.equals(this.dashboardName, dashboardName));
        this.dashboardName = dashboardName;
    }

    /**
     * Returns the url of the dashboard.
     * @return Url of the dashboard.
     */
    public String getUrl() {
        return url;
    }

    /**
     * Sets the url to the dashboard.
     * @param url Url of the dashboard.
     */
    public void setUrl(String url) {
        this.setDirtyIf(!Objects.equals(this.url, url));
        this.url = url;
    }

    /**
     * Returns the dashboard width in pixels.
     * @return Dashboard width.
     */
    public Integer getWidth() {
        return width;
    }

    /**
     * Sets the dashboard width in pixels.
     * @param width Dashboard width.
     */
    public void setWidth(Integer width) {
        this.setDirtyIf(!Objects.equals(this.width, width));
        this.width = width;
    }

    /**
     * Returns the dashboard height in pixels.
     * @return Dashboard height.
     */
    public Integer getHeight() {
        return height;
    }

    /**
     * Sets the dashboard height in pixels.
     * @param height Dashboard height.
     */
    public void setHeight(Integer height) {
        this.setDirtyIf(!Objects.equals(this.height, height));
        this.height = height;
    }

    /**
     * Returns true if this is a Looker Studio dashboard that uses the private data lake.
     * @return True when the dashboard uses a private data lake and requires the credentials.
     */
    public boolean isDqoCloudCredentials() {
        return dqoCloudCredentials;
    }

    /**
     * Sets the flag if this is a Looker Studio dashboard that uses the DQO Looker Studio connector.
     * @param dqoCloudCredentials True when DQO Cloud credentials are required.
     */
    public void setDqoCloudCredentials(boolean dqoCloudCredentials) {
        this.setDirtyIf(this.dqoCloudCredentials != dqoCloudCredentials);
        this.dqoCloudCredentials = dqoCloudCredentials;
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
    public DashboardSpec clone() {
        try {
            DashboardSpec cloned = (DashboardSpec)super.clone();
            return cloned;
        }
        catch (CloneNotSupportedException ex) {
            throw new RuntimeException("Object cannot be cloned.");
        }
    }
}
