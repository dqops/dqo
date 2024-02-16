import React from 'react';
import Input from '../../../Input';
import Select from '../../../Select';
import { TConfigurationItemRow } from './TConfigurationItemRow'

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
      onChange(e)
    } else {
      onChange(e.target.value);
    }
  };

  return (
    <div>
      { !isEnum &&
        <div className={className}>
          <div>{label}</div>
          <Input 
            value={value ?? defaultValue}
            onChange={handleChange} 
          />
        </div>
      }
      { isEnum && options &&
        <div className={className}>
          <div>{label}</div>
          <Select
            options={options}
            value={value ?? defaultValue}
            onChange={handleChange}
          />
        </div>
      }
    </div>
  );
}
