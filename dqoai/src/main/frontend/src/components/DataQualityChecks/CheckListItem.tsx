import React, { useState } from 'react';
import { UICheckModel, UIFieldModel, UIRuleThresholdsModel } from '../../api';
import Checkbox from '../Checkbox';
import SvgIcon from '../SvgIcon';
import CheckRulesTable from './CheckRulesTable';
import CheckSettings from './CheckSettings';
import SensorParameters from './SensorParameters';
import Switch from '../Switch';
import clsx from 'clsx';

interface ICheckListItemProps {
  check: UICheckModel;
  onChange: (check: UICheckModel) => void;
}

export interface ITab {
  label: string;
  value: string;
  type?: string;
  field?: UIFieldModel;
}

const CheckListItem = ({ check, onChange }: ICheckListItemProps) => {
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

  const openCheckSensorParameter = (field: UIFieldModel) => {
    setExpanded(true);
    setActiveTab(field.definition?.field_name || '');

    setTabs([
      {
        label: (field.definition?.field_name || '')?.split('_').join(' '),
        value: field.definition?.field_name || '',
        type: 'sensor_parameter',
        field
      }
    ]);
  };

  const handleChange = (obj: any) => {
    onChange({
      ...check,
      ...obj
    });
  };

  return (
    <>
      <tr className={clsx(check?.configured ? 'text-gray-700' : 'opacity-75')}>
        <td className="py-2 align-top pr-4">
          <div className="flex space-x-2 items-center min-w-60">
            {/*<div className="w-5">*/}
            {/*  <Checkbox checked={checked} onChange={setChecked} />*/}
            {/*</div>*/}
            <div>
              <Switch
                checked={!!check?.configured}
                onChange={(configured) => handleChange({ configured })}
              />
            </div>
            <div>{check.check_name}</div>
            <SvgIcon
              name="cog"
              className="w-4 h-4 text-blue-700 cursor-pointer"
              onClick={openCheckSettings}
            />
          </div>
        </td>
        <td className="py-2 align-top">
          <div className="flex space-x-2">
            <div className="text-gray-700 text-sm w-full">
              <SensorParameters
                parameters={check.sensor_parameters || []}
                onChange={(parameters: UIFieldModel[]) =>
                  handleChange({ sensor_parameters: parameters })
                }
                openCheckSensorParameter={openCheckSensorParameter}
                disabled={!check.configured}
              />
            </div>
          </div>
        </td>
        <td className="py-2 align-top">
          <CheckRulesTable
            rules={check?.rules?.slice(0, 1) || []}
            onChange={(rules: UIRuleThresholdsModel[]) =>
              handleChange({ rules })
            }
            disabled={!check.configured}
          />
        </td>
      </tr>
      {expanded && (
        <tr>
          <td colSpan={3}>
            <CheckSettings
              activeTab={activeTab}
              setActiveTab={setActiveTab}
              tabs={tabs}
              onClose={() => setExpanded(false)}
              onChange={onChange}
              check={check}
            />
          </td>
        </tr>
      )}
    </>
  );
};

export default CheckListItem;
