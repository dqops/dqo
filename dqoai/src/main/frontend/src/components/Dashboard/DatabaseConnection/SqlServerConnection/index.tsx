import React from 'react';

import Input from '../../../Input';
import SectionWrapper from '../../SectionWrapper';
import { SqlServerParametersSpec } from "../../../../api";
import SnowflakePropertiesView from "../SnowflakeProperties";
import Checkbox from "../../../Checkbox";

interface ISqlServerConnectionProps {
  sqlserver?: SqlServerParametersSpec;
  onChange?: (obj: SqlServerParametersSpec) => void;
}

const SqlServerConnection = ({
  sqlserver,
  onChange
}: ISqlServerConnectionProps) => {

  const handleChange = (obj: Partial<SqlServerParametersSpec>) => {
    if (!onChange) return;

    onChange({
      ...sqlserver,
      ...obj
    });
  };

  return (
    <SectionWrapper title="Microsoft SQL Server/SQL Server connection parameters" className="mb-4">
      <Input
        label="Host"
        className="mb-4"
        value={sqlserver?.host}
        onChange={(e) => handleChange({ host: e.target.value })}
      />
      <Input
        label="Port"
        className="mb-4"
        value={sqlserver?.port || 1433}
        onChange={(e) => handleChange({ port: e.target.value })}
      />
      <Input
        label="Database"
        className="mb-4"
        value={sqlserver?.database}
        onChange={(e) => handleChange({ database: e.target.value })}
      />
      <Input
        label="User name"
        className="mb-4"
        value={sqlserver?.user}
        onChange={(e) => handleChange({ user: e.target.value })}
      />
      <Input
        label="Password"
        type="password"
        className="mb-4"
        value={sqlserver?.password}
        onChange={(e) => handleChange({ password: e.target.value })}
      />
      <Checkbox
        checked={sqlserver?.ssl}
        onChange={(checked) => handleChange({ ssl: checked })}
        label="Use SSL"
        labelPosition="left"
      />
      <SnowflakePropertiesView
        properties={sqlserver?.properties}
        onChange={(properties) => handleChange({ properties })}
      />
    </SectionWrapper>
  );
};

export default SqlServerConnection;
