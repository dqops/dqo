import React, { useMemo } from 'react';
import {
  ParameterDefinitionSpecDataTypeEnum,
  ParameterDefinitionSpecDisplayHintEnum,
  FieldModel
} from '../../api';
import Checkbox from '../Checkbox';
import Input from '../Input';
import NumberInput from '../NumberInput';
import Select from '../Select';
import ObjectField from '../ObjectField';
import StringListField from '../StringListField';
import ColumnSelect from './ColumnSelect';
import TextArea from '../TextArea';
import IntegerListField from '../IntegerListField';
import FieldDatePicker from '../FieldDatePicker';
import clsx from 'clsx';
import FloatingPointInput from '../FloatingPointInput';

interface ISensorParametersFieldSettingsProps {
  field: FieldModel;
  onChange: (field: FieldModel) => void;
  disabled?: boolean;
  className?: string;
  onSave?: () => void;
  checkBoxNotRed?: boolean;
}

const FieldControl = ({
  field,
  onChange,
  disabled,
  className,
  onSave,
  checkBoxNotRed
}: ISensorParametersFieldSettingsProps) => {
  const type = field?.definition?.data_type;
  const label = field?.definition?.display_name;
  const tooltip = field?.definition?.help_text;

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
          error={checkBoxNotRed ? false : isInvalid}
        />
      )}
      {type === ParameterDefinitionSpecDataTypeEnum.string && (
        <>
          {field.definition?.display_hint ===
          ParameterDefinitionSpecDisplayHintEnum.textarea ? (
            <TextArea
              label={label}
              value={value}
              tooltipText={tooltip}
              className="!min-w-30 !max-w-30 !text-xs"
              onChange={(e) => handleChange({ string_value: e.target.value })}
              disabled={disabled}
              error={isInvalid}
              rows={1}
            />
          ) : (
            <Input
              label={label}
              value={value}
              tooltipText={tooltip}
              className={clsx(
                '!h-8 !text-xs',
                className ? className : '!min-w-30 !max-w-30'
              )}
              onChange={(e) => handleChange({ string_value: e.target.value })}
              disabled={disabled}
              error={isInvalid}
            />
          )}
        </>
      )}
      {type === ParameterDefinitionSpecDataTypeEnum.integer && (
        <NumberInput
          label={label}
          value={value}
          onChange={(value) => handleChange({ integer_value: value })}
          tooltipText={tooltip}
          className={clsx(
            '!h-8 !text-xs !min-w-30 !max-w-30',
            className ? className : '!min-w-30 !max-w-30'
          )}
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
          className={clsx(
            '!h-8 !text-xs !min-w-30 !max-w-30',

            className ? className : '!min-w-30 !max-w-30'
          )}
          disabled={disabled}
          error={isInvalid}
        />
      )}
      {type === ParameterDefinitionSpecDataTypeEnum.double && (
        <FloatingPointInput
          label={label}
          value={String(value)}
          onChange={(value) =>
            handleChange({
              double_value: Number(String(value))
            })
          }
          tooltipText={tooltip}
          className={clsx(
            '!h-8 !text-xs !min-w-30 !max-w-30',

            className ? className : '!min-w-30 !max-w-30'
          )}
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
          triggerClassName={clsx(
            '!h-8 !text-xs !min-w-30 !max-w-30',

            className ? className : '!min-w-30 !max-w-30'
          )}
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
          onSave={onSave}
        />
      )}
      {field?.definition?.data_type ===
        ParameterDefinitionSpecDataTypeEnum.integer_list && (
        <IntegerListField
          value={value}
          label={label}
          tooltipText={tooltip}
          onChange={(value: number[]) =>
            handleChange({ integer_list_value: value })
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
          triggerClassName={clsx(
            '!h-8 !text-xs !min-w-30 !max-w-30',

            className ? className : '!min-w-30 !max-w-30'
          )}
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
          <FieldDatePicker
            label={label}
            value={value}
            onChange={(date_value) => handleChange({ date_value })}
            className={clsx('!h-8 !text-xs min-w-30 max-w-30')}
            tooltipText={tooltip}
            disabled={disabled}
            error={isInvalid}
          />
        </div>
      )}
    </div>
  );
};

export default FieldControl;
