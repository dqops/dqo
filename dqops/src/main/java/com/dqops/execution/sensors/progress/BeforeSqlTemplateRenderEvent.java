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

/**
 * Progress event raised before an SQL template is expanded (rendered).
 */
public class BeforeSqlTemplateRenderEvent extends CheckExecutionProgressEvent {
    private final JinjaTemplateRenderInput inputDto;

    /**
     * Creates a progress event.
     *
     * @param inputDto Input object passed to the template rendering script.
     */
    public BeforeSqlTemplateRenderEvent(JinjaTemplateRenderInput inputDto) {
        this.inputDto = inputDto;
    }

    /**
     * Input object passed to the template rendering script.
     *
     * @return Input object passed to the template rendering script.
     */
    public JinjaTemplateRenderInput getInputDto() {
        return inputDto;
    }
}
