import React from 'react';
import Checkbox from '../Checkbox';
import Input from '../Input';

export default function RuleMiningFilters({
  configuration
}: {
  configuration: any;
}) {
  return (
    <div>
      <div className="flex items-center">
        <Input label="Category" value={configuration} />
        <Input label="Category" value={configuration} />
        <Input label="Category" value={configuration} />
        <Input label="Category" value={configuration} />
      </div>
      <div className="flex items-center">
        <Checkbox
          label="Category"
          checked={configuration}
          onChange={() => undefined}
        />
        <Checkbox
          label="Category"
          checked={configuration}
          onChange={() => undefined}
        />
      </div>
    </div>
  );
}
