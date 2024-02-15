import React from 'react';
import Input from '../../Input';
import Select from '../../Select';
import { TConfigurationItemRow } from './TConfigurationItemRow'

export default function ConfigurationItemRow({
  label,
  value,
  onChange,
  defaultValue, 
  isEnum,
  options
}: TConfigurationItemRow) {
  const handleChange = (e: React.ChangeEvent<HTMLInputElement> | string) => {
    if (typeof e === 'string') {
      onChange(e)
    } else {
      onChange(e.target.value);
    }
  };

  return (
    <div>
      <div className="py-1">{label}</div>
      { !isEnum &&
        <Input 
          value={value ?? defaultValue}
          onChange={handleChange} />
      }
      { isEnum && options &&
        <Select
          options={options}
          className="mb-4"
          value={value ?? defaultValue}
          onChange={handleChange}
        />
      }
    </div>
  );
}
