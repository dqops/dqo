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
