/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.execution.sqltemplates.rendering;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Python template render output.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = false)
public class JinjaTemplateRenderOutput {
    private String template;
    private Map<String, Object> parameters = new LinkedHashMap<>();
    private String result;
    private String error;
    private Integer totalProcessingMillis;
    private Integer receivingMillis;
    private Integer parsingTemplateMillis;
    private Integer renderingMillis;

    /**
     * Jinja2 template as a string that was rendered.
     * @return Template as a string.
     */
    public String getTemplate() {
        return template;
    }

    /**
     * Sets a Jinja2 template as a string.
     * @param template Template as a string.
     */
    public void setTemplate(String template) {
        this.template = template;
    }

    /**
     * Gets a dictionary of additional parameters.
     * @return Dictionary of additional parameters.
     */
    public Map<String, Object> getParameters() {
        return parameters;
    }

    /**
     * Sets a new dictionary of additional parameters.
     * @param parameters Dictionary of additional parameters.
     */
    public void setParameters(Map<String, Object> parameters) {
        this.parameters = parameters;
    }

    /**
     * Returns a rendered template as a string. The value is not null when rendering was successful.
     * @return Rendered template.
     */
    public String getResult() {
        return result;
    }

    /**
     * Sets a rendered template content.
     * @param result Rendered template content.
     */
    public void setResult(String result) {
        this.result = result;
    }

    /**
     * Returns the text of the exception if the template rendering failed. This field is null and the result is provided
     * on successful template expansion. In case of a failure (template rendering errors), the error will be returned in this property.
     * @return Optional error text.
     */
    public String getError() {
        return error;
    }

    /**
     * Sets the error text.
     * @param error Error text.
     */
    public void setError(String error) {
        this.error = error;
    }

    /**
     * Returns the total time spent by the template from the moment of receiving the beginning of the request message until the response could be sent back to the client.
     * @return Total time spent in processing, given in milliseconds.
     */
    public Integer getTotalProcessingMillis() {
        return totalProcessingMillis;
    }

    /**
     * Sets the total time spent in processing.
     * @param totalProcessingMillis Total time spent in processing.
     */
    public void setTotalProcessingMillis(Integer totalProcessingMillis) {
        this.totalProcessingMillis = totalProcessingMillis;
    }

    /**
     * Returns the total time spent in parsing the json parameters for the rendering operation, given in milliseconds.
     * @return Milliseconds spends in parsing the rendering command.
     */
    public Integer getReceivingMillis() {
        return receivingMillis;
    }

    /**
     * Sets the time in milliseconds, spent in parsing the request message.
     * @param receivingMillis  Parsing time milliseconds.
     */
    public void setReceivingMillis(Integer receivingMillis) {
        this.receivingMillis = receivingMillis;
    }

    /**
     * Returns the time spent at parsing the Jinja2 template, given in milliseconds.
     * @return Time spent in parsing the template.
     */
    public Integer getParsingTemplateMillis() {
        return parsingTemplateMillis;
    }

    /**
     * Sets the time spent in parsing the template.
     * @param parsingTemplateMillis Time spent in parsing the template.
     */
    public void setParsingTemplateMillis(Integer parsingTemplateMillis) {
        this.parsingTemplateMillis = parsingTemplateMillis;
    }

    /**
     * Returns the time spent in rendering the template, given in milliseconds.
     * @return Time spent in rendering the template.
     */
    public Integer getRenderingMillis() {
        return renderingMillis;
    }

    /**
     * Sets the time spent in rendering the template.
     * @param renderingMillis Time spent in rendering the template.
     */
    public void setRenderingMillis(Integer renderingMillis) {
        this.renderingMillis = renderingMillis;
    }
}
