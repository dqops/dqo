import React, { ChangeEvent, useEffect, useState } from "react";
import Select from "../../Select";
import Input from "../../Input";
import clsx from "clsx";

interface FieldTypeInputProps {
  className?: string;
  label?: string;
  value?: string;
  name?: string;
  maskingType?: string;
  onChange?: (value: string) => void;
  disabled?: boolean
}

const FieldTypeInput = ({ className, label, value, name, maskingType, onChange, disabled}: FieldTypeInputProps) => {
  const options = [
    {
      label: 'clear text',
      value: 'text',
    },
    {
      label: '${ENV_VAR}',
      value: 'env',
    }
  ];
  const [text, setText] = useState('');
  const [type, setType] = useState('text');

  const onChangeType = (newType: string) => {
    setType(newType)

    if (!onChange) return;
    onChange(encodeValue(text, newType));
  };

  const handleChange = (e: ChangeEvent<HTMLInputElement>) => {
    if (!onChange) return;

    setText(e.target.value);
    onChange(encodeValue(e.target.value, type));
  };

  const encodeValue = (text: string, textType: string) => {
    if (textType === 'env') {
      return '${' + text + '}';
    }
    return text;
  };

  useEffect(() => {
    if (!value) return;

    if (value.startsWith('${') && value.endsWith('}')) {
      setType('env');
      setText(value.substr(2, value.length - 3));
    } else {
      setText(value);
      setType('text');
    }
  }, [value]);

  const inputType = maskingType === 'password' && type !== 'env'
  ? 'password'
  : 'text';

  return (
    <div className={clsx('', className)}>
      <div>{label}</div>
      <div className="flex items-end space-x-3">
        <div className="flex-1 flex space-x-1 items-center">
          {type === 'env' && <div>{'${'}</div>}
          <div className="flex-1">
            <Input name={name} value={text} onChange={handleChange} type={inputType} disabled={disabled}/>
          </div>
          {type === 'env' && <div>{'}'}</div>}
        </div>
        <Select options={options} value={type} onChange={onChangeType} disabled={disabled}/>
      </div>
    </div>
  );
};

export default FieldTypeInput;
