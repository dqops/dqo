import clsx from 'clsx';
import React, { useMemo } from 'react';
import { useSelector } from 'react-redux';
import {
  FieldModel,
  ParameterDefinitionSpecDataTypeEnum,
  ParameterDefinitionSpecDisplayHintEnum
} from '../../api';
import { IRootState } from '../../redux/reducers';
import CheckboxColumn from '../Checkbox/CheckBoxColumn';
import ColumnsRecordDialog from '../ColumnsRecordDialog/ColumnsRecordDialog';
import ExtendedTextAre from '../ExtendedTextArea';
import FieldDatePicker from '../FieldDatePicker';
import FloatingPointInput from '../FloatingPointInput';
import Input from '../Input';
import IntegerListField from '../IntegerListField';
import NumberInput from '../NumberInput';
import ObjectField from '../ObjectField';
import Select from '../Select';
import StringListField from '../StringListField';
import ColumnSelect from './ColumnSelect';

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
  const { userProfile } = useSelector((state: IRootState) => state.job);
  const type = field?.definition?.data_type;
  const label = field?.definition?.display_name;
  const tooltip = field?.definition?.help_text;

  const value: any = useMemo(() => {
    switch (field.definition?.data_type) {
      case ParameterDefinitionSpecDataTypeEnum.string:
        return field.string_value || '';
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

  const isInvalid =
    !field.optional &&
    !disabled &&
    (value === undefined ||
      value === '' ||
      (Array.isArray(value) && value.length === 0));

  const isActionInvalid =
    field.definition?.field_name === 'use_ai' &&
    userProfile.can_use_ai_anomaly_detection !== true;

  return (
    <div>
      {type === 'boolean' && (
        <div className="relative mb-4">
          {isActionInvalid && value && (
            <div
              className="absolute !h-15 !w-18 border-3 border-red-500 left-0 !z-[99]"
              style={{ left: '-5px', pointerEvents: 'none' }}
            ></div>
          )}
          <CheckboxColumn
            onChange={(value) => handleChange({ boolean_value: value })}
            checked={value}
            label={label}
            tooltipText={tooltip}
            disabled={disabled}
            error={checkBoxNotRed ? false : isInvalid}
          />
        </div>
      )}
      {type === ParameterDefinitionSpecDataTypeEnum.string && (
        <>
          {field.definition?.display_hint ===
          ParameterDefinitionSpecDisplayHintEnum.textarea ? (
            <ExtendedTextAre
              label={label}
              value={value}
              tooltipText={tooltip}
              className="!min-w-30 !max-w-30 !text-xs"
              onChange={(e) => handleChange({ string_value: e.target.value })}
              setValue={(value: string) =>
                handleChange({ string_value: value })
              }
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
          onChange={(value) =>
            handleChange({
              integer_value:
                String(value).length === 0 ? undefined : Number(value)
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
      {type === ParameterDefinitionSpecDataTypeEnum.long && (
        <NumberInput
          label={label}
          value={value}
          onChange={(value) =>
            handleChange({
              long_value: String(value).length === 0 ? undefined : Number(value)
            })
          }
          onChangeUndefined={() => handleChange({ long_value: undefined })}
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
          value={value}
          onChange={(value) =>
            handleChange({
              double_value:
                String(value).length === 0 ? undefined : Number(value)
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
        <div>
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
              '!h-8 !text-xs !min-w-30 !max-w-30 !bg-white !ml-2',
              className ? className : '!min-w-30 !max-w-30'
            )}
            menuClassName="!top-13"
            className="text-sm"
            onChange={(value) => handleChange({ enum_value: value })}
            disabled={disabled}
            error={isInvalid}
          />
        </div>
      )}
      {field?.definition?.data_type ===
        ParameterDefinitionSpecDataTypeEnum.string_list && (
        <div className="mt-4">
          {field.definition?.display_hint === 'column_names' ? (
            <ColumnsRecordDialog
              value={value}
              label={label}
              tooltipText={tooltip}
              onChange={(value: string[]) =>
                handleChange({ string_list_value: value })
              }
              onSave={onSave}
              disabled={disabled}
            />
          ) : (
            <StringListField
              value={value}
              label={label}
              tooltipText={tooltip}
              onChange={(value: string[]) =>
                handleChange({ string_list_value: value })
              }
              onSave={onSave}
              disabled={disabled}
            />
          )}
        </div>
      )}
      {field?.definition?.data_type ===
        ParameterDefinitionSpecDataTypeEnum.integer_list && (
        <div className="mt-4">
          <IntegerListField
            value={value}
            label={label}
            tooltipText={tooltip}
            onChange={(value: number[]) =>
              handleChange({ integer_list_value: value })
            }
            disabled={disabled}
          />
        </div>
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
