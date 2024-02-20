import React from 'react';
import SectionWrapper from '../Dashboard/SectionWrapper';
import SelectInput from '../SelectInput';
import { DuckdbParametersSpecSourceFilesTypeEnum } from '../../api';
import CsvFormatConfiguration from './FormatsConfiguration/CsvFormatConfiguration';
import JsonFormatConfiguration from './FormatsConfiguration/JsonFormatConfiguration';
import ParquetFormatConfiguration from './FormatsConfiguration/ParquetFormatConfiguration';
import FilePath from './FilePath';
import { TConfiguration } from './TConfiguration';
import KeyValueProperties from './KeyValueProperties';

type TFileFormatConfigurationProps = {
  fileFormatType: DuckdbParametersSpecSourceFilesTypeEnum;
  configuration: TConfiguration;
  onChangeFile: (val: DuckdbParametersSpecSourceFilesTypeEnum) => void;
  onChangeConfiguration: (params: Partial<TConfiguration>) => void;
  cleanConfiguration: () => void;
  freezeFileType: boolean;
  children: any;
};

export default function FileFormatConfiguration({
  fileFormatType,
  onChangeFile,
  configuration,
  cleanConfiguration,
  onChangeConfiguration,
  freezeFileType,
  children
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
    <>
      <div className="flex items-center gap-x-5">
        <div>File format</div>
        {!freezeFileType && (
          <SelectInput // todo: bug on names, you see "csv" instead of "CSV"
            options={sourceFilesTypeOptions}
            onChange={(value) => {
              onChangeFile(value);
              cleanConfiguration();
            }}
            value={fileFormatType}
          />
        )}
        {freezeFileType && <div>{fileFormatType}</div>}
        {children}
      </div>
      {renderConfiguration()}
    </>
  );
}
