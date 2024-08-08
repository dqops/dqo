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
        Flux<String> rows = Flux.fromIterable(errorSamples)
                .map(this::createCsvRow);
        return Flux.concat(header, rows);
    }

    private String createCsvRow(ErrorSampleEntryModel errorSampleEntryModel){
        List<String> row = new ArrayList<>();
        row.add(errorSampleEntryModel.getSampleIndex().toString());
        row.add(errorSampleEntryModel.getCollectedAt().toString());
        row.add(errorSampleEntryModel.getResult().toString());
        row.add(errorSampleEntryModel.getResultDataType().toString());
        row.add(errorSampleEntryModel.getDataGroup());
        row.add(errorSampleEntryModel.getRowId1() != null ? errorSampleEntryModel.getRowId1() : "");
        row.add(errorSampleEntryModel.getRowId2() != null ? errorSampleEntryModel.getRowId2() : "");
        row.add(errorSampleEntryModel.getRowId3() != null ? errorSampleEntryModel.getRowId3() : "");
        row.add(errorSampleEntryModel.getRowId4() != null ? errorSampleEntryModel.getRowId4() : "");
        row.add(errorSampleEntryModel.getRowId5() != null ? errorSampleEntryModel.getRowId5() : "");
        row.add(errorSampleEntryModel.getId());
        String joinedRow = String.join(",", row) + "\n";
        String csvRow = joinedRow.replace("\"", "\"\"");
        return csvRow;
    }

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
