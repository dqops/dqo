import React from 'react';
import {
  CsvFileFormatSpec,
  DuckdbParametersSpecFilesFormatTypeEnum,
  IcebergFileFormatSpec,
  JsonFileFormatSpec,
  ParquetFileFormatSpec
} from '../../api';
import SectionWrapper from '../Dashboard/SectionWrapper';
import Select from '../Select';
import CsvFormatConfiguration from './FormatsConfiguration/CsvFormatConfiguration';
import IcebergFormatConfiguration from './FormatsConfiguration/IcebergFormatConfiguration';
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

// Type guard functions

export default function FileFormatConfiguration({
  fileFormatType,
  onChangeFile,
  configuration,
  cleanConfiguration,
  onChangeConfiguration,
  freezeFileType,
  children
}: TFileFormatConfigurationProps) {
  function isCsvFileFormatSpec(
    config: TConfiguration
  ): config is CsvFileFormatSpec {
    return fileFormatType === DuckdbParametersSpecFilesFormatTypeEnum.csv;
  }

  function isJsonFileFormatSpec(
    config: TConfiguration
  ): config is JsonFileFormatSpec {
    return fileFormatType === DuckdbParametersSpecFilesFormatTypeEnum.json;
  }

  function isParquetFileFormatSpec(
    config: TConfiguration
  ): config is ParquetFileFormatSpec {
    return fileFormatType === DuckdbParametersSpecFilesFormatTypeEnum.parquet;
  }

  function isIcebergFileFormatSpec(
    config: TConfiguration
  ): config is IcebergFileFormatSpec {
    return fileFormatType === DuckdbParametersSpecFilesFormatTypeEnum.iceberg;
  }

  const renderConfiguration = () => {
    switch (fileFormatType) {
      case DuckdbParametersSpecFilesFormatTypeEnum.csv: {
        return isCsvFileFormatSpec(configuration) ? (
          <CsvFormatConfiguration
            configuration={configuration}
            onChangeConfiguration={onChangeConfiguration}
          />
        ) : null;
      }
      case DuckdbParametersSpecFilesFormatTypeEnum.json: {
        return isJsonFileFormatSpec(configuration) ? (
          <JsonFormatConfiguration
            configuration={configuration}
            onChangeConfiguration={onChangeConfiguration}
          />
        ) : null;
      }
      case DuckdbParametersSpecFilesFormatTypeEnum.parquet: {
        return isParquetFileFormatSpec(configuration) ? (
          <ParquetFormatConfiguration
            configuration={configuration}
            onChangeConfiguration={onChangeConfiguration}
          />
        ) : null;
      }
      case DuckdbParametersSpecFilesFormatTypeEnum.iceberg: {
        return isIcebergFileFormatSpec(configuration) ? (
          <IcebergFormatConfiguration
            configuration={configuration}
            onChangeConfiguration={onChangeConfiguration}
          />
        ) : null;
      }
      default:
        return null;
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
