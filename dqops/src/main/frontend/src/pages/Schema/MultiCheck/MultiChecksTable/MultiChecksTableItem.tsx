import React from 'react';
import { CheckConfigurationModel } from '../../../../api';
import Checkbox from '../../../../components/Checkbox';
import DisplaySensorParameters from '../../DisplaySensorParameters';
import FieldValue from '../../FieldValue';

type TMultiChecksTableItem = {
  checkTarget: 'column' | 'table' | undefined;
  check: CheckConfigurationModel;
  checked: boolean;
  onChangeSelection: (check: CheckConfigurationModel) => void;
};

export default function MultiChecksTableItem({
  checkTarget,
  check,
  checked,
  onChangeSelection
}: TMultiChecksTableItem) {
  return (
    <tr>
      <td className="px-4 py-2 text-left">
        <div className="flex">
          <Checkbox
            onChange={() => onChangeSelection(check)}
            checked={checked}
          />
        </div>
      </td>
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
