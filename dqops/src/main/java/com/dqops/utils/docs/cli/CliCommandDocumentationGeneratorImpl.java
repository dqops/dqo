/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.utils.docs.cli;

import com.dqops.cli.commands.DqoRootCliCommand;
import com.dqops.utils.docs.HandlebarsDocumentationUtilities;
import com.dqops.utils.docs.files.DocumentationFolder;
import com.dqops.utils.docs.files.DocumentationMarkdownFile;
import com.github.jknack.handlebars.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Generates documentation about CLI commands.
 */
@Component
public class CliCommandDocumentationGeneratorImpl implements CliCommandDocumentationGenerator {
    private final CliCommandDocumentationModelFactory commandDocumentationModelFactory;

    @Autowired
    public CliCommandDocumentationGeneratorImpl(CliCommandDocumentationModelFactory commandDocumentationModelFactory) {
        this.commandDocumentationModelFactory = commandDocumentationModelFactory;
    }

    /**
     * Generates documentation of all commands.
     * @param projectRootPath Project root path, used to identify the target file paths for generated documentation articles.
     * @return Documentation folder tree with generated documentation as markdown files.
     */
    @Override
    public DocumentationFolder generateDocumentationForCliCommands(Path projectRootPath) {
        DocumentationFolder cliFolder = new DocumentationFolder();
        cliFolder.setFolderName("command-line-interface");
        cliFolder.setLinkName("Command-line interface");
        cliFolder.setDirectPath(projectRootPath.resolve("../docs/command-line-interface").toAbsolutePath().normalize());

        List<CliRootCommandDocumentationModel> commandModels = new ArrayList<>(createCommandModels());

        MainPageCliCommandDocumentationModel mainPageCliCommandDocumentationModel = new MainPageCliCommandDocumentationModel();
        mainPageCliCommandDocumentationModel.setAllCommands(commandModels);

        Template main_page_template = HandlebarsDocumentationUtilities.compileTemplate("cli/main_page_documentation");
        DocumentationMarkdownFile mainPageDocumentationMarkdownFile = cliFolder.addNestedFile("index" + ".md");
        mainPageDocumentationMarkdownFile.setRenderContext(mainPageCliCommandDocumentationModel);

        String renderedMainPageDocument = HandlebarsDocumentationUtilities.renderTemplate(main_page_template, mainPageCliCommandDocumentationModel);
        mainPageDocumentationMarkdownFile.setFileContent(renderedMainPageDocument);

        Template template = HandlebarsDocumentationUtilities.compileTemplate("cli/cli_documentation");

        for (CliRootCommandDocumentationModel command : commandModels) {

            DocumentationMarkdownFile documentationMarkdownFile = cliFolder.addNestedFile(command.getRootCommandName() + ".md");
            documentationMarkdownFile.setRenderContext(command);

            String renderedDocument = HandlebarsDocumentationUtilities.renderTemplate(template, command);
            documentationMarkdownFile.setFileContent(renderedDocument);
        }
        return cliFolder;
    }

    /**
     * Create a list of root commands used for the documentation.
     * @return List of root commands and their subcommands.
     */
    public List<CliRootCommandDocumentationModel> createCommandModels() {
        List<CliRootCommandDocumentationModel> rootCommands = new ArrayList<>();
        CommandLine commandLine = new CommandLine(new DqoRootCliCommand(null, null, null));
        rootCommands.add(createRootDqoCommandModel(commandLine));
        Map<String, CommandLine> firstLevelCommands = commandLine.getSubcommands();
        for (Map.Entry<String, CommandLine> subcommand : firstLevelCommands.entrySet()) {
            CliRootCommandDocumentationModel rootCommandModel = new CliRootCommandDocumentationModel();
            rootCommandModel.setRootCommandName(subcommand.getKey());
            rootCommandModel.setRootCommandHeader(extractRootCommandHeader(subcommand.getValue()));
            rootCommandModel.setRootCommandDescription(extractRootCommandDescription(subcommand.getValue()));
            rootCommands.add(rootCommandModel);

            collectSubCommands(rootCommandModel, subcommand.getValue());
        }

        return rootCommands;
    }

    /**
     * Retrieves the header from the root command.
     * @param commandLine Command line.
     * @return Command header.
     */
    public String extractRootCommandHeader(CommandLine commandLine) {
        CommandLine.Model.CommandSpec commandSpec = commandLine.getCommandSpec();

        CommandLine.Model.UsageMessageSpec usageMessageSpec = commandSpec.usageMessage();
        String[] header = usageMessageSpec.header();
        if (header == null) {
            return null;
        }

        return String.join(" ", header);
    }

    /**
     * Retrieves the description from the root command.
     * @param commandLine Command line.
     * @return Command description.
     */
    public String extractRootCommandDescription(CommandLine commandLine) {
        CommandLine.Model.CommandSpec commandSpec = commandLine.getCommandSpec();

        CommandLine.Model.UsageMessageSpec usageMessageSpec = commandSpec.usageMessage();
        String[] description = usageMessageSpec.description();
        if (description == null) {
            return null;
        }

        return String.join(" ", description);
    }

    /**
     * Create 'dqo' root command used for the documentation.
     * @return Root commands and their subcommands.
     */
    private CliRootCommandDocumentationModel createRootDqoCommandModel(CommandLine commandLine) {
        CliRootCommandDocumentationModel rootDqoCommandModel = new CliRootCommandDocumentationModel();
        rootDqoCommandModel.setRootCommandName("dqo");
        rootDqoCommandModel.setRootCommandHeader(extractRootCommandHeader(commandLine));
        rootDqoCommandModel.setRootCommandDescription(extractRootCommandDescription(commandLine));
        CliCommandDocumentationModel myCommandModel = this.commandDocumentationModelFactory.makeDocumentationForCommand(commandLine);
        myCommandModel.setHeader(new String[]{"DQOps command-line entry point script"});
        myCommandModel.setDescription(new String[]{"*dqo* is an executable script installed in the Python scripts local folder when DQOps is installed locally by installing the *dqops* package from PyPi. " +
                "When the python environment Scripts folder is in the path, running *dqo* from the command line (bash, etc.) will start a DQOps local instance."});
        rootDqoCommandModel.getCommands().add(myCommandModel);

        return rootDqoCommandModel;
    }

    /**
     * Iterates over child commands and generates documentation for all of them.
     * @param rootCommandModel Root command model that should be filed with child commands.
     * @param rootCommandLine Command line object for the root command.
     */
    public void collectSubCommands(CliRootCommandDocumentationModel rootCommandModel, CommandLine rootCommandLine) {
        Map<String, CommandLine> childCommands = rootCommandLine.getSubcommands();

        CliCommandDocumentationModel myCommandModel = this.commandDocumentationModelFactory.makeDocumentationForCommand(rootCommandLine);
        if (myCommandModel != null) {
            rootCommandModel.getCommands().add(myCommandModel);
        }

        for (Map.Entry<String, CommandLine> childCommand : childCommands.entrySet()) {
            collectSubCommands(rootCommandModel, childCommand.getValue());
        }
    }
}
