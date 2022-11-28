import React, { useMemo } from 'react';
import { ParameterDefinitionSpecDataTypeEnum, UIFieldModel } from '../../api';
import Checkbox from '../Checkbox';
import Input from '../Input';
import NumberInput from '../NumberInput';
import Select from '../Select';
import ObjectField from '../ObjectField';
import StringListField from '../StringListField';
import ColumnSelect from './ColumnSelect';

interface ISensorParametersFieldSettingsProps {
  field: UIFieldModel;
  onChange: (field: UIFieldModel) => void;
  disabled?: boolean;
}

const FieldControl = ({
  field,
  onChange,
  disabled
}: ISensorParametersFieldSettingsProps) => {
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
        return field.string_list_value || [];
      case ParameterDefinitionSpecDataTypeEnum.enum:
        return field.enum_value;
      case ParameterDefinitionSpecDataTypeEnum.instant:
        return field.date_time_value;
      case ParameterDefinitionSpecDataTypeEnum.object:
        return field.integer_value;
      case ParameterDefinitionSpecDataTypeEnum.date:
        return field.date_value;
    }
  }, [field]);

  const handleChange = (obj: any) => {
    onChange({
      ...field,
      ...obj
    });
  };

  const isInvalid = !field.optional && !value && value !== 0 && !disabled;

  return (
    <div>
      {type === 'boolean' && (
        <Checkbox
          onChange={(value) => handleChange({ boolean_value: value })}
          checked={value}
          label={label}
          tooltipText={tooltip}
          disabled={disabled}
          error={isInvalid}
        />
      )}
      {type === ParameterDefinitionSpecDataTypeEnum.string && (
        <Input
          label={label}
          value={value}
          tooltipText={tooltip}
          className="!h-8 !min-w-30 !max-w-30"
          onChange={(e) => handleChange({ string_value: e.target.value })}
          disabled={disabled}
          error={isInvalid}
        />
      )}
      {type === ParameterDefinitionSpecDataTypeEnum.integer && (
        <NumberInput
          label={label}
          value={value}
          onChange={(value) => handleChange({ integer_value: value })}
          tooltipText={tooltip}
          className="!h-8 !min-w-30 !max-w-30"
          disabled={disabled}
          error={isInvalid}
        />
      )}
      {type === ParameterDefinitionSpecDataTypeEnum.long && (
        <NumberInput
          label={label}
          value={value}
          onChange={(value) => handleChange({ long_value: value })}
          tooltipText={tooltip}
          className="!h-8 !min-w-30 !max-w-30"
          disabled={disabled}
          error={isInvalid}
        />
      )}
      {type === ParameterDefinitionSpecDataTypeEnum.double && (
        <NumberInput
          label={label}
          value={value}
          onChange={(value) => handleChange({ double_value: value })}
          tooltipText={tooltip}
          className="!h-8 !min-w-30 !max-w-30"
          disabled={disabled}
          error={isInvalid}
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
          onChange={(value) => handleChange({ enum_value: value })}
          disabled={disabled}
          error={isInvalid}
        />
      )}
      {field?.definition?.data_type ===
        ParameterDefinitionSpecDataTypeEnum.string_list && (
        <StringListField
          value={value}
          label={label}
          tooltipText={tooltip}
          onChange={(value: string[]) =>
            handleChange({ string_list_value: value })
          }
        />
      )}
      {field?.definition?.data_type ===
        ParameterDefinitionSpecDataTypeEnum.object && (
        <ObjectField label={label} value={value} tooltipText={tooltip} />
      )}
      {field?.definition?.data_type ===
        ParameterDefinitionSpecDataTypeEnum.column_name && (
        <ColumnSelect
          triggerClassName="!h-8"
          label={label}
          value={value}
          tooltipText={tooltip}
          onChange={(value: string) =>
            handleChange({ column_name_value: value })
          }
          disabled={disabled}
          error={isInvalid}
        />
      )}
      {field?.definition?.data_type ===
        ParameterDefinitionSpecDataTypeEnum.date && (
        <div>
          <Input
            label={label}
            value={value}
            type="date"
            tooltipText={tooltip}
            className="!h-8 !min-w-40 !max-w-40"
            onChange={(e) => handleChange({ date_value: e.target.value })}
            disabled={disabled}
            error={isInvalid}
          />
        </div>
      )}
    </div>
  );
};

export default FieldControl;
