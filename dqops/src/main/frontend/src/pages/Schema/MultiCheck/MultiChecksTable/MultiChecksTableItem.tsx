import React from 'react';
import { CheckConfigurationModel } from '../../../../api';
import Checkbox from '../../../../components/Checkbox';
import DisplaySensorParameters from '../../DisplaySensorParameters';
import FieldValue from '../../FieldValue';
import Switch from '../../../../components/Switch';
import Button from '../../../../components/Button';

type TCheckDefRouting =
  | {
      table: string;
      column: string;
    }
  | { table: string };

type TMultiChecksTableItem = {
  checkTarget: 'column' | 'table' | undefined;
  check: CheckConfigurationModel;
  checked: boolean;
  onChangeSelection: (check: CheckConfigurationModel) => void;
  goToCheckDefinition: (arg: TCheckDefRouting) => void;
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
          <div>
            <Switch
              className="pointer-events-none"
              checked={check.configured ?? false}
            />
          </div>
        </div>
      </td>
      <Button
        label={check.check_name}
        variant="text"
        className="px-4 py-2 text-left underline text-sm"
        onClick={() =>
          goToCheckDefinition(
            check.column_name
              ? {
                  table: check.table_name ?? '',
                  column: check.column_name ?? ''
                }
              : { table: check.table_name ?? '' }
          )
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
