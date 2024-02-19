import React from 'react';
import SectionWrapper from '../Dashboard/SectionWrapper';
import SelectInput from '../SelectInput';
import { 
  CsvFileFormatSpec, 
  JsonFileFormatSpec,
  ParquetFileFormatSpec,
  DuckdbParametersSpecSourceFilesTypeEnum
} from '../../api';
import CsvFormatConfiguration from './CsvFormatConfiguration';
import JsonFormatConfiguration from './JsonFormatConfiguration';
import ParquetFormatConfiguration from './ParquetFormatConfiguration';
import FilePath from './FilePath';

type TFileFormatConfigurationProps = {
  paths: string[];
  onChangePath: (val: string) => void;
  onAddPath: () => void;
  fileFormatType: DuckdbParametersSpecSourceFilesTypeEnum;
  onChangeFile: (val: DuckdbParametersSpecSourceFilesTypeEnum) => void;
  configuration: TConfiguration;
  onChangeConfiguration: (params: Partial<TConfiguration>) => void;
  cleanConfiguration: () => void;
  onDeletePath: (index: number) => void;
};

// enum fileFormat {
//   csv = 'csv_file_format',
//   json = 'json_file_format',
//   parquet = 'parquet_file_format',
//   file_path = 'file_path'
// }

type TConfiguration = CsvFileFormatSpec | JsonFileFormatSpec | ParquetFileFormatSpec;

export default function FileFormatConfiguration({
  paths,
  onAddPath,
  onChangePath,
  fileFormatType,
  onChangeFile,
  configuration,
  cleanConfiguration,
  onChangeConfiguration,
  onDeletePath
}: TFileFormatConfigurationProps) {

  const renderConfiguration = () => {
    switch (fileFormatType) {
      case DuckdbParametersSpecSourceFilesTypeEnum.csv: {
        return (
          <CsvFormatConfiguration
            configuration={configuration}
            onChangeConfiguration={onChangeConfiguration}
          />
        );
      }
      case DuckdbParametersSpecSourceFilesTypeEnum.json: {
        return (
          <JsonFormatConfiguration
            configuration={configuration}
            onChangeConfiguration={onChangeConfiguration}
          />
        );
      }
      case DuckdbParametersSpecSourceFilesTypeEnum.parquet: {
        return (
          <ParquetFormatConfiguration
            configuration={configuration}
            onChangeConfiguration={onChangeConfiguration}
          />
        );
      }
    }
  };

  return (
    <SectionWrapper
      title="File format configuration"
      className="text-sm my-4 text-black"
    >
      <FilePath
        paths={paths}
        onAddPath={onAddPath}
        onChangePath={onChangePath}
        onDeletePath={onDeletePath}
      />
      <div className="flex items-center gap-x-5">
        <div>File format</div>{' '}
        <SelectInput
          options={Object.values(DuckdbParametersSpecSourceFilesTypeEnum).map((x) => ({
            label: x,
            value: x
          }))}
          onChange={(value) => {
            onChangeFile(value);
            cleanConfiguration();
          }}
          value={fileFormatType}
        />{' '}
      </div>
      {renderConfiguration()}
    </SectionWrapper>
  );
}
