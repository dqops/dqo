/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.services.check.matching;

import com.dqops.checks.CheckTarget;
import com.dqops.execution.rules.finder.RuleDefinitionFindService;
import com.dqops.execution.sensors.finder.SensorDefinitionFindService;
import com.dqops.metadata.storage.localfiles.dqohome.DqoHomeContextFactory;
import com.dqops.services.check.mapping.SpecToModelCheckMappingServiceImpl;
import com.dqops.utils.reflection.ReflectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Singleton that stores a cache of similar checks, detecting only built-in default checks.
 * Returns the definitions of similar checks in on the table level and on the column level.
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class SimilarCheckCacheImpl implements SimilarCheckCache {
    private final SimilarCheckMatchingService similarCheckMatchingService;
    private final Object lock = new Object();
    private SimilarChecksContainer tableLevelSimilarChecks;
    private SimilarChecksContainer columnLevelSimilarChecks;

    /**
     * Default dependency injection constructor.
     */
    @Autowired
    public SimilarCheckCacheImpl(ReflectionService reflectionService,
                                 SensorDefinitionFindService sensorDefinitionFindService,
                                 RuleDefinitionFindService ruleDefinitionFindService,
                                 DqoHomeContextFactory dqoHomeContextFactory) {
        SpecToModelCheckMappingServiceImpl specToUiCheckMappingService = SpecToModelCheckMappingServiceImpl.createInstanceUnsafe(
                reflectionService, sensorDefinitionFindService, ruleDefinitionFindService);
        this.similarCheckMatchingService = new SimilarCheckMatchingServiceImpl(specToUiCheckMappingService, dqoHomeContextFactory, new SimilarCheckGroupingKeyFactoryImpl());
    }

    /**
     * Returns a container of similar table level checks.
     * @return Similar table level checks.
     */
    @Override
    public SimilarChecksContainer getTableLevelSimilarChecks() {
        synchronized (this.lock) {
            if (this.tableLevelSimilarChecks != null) {
                return this.tableLevelSimilarChecks;
            }

            SimilarChecksContainer similarTableChecks = this.similarCheckMatchingService.findSimilarTableChecks();
            this.tableLevelSimilarChecks = similarTableChecks;
            return similarTableChecks;
        }
    }

    /**
     * Returns a container of similar column level checks.
     * @return Similar column level checks.
     */
    @Override
    public SimilarChecksContainer getColumnLevelSimilarChecks() {
        synchronized (this.lock) {
            if (this.columnLevelSimilarChecks != null) {
                return this.columnLevelSimilarChecks;
            }

            SimilarChecksContainer similarColumnChecks = this.similarCheckMatchingService.findSimilarColumnChecks();

            this.columnLevelSimilarChecks = similarColumnChecks;
            return similarColumnChecks;
        }
    }

    /**
     * Finds checks similar to a given check.
     *
     * @param checkTarget Check target (table or column).
     * @param checkName   Check name.
     * @return List of similar checks to this one (including the requested check) or returns null when there are no similar checks.
     */
    @Override
    public List<SimilarCheckModel> findSimilarChecksTo(CheckTarget checkTarget, String checkName) {
        SimilarChecksContainer similarChecksContainer;
        switch (checkTarget) {
            case table:
                similarChecksContainer = getTableLevelSimilarChecks();
                break;

            case column:
                similarChecksContainer = getColumnLevelSimilarChecks();
                break;

            default:
                throw new IllegalArgumentException("Unknown check target: " + checkTarget);
        }

        SimilarChecksGroup similarChecksGroup = similarChecksContainer.getSimilarChecksTo(checkName);
        if (similarChecksGroup == null) {
            return null;
        }

        return similarChecksGroup.getSimilarChecks();
    }
}
