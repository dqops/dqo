import React from 'react';

import SectionWrapper from '../../SectionWrapper';
import { SharedCredentialListModel, SqlServerParametersSpec } from "../../../../api";
import JdbcPropertiesView from "../JdbcProperties";
import FieldTypeInput from "../../../Connection/ConnectionView/FieldTypeInput";
import Checkbox from "../../../Checkbox";

interface ISqlServerConnectionProps {
  sqlserver?: SqlServerParametersSpec;
  onChange?: (obj: SqlServerParametersSpec) => void;
  sharedCredentials ?: SharedCredentialListModel[];
}

const SqlServerConnection = ({
  sqlserver,
  onChange,
  sharedCredentials
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
      <FieldTypeInput
        label="Host"
        className="mb-4"
        value={sqlserver?.host}
        onChange={(value) => handleChange({ host: value })}
      />
      <FieldTypeInput
        label="Port"
        className="mb-4"
        value={(sqlserver?.port || 1433).toString()}
        onChange={(value) => handleChange({ port: value })}
      />
      <FieldTypeInput
        label="Database"
        className="mb-4"
        value={sqlserver?.database}
        onChange={(value) => handleChange({ database: value })}
      />
      <FieldTypeInput
        label="User name"
        className="mb-4"
        value={sqlserver?.user}
        onChange={(value) => handleChange({ user: value })}
      />
      <FieldTypeInput
        label="Password"
        className="mb-4"
        maskingType="password"
        value={sqlserver?.password}
        onChange={(value) => handleChange({ password: value })}
      />
      <Checkbox
        checked={sqlserver?.disable_encryption}
        onChange={(checked) => handleChange({ disable_encryption: checked })}
        label="Disable encryption"
        labelPosition="left"
      />
      <JdbcPropertiesView
        properties={sqlserver?.properties}
        onChange={(properties) => handleChange({ properties })}
        sharedCredentials={sharedCredentials}
      />
    </SectionWrapper>
  );
};

export default SqlServerConnection;
