package ai.dqo.utils.docs.yaml;

import ai.dqo.utils.docs.files.DocumentationFolder;

import java.nio.file.Path;

/**
 * Yaml documentation generator that generate documentation for yaml.
 */
public interface YamlDocumentationGenerator {
    /**
     * Renders documentation for all yaml classes as markdown files.
     *
     * @param projectRootPath Path to the project root folder, used to find the target/classes folder and scan for classes.
     * @return Folder structure with rendered markdown files.
     */
    DocumentationFolder renderYamlDocumentation(Path projectRootPath);
}
