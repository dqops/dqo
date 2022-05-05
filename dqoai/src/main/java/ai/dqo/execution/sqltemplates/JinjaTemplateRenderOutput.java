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
package ai.dqo.execution.sqltemplates;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;

import java.util.HashMap;
import java.util.Map;

/**
 * Python template render output.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = false)
public class JinjaTemplateRenderOutput {
    private String template;
    private Map<String, Object> parameters = new HashMap<>();
    private String result;
    private String error;

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
}
