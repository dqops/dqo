import React, { useState } from 'react';

import SectionWrapper from '../../SectionWrapper';
import {
  DuckdbParametersSpec,
  SharedCredentialListModel,
  DuckdbParametersSpecSourceFilesTypeEnum
} from '../../../../api';
import FileFormatConfiguration from '../../../FileFormatConfiguration/FileFormatConfiguration';
import { TConfiguration } from '../../../../components/FileFormatConfiguration/TConfiguration';
import FilePath from '../../../FileFormatConfiguration/FilePath';
import KeyValueProperties from '../../../FileFormatConfiguration/KeyValueProperties';

interface IDuckdbConnectionProps {
  duckdb?: DuckdbParametersSpec;
  onChange: (obj: DuckdbParametersSpec) => void;
  sharedCredentials?: SharedCredentialListModel[];
  freezeFileType?: boolean;
}

const DuckdbConnection = ({
  duckdb,
  onChange,
  sharedCredentials,
  freezeFileType = false
}: IDuckdbConnectionProps) => {
  const handleChange = (obj: Partial<DuckdbParametersSpec>) => {
    if (!onChange) return;
    onChange({
      ...duckdb,
      ...obj
    });
  };

  const [fileFormatType, setFileFormatType] =
    useState<DuckdbParametersSpecSourceFilesTypeEnum>(
      DuckdbParametersSpecSourceFilesTypeEnum.csv
    );

  const [configuration, setConfiguration] = useState<TConfiguration>({});
  const onChangeConfiguration = (params: Partial<TConfiguration>) => {
    setConfiguration((prev) => ({
      ...prev,
      ...params
    }));
    handleChange({
      [fileFormatType as keyof DuckdbParametersSpec]: configuration
    });
  };
  const cleanConfiguration = () => {
    setConfiguration({});
  };

  const onChangeFile = (val: DuckdbParametersSpecSourceFilesTypeEnum) =>
    setFileFormatType(val);

  return (
    <SectionWrapper title="DuckDB connection parameters" className="mb-4">
      <SectionWrapper
        title="File format configuration"
        className="text-sm my-4 text-black"
      >
        <FileFormatConfiguration
          fileFormatType={fileFormatType}
          onChangeFile={onChangeFile}
          configuration={configuration}
          onChangeConfiguration={onChangeConfiguration}
          cleanConfiguration={cleanConfiguration}
          freezeFileType={freezeFileType}
        >
          <KeyValueProperties
            properties={duckdb?.properties}
            onChange={(properties) => onChange({ properties: properties })}
          />
        </FileFormatConfiguration>
      </SectionWrapper>
    </SectionWrapper>
  );
};

export default DuckdbConnection;
