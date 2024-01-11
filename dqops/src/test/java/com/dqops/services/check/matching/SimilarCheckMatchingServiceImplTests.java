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
package com.dqops.services.check.matching;

import com.dqops.BaseTest;
import com.dqops.execution.rules.finder.RuleDefinitionFindServiceImpl;
import com.dqops.execution.sensors.finder.SensorDefinitionFindServiceImpl;
import com.dqops.metadata.storage.localfiles.dqohome.DqoHomeContextFactory;
import com.dqops.metadata.storage.localfiles.dqohome.DqoHomeContextFactoryObjectMother;
import com.dqops.services.check.mapping.SpecToModelCheckMappingServiceImpl;
import com.dqops.utils.reflection.ReflectionServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Unit tests for SimilarCheckMatchingServiceImpl.
 */
@SpringBootTest
public class SimilarCheckMatchingServiceImplTests extends BaseTest {
    private SimilarCheckMatchingServiceImpl sut;

    @BeforeEach
    void setUp() {
        SpecToModelCheckMappingServiceImpl specToModelCheckMappingService = SpecToModelCheckMappingServiceImpl.createInstanceUnsafe(
                new ReflectionServiceImpl(), new SensorDefinitionFindServiceImpl(), new RuleDefinitionFindServiceImpl());
        DqoHomeContextFactory dqoHomeContextFactory = DqoHomeContextFactoryObjectMother.getRealDqoHomeContextFactory();
        this.sut = new SimilarCheckMatchingServiceImpl(specToModelCheckMappingService, dqoHomeContextFactory, new SimilarCheckGroupingKeyFactoryImpl());
    }

    @Test
    void findSimilarTableChecks_whenEmptyTable_thenCreatesSimilarChecks() {
        SimilarChecksContainer similarChecks = this.sut.findSimilarTableChecks();
        Assertions.assertNotNull(similarChecks);
        Assertions.assertTrue(similarChecks.getSimilarCheckGroups().size() > 3);
    }

    @Test
    void findSimilarColumnChecks_whenEmptyColumn_thenCreatesSimilarChecks() {
        SimilarChecksContainer similarChecks = this.sut.findSimilarColumnChecks();
        Assertions.assertNotNull(similarChecks);
        Assertions.assertTrue(similarChecks.getSimilarCheckGroups().size() > 10);
        SimilarChecksGroup profileNullsCountSimilarChecks = similarChecks.getSimilarChecksTo("profile_nulls_count");
        Assertions.assertNotNull(profileNullsCountSimilarChecks);
        Assertions.assertEquals(5, profileNullsCountSimilarChecks.getSimilarChecks().size());
        Assertions.assertTrue(profileNullsCountSimilarChecks.getSimilarChecks().stream().anyMatch(s -> Objects.equals(s.getCheckName(), "daily_nulls_count")));
    }

    @Test
    void findSimilarColumnChecks_whenColumnChecks_thenNoCheckHasMoreThanFiveSimilarChecks() {
        SimilarChecksContainer similarChecks = this.sut.findSimilarColumnChecks();
        Assertions.assertNotNull(similarChecks);
        Assertions.assertTrue(similarChecks.getSimilarCheckGroups().size() > 10);
        List<SimilarChecksGroup> checksWithMoreThan5Similars = similarChecks.getChecksPerGroup().values().stream()
                .flatMap(similarChecksGroups -> similarChecksGroups.stream())
                .filter(similarChecksGroups -> similarChecksGroups.getSimilarChecks().size() > 5)
                .collect(Collectors.toList());

        Assertions.assertEquals(0, checksWithMoreThan5Similars.size());
    }
}
