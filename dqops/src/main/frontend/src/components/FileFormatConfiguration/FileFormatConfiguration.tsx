import React from 'react';
import { DuckdbParametersSpecFilesFormatTypeEnum } from '../../api';
import SectionWrapper from '../Dashboard/SectionWrapper';
import Select from '../Select';
import CsvFormatConfiguration from './FormatsConfiguration/CsvFormatConfiguration';
import JsonFormatConfiguration from './FormatsConfiguration/JsonFormatConfiguration';
import ParquetFormatConfiguration from './FormatsConfiguration/ParquetFormatConfiguration';
import { TConfiguration } from './TConfiguration';
import IcebergFormatConfiguration from './FormatsConfiguration/IcebergFormatConfiguration';

type TFileFormatConfigurationProps = {
  fileFormatType: DuckdbParametersSpecFilesFormatTypeEnum;
  configuration: TConfiguration;
  onChangeFile: (val: DuckdbParametersSpecFilesFormatTypeEnum) => void;
  onChangeConfiguration: (params: Partial<TConfiguration>) => void;
  cleanConfiguration: () => void;
  freezeFileType: boolean;
  children: any;
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
  },
  {
    label: 'Iceberg',
    value: DuckdbParametersSpecFilesFormatTypeEnum.iceberg
  }
];

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
      // case DuckdbParametersSpecFilesFormatTypeEnum.iceberg: {
      //   return (
      //     <IcebergFormatConfiguration
      //       configuration={configuration}
      //       onChangeConfiguration={onChangeConfiguration}
      //     />
      //   );
      // }
    }
  };

  return (
    <div className="mt-8">
      <SectionWrapper
        title="Import configuration"
        className="text-sm text-black"
      >
        <div className="flex items-center gap-x-5 text-sm">
          <div>File format</div>
          {!freezeFileType ? (
            <Select
              options={sourceFilesTypeOptions}
              onChange={(value) => {
                onChangeFile(value);
                cleanConfiguration();
              }}
              value={
                fileFormatType ===
                DuckdbParametersSpecFilesFormatTypeEnum.parquet
                  ? fileFormatType.replace(/./, (c) => c.toUpperCase())
                  : fileFormatType.toUpperCase()
              }
              className="text-sm"
            />
          ) : (
            <div className="font-bold">{fileFormatType}</div>
          )}
        </div>
        {/* {freezeFileType && <div>{fileFormatType}</div>} */}
        {children}
        <div>{renderConfiguration()}</div>
      </SectionWrapper>
    </div>
  );
}
