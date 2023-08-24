import React, { useMemo } from 'react';
import {
  ParameterDefinitionSpecDataTypeEnum,
  FieldModel
} from '../../api';

interface IFieldValueProps {
  field: FieldModel;
}

const FieldValue = ({
  field,
}: IFieldValueProps) => {
  const label = field?.definition?.display_name;

  const value: any = useMemo(() => {
    switch (field.definition?.data_type) {
      case ParameterDefinitionSpecDataTypeEnum.string:
        return field.string_value;
      case ParameterDefinitionSpecDataTypeEnum.column_name:
        return field.column_name_value;
      case ParameterDefinitionSpecDataTypeEnum.boolean:
        return field.boolean_value;
      case ParameterDefinitionSpecDataTypeEnum.double:
        return field.double_value;
      case ParameterDefinitionSpecDataTypeEnum.integer:
        return field.integer_value;
      case ParameterDefinitionSpecDataTypeEnum.long:
        return field.long_value;
      case ParameterDefinitionSpecDataTypeEnum.string_list:
        return field.string_list_value || [];
      case ParameterDefinitionSpecDataTypeEnum.integer_list:
        return field.integer_list_value || [];
      case ParameterDefinitionSpecDataTypeEnum.enum:
        return field.enum_value;
      case ParameterDefinitionSpecDataTypeEnum.datetime:
        return field.datetime_value;
      case ParameterDefinitionSpecDataTypeEnum.object:
        return field.integer_value;
      case ParameterDefinitionSpecDataTypeEnum.date:
        return field.date_value;
    }
  }, [field]);

  return (
    <div>
      {label}: {value?.toString()}
    </div>
  );
};

export default FieldValue;
