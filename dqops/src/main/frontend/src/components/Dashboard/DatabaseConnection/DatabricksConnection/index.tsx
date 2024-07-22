import React from 'react';

import SectionWrapper from '../../SectionWrapper';
import {
  DatabricksParametersSpec,
  SharedCredentialListModel
} from '../../../../api';
import JdbcPropertiesView from '../JdbcProperties';
import FieldTypeInput from '../../../Connection/ConnectionView/FieldTypeInput';

interface IDatabricksConnectionProps {
  databricks?: DatabricksParametersSpec;
  onChange?: (obj: DatabricksParametersSpec) => void;
  sharedCredentials?: SharedCredentialListModel[];
}

const DatabricksConnection = ({
  databricks,
  onChange,
  sharedCredentials
}: IDatabricksConnectionProps) => {
  const handleChange = (obj: Partial<DatabricksParametersSpec>) => {
    if (!onChange) return;

    onChange({
      ...databricks,
      ...obj
    });
  };

  return (
    <SectionWrapper title="Databricks connection parameters" className="mb-4">
      <FieldTypeInput
        data={sharedCredentials}
        label="Host"
        className="mb-4"
        value={databricks?.host}
        onChange={(value) => handleChange({ host: value })}
        inputClassName={!databricks?.host ? 'border border-red-500' : ''}
      />
      <FieldTypeInput
        data={sharedCredentials}
        label="Port"
        className="mb-4"
        value={databricks?.port}
        onChange={(value) => handleChange({ port: value })}
      />
      <FieldTypeInput
        data={sharedCredentials}
        label="Catalog"
        className="mb-4"
        value={databricks?.catalog}
        onChange={(value) => handleChange({ catalog: value })}
      />
      <FieldTypeInput
        data={sharedCredentials}
        label="HttpPath"
        className="mb-4"
        value={databricks?.http_path}
        onChange={(value) => handleChange({ http_path: value })}
      />
      <FieldTypeInput
        data={sharedCredentials}
        label="Access token"
        className="mb-4"
        value={databricks?.access_token}
        onChange={(value) => handleChange({ access_token: value })}
      />
      <FieldTypeInput
        data={sharedCredentials}
        label="Initialization SQL"
        className="mb-4"
        value={databricks?.initialization_sql}
        onChange={(value) => handleChange({ initialization_sql: value })}
      />
      <JdbcPropertiesView
        properties={databricks?.properties}
        onChange={(properties) => handleChange({ properties })}
        sharedCredentials={sharedCredentials}
      />
    </SectionWrapper>
  );
};

export default DatabricksConnection;
