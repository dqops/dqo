import React, { useState } from 'react';

import SectionWrapper from '../../SectionWrapper';
import {
  DuckdbParametersSpec,
  SharedCredentialListModel,
  DuckdbParametersSpecSourceFilesTypeEnum
} from '../../../../api';
import FileFormatConfiguration from '../../../FileFormatConfiguration/FileFormatConfiguration'
import { TConfiguration } from '../../../../components/FileFormatConfiguration/TConfiguration';

interface IDuckdbConnectionProps {
  duckdb?: DuckdbParametersSpec;
  onChange?: (obj: DuckdbParametersSpec) => void;
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

  const [paths, setPaths] = useState<Array<string>>(['']);
  const [fileFormatType, setFileFormatType] = useState<DuckdbParametersSpecSourceFilesTypeEnum>(
    DuckdbParametersSpecSourceFilesTypeEnum.csv
  );

  const [configuration, setConfiguration] = useState<TConfiguration>({});
  const onChangeConfiguration = (params: Partial<TConfiguration>) => {
    setConfiguration((prev) => ({
      ...prev,
      ...params
    }));
  };
  const cleanConfiguration = () => { setConfiguration({}); };

  const onAddPath = () => setPaths((prev) => [...prev, '']);
  const onChangePath = (value: string) => {
    const copiedPaths = [...paths];
    copiedPaths[paths.length - 1] = value;
    setPaths(copiedPaths);
  };
  const onDeletePath = (index: number) => setPaths((prev) => prev.filter((x, i) => i !== index));
  
  const onChangeFile = (val: DuckdbParametersSpecSourceFilesTypeEnum) => setFileFormatType(val);

  return (
    <SectionWrapper title="DuckDB connection parameters" className="mb-4">
      {/* // todo: paths */}
      <SectionWrapper
            title="File format configuration"
            className="text-sm my-4 text-black"
          >
        <FileFormatConfiguration 
          // paths={paths}
          // onAddPath={onAddPath}
          // onChangePath={onChangePath}
          fileFormatType={fileFormatType}
          onChangeFile={onChangeFile}
          configuration={configuration}
          onChangeConfiguration={onChangeConfiguration}
          cleanConfiguration={cleanConfiguration}
          // onDeletePath={onDeletePath}
          freezeFileType={freezeFileType}
        />
      </SectionWrapper>
    </SectionWrapper>
  );
};

export default DuckdbConnection;
