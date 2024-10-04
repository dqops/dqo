import React, { useState } from 'react';

import clsx from 'clsx';
import {
  Db2ParametersSpec,
  SharedCredentialListModel,
} from '../../../../api';
import FieldTypeInput from '../../../Connection/ConnectionView/FieldTypeInput';
import Select from '../../../Select';
import SectionWrapper from '../../SectionWrapper';
import JdbcPropertiesView from '../JdbcProperties';

interface IDb2ParametersSpec {
  db2?: Db2ParametersSpec;
  onChange?: (obj: Db2ParametersSpec) => void;
  sharedCredentials?: SharedCredentialListModel[];
  nameOfDatabase?: string;
  onNameOfDatabaseChange?: (name: string) => void;
}

// const options = [
//   {
//     label: 'Trino',
//     value: TrinoParametersSpecTrinoEngineTypeEnum.trino
//   },
//   {
//     label: 'AWS Athena',
//     value: TrinoParametersSpecTrinoEngineTypeEnum.athena
//   }
// ];


const Db2Connection = ({
  db2,
  onChange,
  sharedCredentials,
  nameOfDatabase,
  onNameOfDatabaseChange
}: IDb2ParametersSpec) => {
  const [selectedInput, setSelectedInput] = useState<number | string>();
  const handleChange = (obj: Partial<Db2ParametersSpec>) => {
    if (!onChange) return;
    onChange({
      ...db2,
      ...obj
    });
  };

  return (
    <SectionWrapper title="DB2 connection parameters" className="mb-4">
      {/* <Select
        label="Trino engine type"
        options={options}
        className={clsx(
          'mb-4',
          !trino?.trino_engine_type ? 'border border-red-500' : ''
        )}
        value={
          trino?.trino_engine_type ||
          (nameOfDatabase &&
            (nameOfDatabase.toLowerCase() as TrinoParametersSpecTrinoEngineTypeEnum))
        }
        onChange={(value) => {
          handleChange({
            trino_engine_type: value,
            catalog:
              value === TrinoParametersSpecTrinoEngineTypeEnum.trino
                ? ''
                : 'awsdatacatalog'
          }),
            value &&
              onNameOfDatabaseChange &&
              onNameOfDatabaseChange(
                String(value).replace(/\w/, (x) => x.toUpperCase())
              );
        }}
        onClickValue={setSelectedInput}
        selectedMenu={selectedInput}
        menuClassName="!top-14"
      /> */}
        <FieldTypeInput
          data={sharedCredentials}
          label="Host"
          className="mb-4"
          value={db2?.host}
          onChange={(value) => handleChange({ host: value })}
          inputClassName={!db2?.host ? 'border border-red-500' : ''}
        />
        <FieldTypeInput
          data={sharedCredentials}
          label="Port"
          className="mb-4"
          value={db2?.port}
          onChange={(value) => handleChange({ port: value })}
        />
        <FieldTypeInput
          data={sharedCredentials}
          label="Database"
          className="mb-4"
          value={db2?.database}
          onChange={(value) => handleChange({ database: value })}
        />
        <FieldTypeInput
          data={sharedCredentials}
          label="User name"
          className="mb-4"
          value={db2?.user}
          onChange={(value) => handleChange({ user: value })}
        />
        <FieldTypeInput
        data={sharedCredentials}
        label="Password"
        className="mb-4"
        maskingType="password"
        value={db2?.password}
        onChange={(value) => handleChange({ password: value })}
      />
      <JdbcPropertiesView
        properties={db2?.properties}
        onChange={(properties) => handleChange({ properties })}
        sharedCredentials={sharedCredentials}
      />
    </SectionWrapper>
  );
};

export default Db2Connection;
