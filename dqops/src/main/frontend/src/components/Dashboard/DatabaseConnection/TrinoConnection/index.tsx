import React from 'react';

import SectionWrapper from '../../SectionWrapper';
import {
  TrinoParametersSpec,
  SharedCredentialListModel
} from '../../../../api';
import JdbcPropertiesView from '../JdbcProperties';
import FieldTypeInput from '../../../Connection/ConnectionView/FieldTypeInput';

interface ITrinoConnectionProps {
  trino?: TrinoParametersSpec;
  onChange?: (obj: TrinoParametersSpec) => void;
  sharedCredentials?: SharedCredentialListModel[];
}

const TrinoConnection = ({
  trino,
  onChange,
  sharedCredentials
}: ITrinoConnectionProps) => {
  const handleChange = (obj: Partial<TrinoParametersSpec>) => {
    if (!onChange) return;

    onChange({
      ...trino,
      ...obj
    });
  };

  return (
    <SectionWrapper title="Trino connection parameters" className="mb-4">
      <FieldTypeInput
        data={sharedCredentials}
        label="Host"
        className="mb-4"
        value={trino?.host}
        onChange={(value) => handleChange({ host: value })}
      />
      <FieldTypeInput
        data={sharedCredentials}
        label="Port"
        className="mb-4"
        value={trino?.port}
        onChange={(value) => handleChange({ port: value })}
      />
      <FieldTypeInput
        data={sharedCredentials}
        label="Database"
        className="mb-4"
        value={trino?.database}
        onChange={(value) => handleChange({ database: value })}
      />
      <FieldTypeInput
        data={sharedCredentials}
        label="User name"
        className="mb-4"
        value={trino?.user}
        onChange={(value) => handleChange({ user: value })}
      />
      <JdbcPropertiesView
        properties={trino?.properties}
        onChange={(properties) => handleChange({ properties })}
        sharedCredentials={sharedCredentials}
      />
    </SectionWrapper>
  );
};

export default TrinoConnection;
