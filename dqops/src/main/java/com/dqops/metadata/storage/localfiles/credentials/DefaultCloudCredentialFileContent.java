/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
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

    /**
     * The initial content of the Azure_default_credentials shared secret file.
     */
    public static final String AZURE_DEFAULT_CREDENTIALS_INITIAL_CONTENT =
            "tenant_id=PLEASE_REPLACE_WITH_YOUR_AZURE_TENANT_ID\n" +
            "client_id=PLEASE_REPLACE_WITH_YOUR_AZURE_CLIENT_ID\n" +
            "client_secret=PLEASE_REPLACE_WITH_YOUR_AZURE_CLIENT_SECRET\n" +
            "account_name=PLEASE_REPLACE_WITH_YOUR_AZURE_STORAGE_ACCOUNT_NAME";
}
