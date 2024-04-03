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
package com.dqops.execution.sqltemplates.rendering;

import com.dqops.metadata.storage.localfiles.HomeType;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;

import java.time.Instant;

/**
 * Json object that is sent to the python script that renders sql templates.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = false)
public class JinjaTemplateRenderInput {
    private String templateText;
    private Instant templateLastModified;
    private String userHomePath;
    private HomeType homeType;
    private String templateHomePath;
    private JinjaTemplateRenderParameters parameters;

    /**
     * Jinja2 template as a string.
     * @return Template as a string.
     */
    public String getTemplateText() {
        return templateText;
    }

    /**
     * Sets a Jinja2 template as a string.
     * @param templateText Template as a string.
     */
    public void setTemplateText(String templateText) {
        this.templateText = templateText;
    }

    /**
     * Returns the file timestamp when the template was modified for the last time.
     * This timestamp is used by the rendering engine to decide if the cached template is still valid.
     * @return Returns the timestamp when the template was last modified.
     */
    public Instant getTemplateLastModified() {
        return templateLastModified;
    }

    /**
     * Sets the timestamp when the template was last modified.
     * @param templateLastModified Timestamp when the template was last modified.
     */
    public void setTemplateLastModified(Instant templateLastModified) {
        this.templateLastModified = templateLastModified;
    }

    /**
     * Absolute path to the user home. Not null when a user home exists on the local disk and it is not a virtual file system.
     * @return Absolute path to the user home.
     */
    public String getUserHomePath() {
        return userHomePath;
    }

    /**
     * Sets the absolute path to the user home.
     * @param userHomePath Absolute path to the user home.
     */
    public void setUserHomePath(String userHomePath) {
        this.userHomePath = userHomePath;
    }

    /**
     * Get the home type (user home or dqo home) where the sql template was found.
     * @return Home type where the template is defined.
     */
    public HomeType getHomeType() {
        return homeType;
    }

    /**
     * Sets the home type where the template is defined.
     * @param homeType Home type.
     */
    public void setHomeType(HomeType homeType) {
        this.homeType = homeType;
    }

    /**
     * Gets the home relative path to the jinja2 file template. Not null only when the template is disk based and the template should be loaded directly from the disk by Jinja2 loader.
     * @return Relative path to the jinja2 template. The path is relative to the user home folder or dqo home, depending on the home type.
     */
    public String getTemplateHomePath() {
        return templateHomePath;
    }

    /**
     * Sets the relative path to the template file.
     * @param templateHomePath Relative path to the template file.
     */
    public void setTemplateHomePath(String templateHomePath) {
        this.templateHomePath = templateHomePath;
    }

    /**
     * Jinja template render parameters. Contains specifications of the connection, table, column, sensor parameters, sensor definition, provider sensor definition.
     * @return Template parameters that is sent to the template.
     */
    public JinjaTemplateRenderParameters getParameters() {
        return parameters;
    }

    /**
     * Sets the parameters that are used by the jinja2 template.
     * @param parameters Parameters to use.
     */
    public void setParameters(JinjaTemplateRenderParameters parameters) {
        this.parameters = parameters;
    }
}
