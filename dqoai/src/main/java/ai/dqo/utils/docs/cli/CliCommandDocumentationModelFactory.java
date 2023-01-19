package ai.dqo.utils.docs.cli;

import picocli.CommandLine;

/**
 * Documentation factory that creates documentation of a single CLI command.
 */
public interface CliCommandDocumentationModelFactory {
    /**
     * Generates a documentation for a single command. May return null for commands that are just intermediate nodes in the command tree
     * and are not callable.
     *
     * @param commandLine Command line for the command.
     * @return Command documentation or null if the command is not callable.
     */
    CliCommandDocumentationModel makeDocumentationForCommand(CommandLine commandLine);
}
