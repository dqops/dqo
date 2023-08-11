import React from 'react';

import SectionWrapper from '../../SectionWrapper';
import { MysqlParametersSpec } from "../../../../api";
import JdbcPropertiesView from "../JdbcProperties";
import Checkbox from "../../../Checkbox";
import FieldTypeInput from "../../../Connection/ConnectionView/FieldTypeInput";

interface IMySQLConnectionProps {
  mysql?: MysqlParametersSpec;
  onChange?: (obj: MysqlParametersSpec) => void;
}

const MySQLConnection = ({
  mysql,
  onChange
}: IMySQLConnectionProps) => {

  const handleChange = (obj: Partial<MysqlParametersSpec>) => {
    if (!onChange) return;

    onChange({
      ...mysql,
      ...obj
    });
  };

  return (
    <SectionWrapper title="MySQL connection parameters" className="mb-4">
      <FieldTypeInput
        label="Host"
        className="mb-4"
        value={mysql?.host}
        onChange={(value) => handleChange({ host: value })}
      />
      <FieldTypeInput
        label="Port"
        className="mb-4"
        value={(mysql?.port || 3306).toString()}
        onChange={(value) => handleChange({ port: value })}
      />
      <FieldTypeInput
        label="User name"
        className="mb-4"
        value={mysql?.user}
        onChange={(value) => handleChange({ user: value })}
      />
      <FieldTypeInput
        label="Password"
        className="mb-4"
        maskingType="password"
        value={mysql?.password}
        onChange={(value) => handleChange({ password: value })}
      />
      <JdbcPropertiesView
        properties={mysql?.properties}
        onChange={(properties) => handleChange({ properties })}
      />
    </SectionWrapper>
  );
};

export default MySQLConnection;
