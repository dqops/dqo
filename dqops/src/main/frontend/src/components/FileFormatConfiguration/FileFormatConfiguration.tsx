import React, { useState } from 'react';
import SectionWrapper from '../Dashboard/SectionWrapper';
import { IconButton } from '@material-tailwind/react';
import SvgIcon from '../SvgIcon';
import Input from '../Input';
import SelectInput from '../SelectInput';
import { CsvFileFormatSpec } from '../../api';
import CsvFormatConfiguration from './CsvFormatConfiguration';

type TFileFormatConfigurationProps = {
  paths: string[];
  onChangePath: (val: string) => void;
  onAddPath: () => void;
  fileFormatType: fileFormat;
  onChangeFile: (val: fileFormat) => void;
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

export default function FileFormatConfiguration({
  paths,
  onAddPath,
  onChangePath,
  fileFormatType,
  onChangeFile
}: TFileFormatConfigurationProps) {
  const [configuration, setConfiguration] = useState<TConfiguration>({});

  const onChangeConfiguration = (params: Partial<TConfiguration>) => {
    setConfiguration((prev) => ({
      ...prev,
      ...params
    }));
  };

  const cleanConfiguration = () => {
    setConfiguration({});
  };

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
      <div className="flex items-center font-bold ">
        <div className="text-left min-w-40 w-11/12 pr-4 py-2">
          File path or file name pattern
        </div>
        <div className="px-8 py-2 text-center max-w-34 min-w-34 w-34">
          Action
        </div>
      </div>
      {paths.slice(0, paths.length - 1).map((x, index) => (
        <div key={index} className="text-black py-1.5">
          {x}
        </div>
      ))}
      <div className="flex items-center w-full">
        <div className="pr-4 min-w-40 py-2 w-11/12">
          <Input
            className="focus:!ring-0 focus:!border"
            value={paths.length ? paths[paths.length - 1] : ''}
            onChange={(e) => onChangePath(e.target.value)}
          />
        </div>
        <div className="px-8 max-w-34 min-w-34 py-2">
          <div className="flex justify-center">
            <IconButton
              size="sm"
              className="bg-teal-500"
              onClick={onAddPath}
              disabled={!paths[paths.length - 1].length}
            >
              <SvgIcon name="add" className="w-4" />
            </IconButton>
          </div>
        </div>
      </div>
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
