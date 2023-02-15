import React from 'react';

import Input from '../../../Input';
import SectionWrapper from '../../SectionWrapper';
import { RedshiftParametersSpec } from "../../../../api";
import SnowflakePropertiesView from "../SnowflakeProperties";
import Checkbox from "../../../Checkbox";

interface IRedshiftConnectionProps {
  redshift?: RedshiftParametersSpec;
  onChange?: (obj: RedshiftParametersSpec) => void;
}

const RedshiftConnection = ({
  redshift,
  onChange
}: IRedshiftConnectionProps) => {

  const handleChange = (obj: Partial<RedshiftParametersSpec>) => {
    if (!onChange) return;

    onChange({
      ...redshift,
      ...obj
    });
  };

  return (
    <SectionWrapper title="Redshift connection parameters" className="mb-4">
      <Input
        label="Host"
        className="mb-4"
        value={redshift?.host}
        onChange={(e) => handleChange({ host: e.target.value })}
      />
      <Input
        label="Port"
        className="mb-4"
        value={redshift?.port || 5439}
        onChange={(e) => handleChange({ port: e.target.value })}
      />
      <Input
        label="Database"
        className="mb-4"
        value={redshift?.database}
        onChange={(e) => handleChange({ database: e.target.value })}
      />
      <Input
        label="User name"
        className="mb-4"
        value={redshift?.user}
        onChange={(e) => handleChange({ user: e.target.value })}
      />
      <Input
        label="Password"
        type="password"
        className="mb-4"
        value={redshift?.password}
        onChange={(e) => handleChange({ password: e.target.value })}
      />
      <Checkbox
        checked={redshift?.ssl}
        onChange={(checked) => handleChange({ ssl: checked })}
        label="Use SSL"
        labelPosition="left"
      />

      <SnowflakePropertiesView
        properties={redshift?.properties}
        onChange={(properties) => handleChange({ properties })}
      />
    </SectionWrapper>
  );
};

export default RedshiftConnection;
