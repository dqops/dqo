import React, { useState } from 'react';

import SectionWrapper from '../../SectionWrapper';
import {
  DuckdbParametersSpec,
  SharedCredentialListModel,
  DuckdbParametersSpecSourceFilesTypeEnum,
  DuckdbParametersSpecSecretsTypeEnum
} from '../../../../api';
import FileFormatConfiguration from '../../../FileFormatConfiguration/FileFormatConfiguration';
import { TConfiguration } from '../../../../components/FileFormatConfiguration/TConfiguration';
import FilePath from '../../../FileFormatConfiguration/FilePath';
import KeyValueProperties from '../../../FileFormatConfiguration/KeyValueProperties';
import Select from '../../../Select';
import FieldTypeInput from '../../../Connection/ConnectionView/FieldTypeInput';

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

  const secretsTypeOptions = [
    {
      label: 'None',
      value: undefined
    },
    {
      label: 'AWS S3',
      value: DuckdbParametersSpecSecretsTypeEnum.s3
    },
    {
      label: 'Google Cloud Storage',
      value: DuckdbParametersSpecSecretsTypeEnum.gcs
    },
    {
      label: 'Azure Blob Storage',
      value: DuckdbParametersSpecSecretsTypeEnum.azure
    },
    {
      label: 'Cloudflare R2',
      value: DuckdbParametersSpecSecretsTypeEnum.r2
    }
  ];

  return (
    <SectionWrapper title="DuckDB connection parameters" className="mb-4">
      
      <Select
          label="Cloud storage type"
          options={secretsTypeOptions}
          className="mb-4"
          value={ duckdb?.secrets_type }
          onChange={(value) => { handleChange({ secrets_type: value })}}
        />

      { duckdb?.secrets_type === DuckdbParametersSpecSecretsTypeEnum.s3 &&
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
            label="Password/Token"
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
