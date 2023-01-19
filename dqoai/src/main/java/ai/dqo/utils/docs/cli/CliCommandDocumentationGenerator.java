package ai.dqo.utils.docs.cli;

import ai.dqo.utils.docs.files.DocumentationFolder;

import java.nio.file.Path;

/**
 * Generates documentation about CLI commands.
 */
public interface CliCommandDocumentationGenerator {
    /**
     * Generates documentation of all commands.
     *
     * @param projectRootPath Project root path, used to identify the target file paths for generated documentation articles.
     * @return Documentation folder tree with generated documentation as markdown files.
     */
    DocumentationFolder generateDocumentationForCliCommands(Path projectRootPath);
}
