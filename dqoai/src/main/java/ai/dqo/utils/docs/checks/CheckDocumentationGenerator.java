package ai.dqo.utils.docs.checks;

import ai.dqo.utils.docs.files.DocumentationFolder;

import java.nio.file.Path;

/**
 * Generates documentation about checks.
 */
public interface CheckDocumentationGenerator {
    /**
     * Generates documentation of all checks.
     *
     * @param projectRootPath Project root path, used to identify the target file paths for generated documentation articles.
     * @return Documentation folder tree with generated documentation as markdown files.
     */
    DocumentationFolder renderCheckDocumentation(Path projectRootPath);
}
