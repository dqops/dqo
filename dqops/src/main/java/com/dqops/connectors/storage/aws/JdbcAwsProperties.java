/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.connectors.storage.aws;

/**
 * Property names passed to jdbc driver during establishing connection to database engine.
 */
public final class JdbcAwsProperties {

    /**
     * Access key id name.
     */
    public static final String ACCESS_KEY_ID = "AccessKeyId";

    /**
     * Secret access key name.
     */
    public static final String SECRET_ACCESS_KEY = "SecretAccessKey";

    /**
     * Region name.
     */
    public static final String REGION = "Region";

}
