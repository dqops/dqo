import React from 'react';
import Input from '../../../Input';
import Select from '../../../Select';
import { TConfigurationItemRow } from './TConfigurationItemRow';
import SelectInput from '../../../SelectInput';

export default function ConfigurationItemRow({
  label,
  value,
  onChange,
  defaultValue,
  isEnum,
  options,
  className
}: TConfigurationItemRow) {
  const handleChange = (e: React.ChangeEvent<HTMLInputElement> | string) => {
    if (typeof e === 'string') {
      onChange(e);
    } else {
      onChange(e.target.value);
    }
  };

  return (
    <div>
      {!isEnum && (
        <div className={className}>
          <div className="pb-1">{label}</div>
          <Input value={value ?? defaultValue} onChange={handleChange} />
        </div>
      )}
      {isEnum && options && (
        <div className={className}>
          <div className="pb-1">{label}</div>
          <SelectInput
            options={options}
            value={String(value ?? defaultValue)}
            onChange={handleChange}
          />
        </div>
      )}
    </div>
  );
}
