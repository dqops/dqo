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

    /**
     * The name for the file with the default application credentials for Azure.
     */
    public static final String AZURE_DEFAULT_CREDENTIALS_NAME = "Azure_default_credentials";
}
