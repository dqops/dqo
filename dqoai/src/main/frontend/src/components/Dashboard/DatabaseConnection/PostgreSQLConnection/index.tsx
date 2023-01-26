import React from 'react';

import Input from '../../../Input';
import SectionWrapper from '../../SectionWrapper';
import { PostgresqlParametersSpec } from "../../../../api";
import SnowflakePropertiesView from "../SnowflakeProperties";
import Checkbox from "../../../Checkbox";

interface IPostgreSQLConnectionProps {
  postgresql?: PostgresqlParametersSpec;
  onChange?: (obj: PostgresqlParametersSpec) => void;
}

const PostgreSQLConnection = ({
  postgresql,
  onChange
}: IPostgreSQLConnectionProps) => {

  const handleChange = (obj: Partial<PostgresqlParametersSpec>) => {
    if (!onChange) return;

    onChange({
      ...postgresql,
      ...obj
    });
  };

  return (
    <SectionWrapper title="PostgreSQL connection parameters" className="mb-4">
      <Input
        label="Host"
        className="mb-4"
        value={postgresql?.host}
        onChange={(e) => handleChange({ host: e.target.value })}
      />
      <Input
        label="Port"
        className="mb-4"
        value={postgresql?.port || 5432}
        onChange={(e) => handleChange({ port: e.target.value })}
      />
      <Input
        label="User name"
        className="mb-4"
        value={postgresql?.user}
        onChange={(e) => handleChange({ user: e.target.value })}
      />
      <Input
        label="Password"
        type="password"
        className="mb-4"
        value={postgresql?.password}
        onChange={(e) => handleChange({ password: e.target.value })}
      />
      <Checkbox
        checked={postgresql?.ssl}
        onChange={(checked) => handleChange({ ssl: checked })}
        label="Use SSL"
        labelPosition="left"
      />

      <SnowflakePropertiesView
        properties={postgresql?.properties}
        onChange={(properties) => handleChange({ properties })}
      />
    </SectionWrapper>
  );
};

export default PostgreSQLConnection;
