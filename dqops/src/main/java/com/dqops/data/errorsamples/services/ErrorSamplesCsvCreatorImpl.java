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
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;

/**
 * Prepares CSV formatted data from error sample entries.
 */
@Component
public class ErrorSamplesCsvCreatorImpl implements ErrorSamplesCsvCreator {

    /**
     * Creates a Flux of CSV formatted data from the list of error sample entries.
     * @param errorSamples List of error sample entries.
     * @return Flux of CSV formatted data.
     */
    public Flux<String> createCsvData(List<ErrorSampleEntryModel> errorSamples){
        Flux<String> header = Flux.just(createCsvHeader());
        if(errorSamples == null || errorSamples.isEmpty()){
            return header;
        }
        Flux<String> rows = Flux.fromIterable(errorSamples)
                .map(this::createCsvRow);
        return Flux.concat(header, rows);
    }

    /**
     * Creates a CSV row formed from the ErrorSampleEntryModel.
     * @param errorSampleEntryModel An ErrorSampleEntryModel.
     * @return CSV row formed from ErrorSampleEntryModel.
     */
    private String createCsvRow(ErrorSampleEntryModel errorSampleEntryModel){
        List<String> row = new ArrayList<>();
        row.add(errorSampleEntryModel.getSampleIndex().toString());
        row.add(errorSampleEntryModel.getCollectedAt().toString());
        row.add(errorSampleEntryModel.getResult() != null && !errorSampleEntryModel.getResult().toString().isEmpty() ? quoteValue(errorSampleEntryModel.getResult().toString()) : "");
        row.add(quoteValue(errorSampleEntryModel.getResultDataType().toString()));
        row.add(quoteValue(errorSampleEntryModel.getDataGroup()));
        row.add(errorSampleEntryModel.getRowId1() != null ? quoteValue(errorSampleEntryModel.getRowId1()) : "");
        row.add(errorSampleEntryModel.getRowId2() != null ? quoteValue(errorSampleEntryModel.getRowId2()) : "");
        row.add(errorSampleEntryModel.getRowId3() != null ? quoteValue(errorSampleEntryModel.getRowId3()) : "");
        row.add(errorSampleEntryModel.getRowId4() != null ? quoteValue(errorSampleEntryModel.getRowId4()) : "");
        row.add(errorSampleEntryModel.getRowId5() != null ? quoteValue(errorSampleEntryModel.getRowId5()) : "");
        row.add(quoteValue(errorSampleEntryModel.getId()));
        String csvRow = String.join(",", row) + "\n";
        return csvRow;
    }

    /**
     * Quotes the string value for the CSV, double-quoting all quotes of the string.
     * @param value The input string.
     * @return Quoted string.
     */
    private String quoteValue(String value){
        return "\"" + value.replace("\"", "\"\"") + "\"";
    }

    /**
     * Creates a CSV header row.
     * @return CSV header row.
     */
    private String createCsvHeader() {
        List<String> header = new ArrayList<>();
        header.add("Sample Index");
        header.add("Collected at");
        header.add("Result");
        header.add("Result data type");
        header.add("Data grouping");
        header.add("ID Column 1");
        header.add("ID Column 2");
        header.add("ID Column 3");
        header.add("ID Column 4");
        header.add("ID Column 5");
        header.add("Id");
        String csvHeader = String.join(",", header) + "\n";
        return csvHeader;
    }

}
