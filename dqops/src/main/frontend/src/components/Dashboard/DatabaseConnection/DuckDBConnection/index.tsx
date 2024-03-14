import React, { useEffect, useState } from 'react';

import {
  DuckdbParametersSpec,
  DuckdbParametersSpecFilesFormatTypeEnum,
  DuckdbParametersSpecStorageTypeEnum,
  SharedCredentialListModel
} from '../../../../api';
import { TConfiguration } from '../../../../components/FileFormatConfiguration/TConfiguration';
import FieldTypeInput from '../../../Connection/ConnectionView/FieldTypeInput';
import FileFormatConfiguration from '../../../FileFormatConfiguration/FileFormatConfiguration';
import KeyValueProperties from '../../../FileFormatConfiguration/KeyValueProperties';
import Select from '../../../Select';
import SectionWrapper from '../../SectionWrapper';
import JdbcPropertiesView from '../JdbcProperties';

interface IDuckdbConnectionProps {
  duckdb?: DuckdbParametersSpec;
  onChange: (obj: DuckdbParametersSpec) => void;
  sharedCredentials?: SharedCredentialListModel[];
  freezeFileType?: boolean;
}

const storageTypeOptions = [
  {
    label: 'Local files',
    value: undefined
  },
  {
    label: 'AWS S3',
    value: DuckdbParametersSpecStorageTypeEnum.s3
  }
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

  // remember to declare constans outside the component.
];
// wyczysc path po zmianie
//connectionName jest pusty i test connection jest succesfull
// s3 polaczenie succesfull gdzie sa zle credentiale i w prefix nie jest dla s3
// csv/ json/ parquet readonly jak user wybieze odpowiedni typ
// s3 path walidacja od razu po zmianie
// propertiesy odwracana kolejnosc
// path nie wskoczyl na s3
// nie zapisujemy gdy path jest pusty
// parametry sie nie dofetchowuja na connectionie
// margin wrapper i na dole mniej
const DuckdbConnection = ({
  duckdb,
  onChange,
  sharedCredentials,
  freezeFileType = false
}: IDuckdbConnectionProps) => {
  const [fileFormatType, setFileFormatType] =
    useState<DuckdbParametersSpecFilesFormatTypeEnum>(
      duckdb?.files_format_type ?? DuckdbParametersSpecFilesFormatTypeEnum.csv
    );

  const [configuration, setConfiguration] = useState<TConfiguration>({});
  const handleChange = (obj: Partial<DuckdbParametersSpec>) => {
    if (!onChange) return;
    onChange({
      ...duckdb,
      ...obj
    });
  };

  const onChangeConfiguration = (params: Partial<TConfiguration>) => {
    setConfiguration((prev) => ({
      ...prev,
      ...params
    }));
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

  useEffect(() => {
    if (configuration) {
      handleChange({
        [fileFormatType as keyof DuckdbParametersSpec]: configuration
      });
    }
  }, [configuration]);

  const changeStorageTypeDirectoryPrefixes = (
    storage_type: DuckdbParametersSpecStorageTypeEnum
  ) => {
    const directories = { ...duckdb?.directories };

    Object.keys(directories ?? {}).forEach((key) => {
      if (directories[key].length && directories[key] !== 's3://') {
        return;
      }
      if (storage_type === DuckdbParametersSpecStorageTypeEnum.s3) {
        directories[key] = 's3://';
      } else {
        directories[key] = '';
      }
    });
    handleChange({ directories, storage_type });
  };

  return (
    <SectionWrapper title="DuckDB connection parameters" className="mb-4">
      <Select
        label="Files location"
        options={storageTypeOptions}
        className="mb-4"
        value={duckdb?.storage_type}
        onChange={changeStorageTypeDirectoryPrefixes}
      />

      {duckdb?.storage_type === DuckdbParametersSpecStorageTypeEnum.s3 && (
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
      )}

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
          onChange={(directories) => {
            handleChange({ directories: directories });
          }}
          sharedCredentials={sharedCredentials}
          storageType={duckdb?.storage_type}
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
