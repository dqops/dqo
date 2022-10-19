import React, { useMemo } from 'react';
import { ParameterDefinitionSpecDataTypeEnum, UIFieldModel } from '../../api';
import Checkbox from '../Checkbox';
import Input from '../Input';
import NumberInput from '../NumberInput';
import Select from '../Select';
import ObjectField from '../ObjectField';
import StringListField from '../StringListField';

interface ISensorParametersFieldSettingsProps {
  field: UIFieldModel;
}

const FieldControl = ({ field }: ISensorParametersFieldSettingsProps) => {
  const type = field?.definition?.data_type;
  const label = field?.definition?.display_name;
  const tooltip = field?.definition?.help_hext;

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
        return field.string_list_value;
      case ParameterDefinitionSpecDataTypeEnum.enum:
        return field.enum_value;
      case ParameterDefinitionSpecDataTypeEnum.instant:
        return field.date_time_value;
      case ParameterDefinitionSpecDataTypeEnum.object:
        return field.integer_value;
    }
  }, [field]);

  return (
    <div>
      {type === 'boolean' && (
        <Checkbox
          onChange={() => {}}
          checked={value}
          label={label}
          tooltipText={tooltip}
        />
      )}
      {type === ParameterDefinitionSpecDataTypeEnum.string && (
        <Input
          label={label}
          value={value}
          tooltipText={tooltip}
          className="!h-8"
        />
      )}
      {(type === ParameterDefinitionSpecDataTypeEnum.integer ||
        type === ParameterDefinitionSpecDataTypeEnum.long ||
        type === ParameterDefinitionSpecDataTypeEnum.double) && (
        <NumberInput
          label={label}
          value={value}
          onChange={() => {}}
          tooltipText={tooltip}
          className="!h-8"
        />
      )}
      {field?.definition?.data_type ===
        ParameterDefinitionSpecDataTypeEnum.enum && (
        <Select
          label={label}
          value={value}
          options={
            field.definition?.allowed_values?.map((item) => ({
              label: item,
              value: item
            })) || []
          }
          tooltipText={tooltip}
          triggerClassName="!h-8"
        />
      )}
      {field?.definition?.data_type ===
        ParameterDefinitionSpecDataTypeEnum.string_list && (
        <StringListField value={value} label={label} tooltipText={tooltip} />
      )}
      {field?.definition?.data_type ===
        ParameterDefinitionSpecDataTypeEnum.object && (
        <ObjectField label={label} value={value} tooltipText={tooltip} />
      )}
    </div>
  );
};

export default FieldControl;
