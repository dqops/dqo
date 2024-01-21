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
package com.dqops.utils.docs.cli;

import lombok.Data;

import java.util.List;

/**
 * Documentation model for a single parameter (option) of a CLI command.
 */
@Data
public class CliOptionDocumentationModel {
    /**
     * Array of all option names (shorter, longer).
     */
    private List<String> names;

    /**
     * Option description.
     */
    private String[] description;

    /**
     * The option is required.
     */
    private boolean required;

    /**
     * Array of accepted values.
     */
    private String[] acceptedValues;
}
