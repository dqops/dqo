import React from 'react';

import SectionWrapper from '../../SectionWrapper';
import {
  SharedCredentialListModel,
  SnowflakeParametersSpec
} from '../../../../api';
import JdbcPropertiesView from '../JdbcProperties';
import FieldTypeInput from '../../../Connection/ConnectionView/FieldTypeInput';

interface ISnowflakeConnectionProps {
  snowflake?: SnowflakeParametersSpec;
  onChange?: (obj: SnowflakeParametersSpec) => void;
  sharedCredentials: SharedCredentialListModel[];
}

const SnowflakeConnection = ({
  snowflake,
  onChange,
  sharedCredentials
}: ISnowflakeConnectionProps) => {
  const handleChange = (obj: Partial<SnowflakeParametersSpec>) => {
    if (!onChange) return;

    onChange({
      ...snowflake,
      ...obj
    });
  };

  return (
    <SectionWrapper title="Snowflake connection parameters" className="mb-4">
      <FieldTypeInput
        label="Account"
        className="mb-4"
        value={snowflake?.account}
        onChange={(value) => handleChange({ account: value })}
        data={sharedCredentials}
        inputClassName={!snowflake?.account ? 'border border-red-500' : ''}
      />
      <FieldTypeInput
        label="Warehouse"
        className="mb-4"
        value={snowflake?.warehouse}
        onChange={(value) => handleChange({ warehouse: value })}
        data={sharedCredentials}
      />
      <FieldTypeInput
        label="Database"
        className="mb-4"
        value={snowflake?.database}
        onChange={(value) => handleChange({ database: value })}
        data={sharedCredentials}
      />
      <FieldTypeInput
        label="User name"
        className="mb-4"
        value={snowflake?.user}
        onChange={(value) => handleChange({ user: value })}
        data={sharedCredentials}
      />
      <FieldTypeInput
        label="Password"
        className="mb-4"
        maskingType="password"
        value={snowflake?.password}
        onChange={(value) => handleChange({ password: value })}
        data={sharedCredentials}
      />
      <FieldTypeInput
        label="Role"
        value={snowflake?.role}
        onChange={(value) => handleChange({ role: value })}
        data={sharedCredentials}
      />

      <JdbcPropertiesView
        properties={snowflake?.properties}
        onChange={(properties) => handleChange({ properties })}
        sharedCredentials={sharedCredentials}
      />
    </SectionWrapper>
  );
};

export default SnowflakeConnection;
