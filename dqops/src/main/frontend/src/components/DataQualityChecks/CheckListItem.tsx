import React, { useEffect, useState } from 'react';
import {
  CheckResultsOverviewDataModel,
  CheckResultsOverviewDataModelStatusesEnum,
  DqoJobHistoryEntryModelStatusEnum,
  TimeWindowFilterParameters,
  CheckModel,
  FieldModel,
  RuleParametersModel
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
import { Tooltip } from '@material-tailwind/react';
import moment from 'moment';
import CheckDetails from './CheckDetails/CheckDetails';
import { CheckTypes } from '../../shared/routes';
import { useParams } from 'react-router-dom';
import Checkbox from '../Checkbox';
import { setCurrentJobId } from '../../redux/actions/source.actions';
import { useActionDispatch } from '../../hooks/useActionDispatch';
import { getFirstLevelActiveTab } from '../../redux/selectors';

export interface ITab {
  label: string;
  value: string;
  type?: string;
  field?: FieldModel;
}

interface ICheckListItemProps {
  check: CheckModel;
  onChange: (check: CheckModel) => void;
  category?: string;
  checkResult?: CheckResultsOverviewDataModel;
  getCheckOverview: () => void;
  onUpdate: () => void;
  timeWindowFilter?: TimeWindowFilterParameters | null;
  mode?: string;
  changeCopyUI: (checked: boolean) => void;
  checkedCopyUI?: boolean;
  comparisonName?: string;
  isDefaultEditing?: boolean;
}

const CheckListItem = ({
  mode,
  check,
  onChange,
  checkResult,
  getCheckOverview,
  onUpdate,
  timeWindowFilter,
  changeCopyUI,
  checkedCopyUI,
  category,
  comparisonName,
  isDefaultEditing
}: ICheckListItemProps) => {
  const [expanded, setExpanded] = useState(false);
  const [activeTab, setActiveTab] = useState('data-streams');
  const [tabs, setTabs] = useState<ITab[]>([]);
  const { job_dictionary_state } = useSelector(
    (state: IRootState) => state.job || {}
  );
  const [showDetails, setShowDetails] = useState(false);
  const {
    checkTypes,
    connection,
    schema,
    table,
    column
  }: {
    checkTypes: CheckTypes;
    connection: string;
    schema: string;
    table: string;
    column: string;
  } = useParams();
  const [jobId, setJobId] = useState<number>();
  const job = jobId ? job_dictionary_state[jobId] : undefined;
  const dispatch = useActionDispatch();
  const firstLevelActiveTab = useSelector(getFirstLevelActiveTab(checkTypes));
  const [enabledType, setEnabledType] = useState<
    'error' | 'warning' | 'fatal' | ''
  >('');

  useEffect(() => {
    const localState = localStorage.getItem(
      `${checkTypes}_${check.check_name}`
    );

    if (localState === 'true') {
      openCheckSettings();
    }

    const detailsLocalState = localStorage.getItem(
      `${checkTypes}_${check.check_name}_details`
    );

    if (detailsLocalState === 'true') {
      toggleCheckDetails();
    }
  }, [checkTypes, check.check_name]);

  const toggleExpand = () => {
    const newValue = !expanded;
    setExpanded(newValue);
    localStorage.setItem(
      `${checkTypes}_${check.check_name}`,
      newValue.toString()
    );
  };

  const changeEnabled = (
    variable: 'error' | 'warning' | 'fatal' | ''
  ): void => {
    setEnabledType(variable);
  };

  const closeExpand = () => {
    setExpanded(false);
    localStorage.setItem(`${checkTypes}_${check.check_name}`, 'false');
  };

  const openCheckSettings = () => {
    if (showDetails) {
      closeCheckDetails();
    }
    if (check?.configured) {
      toggleExpand();
      const initTabs = [
        {
          label: 'Check Settings',
          value: 'check-settings'
        },
        ...(check?.supports_grouping
          ? [
              {
                label: 'Grouping configuration override',
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
      closeExpand();
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
    const res = await JobApiClient.runChecks(false, undefined, {
      checkSearchFilters: check?.run_checks_job_template,
      ...(checkTypes === CheckTypes.PARTITIONED && timeWindowFilter !== null
        ? { timeWindowFilter }
        : {})
    });
    dispatch(
      setCurrentJobId(
        checkTypes,
        firstLevelActiveTab,
        (res.data as any)?.jobId?.jobId
      )
    );
    setJobId((res.data as any)?.jobId?.jobId);
  };

  const isDisabled = !check?.configured || check?.disabled;

  const getColor = (status: CheckResultsOverviewDataModelStatusesEnum) => {
    switch (status) {
      case 'valid':
        return 'bg-teal-500';
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
    if (
      job?.status === DqoJobHistoryEntryModelStatusEnum.succeeded ||
      job?.status === DqoJobHistoryEntryModelStatusEnum.failed
    ) {
      getCheckOverview();
    }
  }, [job?.status]);

  const getStatusLabel = (
    status: CheckResultsOverviewDataModelStatusesEnum
  ) => {
    if (status === 'valid') {
      return 'Valid';
    }
    if (status === CheckResultsOverviewDataModelStatusesEnum.execution_error) {
      return 'Execution Error';
    }
    return status;
  };

  const closeCheckDetails = () => {
    setShowDetails(false);
    localStorage.setItem(`${checkTypes}_${check.check_name}_details`, 'false');
  };

  const toggleCheckDetails = () => {
    if (expanded && !showDetails) {
      closeExpand();
    }
    const newValue = !showDetails;

    localStorage.setItem(
      `${checkTypes}_${check.check_name}_details`,
      newValue.toString()
    );
    setShowDetails(newValue);
  };
  const getLocalDateInUserTimeZone = (date: Date): string => {
    const userTimeZone = Intl.DateTimeFormat().resolvedOptions().timeZone;
    const options: Intl.DateTimeFormatOptions = {
      year: 'numeric',
      month: '2-digit',
      day: '2-digit',
      hour: '2-digit',
      minute: '2-digit',
      second: '2-digit',
      hour12: false,
      timeZone: userTimeZone
    };

    return date.toLocaleString('en-US', options);
  };

  useEffect(() => {
    if (check.rule?.error?.rule_parameters?.length === 0) {
      if (
        check.rule?.warning?.configured === true &&
        enabledType !== 'warning' &&
        enabledType !== ''
      ) {
        const obj: RuleParametersModel = {
          rule_name: check.rule?.warning?.rule_name,
          rule_parameters: check.rule?.warning?.rule_parameters,
          disabled: check.rule?.warning?.disabled,
          configured: false
        };
        const updatedRule = {
          ...check.rule,
          warning: obj
        };
        handleChange({
          rule: updatedRule
        });
      } else if (enabledType === 'warning') {
        const objFatal: RuleParametersModel = {
          rule_name: check.rule?.fatal?.rule_name,
          rule_parameters: check.rule?.fatal?.rule_parameters,
          disabled: check.rule?.fatal?.disabled,
          configured: false
        };
        const objError: RuleParametersModel = {
          rule_name: check.rule?.error?.rule_name,
          rule_parameters: check.rule?.error?.rule_parameters,
          disabled: check.rule?.error?.disabled,
          configured: false
        };
        const updatedRule = {
          ...check.rule,
          error: objError,
          fatal: objFatal
        };
        handleChange({
          rule: updatedRule
        });
      }
    }
  }, [enabledType]);

  return (
    <>
      <tr
        className={clsx(
          ' border-b border-gray-100',
          !isDisabled ? 'text-gray-700' : 'opacity-75',
          check?.disabled ? 'line-through' : ''
        )}
      >
        <td className="py-2 pl-4 pr-4 min-w-120 max-w-120">
          <div className="flex space-x-1 items-center">
            {mode ? (
              <div className="w-5 h-5 block items-center">
                {check?.configured && (
                  <Checkbox checked={checkedCopyUI} onChange={changeCopyUI} />
                )}
              </div>
            ) : (
              <div>
                <Switch
                  checked={!!check?.configured}
                  onChange={onChangeConfigured}
                />
              </div>
            )}
            <Tooltip
              content={!check?.disabled ? 'Enabled' : 'Disabled'}
              className="max-w-80 py-4 px-4 bg-gray-800"
            >
              <div>
                <SvgIcon
                  name={!check?.disabled ? 'stop' : 'disable'}
                  className={clsx(
                    'w-5 h-5 cursor-pointer',
                    !check?.disabled ? 'text-gray-700' : 'text-red-700'
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
                  className="w-5 h-5 text-gray-700 cursor-pointer"
                  onClick={openCheckSettings}
                />
              </div>
            </Tooltip>
            <Tooltip
              content={
                check?.schedule_override?.disabled
                  ? 'Schedule disabled'
                  : 'Schedule enabled'
              }
              className="max-w-80 py-4 px-4 bg-gray-800"
            >
              <div>
                <SvgIcon
                  name={
                    check?.schedule_override?.disabled ? 'clock-off' : 'clock'
                  }
                  className={clsx(
                    'w-5 h-5 cursor-pointer',
                    check?.schedule_override ? 'text-gray-700' : 'font-bold',
                    check?.schedule_override?.disabled ? 'line-through' : ''
                  )}
                  strokeWidth={check?.schedule_override ? 4 : 2}
                />
              </div>
            </Tooltip>
            {(!job ||
              job?.status === DqoJobHistoryEntryModelStatusEnum.succeeded ||
              job?.status === DqoJobHistoryEntryModelStatusEnum.failed) &&
              isDefaultEditing !== true && (
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
            {isDefaultEditing !== true && (
              <Tooltip
                content="Results"
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
            )}
            <Tooltip
              content={check.help_text}
              className="max-w-80 py-4 px-4 bg-gray-800"
            >
              <div className="w-5 h-5">
                <SvgIcon
                  name="info"
                  className="w-5 h-5 text-gray-700 cursor-pointer"
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
                        <div>
                          Most severe status:{' '}
                          <span className="capitalize">
                            {getStatusLabel(status)}
                          </span>
                        </div>
                        <div>
                          Executed at:{' '}
                          {checkResult?.executedAtTimestamps
                            ? moment(
                                getLocalDateInUserTimeZone(
                                  new Date(
                                    checkResult.executedAtTimestamps[index]
                                  )
                                )
                              ).format('YYYY-MM-DD HH:mm:ss')
                            : ''}
                        </div>
                        <div>
                          Time period:{' '}
                          {checkResult?.timePeriodDisplayTexts
                            ? checkResult.timePeriodDisplayTexts[index]
                            : ''}
                        </div>
                        <div>
                          Data group:{' '}
                          {checkResult?.dataGroups
                            ? checkResult.dataGroups[index]
                            : ''}
                        </div>
                      </div>
                    }
                    className="max-w-80 py-4 px-4 bg-white shadow-2xl"
                  >
                    <div className={clsx('w-3 h-3', getColor(status))} />
                  </Tooltip>
                ))}
              </div>
            )}
            <div className="text-sm relative">
              <p>{check.check_name}</p>
              <p className="absolute left-0 top-full text-xxs">
                {check.quality_dimension}
              </p>
            </div>
          </div>
        </td>
        <td className="py-2 px-4 flex items-end justify-end">
          <div className=" space-x-2">
            <div className="text-gray-700 text-sm w-full ">
              <SensorParameters
                parameters={check.sensor_parameters || []}
                onChange={(parameters: FieldModel[]) =>
                  handleChange({ sensor_parameters: parameters })
                }
                disabled={!check?.configured || check.disabled}
                onUpdate={onUpdate}
              />
            </div>
          </div>
        </td>
        <td className="py-2 px-4 bg-yellow-100 relative">
          <CheckRuleItem
            disabled={isDisabled}
            parameters={check?.rule?.warning}
            onChange={(warning) =>
              handleChange({
                rule: {
                  ...check.rule,
                  warning
                }
              })
            }
            type="warning"
            onUpdate={onUpdate}
            changeEnabled={changeEnabled}
            configuredType={enabledType}
          />
          <div className="w-5 bg-white absolute h-full right-0 top-0"></div>
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
            onUpdate={onUpdate}
            changeEnabled={changeEnabled}
            configuredType={enabledType}
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
            onUpdate={onUpdate}
            changeEnabled={changeEnabled}
            configuredType={enabledType}
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
              onClose={closeExpand}
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
              checkTypes={checkTypes}
              connection={connection}
              schema={schema}
              table={table}
              column={column}
              runCheckType={check.run_checks_job_template?.checkType}
              checkName={check.check_name}
              timeScale={check.run_checks_job_template?.timeScale}
              check={check}
              onClose={closeCheckDetails}
              category={category}
              comparisonName={comparisonName}
              data_clean_job_template={check.data_clean_job_template}
            />
          </td>
        </tr>
      )}
    </>
  );
};

export default CheckListItem;
