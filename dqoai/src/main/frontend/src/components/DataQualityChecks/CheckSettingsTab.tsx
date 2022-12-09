import React from 'react';
import Checkbox from '../Checkbox';
import { UICheckModel } from '../../api';
import TextArea from '../TextArea';

interface ICheckSettingsTabProps {
  check?: UICheckModel;
  onChange: (item: UICheckModel) => void;
}

const CheckSettingsTab = ({ check, onChange }: ICheckSettingsTabProps) => {
  return (
    <div>
      <div className="">
        <table className="w-full">
          <tbody>
            <tr>
              <td className="px-4 py-2 w-60">Disable data quality check</td>
              <td className="px-4 py-2">
                <Checkbox
                  checked={check?.disabled}
                  onChange={(value) => onChange({ ...check, disabled: value })}
                />
              </td>
            </tr>
            <tr>
              <td className="px-4 py-2">Exclude from KPI</td>
              <td className="px-4 py-2">
                <Checkbox
                  checked={check?.exclude_from_kpi}
                  onChange={(value) =>
                    onChange({ ...check, exclude_from_kpi: value })
                  }
                />
              </td>
            </tr>
            <tr>
              <td className="px-4 py-2 align-top">SQL WHERE condition</td>
              <td className="px-4 py-2">
                <TextArea
                  className="!bg-white border !border-gray-400 !text-gray-900"
                  rows={5}
                  value={check?.filter}
                  onChange={(e) =>
                    onChange({ ...check, filter: e.target.value })
                  }
                />
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  );
};

export default CheckSettingsTab;
