import {
  CsvFileFormatSpec,
  IcebergFileFormatSpec,
  JsonFileFormatSpec,
  ParquetFileFormatSpec
} from '../../api';

export type TConfiguration =
  | CsvFileFormatSpec
  | JsonFileFormatSpec
  | ParquetFileFormatSpec
  | IcebergFileFormatSpec;
