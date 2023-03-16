import React, { useEffect, useState } from 'react';
import {
  CheckResultsOverviewDataModel, CheckResultsOverviewDataModelStatusesEnum,
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
import { Tooltip } from '@material-tailwind/react';
import moment from "moment";
import CheckDetails from "./CheckDetails/CheckDetails";

interface ICheckListItemProps {
  check: UICheckModel;
  onChange: (check: UICheckModel) => void;
  category?: string;
  checkResult?: CheckResultsOverviewDataModel;
  getCheckOverview: () => void;
  onUpdate: () => void;
}

export interface ITab {
  label: string;
  value: string;
  type?: string;
  field?: UIFieldModel;
}

const CheckListItem = ({ check, onChange, checkResult, getCheckOverview, onUpdate }: ICheckListItemProps) => {
  const [expanded, setExpanded] = useState(false);
  const [activeTab, setActiveTab] = useState('data-streams');
  const [tabs, setTabs] = useState<ITab[]>([]);
  const { jobs } = useSelector((state: IRootState) => state.job);
  const [showDetails, setShowDetails] = useState(false);

  const job = jobs?.jobs?.find((item) =>
    isEqual(
      item.parameters?.runChecksParameters?.checkSearchFilters,
      check.run_checks_job_template
    )
  );

  const openCheckSettings = () => {
    if (showDetails) {
      setShowDetails(false);
    }
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
              }
            }
          }
        : {})
    });
  };

  const onRunCheck = async () => {
    if (!check.configured || check?.disabled) {
      return;
    }
    await onUpdate();
    JobApiClient.runChecks({
      checkSearchFilters: check?.run_checks_job_template
    });
  };

  const isDisabled = !check?.configured || check?.disabled;

  const getColor = (status: CheckResultsOverviewDataModelStatusesEnum) => {
    switch (status) {
      case 'valid':
        return 'bg-teal-900';
      case 'warning':
        return 'bg-yellow-900';
      case 'error':
        return 'bg-orange-900';
      case 'fatal':
        return 'bg-red-900';
      case 'execution_error':
        return 'bg-black';
      default:
        return 'bg-black';
    }
  };

  useEffect(() => {
    if (job?.status === DqoJobHistoryEntryModelStatusEnum.succeeded || job?.status === DqoJobHistoryEntryModelStatusEnum.failed) {
      getCheckOverview();
    }
  }, [job?.status]);

  const getStatusLabel = (status: CheckResultsOverviewDataModelStatusesEnum) => {
    if (status === 'valid') {
      return 'Valid';
    }
    if (status === CheckResultsOverviewDataModelStatusesEnum.execution_error) {
      return 'Execution Error'
    }
    return status;
  };

  const closeCheckDetails = () => {
    setShowDetails(false);
  };

  const toggleCheckDetails = () => {
    if (expanded && !showDetails) {
      setExpanded(false);
    }
    setShowDetails(!showDetails);
  };

  return (
    <>
      <tr
        className={clsx(
          ' border-b border-gray-100',
          !isDisabled ? 'text-gray-700' : 'opacity-75',
          check?.disabled ? 'line-through' : ''
        )}
      >
        <td className="py-2 pl-4 pr-4">
          <div className="flex space-x-1 items-center min-w-60">
            {/*<div className="w-5">*/}
            {/*  <Checkbox checked={checked} onChange={setChecked} />*/}
            {/*</div>*/}
            <div>
              <Switch
                checked={!!check?.configured}
                onChange={onChangeConfigured}
              />
            </div>
            <Tooltip
              content={!check?.disabled ? 'Enabled' : 'Disabled'}
              className="max-w-80 py-4 px-4 bg-gray-800"
            >
              <div>
                <SvgIcon
                  name={!check?.disabled ? 'stop' : 'disable'}
                  className={clsx(
                    'w-5 h-5 cursor-pointer',
                    !check?.disabled ? 'text-blue-700' : 'text-red-700'
                  )}
                  onClick={() =>
                    check?.configured &&
                    handleChange({ disabled: !check?.disabled })
                  }
                />
              </div>
            </Tooltip>
            <Tooltip
              content="Settings"
              className="max-w-80 py-4 px-4 bg-gray-800"
            >
              <div>
                <SvgIcon
                  name="cog"
                  className="w-5 h-5 text-blue-700 cursor-pointer"
                  onClick={openCheckSettings}
                />
              </div>
            </Tooltip>
            <Tooltip
              content={check?.schedule_override?.disabled ? 'Schedule disabled' : 'Schedule enabled'}
              className="max-w-80 py-4 px-4 bg-gray-800"
            >
              <div>
                <SvgIcon
                  name={
                    check?.schedule_override?.disabled ? 'clock-off' : 'clock'
                  }
                  className={clsx(
                    'w-5 h-5 cursor-pointer',
                    check?.schedule_override
                      ? 'text-gray-700'
                      : 'font-bold',
                    check?.schedule_override?.disabled ? 'line-through' : ''
                  )}
                  strokeWidth={check?.schedule_override ? 4 : 2}
                />
              </div>
            </Tooltip>
            {(!job ||
              job?.status === DqoJobHistoryEntryModelStatusEnum.succeeded ||
              job?.status === DqoJobHistoryEntryModelStatusEnum.failed) && (
                <Tooltip
                  content="Run Check"
                  className="max-w-80 py-4 px-4 bg-gray-800"
                >
                  <div>
                    <SvgIcon
                      name="play"
                      className="text-primary h-5 cursor-pointer"
                      onClick={onRunCheck}
                    />
                  </div>
                </Tooltip>
            )}
            {job?.status === DqoJobHistoryEntryModelStatusEnum.waiting && (
              <Tooltip
                content="Waiting"
                className="max-w-80 py-4 px-4 bg-gray-800"
              >
                <div>
                  <SvgIcon
                    name="hourglass"
                    className="text-gray-700 h-5 cursor-pointer"
                  />
                </div>
              </Tooltip>
            )}
            {(job?.status === DqoJobHistoryEntryModelStatusEnum.running ||
              job?.status === DqoJobHistoryEntryModelStatusEnum.queued) && (
              <Tooltip
                content="Running"
                className="max-w-80 py-4 px-4 bg-gray-800"
              >
                <div>
                  <SvgIcon
                    name="hourglass"
                    className="text-gray-700 h-5 cursor-pointer"
                  />
                </div>
              </Tooltip>
            )}
            <Tooltip
              content="Check Details"
              className="max-w-80 py-4 px-4 bg-gray-800"
            >
              <div className="w-5 h-5">
                <SvgIcon
                  name="rectangle-list"
                  className="text-gray-700 h-5 cursor-pointer"
                  onClick={toggleCheckDetails}
                />
              </div>
            </Tooltip>
            <Tooltip
              content={check.help_text}
              className="max-w-80 py-4 px-4 bg-gray-800"
            >
              <div className="w-5 h-5">
                <SvgIcon
                  name="info"
                  className="w-5 h-5 text-blue-700 cursor-pointer"
                />
              </div>
            </Tooltip>
    
            {checkResult && (
              <div className="flex space-x-1">
                {checkResult?.statuses?.map((status, index) => (
                  <Tooltip
                    key={index}
                    content={
                      <div className="text-gray-900">
                        <div>Sensor value: {checkResult?.results?.[index]}</div>
                        <div>Most severe status: <span className="capitalize">{getStatusLabel(status)}</span></div>
                        <div>Executed at: {checkResult?.executedAtTimestamps ? moment(checkResult.executedAtTimestamps[index]).format('YYYY-MM-DD HH:mm:ss') + ' UTC' : ''}</div>
                        <div>Time period: {checkResult?.timePeriodDisplayTexts ? checkResult.timePeriodDisplayTexts[index] : ''}</div>                        
                        <div>Data stream: {checkResult?.dataStreams ? checkResult.dataStreams[index] : ''}</div>
                      </div>
                    }
                    className="max-w-80 py-4 px-4 bg-white shadow-2xl"
                  >
                    <div className={clsx("w-3 h-3", getColor(status))} />
                  </Tooltip>
                ))}
              </div>
            )}
            <div className="text-sm">{check.check_name}</div>
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
      {showDetails && (
        <tr>
          <td colSpan={6}>
            <CheckDetails
              check={check}
              onClose={closeCheckDetails}
            />
          </td>
        </tr>
      )}
    </>
  );
};

export default CheckListItem;
