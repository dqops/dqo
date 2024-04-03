import React, { useState } from 'react';
import {
  ParameterDefinitionSpec,
  ParameterDefinitionSpecDataTypeEnum,
  ParameterDefinitionSpecDisplayHintEnum
} from '../../api';
import Input from '../Input';
import TextArea from '../TextArea';
import Select from '../Select';
import Checkbox from '../Checkbox';
import StringListField from '../StringListField';
import { IconButton } from '@material-tailwind/react';
import SvgIcon from '../SvgIcon';

type RuleFieldAddProps = {
  onAdd: (field: ParameterDefinitionSpec) => void;
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

const RuleFieldAdd = ({ onAdd }: RuleFieldAddProps) => {
  const [field, setField] = useState<ParameterDefinitionSpec>({});

  const onChange = (obj: Partial<ParameterDefinitionSpec>) => {
    setField({
      ...field,
      ...obj
    });
  };

  const handleAdd = () => {
    onAdd(field);
    setField({});
  };

  return (
    <tr>
      <td className="pr-4 py-2  align-top w-40">
        <Input
          value={field.field_name}
          onChange={(e) =>
            onChange({
              field_name: e.target.value
            })
          }
        />
      </td>
      <td className="px-4 py-2  align-top w-40">
        <Input
          value={field.display_name}
          onChange={(e) =>
            onChange({
              display_name: e.target.value
            })
          }
        />
      </td>
      <td className="px-4 py-2  align-top">
        <TextArea
          className="h-9 min-h-15 !py-1.5"
          value={field.help_text ?? ''}
          onChange={(e) =>
            onChange({
              help_text: e.target.value
            })
          }
        />
      </td>
      <td className="px-4 py-2  align-top w-40">
        <Select
          value={field.data_type}
          onChange={(data_type) => onChange({ data_type })}
          options={dataTypeOptions}
        />
      </td>
      <td className="px-4 py-2  align-top w-40">
        <Select
          value={field.display_hint}
          onChange={(display_hint) => onChange({ display_hint })}
          options={displayHintOptions}
        />
      </td>
      <td className="px-4 py-2  align-top w-20">
        <div>
          <Checkbox
            onChange={(required) => onChange({ required })}
            checked={field.required}
          />
        </div>
      </td>
      <td className="w-60 px-4 py-2  align-top">
        <StringListField
          value={field.allowed_values || []}
          onChange={(allowed_values) => onChange({ allowed_values })}
        />
      </td>
      <td className="px-4 py-2  align-top w-20">
        <IconButton
          color="teal"
          size="sm"
          onClick={handleAdd}
          className="!shadow-none"
        >
          <SvgIcon name="add" className="w-4" />
        </IconButton>
      </td>
    </tr>
  );
};

export default RuleFieldAdd;
