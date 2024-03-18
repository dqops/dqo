import React, { useEffect, useState } from 'react';

import { cloneDeep } from 'lodash';
import { useSelector } from 'react-redux';
import { useParams } from 'react-router-dom';
import {
  DuckdbParametersSpec,
  DuckdbParametersSpecFilesFormatTypeEnum,
  DuckdbParametersSpecStorageTypeEnum,
  SharedCredentialListModel
} from '../../../../api';
import { TConfiguration } from '../../../../components/FileFormatConfiguration/TConfiguration';
import { getFirstLevelActiveTab } from '../../../../redux/selectors';
import { CheckTypes } from '../../../../shared/routes';
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
    value: DuckdbParametersSpecStorageTypeEnum.local
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
// wyczysc path po zmianie ->
//connectionName jest pusty i test connection jest succesfull ->
// s3 polaczenie succesfull gdzie sa zle credentiale i w prefix nie jest dla s3 ->
// csv/ json/ parquet readonly jak user wybieze odpowiedni typ
// s3 path walidacja od razu po zmianie ->
// propertiesy odwracana kolejnosc ->
// path nie wskoczyl na s3 ->
// nie zapisujemy gdy path jest pusty ->
// parametry sie nie dofetchowuja na connectionie
// margin wrapper i na dole mniej ->
const DuckdbConnection = ({
  duckdb,
  onChange,
  sharedCredentials,
  freezeFileType = false
}: IDuckdbConnectionProps) => {
  const [copiedDatabase, setCopiedDatabase] = useState<DuckdbParametersSpec>(
    cloneDeep(duckdb) ?? {}
  );
  const { checkTypes }: { checkTypes: CheckTypes } = useParams();
  const firstLevelActiveTab = useSelector(getFirstLevelActiveTab(checkTypes));
  const [fileFormatType, setFileFormatType] = useState<
    keyof DuckdbParametersSpec
  >(duckdb?.files_format_type ?? DuckdbParametersSpecFilesFormatTypeEnum.csv);
  const handleChange = (obj: Partial<DuckdbParametersSpec>) => {
    if (!onChange) return;
    onChange({
      ...copiedDatabase,
      ...obj
    });
  };

  const onChangeConfiguration = (params: Partial<TConfiguration>) => {
    const updatedObj = {
      ...copiedDatabase,
      [fileFormatType]: {
        ...(copiedDatabase?.[fileFormatType] as TConfiguration),
        ...params
      }
    };
    setCopiedDatabase(updatedObj);
  };
  const cleanConfiguration = () => {};

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
    onChange(cloneDeep(copiedDatabase) ?? {});
  }, [copiedDatabase]);

  useEffect(() => {
    setCopiedDatabase(cloneDeep(duckdb) ?? {});
  }, [firstLevelActiveTab]);

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
        fileFormatType={
          fileFormatType as DuckdbParametersSpecFilesFormatTypeEnum
        }
        onChangeFile={onChangeFile}
        configuration={
          copiedDatabase?.[
            fileFormatType as keyof DuckdbParametersSpec
          ] as TConfiguration
        }
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
