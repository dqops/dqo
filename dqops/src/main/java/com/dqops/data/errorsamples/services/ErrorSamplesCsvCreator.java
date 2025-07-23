/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.data.errorsamples.services;

import com.dqops.data.errorsamples.models.ErrorSampleEntryModel;
import reactor.core.publisher.Flux;

import java.util.List;

/**
 * Prepares CSV formatted data from error sample entries.
 */
public interface ErrorSamplesCsvCreator {

    /**
     * Creates a Flux of CSV formatted data from the list of error sample entries.
     * @param errorSamples List of error sample entries.
     * @return Flux of CSV formatted data.
     */
    Flux<String> createCsvData(List<ErrorSampleEntryModel> errorSamples);
}
