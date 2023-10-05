import React, { ChangeEvent, useEffect, useState } from "react";
import Select from "../../Select";
import Input from "../../Input";
import clsx from "clsx";
import SelectInput from "../../SelectInput";

interface FieldTypeInputProps {
  className?: string;
  label?: string;
  value?: string;
  name?: string;
  maskingType?: string;
  onChange?: (value: string) => void;
  disabled?: boolean;
  data?: any
  credential?: boolean
}

const FieldTypeInput = ({ className, label, value, name, maskingType, onChange, disabled, data, credential}: FieldTypeInputProps) => {
  const options = [
    {
      label: 'clear text',
      value: 'text',
    },
    {
      label: '${ENV_VAR}',
      value: 'env',
    },
    // {
    //   label: '${credential: CREDENTIAL}',
    //   value: 'credential'
    // }
  ];
  const [text, setText] = useState('');
  const [type, setType] = useState('text');

  const onChangeType = (newType: string) => {
    setType(newType)

    if (!onChange) return;
    onChange(encodeValue(text, newType));
  };

  const handleChange = (e: ChangeEvent<HTMLInputElement>) => {
    console.log(e)
    if (!onChange) return;

    setText(e.target.value);
    onChange(encodeValue(e.target.value, type));
  };
  
  const handleSelectChange = (input: string) => {
    if (!onChange) return;

    setText(input);
    onChange(encodeValue(input, type));
  };

  const encodeValue = (text: string, textType: string) => {
    if (textType === 'env') {
      return '${' + text + '}';
    }else if (textType === 'credential') {
      return '${credential' + text + '}';
    }
    return text;
  };

  // useEffect(() => {
  //   if (!value) return;

  //   if (value.startsWith('${') && value.endsWith('}')) {
  //     setText(value.substr(2, value.length - 3));
  //   } else {
  //     setText(value);
  //     setType('text');
  //   }
  // }, [value]);

  const inputType = maskingType === 'password' && type !== 'env'
  ? 'password'
  : 'text';
console.log(data)
  return (
    <div className={clsx('', className)}>
      <div>{label}</div>
      <div className="flex items-end space-x-3">
        <div className="flex-1 flex space-x-1 items-center">
          {(type === 'env' || type === 'credential') && <div>{'${'} {type === 'credential' ? 'credential: ' : ''}</div>}
          <div className="flex-1">
           {type === 'credential' ? 
           <SelectInput 
           options={Object.values(data).map((x : any) =>({
            label: x.credential_name,
            value: x.credential_name
           }))}
           value={text} onChange={handleSelectChange} disabled={disabled}/> 
            :  
           <Input name={name} value={text} onChange={handleChange} type={inputType} disabled={disabled}/> }
          </div>
          {(type === 'env' || type === 'credential') && <div>{'}'}</div>}
        </div>
        <Select options={credential ? [...options, { 
        label: '${credential: CREDENTIAL}',
        value: 'credential'
        }] : options}
      value={type} onChange={onChangeType} disabled={disabled} placeholder=""/>
      </div>
    </div>
  );
};

export default FieldTypeInput;
