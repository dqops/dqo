import React from 'react';
import {
  ParameterDefinitionSpec,
  ParameterDefinitionSpecDataTypeEnum,
  ParameterDefinitionSpecDisplayHintEnum
} from '../../api';
import Checkbox from '../Checkbox';
import StringListField from '../StringListField';
import { IconButton } from '@material-tailwind/react';
import SvgIcon from '../SvgIcon';
import { EditInput } from '../EditInput';
import EditSelect from '../EditSelect';
import TextArea from '../TextArea';

type RuleFieldRowProps = {
  field: ParameterDefinitionSpec;
  onChange: (obj: Partial<ParameterDefinitionSpec>) => void;
  onDelete: () => void;
  isReadOnly?: boolean;
};

const emptyOption = {
  label: 'None',
  value: undefined
};

const dataTypeOptions = [
  emptyOption,
  ...Object.values(ParameterDefinitionSpecDataTypeEnum).map((item) => ({
    label: item,
    value: item
  }))
];

const displayHintOptions = [
  emptyOption,
  ...Object.values(ParameterDefinitionSpecDisplayHintEnum).map((item) => ({
    label: item,
    value: item
  }))
];

const RuleFieldRow = ({
  field,
  onChange,
  onDelete,
  isReadOnly
}: RuleFieldRowProps) => {
  return (
    <tr>
      <td className="pr-4 py-2  align-top w-40">
        <EditInput
          isReadOnly={isReadOnly}
          value={field.field_name}
          onChange={(e) =>
            onChange({
              field_name: e.target.value
            })
          }
          error={!field.field_name}
        />
      </td>
      <td className="px-4 py-2  align-top w-40">
        <EditInput
          isReadOnly={isReadOnly}
          value={field.display_name}
          onChange={(e) =>
            onChange({
              display_name: e.target.value
            })
          }
          error={!field.display_name}
        />
      </td>
      <td className="px-4 py-2 align-top">
        <TextArea
          disabled={isReadOnly}
          className="min-h-15 !py-1.5"
          value={field.help_text}
          onChange={(e) =>
            onChange({
              help_text: e.target.value
            })
          }
        />
      </td>
      <td className="px-4 py-2  align-top w-40">
        <EditSelect
          isReadOnly={isReadOnly}
          value={field.data_type}
          onChange={(data_type) => onChange({ data_type })}
          options={dataTypeOptions}
          error={!field.data_type}
        />
      </td>
      <td className="px-4 py-2  align-top w-40">
        <EditSelect
          isReadOnly={isReadOnly}
          value={field.display_hint}
          onChange={(display_hint) => onChange({ display_hint })}
          options={displayHintOptions}
        />
      </td>
      <td className="px-4 py-2  align-top w-20">
        <div>
          {isReadOnly ? (
            <div>{field.required ? 'True' : 'False'}</div>
          ) : (
            <Checkbox
              onChange={(required) => onChange({ required })}
              checked={field.required}
            />
          )}
        </div>
      </td>
      <td className="w-60 px-4 py-2  align-top">
        {isReadOnly ? (
          <div>{field.allowed_values?.join(', ')}</div>
        ) : (
          <StringListField
            value={field.allowed_values || []}
            onChange={(allowed_values) => onChange({ allowed_values })}
          />
        )}
      </td>
      {!isReadOnly && (
        <td className="px-4 py-2  align-top w-20">
          <IconButton
            color="teal"
            size="sm"
            onClick={onDelete}
            className="!shadow-none"
          >
            <SvgIcon name="delete" className="w-4" />
          </IconButton>
        </td>
      )}
    </tr>
  );
};

export default RuleFieldRow;
