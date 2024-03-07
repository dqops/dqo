import React, { useState } from 'react';

import SectionWrapper from '../../SectionWrapper';
import {
  DuckdbParametersSpec,
  SharedCredentialListModel,
  DuckdbParametersSpecFilesFormatTypeEnum,
  DuckdbParametersSpecStorageTypeEnum
} from '../../../../api';
import FileFormatConfiguration from '../../../FileFormatConfiguration/FileFormatConfiguration';
import { TConfiguration } from '../../../../components/FileFormatConfiguration/TConfiguration';
import KeyValueProperties from '../../../FileFormatConfiguration/KeyValueProperties';
import Select from '../../../Select';
import FieldTypeInput from '../../../Connection/ConnectionView/FieldTypeInput';
import JdbcPropertiesView from '../JdbcProperties';

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

  const [storageType, setStorageType] = useState(duckdb?.storage_type);

  const handleChange = (obj: Partial<DuckdbParametersSpec>) => {
    if (!onChange) return;
    setStorageType(obj?.storage_type);
    onChange({
      ...duckdb,
      ...obj
    });
  };

  const [fileFormatType, setFileFormatType] =
    useState<DuckdbParametersSpecFilesFormatTypeEnum>(
      duckdb?.files_format_type ?? DuckdbParametersSpecFilesFormatTypeEnum.csv
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

  const onChangeFile = (val: DuckdbParametersSpecFilesFormatTypeEnum) => {
    handleChange({
      [fileFormatType as keyof DuckdbParametersSpec]: undefined,
      [val as keyof DuckdbParametersSpec]: {},
      files_format_type: val
    });
    setFileFormatType(val);
    cleanConfiguration();
  };

  const storageTypeOptions = [
    {
      label: 'Local files',
      value: undefined
    },
    {
      label: 'AWS S3',
      value: DuckdbParametersSpecStorageTypeEnum.s3
    },
    // todo: uncomment below when implemented
    // {
    //   label: 'Google Cloud Storage',
    //   value: DuckdbParametersSpecStorageTypeEnum.gcs
    // },
    // {
    //   label: 'Azure Blob Storage',
    //   value: DuckdbParametersSpecStorageTypeEnum.azure
    // },
    // {
    //   label: 'Cloudflare R2',
    //   value: DuckdbParametersSpecStorageTypeEnum.r2
    // }
  ];

  return (
    <SectionWrapper title="DuckDB connection parameters" className="mb-4">

      <Select
          label="Files location"
          options={storageTypeOptions}
          className="mb-4"
          value={ duckdb?.storage_type }
          onChange={(value) => { handleChange({ storage_type: value })}}
        />

      { duckdb?.storage_type === DuckdbParametersSpecStorageTypeEnum.s3 &&
        <>
          <FieldTypeInput
            data={sharedCredentials}
            label="User name/Key ID"
            className="mb-4"
            value={duckdb?.user}
            onChange={(value) => handleChange({ user: value })}
          />
          <FieldTypeInput
            data={sharedCredentials}
            label="Password/Secret Key"
            className="mb-4"
            maskingType="password"
            value={duckdb?.password}
            onChange={(value) => handleChange({ password: value })}
          />
          <FieldTypeInput
            data={sharedCredentials}
            label="Region"
            className="mb-4"
            maskingType="region"
            value={duckdb?.region}
            onChange={(value) => handleChange({ region: value })}
          />
        </>
      }

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
          onChange={(directories) => { handleChange({ directories: directories })}}
          sharedCredentials={sharedCredentials}
          storageType={storageType}
        />
      </FileFormatConfiguration>
      <JdbcPropertiesView
        properties={duckdb?.properties}
        onChange={(properties) => handleChange({ properties })}
        sharedCredentials={sharedCredentials}
      />
    </SectionWrapper>
  );
};

export default DuckdbConnection;
