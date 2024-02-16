import React from 'react';
import SectionWrapper from '../Dashboard/SectionWrapper';
import SelectInput from '../SelectInput';
import { DuckdbParametersSpecSourceFilesTypeEnum } from '../../api';
import CsvFormatConfiguration from './CsvFormatConfiguration';
import JsonFormatConfiguration from './JsonFormatConfiguration';
import ParquetFormatConfiguration from './ParquetFormatConfiguration';
import FilePath from './FilePath';
import { TConfiguration } from './TConfiguration';
import KeyValueProperties from './KeyValueProperties';

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
  renderOptions: boolean;
};

export default function FileFormatConfiguration({
  paths,
  onAddPath,
  onChangePath,
  fileFormatType,
  onChangeFile,
  configuration,
  cleanConfiguration,
  onChangeConfiguration,
  onDeletePath,
  renderOptions
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

  const sourceFilesTypeOptions = [
    {
      label: 'CSV',
      value: DuckdbParametersSpecSourceFilesTypeEnum.csv
    },
    {
      label: 'JSON',
      value: DuckdbParametersSpecSourceFilesTypeEnum.json
    },
    {
      label: 'Parquet',
      value: DuckdbParametersSpecSourceFilesTypeEnum.parquet
    }
  ];

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

      {/* <KeyValueProperties
        properties={duckdb?.directories}
        onChange={handleChange}
        sharedCredentials = {sharedCredentials}
      /> */}

      <div className="flex items-center gap-x-5">
        <div>File format</div>{' '}
        <SelectInput    // todo: bug on names, you see "csv" instead of "CSV"
          options={sourceFilesTypeOptions}
          onChange={(value) => {
            onChangeFile(value);
            cleanConfiguration();
          }}
          value={fileFormatType}
        />{' '}
      </div>
      {renderOptions && renderConfiguration()}
    </SectionWrapper>
  );
}
