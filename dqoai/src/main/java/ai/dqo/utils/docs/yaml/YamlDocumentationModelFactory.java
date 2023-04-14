package ai.dqo.utils.docs.yaml;

import java.util.List;

/**
 * Yaml documentation model factory that creates a yaml documentation.
 * It should be only used from post processor classes that are called by Maven during build.
 */
public interface YamlDocumentationModelFactory {
    /**
     * Create a yaml documentation models.
     *
     * @return Yaml superior documentation models.
     */
    List<YamlSuperiorObjectDocumentationModel> createDocumentationForYaml();
}
