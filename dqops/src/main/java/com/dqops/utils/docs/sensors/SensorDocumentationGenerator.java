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
package com.dqops.utils.docs.sensors;

import com.dqops.metadata.dqohome.DqoHome;
import com.dqops.utils.docs.HandledClassesLinkageStore;
import com.dqops.utils.docs.files.DocumentationFolder;

import java.nio.file.Path;

/**
 * Sensor documentation generator that generates documentation for sensors.
 */
public interface SensorDocumentationGenerator {
    /**
     * Renders documentation for all sensors as markdown files.
     *
     * @param projectRootPath Path to the project root folder, used to find the target/classes folder and scan for classes.
     * @param linkageStore
     * @param dqoHome         DQOps home.
     * @return Folder structure with rendered markdown files.
     */
    DocumentationFolder renderSensorDocumentation(Path projectRootPath, HandledClassesLinkageStore linkageStore, DqoHome dqoHome);
}
