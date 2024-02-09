import React from 'react';
import Input from '../Input';

type TConfigurationItemRowProps = {
  label: string;
  value?: string | number;
  onChange: (str: string) => void;
};

export default function ConfigurationItemRow({
  label,
  value,
  onChange
}: TConfigurationItemRowProps) {
  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    onChange(e.target.value);
  };

  return (
    <div>
      <div>{label}</div>
      <Input value={value} onChange={handleChange} />
    </div>
  );
}
