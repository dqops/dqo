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
package com.dqops.core.domains;

import com.dqops.metadata.settings.domains.LocalDataDomainSpec;
import com.dqops.utils.docs.generators.SampleValueFactory;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;


/**
 * Model that describes a local data domain. It is also used to create new data domains.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@ApiModel(value = "LocalDataDomainModel", description = "Model that describes a local data domain. It is also used to create new data domains.")
public class LocalDataDomainModel {
    /**
     * Data domain name.
     */
    @JsonPropertyDescription("Data domain name.")
    private String domainName;

    /**
     * Data domain display name.
     */
    @JsonPropertyDescription("Data domain display name (user-friendly name).")
    private String displayName;

    /**
     * Data domain creation time.
     */
    @JsonPropertyDescription("Data domain creation time.")
    private OffsetDateTime createdAt;

    /**
     * Enables the job scheduler for this domain.
     */
    @JsonPropertyDescription("Enables the job scheduler for this domain.")
    private boolean enableScheduler = true;

    public LocalDataDomainModel() {
    }

    /**
     * Creates a data domain model from a specification object.
     * @param localDataDomainSpec Local data domain specification as a source.
     * @return Data domain model.
     */
    public static LocalDataDomainModel createFromSpec(LocalDataDomainSpec localDataDomainSpec) {
        LocalDataDomainModel localDataDomainModel = new LocalDataDomainModel() {{
            setDomainName(localDataDomainSpec.getDataDomainName());
            setDisplayName(localDataDomainSpec.getDisplayName());
            setCreatedAt(localDataDomainSpec.getCreatedAt());
            setEnableScheduler(localDataDomainSpec.isEnableScheduler());
        }};

        return localDataDomainModel;
    }

    /**
     * Creates a local data domain specification from the model.
     * @return Local data domain specification object.
     */
    public LocalDataDomainSpec toLocalDataDomainSpec() {
        LocalDataDomainSpec localDataDomainSpec = new LocalDataDomainSpec() {{
            setDataDomainName(this.getDataDomainName());
            setDisplayName(this.getDisplayName());
            setCreatedAt(this.getCreatedAt() != null ? this.getCreatedAt() : OffsetDateTime.now()); // we are using the current time otherwise
            setEnableScheduler(this.isEnableScheduler());
        }};

        return localDataDomainSpec;
    }

    /**
     * Documentation samples factory.
     */
    public static class LocalDataDomainModelSampleFactory implements SampleValueFactory<LocalDataDomainModel> {
        @Override
        public LocalDataDomainModel createSample() {
            return new LocalDataDomainModel() {{
                setDomainName("sales");
                setDisplayName("Sales data domain");
                setCreatedAt(OffsetDateTime.of(LocalDateTime.of(2024, Month.SEPTEMBER, 14, 18, 33), ZoneOffset.UTC));
            }};
        }
    }
}
