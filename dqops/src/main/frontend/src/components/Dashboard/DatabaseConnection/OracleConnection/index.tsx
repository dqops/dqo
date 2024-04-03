import React from 'react';

import SectionWrapper from '../../SectionWrapper';
import {
  OracleParametersSpec,
  SharedCredentialListModel
} from '../../../../api';
import JdbcPropertiesView from '../JdbcProperties';
import FieldTypeInput from '../../../Connection/ConnectionView/FieldTypeInput';

interface IOracleConnectionProps {
  oracle?: OracleParametersSpec;
  onChange?: (obj: OracleParametersSpec) => void;
  sharedCredentials?: SharedCredentialListModel[];
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
        data={sharedCredentials}
        label="Host"
        className="mb-4"
        value={oracle?.host}
        onChange={(value) => handleChange({ host: value })}
        inputClassName={!oracle?.host ? 'border border-red-500' : ''}
      />
      <FieldTypeInput
        data={sharedCredentials}
        label="Port"
        className="mb-4"
        value={oracle?.port}
        onChange={(value) => handleChange({ port: value })}
      />
      <FieldTypeInput
        data={sharedCredentials}
        label="Database"
        className="mb-4"
        value={oracle?.database}
        onChange={(value) => handleChange({ database: value })}
      />
      <FieldTypeInput
        data={sharedCredentials}
        label="User name"
        className="mb-4"
        value={oracle?.user}
        onChange={(value) => handleChange({ user: value })}
      />
      <FieldTypeInput
        data={sharedCredentials}
        label="Password"
        className="mb-4"
        maskingType="password"
        value={oracle?.password}
        onChange={(value) => handleChange({ password: value })}
      />
      <FieldTypeInput
        data={sharedCredentials}
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
