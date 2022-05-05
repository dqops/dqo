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
package ai.dqo.execution.checks.progress;

import ai.dqo.execution.sqltemplates.JinjaTemplateRenderInput;
import ai.dqo.execution.sqltemplates.JinjaTemplateRenderOutput;

/**
 * Progress event raised after an SQL template was rendered from a Jinja2 template.
 */
public class SqlTemplateRenderedRendered extends CheckExecutionProgressEvent {
    private final JinjaTemplateRenderInput input;
    private final JinjaTemplateRenderOutput output;

    /**
     * Creates a progress event.
     *
     * @param input  Input object with the template to be rendered.
     * @param output Rendered SQL or an error message.
     */
    public SqlTemplateRenderedRendered(JinjaTemplateRenderInput input, JinjaTemplateRenderOutput output) {
        this.input = input;
        this.output = output;
    }

    /**
     * Input object passed to the SQL rendering script.
     *
     * @return Input object.
     */
    public JinjaTemplateRenderInput getInput() {
        return input;
    }

    /**
     * SQL template rendering result - the rendered template or an error (stack trace).
     *
     * @return Rendered SQL or an error.
     */
    public JinjaTemplateRenderOutput getOutput() {
        return output;
    }
}
