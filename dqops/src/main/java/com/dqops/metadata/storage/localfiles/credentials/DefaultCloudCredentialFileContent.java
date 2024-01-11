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
 * Constants with initial content of cloud credential files. The files are created in the DQO_USER_HOME/.credentials folder as shared credentials.
 */
public final class DefaultCloudCredentialFileContent {
    /**
     * The initial content of the GCP_application_default_credentials.json file.
     */
    public static final String GCP_APPLICATION_DEFAULT_CREDENTIALS_JSON_INITIAL_CONTENT =
            "{\n" +
            "    this is the placeholder of the JSON key file for a service account that will be accessing GCP,\n" +
            "    please replace the content of this file with your Service Account Key to use default authentication to GCP\n" +
            "}\n";

    /**
     * The initial content of the AWS_default_credentials shared secret file.
     */
    public static final String AWS_DEFAULT_CREDENTIALS_INITIAL_CONTENT =
            "[default]\n" +
            "aws_access_key_id=PLEASE_REPLACE_WITH_YOUR_AWS_ACCESS_KEY_ID\n" +
            "aws_secret_access_key=PLEASE_REPLACE_WITH_YOUR_AWS_SECRET_ACCESS_KEY\n";

    /**
     * The initial content of the AWS_default_config shared secret file.
     */
    public static final String AWS_DEFAULT_CONFIG_INITIAL_CONTENT =
            "[default]\n" +
            "region = us-east-1\n";
}
