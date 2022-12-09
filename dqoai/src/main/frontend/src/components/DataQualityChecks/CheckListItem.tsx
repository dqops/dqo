import React, { useState } from 'react';
import {
  DqoJobHistoryEntryModelStatusEnum,
  UICheckModel,
  UIFieldModel
} from '../../api';
import SvgIcon from '../SvgIcon';
import CheckSettings from './CheckSettings';
import SensorParameters from './SensorParameters';
import Switch from '../Switch';
import clsx from 'clsx';
import CheckRuleItem from './CheckRuleItem';
import { JobApiClient } from '../../services/apiClient';
import { useSelector } from 'react-redux';
import { IRootState } from '../../redux/reducers';
import { isEqual } from 'lodash';

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
  const { jobs } = useSelector((state: IRootState) => state.job);

  const job = jobs?.jobs?.find((item) =>
    isEqual(
      item.parameters?.runChecksParameters?.checkSearchFilters,
      check.run_checks_job_template
    )
  );

  const openCheckSettings = () => {
    if (check?.configured) {
      setExpanded(!expanded);
      const initTabs = [
        {
          label: 'Check Settings',
          value: 'check-settings'
        },
        ...(check?.supports_data_streams
          ? [
              {
                label: 'Data streams override',
                value: 'data-streams'
              }
            ]
          : []),
        {
          label: 'Schedule override',
          value: 'schedule'
        },
        ...(check?.supports_time_series
          ? [
              {
                label: 'Time series override',
                value: 'time'
              }
            ]
          : []),
        {
          label: 'Comments',
          value: 'comments'
        }
      ];
      setTabs(initTabs);
      setActiveTab(initTabs[0].value);
    }
  };

  const handleChange = (obj: any) => {
    onChange({
      ...check,
      ...obj
    });
  };

  const onChangeConfigured = (configured: boolean) => {
    if (!configured) {
      setExpanded(false);
    }
    handleChange({
      configured,
      disabled: configured ? check?.disabled : null,
      ...(configured
        ? {
            rule: {
              ...check.rule,
              error: {
                ...check.rule?.error,
                configured: true
              },
              fatal: {
                ...check.rule?.fatal,
                configured: true
              },
              warning: {
                ...check.rule?.warning,
                configured: true
              }
            }
          }
        : {})
    });
  };

  const onRunCheck = () => {
    JobApiClient.runChecks(check?.run_checks_job_template);
  };

  const isDisabled = !check?.configured || check?.disabled;

  return (
    <>
      <tr
        className={clsx(
          ' border-b border-gray-100',
          !isDisabled ? 'text-gray-700' : 'opacity-75',
          check?.disabled ? 'line-through' : ''
        )}
      >
        <td className="py-2 px-4 pr-4">
          <div className="flex space-x-2 items-center min-w-60">
            {/*<div className="w-5">*/}
            {/*  <Checkbox checked={checked} onChange={setChecked} />*/}
            {/*</div>*/}
            <div>
              <Switch
                checked={!!check?.configured}
                onChange={onChangeConfigured}
              />
            </div>
            <SvgIcon
              name={!check?.disabled ? 'stop' : 'disable'}
              className={clsx(
                'w-5 h-5',
                !check?.disabled ? 'text-blue-700' : 'text-red-700'
              )}
              onClick={() =>
                check?.configured &&
                handleChange({ disabled: !check?.disabled })
              }
            />
            <SvgIcon
              name="cog"
              className="w-5 h-5 text-blue-700 cursor-pointer"
              onClick={openCheckSettings}
            />
            <SvgIcon
              name={check?.schedule_override?.disabled ? 'clock-off' : 'clock'}
              className={clsx(
                'w-5 h-5 cursor-pointer',
                check?.schedule_override
                  ? 'text-gray-700'
                  : 'text-black font-bold',
                check?.schedule_override?.disabled ? 'line-through' : ''
              )}
              strokeWidth={check?.schedule_override ? 4 : 2}
            />
            {(!job ||
              job?.status === DqoJobHistoryEntryModelStatusEnum.succeeded ||
              job?.status === DqoJobHistoryEntryModelStatusEnum.failed) && (
              <SvgIcon
                name="play"
                className="text-green-700 h-5 cursor-pointer"
                onClick={onRunCheck}
              />
            )}
            {job?.status === DqoJobHistoryEntryModelStatusEnum.waiting && (
              <SvgIcon
                name="hourglass"
                className="text-gray-700 h-5 cursor-pointer"
              />
            )}
            {(job?.status === DqoJobHistoryEntryModelStatusEnum.running ||
              job?.status === DqoJobHistoryEntryModelStatusEnum.queued) && (
              <SvgIcon
                name="hourglass"
                className="text-gray-700 h-5 cursor-pointer"
              />
            )}
            <div>{check.check_name}</div>
          </div>
        </td>
        <td className="py-2 px-4">
          <div className="flex space-x-2">
            <div className="text-gray-700 text-sm w-full">
              <SensorParameters
                parameters={check.sensor_parameters || []}
                onChange={(parameters: UIFieldModel[]) =>
                  handleChange({ sensor_parameters: parameters })
                }
                disabled={!check?.configured || check.disabled}
              />
            </div>
          </div>
        </td>
        <td className="py-2 px-4 bg-orange-100">
          <CheckRuleItem
            disabled={isDisabled}
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
        <td className="py-2 px-4 bg-red-100">
          <CheckRuleItem
            disabled={isDisabled}
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
        <td className="py-2 px-4 bg-yellow-100">
          <CheckRuleItem
            disabled={isDisabled}
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
