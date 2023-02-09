import React from 'react';

import Input from '../../../Input';
import SectionWrapper from '../../SectionWrapper';
import { SnowflakeParametersSpec } from "../../../../api";
import SnowflakePropertiesView from "../SnowflakeProperties";

interface ISnowflakeConnectionProps {
  snowflake?: SnowflakeParametersSpec;
  onChange?: (obj: SnowflakeParametersSpec) => void;
}

const SnowflakeConnection = ({
  snowflake,
  onChange
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
      <Input
        label="Account"
        className="mb-4"
        value={snowflake?.account}
        onChange={(e) => handleChange({ account: e.target.value })}
      />
      <Input
        label="Warehouse"
        className="mb-4"
        value={snowflake?.warehouse}
        onChange={(e) => handleChange({ warehouse: e.target.value })}
      />
      <Input
        label="Database"
        className="mb-4"
        value={snowflake?.database}
        onChange={(e) => handleChange({ database: e.target.value })}
      />
      <Input
        label="User name"
        className="mb-4"
        value={snowflake?.user}
        onChange={(e) => handleChange({ user: e.target.value })}
      />
      <Input
        label="Password"
        type="password"
        className="mb-4"
        value={snowflake?.password}
        onChange={(e) => handleChange({ password: e.target.value })}
      />
      <Input
        label="Role"
        value={snowflake?.role}
        onChange={(e) => handleChange({ role: e.target.value })}
      />

      <SnowflakePropertiesView
        properties={snowflake?.properties}
        onChange={(properties) => handleChange({ properties })}
      />
    </SectionWrapper>
  );
};

export default SnowflakeConnection;
