import React from 'react';
import { CheckConfigurationModel } from '../../../../api';
import Checkbox from '../../../../components/Checkbox';
import DisplaySensorParameters from '../../DisplaySensorParameters';
import FieldValue from '../../FieldValue';
import Switch from '../../../../components/Switch';
import Button from '../../../../components/Button';

type TMultiChecksTableItem = {
  checkTarget: 'column' | 'table' | undefined;
  check: CheckConfigurationModel;
  checked: boolean;
  onChangeSelection: (check: CheckConfigurationModel) => void;
  goToCheckDefinition: (table: string, column?: string) => void;
};

export default function MultiChecksTableItem({
  checkTarget,
  check,
  checked,
  onChangeSelection,
  goToCheckDefinition
}: TMultiChecksTableItem) {
  return (
    <tr>
      <td className="px-4 py-2 text-left">
        <div className="flex gap-x-8">
          <Checkbox
            onChange={() => onChangeSelection(check)}
            checked={checked}
          />
          <div className="cursor-not-allowed">
            <Switch
              className="pointer-events-none cursor-not-allowed"
              checked={check.configured ?? false}
            />
          </div>
        </div>
      </td>
      <Button
        label={check.check_name}
        variant="text"
        className="px-4 py-2 text-left underline"
        onClick={() =>
          goToCheckDefinition(check.table_name ?? '', check.column_name)
        }
      ></Button>
      <td className="px-4 py-2 text-left">{check.category_name}</td>
      <td className="px-4 py-2 text-left">{check.table_name}</td>
      {checkTarget === 'column' && (
        <td className="px-4 py-2 text-left">{check.column_name}</td>
      )}
      <td className="px-4 py-2 text-left">
        <DisplaySensorParameters parameters={check.sensor_parameters || []} />
      </td>
      <td className="px-4 py-2 text-left truncate">
        {check.warning?.rule_parameters?.map((item, index) => (
          <div key={index}>
            <FieldValue field={item} />
          </div>
        ))}
      </td>
      <td className="px-4 py-2 text-left truncate">
        {check.error?.rule_parameters?.map((item, index) => (
          <div key={index}>
            <FieldValue field={item} />
          </div>
        ))}
      </td>
      <td className="px-4 py-2 text-left truncate">
        {check.fatal?.rule_parameters?.map((item, index) => (
          <div key={index}>
            <FieldValue field={item} />
          </div>
        ))}
      </td>
    </tr>
  );
}
