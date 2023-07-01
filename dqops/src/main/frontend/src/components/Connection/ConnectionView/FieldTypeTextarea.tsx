import React, { ChangeEvent, useEffect, useState } from "react";
import Select from "../../Select";
import clsx from "clsx";
import TextArea from "../../TextArea";

interface FieldTypeTextareaProps {
  className?: string;
  label?: string;
  value?: string;
  name?: string;
  onChange?: (value: string) => void;
}

const FieldTypeTextarea = ({ className, label, value, name, onChange }: FieldTypeTextareaProps) => {
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

  const handleChange = (e: ChangeEvent<HTMLTextAreaElement>) => {
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

  return (
    <div className={clsx('', className)}>
      <div>{label}</div>
      <div className="flex items-start space-x-3">
        <div className="flex-1 flex space-x-1 items-start">
          {type === 'env' && <div className="mt-2">{'${'}</div>}
          <div className="flex-1">
            <TextArea name={name} value={text} onChange={handleChange} rows={10} />
          </div>
          {type === 'env' && <div className="mt-2">{'}'}</div>}
        </div>
        <Select options={options} value={type} onChange={onChangeType} />
      </div>
    </div>
  );
};

export default FieldTypeTextarea;
