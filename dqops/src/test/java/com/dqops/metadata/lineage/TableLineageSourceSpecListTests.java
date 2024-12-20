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

package com.dqops.metadata.lineage;

import com.dqops.BaseTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TableLineageSourceSpecListTests extends BaseTest {
    private TableLineageSourceSpecList sut;

    @BeforeEach
    void setUp() {
        this.sut = new TableLineageSourceSpecList();
    }

    @Test
    void getByObjectName_whenObjectAddedWithAdd_thenCanBeRetrieved() {
        TableLineageSourceSpec spec = new TableLineageSourceSpec() {{
           setSourceConnection("conn");
           setSourceSchema("schema");
           setSourceTable("tab");
        }};
        this.sut.add(spec);

        Assertions.assertEquals(1, this.sut.size());

        TableLineageSourceSpec foundObject = this.sut.getByObjectName(new TableLineageSource("conn", "schema", "tab"), false);
        Assertions.assertSame(spec, foundObject);
    }
}
