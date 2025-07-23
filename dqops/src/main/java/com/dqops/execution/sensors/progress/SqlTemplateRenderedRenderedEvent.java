/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.execution.sensors.progress;

import com.dqops.execution.checks.progress.CheckExecutionProgressEvent;
import com.dqops.execution.sqltemplates.rendering.JinjaTemplateRenderInput;
import com.dqops.execution.sqltemplates.rendering.JinjaTemplateRenderOutput;

/**
 * Progress event raised after an SQL template was rendered from a Jinja2 template.
 */
public class SqlTemplateRenderedRenderedEvent extends CheckExecutionProgressEvent {
    private final JinjaTemplateRenderInput input;
    private final JinjaTemplateRenderOutput output;

    /**
     * Creates a progress event.
     *
     * @param input  Input object with the template to be rendered.
     * @param output Rendered SQL or an error message.
     */
    public SqlTemplateRenderedRenderedEvent(JinjaTemplateRenderInput input, JinjaTemplateRenderOutput output) {
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
