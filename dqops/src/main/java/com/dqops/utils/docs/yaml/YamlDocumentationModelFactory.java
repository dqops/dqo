/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.utils.docs.yaml;

import java.util.List;

/**
 * Yaml documentation model factory that creates a yaml documentation.
 * It should be only used from post processor classes that are called by Maven during build.
 */
public interface YamlDocumentationModelFactory {
    /**
     * Create a yaml documentation models.
     * @param yamlDocumentationSchema Yaml documentation schema.
     * @return Yaml superior documentation models.
     */
    List<YamlSuperiorObjectDocumentationModel> createDocumentationForYaml(List<YamlDocumentationSchemaNode> yamlDocumentationSchema);
}
