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

import com.dqops.cli.commands.ICommand;
import com.google.api.client.util.Lists;
import org.apache.commons.lang3.StringUtils;
import org.apache.parquet.Strings;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Documentation factory that creates documentation of a single CLI command.
 */
@Component
public class CliCommandDocumentationModelFactoryImpl implements CliCommandDocumentationModelFactory {
    /**
     * Generates a documentation for a single command. May return null for commands that are just intermediate nodes in the command tree
     * and are not callable.
     * @param commandLine Command line for the command.
     * @return Command documentation or null if the command is not callable.
     */
    @Override
    public CliCommandDocumentationModel makeDocumentationForCommand(CommandLine commandLine) {
        Object commandObjectInstance = commandLine.getCommand();
        boolean isCallableCommand = commandObjectInstance instanceof ICommand; // implements a callable model

        if (!isCallableCommand) {
            return null;
        }

        CliCommandDocumentationModel commandModel = new CliCommandDocumentationModel();
        commandModel.setCommandLine(commandLine);

        CommandLine.Model.CommandSpec commandSpec = commandLine.getCommandSpec();
        commandModel.setCommandSpec(commandSpec);
        commandModel.setQualifiedName(commandSpec.qualifiedName().trim());

        CommandLine.Help help = commandLine.getHelp();
        commandModel.setHelp(help);

        String synopsis = help.synopsis(0);
        commandModel.setSynopsis(synopsis);

        CommandLine.Model.UsageMessageSpec usageMessageSpec = commandSpec.usageMessage();
        commandModel.setUsageMessageSpec(usageMessageSpec);
        commandModel.setDescription(usageMessageSpec.description());
        commandModel.setHeader(usageMessageSpec.header());
        commandModel.setCustomSynopsis(usageMessageSpec.customSynopsis());

        List<CommandLine.Model.OptionSpec> options = new ArrayList<>(commandSpec.options());
        options.sort((a, b) -> StringUtils.compare(a.longestName(), b.longestName()));

        for (CommandLine.Model.OptionSpec optionSpec : options) {
            CliOptionDocumentationModel optionDocumentationModel = new CliOptionDocumentationModel();
            optionDocumentationModel.setNames(List.of(optionSpec.names()).stream().map(o -> o.trim()).collect(Collectors.toList()));
            optionDocumentationModel.setDescription(optionSpec.description());
            optionDocumentationModel.setRequired(optionSpec.required());

            Iterable<String> completionCandidates = optionSpec.completionCandidates();
            if (completionCandidates != null) {
                ArrayList<String> completionCandidatesList = Lists.newArrayList(completionCandidates);
                if (completionCandidatesList.size() > 0) {
                    optionDocumentationModel.setAcceptedValues(completionCandidatesList.toArray(String[]::new));
                }
            }

            commandModel.getOptions().add(optionDocumentationModel);
        }

        return commandModel;
    }
}
