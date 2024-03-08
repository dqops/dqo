import React from 'react';
import SectionWrapper from '../Dashboard/SectionWrapper';
import SelectInput from '../SelectInput';
import { DuckdbParametersSpecFilesFormatTypeEnum } from '../../api';
import CsvFormatConfiguration from './FormatsConfiguration/CsvFormatConfiguration';
import JsonFormatConfiguration from './FormatsConfiguration/JsonFormatConfiguration';
import ParquetFormatConfiguration from './FormatsConfiguration/ParquetFormatConfiguration';
import { TConfiguration } from './TConfiguration';

type TFileFormatConfigurationProps = {
  fileFormatType: DuckdbParametersSpecFilesFormatTypeEnum;
  configuration: TConfiguration;
  onChangeFile: (val: DuckdbParametersSpecFilesFormatTypeEnum) => void;
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
      case DuckdbParametersSpecFilesFormatTypeEnum.csv: {
        return (
          <CsvFormatConfiguration
            configuration={configuration}
            onChangeConfiguration={onChangeConfiguration}
          />
        );
      }
      case DuckdbParametersSpecFilesFormatTypeEnum.json: {
        return (
          <JsonFormatConfiguration
            configuration={configuration}
            onChangeConfiguration={onChangeConfiguration}
          />
        );
      }
      case DuckdbParametersSpecFilesFormatTypeEnum.parquet: {
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
      value: DuckdbParametersSpecFilesFormatTypeEnum.csv
    },
    {
      label: 'JSON',
      value: DuckdbParametersSpecFilesFormatTypeEnum.json
    },
    {
      label: 'Parquet',
      value: DuckdbParametersSpecFilesFormatTypeEnum.parquet
    }
  ];

  return (
    <div className='mt-8'>
      <SectionWrapper
        title="Import configuration"
        className="text-sm text-black"
      >
        <div className="flex items-center gap-x-5">
          <div>File format</div>
          {!freezeFileType && (
            <SelectInput
              options={sourceFilesTypeOptions}
              onChange={(value) => {
                onChangeFile(value);
                cleanConfiguration();
              }}
              value={
                fileFormatType === DuckdbParametersSpecFilesFormatTypeEnum.parquet
                  ? fileFormatType.replace(/./, (c) => c.toUpperCase())
                  : fileFormatType.toUpperCase()
              }
            />
          )}
          {freezeFileType && <div>{fileFormatType}</div>}
        </div>
        {children}
        <div className='pt-4'>{renderConfiguration()}</div>
      </SectionWrapper>
      
    </div>
  );
}
