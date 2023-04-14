/*
 * Copyright © 2021 DQO.ai (support@dqo.ai)
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
package ai.dqo.rest.models.metadata;

import ai.dqo.BaseTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;
import org.junit.jupiter.api.Assertions;

@SpringBootTest
public class SensorBasicModelTest extends BaseTest {

    private SensorBasicModel sut;

    @BeforeEach
    void setUp() {
        this.sut = new SensorBasicModel();
    }

    @Test
    void addChild_whenSingleFolder_thenReturnNotNull() {
        String path = "folder1";
        boolean isCustom = false;
        this.sut.addChild(path, isCustom);

        Map<String, SensorBasicModel> folders = this.sut.getFolders();
        Assertions.assertNotNull(folders);
        Assertions.assertEquals(1, folders.size());

        SensorBasicModel folder1 = folders.get("folder1");
        Assertions.assertNotNull(folder1);
    }

    @Test
    void addChild_whenDuplicateFolders_thenReturnNotNull() {
        String path = "folder1/folder2/folder3";
        boolean isCustom1 = true;
        boolean isCustom2 = false;

        this.sut.addChild(path, isCustom1);
        this.sut.addChild(path, isCustom2);
        System.out.println(this.sut);
        Map<String, SensorBasicModel> folders = this.sut.getFolders();
        Assertions.assertNotNull(folders);
        Assertions.assertEquals(1, folders.size());

        SensorBasicModel folder1 = folders.get("folder1");
        Assertions.assertNotNull(folder1);
        Map<String, SensorBasicModel> folder1Folders = folder1.getFolders();
        Assertions.assertNotNull(folder1Folders);
        Assertions.assertEquals(1, folder1Folders.size());

        SensorBasicModel folder2 = folder1Folders.get("folder2");
        Assertions.assertNotNull(folder2);
        Map<String, SensorBasicModel> folder2Folders = folder2.getFolders();
        Assertions.assertNotNull(folder2Folders);
        Assertions.assertEquals(1, folder2Folders.size());

        SensorBasicModel folder3 = folder2Folders.get("folder3");
        Assertions.assertNotNull(folder3);
        Assertions.assertNull(folder3.getFolders());
    }
}
