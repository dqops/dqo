/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.metadata.storage.localfiles.userhome;

import com.dqops.BaseTest;
import com.dqops.utils.serialization.JsonSerializerObjectMother;
import com.dqops.utils.serialization.YamlSerializerObjectMother;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class FileUserHomeImplTests extends BaseTest {
    @Test
    void create_whenCalledWithInMemoryFileSystem_thenReturnsModel() {
        FileUserHomeImpl sut = FileUserHomeImpl.create(UserHomeContextObjectMother.createInMemoryFileHomeContext(),
                YamlSerializerObjectMother.createNew(), JsonSerializerObjectMother.createNew(), false);
        Assertions.assertNotNull(sut);
        Assertions.assertNotNull(sut.getConnections());
    }
}
