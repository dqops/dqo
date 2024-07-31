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
package com.dqops.core.configuration;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import picocli.CommandLine;

/**
 * Configuration POJO with the configuration for the dqo.check-mining that configures how the rule mining engine suggests rules.
 */
@Configuration
@ConfigurationProperties(prefix = "dqo.check-mining")
@EqualsAndHashCode(callSuper = false)
@Data
public class DqoCheckMiningConfigurationProperties implements Cloneable {
    /**
     * The rate of the current row count obtained from statistics that is used to configure the row_count check value by the check mining engine. The default value is 0.99 of the current row count.
     */
    @CommandLine.Option(names = {"--dqo.check-mining.minimum-row-count-rate"},
            description = "The rate of the current row count obtained from statistics that is used to configure the row_count check value by the check mining engine. " +
                    "The default value is 0.99 of the current row count.", defaultValue = "0.99")
    private double minimumRowCountRate = 0.99;

    /**
     * The multiplier of the last known table freshness that is used to propose the configuration of the table freshness rule threshold by the rule mining engine.
     */
    @CommandLine.Option(names = {"--dqo.check-mining.freshness-max-days-multiplier"},
            description = "The multiplier of the last known table freshness that is used to propose the configuration of the table freshness rule threshold by the rule mining engine." +
                    "The default value is 2.0x the last known delay.", defaultValue = "2.0")
    private double freshnessMaxDaysMultiplier = 2.0;

    /**
     * The multiplier of the last known table staleness that is used to propose the configuration of the table staleness rule threshold by the rule mining engine.
     */
    @CommandLine.Option(names = {"--dqo.check-mining.staleness-max-days-multiplier"},
            description = "The multiplier of the last known table staleness that is used to propose the configuration of the table staleness rule threshold by the rule mining engine." +
                    "The default value is 2.0x the last known delay.", defaultValue = "2.0")
    private double stalenessMaxDaysMultiplier = 2.0;

    /**
     * The multiplier of the last known table ingestion delay that is used to propose the configuration of the table ingestion delay rule threshold by the rule mining engine.
     */
    @CommandLine.Option(names = {"--dqo.check-mining.ingestion-delay-max-days-multiplier"},
            description = "The multiplier of the last known table ingestion delay that is used to propose the configuration of the table ingestion delay rule threshold by the rule mining engine." +
                    "The default value is 2.0x the last known delay.", defaultValue = "2.0")
    private double ingestionDelayMaxDaysMultiplier = 2.0;

    /**
     * The multiplier of the last known table ingestion delay that is used to propose the configuration of the table ingestion delay rule threshold by the rule mining engine.
     */
    @CommandLine.Option(names = {"--dqo.check-mining.percent-check-delta-rate"},
            description = "The multiplier of the last known percent that is extended by this delta (as a rate/proportion of the percentage) to configure a passing percentage check." +
                    "The default value is 0.3. For this value and when the last known max_percent was 10%, DQOps rule mining engine will propose a save max_count 13%. " +
                    "The additional 3% of the delta is 0.3 * 10%.", defaultValue = "0.3")
    private double percentCheckDeltaRate = 0.3;

    /**
     * The percentage value captured by a profiling check (for example 0.03% of errors or 99.97% of valid) that is used to propose a percentage rule that
     * will treat the values as errors (i.e., max_percent = 0%, or min_percent = 100%).
     */
    @CommandLine.Option(names = {"--dqo.check-mining.fail-checks-at-percent-error-rows"},
            description = "The percentage value captured by a profiling check (for example 0.03% of errors or 99.97% of valid) " +
                    "that is used to propose a percentage rule that will treat the values as errors (i.e., max_percent = 0%, or min_percent = 100%)." +
                    "The default value is 0.1.", defaultValue = "0.1")
    private double failChecksAtPercentErrorRows = 0.1;

    /**
     * Creates a clone of the object.
     * @return Cloned instance.
     */
    @Override
    public DqoCheckMiningConfigurationProperties clone() {
        try {
            DqoCheckMiningConfigurationProperties cloned = (DqoCheckMiningConfigurationProperties) super.clone();
            return cloned;
        }
        catch (CloneNotSupportedException ex) {
            throw new RuntimeException("Cannot clone object", ex);
        }
    }
}
