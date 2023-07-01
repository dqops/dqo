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
