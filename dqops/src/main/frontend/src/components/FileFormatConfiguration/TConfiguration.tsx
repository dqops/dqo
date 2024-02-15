import {
    CsvFileFormatSpec,
    JsonFileFormatSpec,
    ParquetFileFormatSpec,
} from '../../api';

export type TConfiguration = CsvFileFormatSpec | JsonFileFormatSpec | ParquetFileFormatSpec;
