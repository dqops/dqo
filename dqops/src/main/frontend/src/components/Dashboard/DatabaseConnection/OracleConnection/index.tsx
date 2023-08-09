import React from 'react';

import SectionWrapper from '../../SectionWrapper';
import { OracleParametersSpec } from "../../../../api";
import JdbcPropertiesView from "../JdbcProperties";
import Checkbox from "../../../Checkbox";
import FieldTypeInput from "../../../Connection/ConnectionView/FieldTypeInput";

interface IOracleConnectionProps {
  oracle?: OracleParametersSpec;
  onChange?: (obj: OracleParametersSpec) => void;
}

const OracleConnection = ({
  oracle,
  onChange
}: IOracleConnectionProps) => {

  const handleChange = (obj: Partial<OracleParametersSpec>) => {
    if (!onChange) return;

    onChange({
      ...oracle,
      ...obj
    });
  };

  return (
    <SectionWrapper title="Oracle connection parameters" className="mb-4">
      <FieldTypeInput
        label="Host"
        className="mb-4"
        value={oracle?.host}
        onChange={(value) => handleChange({ host: value })}
      />
      <FieldTypeInput
        label="Port"
        className="mb-4"
        value={(oracle?.port || 1521).toString()}
        onChange={(value) => handleChange({ port: value })}
      />
      <FieldTypeInput
        label="User name"
        className="mb-4"
        value={oracle?.user}
        onChange={(value) => handleChange({ user: value })}
      />
      <FieldTypeInput
        label="Password"
        className="mb-4"
        maskingType="password"
        value={oracle?.password}
        onChange={(value) => handleChange({ password: value })}
      />
      <FieldTypeInput
        label="Initialization SQL"
        className="mb-4"
        value={oracle?.initialization_sql}
        onChange={(value) => handleChange({ initialization_sql: value })}
      />
      <Checkbox
        checked={oracle?.ssl}
        onChange={(checked) => handleChange({ ssl: checked })}
        label="Use SSL"
        labelPosition="left"
      />
      <JdbcPropertiesView
        properties={oracle?.properties}
        onChange={(properties) => handleChange({ properties })}
      />
    </SectionWrapper>
  );
};

export default OracleConnection;
