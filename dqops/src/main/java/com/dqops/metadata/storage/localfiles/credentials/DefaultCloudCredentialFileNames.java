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

package com.dqops.metadata.storage.localfiles.credentials;

/**
 * Constants with file names for the default credential files. The files are created in the DQO_USER_HOME/.credentials folder as shared credentials.
 */
public final class DefaultCloudCredentialFileNames {
    /**
     * The name of the file with the default credentials for GCP.
     */
    public static final String GCP_APPLICATION_DEFAULT_CREDENTIALS_JSON_NAME = "GCP_application_default_credentials.json";

    /**
     * The name for the file with the default application credentials for AWS.
     */
    public static final String AWS_DEFAULT_CREDENTIALS_NAME = "AWS_default_credentials";

    /**
     * The name for the file with the default AWS configuration.
     */
    public static final String AWS_DEFAULT_CONFIG_NAME = "AWS_default_config";
}
