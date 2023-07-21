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
package com.dqops.core.filesystem.virtual;

import com.dqops.BaseTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class FolderNameTests extends BaseTest {
    @Test
    void fromObjectName_whenUnsafeNameGiven_thenIsSanitized() {
        FolderName folderName = FolderName.fromObjectName("file/name");
        Assertions.assertNotNull(folderName);
        Assertions.assertEquals("file/name", folderName.getObjectName());
        Assertions.assertEquals("file%2Fname", folderName.getFileSystemName());
    }

    @Test
    void fromFileSystemNameName_whenEncodedNameGiven_thenIsDecoded() {
        FolderName folderName = FolderName.fromFileSystemName("file%2Fname");
        Assertions.assertNotNull(folderName);
        Assertions.assertEquals("file/name", folderName.getObjectName());
        Assertions.assertEquals("file%2Fname", folderName.getFileSystemName());
    }
}
