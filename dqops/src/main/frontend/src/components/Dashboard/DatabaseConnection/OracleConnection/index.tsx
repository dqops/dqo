import React from 'react';

import SectionWrapper from '../../SectionWrapper';
import { OracleParametersSpec, SharedCredentialListModel } from "../../../../api";
import JdbcPropertiesView from "../JdbcProperties";
import FieldTypeInput from "../../../Connection/ConnectionView/FieldTypeInput";

interface IOracleConnectionProps {
  oracle?: OracleParametersSpec;
  onChange?: (obj: OracleParametersSpec) => void;
  sharedCredentials ?: SharedCredentialListModel[];
}

const OracleConnection = ({
  oracle,
  onChange,
  sharedCredentials
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
        label="Database"
        className="mb-4"
        value={oracle?.database}
        onChange={(value) => handleChange({ database: value })}
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
      <JdbcPropertiesView
        properties={oracle?.properties}
        onChange={(properties) => handleChange({ properties })}
        sharedCredentials={sharedCredentials}
      />
    </SectionWrapper>
  );
};

export default OracleConnection;
