import React, { useState } from 'react';
import { UICheckModel, UIFieldModel } from '../../api';
import SvgIcon from '../SvgIcon';
import CheckSettings from './CheckSettings';
import SensorParameters from './SensorParameters';
import Switch from '../Switch';
import clsx from 'clsx';
import CheckRuleItem from './CheckRuleItem';

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
  const [activeTab, setActiveTab] = useState('data-streams');
  const [tabs, setTabs] = useState<ITab[]>([]);

  const openCheckSettings = () => {
    setExpanded(true);
    setActiveTab('data-streams');
    setTabs([
      {
        label: 'Data streams override',
        value: 'data-streams'
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

  const handleChange = (obj: any) => {
    onChange({
      ...check,
      ...obj
    });
  };
  return (
    <>
      <tr
        className={clsx(
          ' border-b border-gray-100',
          check?.configured ? 'text-gray-700' : 'opacity-75 line-through'
        )}
      >
        <td className="py-2 px-4 align-top pr-4">
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
            <SvgIcon
              name={check?.configured ? 'stop' : 'disable'}
              className={clsx(
                'w-5 h-5',
                check?.configured ? 'text-blue-700' : 'text-red-700'
              )}
            />
            <SvgIcon
              name="cog"
              className="w-5 h-5 text-blue-700 cursor-pointer"
              onClick={openCheckSettings}
            />
            <SvgIcon
              name="clock"
              className="w-5 h-5 text-blue-700 cursor-pointer"
            />
            <div>{check.check_name}</div>
          </div>
        </td>
        <td className="py-2 px-4 align-top">
          <div className="flex space-x-2">
            <div className="text-gray-700 text-sm w-full">
              <SensorParameters
                parameters={check.sensor_parameters || []}
                onChange={(parameters: UIFieldModel[]) =>
                  handleChange({ sensor_parameters: parameters })
                }
                disabled={!check.configured}
              />
            </div>
          </div>
        </td>
        <td className="py-2 px-4 align-bottom bg-orange-100">
          <CheckRuleItem
            disabled={!check.configured}
            parameters={check?.rule?.error}
            onChange={(error) =>
              handleChange({
                rule: {
                  ...check?.rule,
                  error
                }
              })
            }
            type="error"
          />
        </td>
        <td className="py-2 px-4 align-bottom bg-red-100">
          <CheckRuleItem
            disabled={!check.configured}
            parameters={check?.rule?.fatal}
            onChange={(fatal) =>
              handleChange({
                rule: {
                  ...check?.rule,
                  fatal
                }
              })
            }
            type="fatal"
          />
        </td>
        <td className="min-w-5 max-w-5 border-b" />
        <td className="py-2 px-4 align-bottom bg-yellow-100">
          <CheckRuleItem
            disabled={!check.configured}
            parameters={check?.rule?.warning}
            onChange={(warning) =>
              handleChange({
                rule: {
                  ...check?.rule,
                  warning
                }
              })
            }
            type="warning"
          />
        </td>
      </tr>
      {expanded && (
        <tr>
          <td colSpan={6}>
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
