import React from 'react';
import SectionWrapper from '../Dashboard/SectionWrapper';
import { DuckdbParametersSpecSourceFilesTypeEnum } from '../../api';
import FilePath from './FilePath';
import { TConfiguration } from './TConfiguration';

type TFileFormatConfigurationProps = {
  paths: string[];
  onChangePath: (val: string) => void;
  onAddPath: () => void;
  fileFormatType: DuckdbParametersSpecSourceFilesTypeEnum;
  configuration: TConfiguration;
  cleanConfiguration: () => void;
  onDeletePath: (index: number) => void;
};

export default function FileFormatConfiguration({
  paths,
  onAddPath,
  onChangePath,
  onDeletePath,
}: TFileFormatConfigurationProps) {

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
      
    </SectionWrapper>
  );
}
