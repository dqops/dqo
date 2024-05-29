import React from 'react';
import Select from '../../../Select';
import { TConfigurationItemRowCompression } from './TConfigurationItemRowCompression';

export default function ConfigurationItemRowCompression({
  label,
  value,
  onChange,
  defaultValue,
  options,
  className
}: TConfigurationItemRowCompression) {
  const handleChange = (e: string) => {
    if(e.endsWith("_no_ext")){
      const value = e.substring(0, e.length - "_no_ext".length)
      onChange(value, true);
    } else {
      onChange(e, false);
    }
  };

  return (
    <div>
      {options && (
        <div className={className}>
          <div className="pb-1">{label}</div>
          <Select
            options={options}
            value={String(value ?? defaultValue)}
            onChange={handleChange}
          />
        </div>
      )}
    </div>
  );
}
