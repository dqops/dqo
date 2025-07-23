/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.utils.browser;

/**
 * Opens an url in a browser.
 */
public interface OpenBrowserService {
    /**
     * Opens an url in a browser.
     * @param url Url to be opened.
     * @throws OpenBrowserFailedException When it was not possible to open a browser. The error message should be shown to the user.
     */
    void openUrlInBrowser(String url);
}
