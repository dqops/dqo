import { Tooltip } from '@material-tailwind/react';
import clsx from 'clsx';
import moment from 'moment';
import React, { useEffect, useState } from 'react';
import { useSelector } from 'react-redux';
import {
  CheckModel,
  CheckResultsOverviewDataModel,
  CheckResultsOverviewDataModelStatusesEnum,
  DqoJobHistoryEntryModelStatusEnum,
  FieldModel,
  RuleParametersModel,
  RuleThresholdsModel,
  TimeWindowFilterParameters
} from '../../api';
import { useActionDispatch } from '../../hooks/useActionDispatch';
import { setCurrentJobId } from '../../redux/actions/source.actions';
import { IRootState } from '../../redux/reducers';
import { getFirstLevelActiveTab } from '../../redux/selectors';
import { JobApiClient } from '../../services/apiClient';
import { CheckTypes } from '../../shared/routes';
import { getLocalDateInUserTimeZone, useDecodedParams } from '../../utils';
import Checkbox from '../Checkbox';
import SvgIcon from '../SvgIcon';
import Switch from '../Switch';
import CheckDetails from './CheckDetails/CheckDetails';
import CheckRuleItem from './CheckRuleItem';
import CheckSettings from './CheckSettings';
import SensorParameters from './SensorParameters';

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
  canUserRunChecks?: boolean;
  isAlreadyDeleted ?: boolean
}

interface IRefetchResultsProps {
  fetchCheckResults : () => void
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
  isDefaultEditing,
  canUserRunChecks,
  isAlreadyDeleted
}: ICheckListItemProps) => {
  const [expanded, setExpanded] = useState(false);
  const [activeTab, setActiveTab] = useState('check-settings');
  const [tabs, setTabs] = useState<ITab[]>([]);
  const { job_dictionary_state, userProfile } = useSelector(
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
  } = useDecodedParams();
  const [jobId, setJobId] = useState<number>();
  const job = jobId ? job_dictionary_state[jobId] : undefined;
  const dispatch = useActionDispatch();
  const firstLevelActiveTab = useSelector(getFirstLevelActiveTab(checkTypes));
  const [enabledType, setEnabledType] = useState<
    'error' | 'warning' | 'fatal' | ''
  >('');

  const [refreshCheckObject, setRefreshCheckObject] = useState<IRefetchResultsProps | undefined>()

  const onChangeRefrshCheckObject = (obj: IRefetchResultsProps) => {
    setRefreshCheckObject(obj)
  }
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

  const openCheckSettings = (tab?: string) => {
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
      if (tab) {
        setActiveTab(tab)
      } else {
        setActiveTab(initTabs[0].value);
      }
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

    let newRuleConfiguration : RuleThresholdsModel | undefined = configured ? {
        ...check.rule
    } : {};
    
    if (!check?.rule?.warning?.configured &&
        !check?.rule?.error?.configured && 
        !check?.rule?.fatal?.configured) {
      newRuleConfiguration = {
        ...newRuleConfiguration,
        error: {
          ...check.rule?.error,
          configured: true
        }
      };
    }

    handleChange({
      configured,
      disabled: configured ? check?.disabled : null,
      ...(configured
        ? {
            rule: newRuleConfiguration
          }
        : {})
    });
  };

  const onRunCheck = async () => {
    if (!(check?.default_check || check.configured) || check?.disabled) {
      return;
    }
    await onUpdate();
    const res = await JobApiClient.runChecks(undefined, false, undefined, {
      check_search_filters: check?.run_checks_job_template,
      ...(checkTypes === CheckTypes.PARTITIONED && timeWindowFilter !== null
        ? { time_window_filter: timeWindowFilter }
        : {})
    });
    dispatch(
      setCurrentJobId(
        checkTypes,
        firstLevelActiveTab,
        res.data?.jobId?.jobId ?? 0
      )
    );
    setJobId(res.data?.jobId?.jobId);
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
      job?.status === DqoJobHistoryEntryModelStatusEnum.finished ||
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
    if(refreshCheckObject) {
      refreshCheckObject.fetchCheckResults()
    }
    setShowDetails(newValue);
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
        className={clsx(expanded || showDetails ? '' :
          ' border-b border-gray-100',
          !isDisabled ? 'text-gray-700' : 'opacity-75',
          check?.disabled ? 'line-through' : ''
        )}
      >
        <td className="py-2 pl-4 pr-4 min-w-120 max-w-120">
          <div className="flex space-x-1 items-center">

            {isAlreadyDeleted !== true && (mode ? (
              <div className="w-5 h-5 block items-center">
                {check?.configured && (
                  <Checkbox checked={checkedCopyUI} onChange={changeCopyUI} />
                )}
              </div>
            ) : (
              <div>
                <Switch
                  checked={!!check?.configured}
                  checkedByDefault={!!check?.default_check}
                  onChange={onChangeConfigured}
                />
              </div>
            ))}
            {isAlreadyDeleted !== true &&
            <Tooltip
              content={!check?.disabled ? 'Enabled' : 'Disabled'}
              className="max-w-80 py-4 px-4 bg-gray-800"
            >
              <div className={clsx(userProfile.can_manage_data_sources===false ? "cursor-not-allowed pointer-events-none" : ""  )}>
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
            }
            {isAlreadyDeleted !== true &&
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
            }
            {isAlreadyDeleted !== true &&
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
                    'w-[18px] h-[18px] cursor-pointer',
                    check?.schedule_override ? 'text-gray-700' : 'font-bold',
                    check?.schedule_override?.disabled ? 'line-through' : ''
                  )}
                  strokeWidth={check?.schedule_override ? 4 : 2}
                />
              </div>
            </Tooltip>
            }
            {(!job ||
              job?.status === DqoJobHistoryEntryModelStatusEnum.finished ||
              job?.status === DqoJobHistoryEntryModelStatusEnum.failed) &&
              isDefaultEditing !== true && isAlreadyDeleted !== true && (
                <Tooltip
                  content="Run check"
                  className="max-w-80 py-4 px-4 bg-gray-800"
                >
                  <div>
                    <SvgIcon
                      name="play"
                      className={clsx("h-[18px]", canUserRunChecks === false ? "text-gray-500 cursor-not-allowed" :  "text-primary cursor-pointer")}
                      onClick={canUserRunChecks!==false && (check?.configured || check?.default_check) ? onRunCheck : undefined}
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
                    className="text-gray-700 h-4 w-[18px] cursor-pointer"
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
                    className="text-gray-700 h-4 w-[18px] cursor-pointer"
                  />
                </div>
              </Tooltip>
            )}
            {isDefaultEditing !== true && (
              <Tooltip
                content="Results"
                className="max-w-80 py-4 px-4 bg-gray-800"
              > 
                <div className={clsx("w-5 h-5", isAlreadyDeleted === true ? "pl-[129px] pr-11" : "")}>
                  <SvgIcon
                    name="rectangle-list"
                    className="text-gray-700 h-5 cursor-pointer"
                    onClick={toggleCheckDetails}
                  />
                </div>
              </Tooltip>
            )}
            {isAlreadyDeleted !== true &&
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
            }
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
              <p>{check.display_name !== '' ? (check.display_name ?? check.check_name) : check.check_name} {
                check.friendly_name &&
                <span className="text-xxs">
                  ({check.friendly_name })
                </span>
              }</p>
              <p className="absolute left-0 top-full text-xxs">
                {check.quality_dimension}
              </p>
            </div>
          </div>
        </td>
        <div className='flex justify-center items-center mt-6 gap-x-6'>
        <SvgIcon name='comment' className='w-4 h-4 ' onClick={() => openCheckSettings('comments')}/>
        {check.configuration_requirements_errors && check.configuration_requirements_errors?.length !== 0 ? 
          <Tooltip
                content={check.configuration_requirements_errors?.map((x) => x)}
                className='pr-6 max-w-80 py-4 px-4 bg-gray-800'>
                <div>
                  <SvgIcon name='warning' className='w-5 h-5'/>
                </div>
          </Tooltip>
        : null }
          
        </div>
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
        {isAlreadyDeleted !== true &&
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
        }
          <div className="w-5 bg-white absolute h-full right-0 top-0"></div>
        </td>
        <td className="py-2 px-4 bg-orange-100">
          {isAlreadyDeleted !== true &&
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
        }
        </td>
        <td className="py-2 px-4 bg-red-100 h-18">
        {isAlreadyDeleted !== true &&
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
        }
        </td>
      </tr>
      {expanded && (
        <tr className={clsx(
        ' border-b border-gray-100'
        )}>
          <td colSpan={6}>
            <CheckSettings
              activeTab={activeTab}
              setActiveTab={setActiveTab}
              tabs={tabs}
              onClose={closeExpand}
              onChange={onChange}
              check={check}
              isDefaultEditing={isDefaultEditing}
            />
          </td>
        </tr>
      )}
      {showDetails && (
        <tr className={clsx(
          ' border-b border-gray-100'
          )}>
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
              onChangeRefreshCheckObject={onChangeRefrshCheckObject}
            />
          </td>
        </tr>
      )}
    </>
  );
};

export default CheckListItem;
