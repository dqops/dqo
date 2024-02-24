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
      duckdb?.source_files_type ?? DuckdbParametersSpecSourceFilesTypeEnum.csv
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

  const onChangeFile = (val: DuckdbParametersSpecSourceFilesTypeEnum) => {
    handleChange({
      [fileFormatType as keyof DuckdbParametersSpec]: undefined,
      [val as keyof DuckdbParametersSpec]: {},
      source_files_type: val
    });
    setFileFormatType(val);
    cleanConfiguration();
  };

  return (
    <SectionWrapper title="DuckDB connection parameters" className="mb-4">
      <FileFormatConfiguration
        fileFormatType={fileFormatType}
        onChangeFile={onChangeFile}
        configuration={configuration}
        onChangeConfiguration={onChangeConfiguration}
        cleanConfiguration={cleanConfiguration}
        freezeFileType={freezeFileType}
      >
        <KeyValueProperties
          properties={duckdb?.directories}
          onChange={(properties) => {
            handleChange({ directories: properties });
          }}
          sharedCredentials={sharedCredentials}
        />
      </FileFormatConfiguration>
    </SectionWrapper>
  );
};

export default DuckdbConnection;
