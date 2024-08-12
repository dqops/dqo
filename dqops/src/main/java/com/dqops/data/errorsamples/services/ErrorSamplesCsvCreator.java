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
