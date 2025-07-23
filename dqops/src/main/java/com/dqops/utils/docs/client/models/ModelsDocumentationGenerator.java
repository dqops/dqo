/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.utils.docs.client.models;

import com.dqops.utils.docs.client.MainPageClientDocumentationModel;
import com.dqops.utils.docs.client.apimodel.ComponentModel;
import com.dqops.utils.docs.files.DocumentationFolder;

import java.nio.file.Path;
import java.util.Collection;

/**
 * Models documentation generator that generate documentation for client models shared between controllers.
 */
public interface ModelsDocumentationGenerator {
    // TODO: add javadocs
    DocumentationFolder renderModelsDocumentation(Path projectRootPath,
                                                  Collection<ComponentModel> componentModels,
                                                  MainPageClientDocumentationModel mainPageModel);
}
