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

import java.util.ArrayList;
import java.util.List;

/**
 * Documentation model about one single CLI command.
 */
@Data
public class CliRootCommandDocumentationModel {
    /**
     * The name of the first command.
     */
    private String rootCommandName;

    /**
     * The header of the root command.
     */
    private String rootCommandHeader;

    /**
     * The description of the root command.
     */
    private String rootCommandDescription;

    /**
     * List of commands.
     */
    private List<CliCommandDocumentationModel> commands = new ArrayList<>();
}
