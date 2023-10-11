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
package com.dqops.metadata.storage.localfiles.fileindices;

import com.dqops.core.filesystem.ApiVersion;
import com.dqops.metadata.fileindices.FileIndexSpec;
import com.dqops.metadata.storage.localfiles.SpecificationKind;

/**
 * File index definition file that defines a tree of files that were synchronized with the DQOps Cloud.
 */
public class FileIndexJson {
    private String apiVersion = ApiVersion.CURRENT_API_VERSION;
    private SpecificationKind kind = SpecificationKind.FILE_INDEX;
    private FileIndexSpec spec = new FileIndexSpec();

    public FileIndexJson() {
    }

    public FileIndexJson(FileIndexSpec spec) {
        this.spec = spec;
    }

    /**
     * Current api version. The current version is dqo/v1
     * @return Current api version.
     */
    public String getApiVersion() {
        return apiVersion;
    }

    /**
     * Sets the api version for the serialized file.
     * @param apiVersion Api version.
     */
    public void setApiVersion(String apiVersion) {
        this.apiVersion = apiVersion;
    }

    /**
     * Returns the YAML file kind.
     * @return Yaml file kind.
     */
    public SpecificationKind getKind() {
        return kind;
    }

    /**
     * Sets YAML file kind.
     * @param kind YAML file kind.
     */
    public void setKind(SpecificationKind kind) {
        this.kind = kind;
    }

    /**
     * Returns a file index specification.
     * @return File index specification.
     */
    public FileIndexSpec getSpec() {
        return spec;
    }

    /**
     * Sets a file index specification.
     * @param spec File index specification.
     */
    public void setSpec(FileIndexSpec spec) {
        this.spec = spec;
    }
}
