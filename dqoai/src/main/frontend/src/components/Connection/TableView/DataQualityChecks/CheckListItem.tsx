import React, { useState } from 'react';
import { UICheckModel, UIRuleThresholdsModel } from '../../../../api';
import Checkbox from '../../../Checkbox';
import SvgIcon from '../../../SvgIcon';
import CheckRulesTable from './CheckRulesTable';
import CheckSettings from './CheckSettings';

interface ICheckListItemProps {
  check: UICheckModel;
}

export interface ITab {
  label: string;
  value: string;
  type?: string;
}

const CheckListItem = ({ check }: ICheckListItemProps) => {
  const [checked, setChecked] = useState(false);
  const [expanded, setExpanded] = useState(false);
  const [activeTab, setActiveTab] = useState('dimension');
  const [tabs, setTabs] = useState<ITab[]>([]);

  const openCheckSettings = () => {
    setExpanded(true);
    setActiveTab('dimension');
    setTabs([
      {
        label: 'Dimension override',
        value: 'dimension'
      },
      {
        label: 'Schedule override',
        value: 'schedule'
      },
      {
        label: 'Time series override',
        value: 'time'
      },
      {
        label: 'Comments',
        value: 'comments'
      }
    ]);
  };
  const openSensorParameters = () => {
    setExpanded(true);
    setActiveTab('parameters');
    setTabs([
      {
        label: 'Sensor Parameters',
        value: 'parameters'
      }
    ]);
  };
  const openCheckRule = (rule: UIRuleThresholdsModel) => {
    setExpanded(true);
    setActiveTab(rule.field_name || '');
    setTabs([
      {
        label: (rule.field_name || '')?.split('_').join(' '),
        value: rule.field_name || '',
        type: 'rule'
      }
    ]);
  };

  return (
    <>
      <tr>
        <td className="py-2 align-top">
          <div className="flex space-x-2 items-center">
            <Checkbox
              checked={checked}
              onChange={setChecked}
              label={check.check_name}
            />
            <SvgIcon
              name="cog"
              className="w-4 h-4 text-blue-700 cursor-pointer"
              onClick={openCheckSettings}
            />
          </div>
        </td>
        <td className="py-2 align-top">
          <div className="flex space-x-2 items-center">
            <div className="text-gray-700 text-sm leading-1.5">
              {check.sensor_parameters?.length
                ? 'Sensor parameters'
                : 'No sensor parameters'}
            </div>
            <SvgIcon
              name="cog"
              className="w-4 h-4 text-blue-700 cursor-pointer"
              onClick={openSensorParameters}
            />
          </div>
        </td>
        <td className="py-2">
          <CheckRulesTable
            rules={check?.rules || []}
            openCheckRule={openCheckRule}
          />
        </td>
      </tr>
      {expanded && (
        <tr>
          <td colSpan={3}>
            <CheckSettings
              activeTab={activeTab}
              onChange={setActiveTab}
              tabs={tabs}
              onClose={() => setExpanded(false)}
            />
          </td>
        </tr>
      )}
    </>
  );
};

export default CheckListItem;
