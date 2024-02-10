import React from 'react';
import SectionWrapper from '../Dashboard/SectionWrapper';
import SelectInput from '../SelectInput';
import { CsvFileFormatSpec } from '../../api';
import CsvFormatConfiguration from './CsvFormatConfiguration';
import FilePath from './FilePath';

type TFileFormatConfigurationProps = {
  paths: string[];
  onChangePath: (val: string) => void;
  onAddPath: () => void;
  fileFormatType: fileFormat;
  onChangeFile: (val: fileFormat) => void;
  configuration: TConfiguration;
  onChangeConfiguration: (params: Partial<TConfiguration>) => void;
  cleanConfiguration: () => void;
  onDeletePath: (index: number) => void;
};

enum fileFormat {
  csv = 'csv_file_format',
  json = 'json_file_format',
  parquet = 'parquet_file_format',
  file_path_list = 'file_path_list',
  file_path = 'file_path'
}

type TConfiguration = CsvFileFormatSpec;
//add json parquet type
// type TConfiguration = CsvFileFormatSpec | JsonFileFormat | ParquetFileFormat etc

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
  // create components for json, parquet etc, with same params required
  const renderConfiguration = () => {
    switch (fileFormatType) {
      case fileFormat.csv: {
        return (
          <CsvFormatConfiguration
            configuration={configuration}
            onChangeConfiguration={onChangeConfiguration}
          />
        );
      }
      case fileFormat.json: {
        return <></>;
      }
      case fileFormat.parquet: {
        return <></>;
      }
      case fileFormat.file_path: {
        return <></>;
      }
      case fileFormat.file_path_list: {
        return <></>;
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
          options={Object.values(fileFormat).map((x) => ({
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
