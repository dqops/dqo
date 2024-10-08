import React, { useState } from 'react';

import clsx from 'clsx';
import {
  MysqlParametersSpec,
  MysqlParametersSpecMysqlEngineTypeEnum,
  MysqlParametersSpecSslmodeEnum,
  SharedCredentialListModel,
  SingleStoreDbParametersSpecLoadBalancingModeEnum
} from '../../../../api';
import Checkbox from '../../../Checkbox';
import FieldTypeInput from '../../../Connection/ConnectionView/FieldTypeInput';
import Select from '../../../Select';
import JdbcPropertiesView from '../JdbcProperties';

interface IMySQLConnectionProps {
  mysql?: MysqlParametersSpec;
  onChange?: (obj: MysqlParametersSpec) => void;
  sharedCredentials?: SharedCredentialListModel[];
  nameOfDatabase?: string;
  onNameOfDatabaseChange?: (name: string) => void;
}

const sslModes = [
  {
    label: ''
  },
  {
    label: 'DISABLED',
    value: MysqlParametersSpecSslmodeEnum.DISABLED
  },
  {
    label: 'PREFERRED',
    value: MysqlParametersSpecSslmodeEnum.PREFERRED
  },
  {
    label: 'REQUIRED',
    value: MysqlParametersSpecSslmodeEnum.REQUIRED
  },
  {
    label: 'VERIFY_CA',
    value: MysqlParametersSpecSslmodeEnum.VERIFY_CA
  },
  {
    label: 'VERIFY_IDENTITY',
    value: MysqlParametersSpecSslmodeEnum.VERIFY_IDENTITY
  }
];

const loadBalancingMode = [
  {
    label: 'None',
    value: SingleStoreDbParametersSpecLoadBalancingModeEnum.none
  },
  {
    label: 'Sequential',
    value: SingleStoreDbParametersSpecLoadBalancingModeEnum.sequential
  },
  {
    label: 'LoadBalance',
    value: SingleStoreDbParametersSpecLoadBalancingModeEnum.loadbalance
  }
];

const mysqlEngineType = [
  {
    label: 'mysql',
    value: MysqlParametersSpecMysqlEngineTypeEnum.mysql
  },
  {
    label: 'singlestoredb',
    value: MysqlParametersSpecMysqlEngineTypeEnum.singlestoredb
  }
];

const MySQLConnection = ({
  mysql,
  onChange,
  sharedCredentials,
  nameOfDatabase,
  onNameOfDatabaseChange
}: IMySQLConnectionProps) => {
  const [selectedInput, setSelectedInput] = useState<number | string>();
  const handleChange = (obj: Partial<MysqlParametersSpec>) => {
    if (!onChange) return;
    onChange({
      ...mysql,
      ...obj,
      single_store_db_parameters_spec: {
        ...mysql?.single_store_db_parameters_spec,
        ...obj?.single_store_db_parameters_spec
      }
    });
  };

  return (
    <div>
      <Select
        label="MySQL Engine Type"
        options={mysqlEngineType}
        className={clsx(
          'mb-4',
          !mysql?.mysql_engine_type ? 'border border-red-500' : ''
        )}
        value={
          mysql?.mysql_engine_type ||
          (nameOfDatabase
            ?.toLowerCase()
            .trim() as MysqlParametersSpecMysqlEngineTypeEnum)
        }
        onChange={(value) => {
          handleChange({
            mysql_engine_type: value
          }),
            value &&
              onNameOfDatabaseChange &&
              onNameOfDatabaseChange(
                String(value).replace(/\w/, (x) => x.toUpperCase())
              );
        }}
        onClickValue={setSelectedInput}
        selectedMenu={selectedInput}
        menuClassName="!top-14"
      />

      {mysql?.mysql_engine_type ===
        MysqlParametersSpecMysqlEngineTypeEnum.mysql && (
        <>
          <FieldTypeInput
            data={sharedCredentials}
            label="Host"
            className="mb-4"
            value={mysql?.host}
            onChange={(value) => handleChange({ host: value })}
            inputClassName={!mysql?.host ? 'border border-red-500' : ''}
          />
          <FieldTypeInput
            data={sharedCredentials}
            label="Port"
            className="mb-4"
            value={mysql?.port}
            onChange={(value) => handleChange({ port: value })}
          />
        </>
      )}
      {mysql?.mysql_engine_type ===
        MysqlParametersSpecMysqlEngineTypeEnum.singlestoredb && (
        <>
          <Select
            label="Load-Balancing Mode"
            options={loadBalancingMode}
            className="mb-4"
            value={mysql?.single_store_db_parameters_spec?.load_balancing_mode}
            onChange={(value) =>
              handleChange({
                single_store_db_parameters_spec: { load_balancing_mode: value }
              })
            }
            onClickValue={setSelectedInput}
            selectedMenu={selectedInput}
            menuClassName="!top-14"
          />
          <FieldTypeInput
            data={sharedCredentials}
            label="Host descriptions"
            className="mb-4"
            value={mysql?.single_store_db_parameters_spec?.host_descriptions?.join(
              ','
            )}
            onChange={(value) =>
              handleChange({
                single_store_db_parameters_spec: {
                  host_descriptions: value.split(',')
                }
              })
            }
          />
          <FieldTypeInput
            data={sharedCredentials}
            label="Database/Schema"
            className="mb-4"
            value={mysql?.single_store_db_parameters_spec?.schema}
            onChange={(value) =>
              handleChange({
                single_store_db_parameters_spec: { schema: value }
              })
            }
          />
        </>
      )}

      {mysql?.mysql_engine_type ===
        MysqlParametersSpecMysqlEngineTypeEnum.mysql && (
        <FieldTypeInput
          data={sharedCredentials}
          label="Database"
          className="mb-4"
          value={mysql?.database}
          onChange={(value) => handleChange({ database: value })}
        />
      )}

      <FieldTypeInput
        data={sharedCredentials}
        label="User name"
        className="mb-4"
        value={mysql?.user}
        onChange={(value) => handleChange({ user: value })}
      />
      <FieldTypeInput
        data={sharedCredentials}
        label="Password"
        className="mb-4"
        maskingType="password"
        value={mysql?.password}
        onChange={(value) => handleChange({ password: value })}
      />

      {mysql?.mysql_engine_type ===
        MysqlParametersSpecMysqlEngineTypeEnum.mysql && (
        <Select
          label="SSL mode"
          options={sslModes}
          className="mb-4"
          value={mysql?.sslmode}
          onChange={(value) => handleChange({ sslmode: value })}
          onClickValue={setSelectedInput}
          selectedMenu={selectedInput}
          menuClassName="!top-14"
        />
      )}

      {mysql?.mysql_engine_type ===
        MysqlParametersSpecMysqlEngineTypeEnum.singlestoredb && (
        <Checkbox
          checked={mysql?.single_store_db_parameters_spec?.use_ssl}
          onChange={(checked) =>
            handleChange({
              single_store_db_parameters_spec: { use_ssl: checked }
            })
          }
          label="Use SSL"
          labelPosition="left"
        />
      )}

      <JdbcPropertiesView
        properties={mysql?.properties}
        onChange={(properties) => handleChange({ properties })}
        sharedCredentials={sharedCredentials}
      />
    </div>
  );
};

export default MySQLConnection;
