/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.utils.logging;

import java.io.InputStream;

public interface DownloadLogsService {
    /**
     * Creates an input stream that is generated on-the-fly by zipping all log files in the .log folder.
     * This method starts a background thread that will generate a zip output stream and reverse it to an input stream
     * that is usable for streaming.
     *
     * @return Zip file as an input stream, to be forwarded to the user.
     */
    InputStream zipLogsOnTheFly();
}
