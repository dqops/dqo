package ai.dqo.utils.docs.cli;

import ai.dqo.cli.commands.DqoRootCliCommand;
import ai.dqo.utils.docs.HandlebarsDocumentationUtilities;
import ai.dqo.utils.docs.files.DocumentationFolder;
import ai.dqo.utils.docs.files.DocumentationMarkdownFile;
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
        cliFolder.setFolderName("cli");
        cliFolder.setLinkName("CLI Command");
        cliFolder.setDirectPath(projectRootPath.resolve("../docs/cli").toAbsolutePath().normalize());

        Template template = HandlebarsDocumentationUtilities.compileTemplate("cli/cli_documentation");

        List<CliRootCommandDocumentationModel> commandModels = new ArrayList<>(createCommandModels());
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
        Map<String, CommandLine> firstLevelCommands = commandLine.getSubcommands();
        for (Map.Entry<String, CommandLine> subcommand : firstLevelCommands.entrySet()) {
            CliRootCommandDocumentationModel rootCommandModel = new CliRootCommandDocumentationModel();
            rootCommandModel.setRootCommandName(subcommand.getKey());
            rootCommands.add(rootCommandModel);

            collectSubCommands(rootCommandModel, subcommand.getValue());
        }

        return rootCommands;
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
