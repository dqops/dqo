import React from 'react';

import { MysqlParametersSpec, MysqlParametersSpecSslmodeEnum, SharedCredentialListModel } from "../../../../api";
import JdbcPropertiesView from "../JdbcProperties";
import Select from '../../../Select';
import SectionWrapper from '../../SectionWrapper';
import FieldTypeInput from "../../../Connection/ConnectionView/FieldTypeInput";

interface IMySQLConnectionProps {
  mysql?: MysqlParametersSpec;
  onChange?: (obj: MysqlParametersSpec) => void;
  sharedCredentials ?: SharedCredentialListModel[];
}

const sslModes = [
  {
    label: '',
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

const MySQLConnection = ({
  mysql,
  onChange,
  sharedCredentials
}: IMySQLConnectionProps) => {

  const handleChange = (obj: Partial<MysqlParametersSpec>) => {
    if (!onChange) return;

    onChange({
      ...mysql,
      ...obj
    });
  };

  return (
    <SectionWrapper title="MySQL connection parameters" className="mb-4">
      <FieldTypeInput  
        data = {sharedCredentials}
        label="Host"
        className="mb-4"
        value={mysql?.host}
        onChange={(value) => handleChange({ host: value })}
      />
      <FieldTypeInput  
        data = {sharedCredentials}
        label="Port"
        className="mb-4"
        value={(mysql?.port || 3306).toString()}
        onChange={(value) => handleChange({ port: value })}
      />
      <FieldTypeInput  
        data = {sharedCredentials}
        label="User name"
        className="mb-4"
        value={mysql?.user}
        onChange={(value) => handleChange({ user: value })}
      />
      <FieldTypeInput  
        data = {sharedCredentials}
        label="Password"
        className="mb-4"
        maskingType="password"
        value={mysql?.password}
        onChange={(value) => handleChange({ password: value })}
      />
      <FieldTypeInput  
        data = {sharedCredentials}
        label="Database"
        className="mb-4"
        value={mysql?.database}
        onChange={(value) => handleChange({ database: value })}
      />
      <Select
        label="SSL mode"
        options={sslModes}
        className="mb-4"
        value={
          mysql?.sslmode
        }
        onChange={(value) => handleChange({ sslmode: value })}
      />
      <JdbcPropertiesView
        properties={mysql?.properties}
        onChange={(properties) => handleChange({ properties })}
        sharedCredentials={sharedCredentials}
      />
    </SectionWrapper>
  );
};

export default MySQLConnection;
