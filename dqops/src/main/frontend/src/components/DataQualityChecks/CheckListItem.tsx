import { Tooltip } from '@material-tailwind/react';
import clsx from 'clsx';
import moment from 'moment';
import React, { useEffect, useState } from 'react';
import { useSelector } from 'react-redux';
import { useHistory } from 'react-router-dom';
import {
  CheckModel,
  CheckModelDefaultSeverityEnum,
  CheckResultsOverviewDataModel,
  CheckResultsOverviewDataModelStatusesEnum,
  DqoJobHistoryEntryModelStatusEnum,
  FieldModel,
  RuleParametersModel,
  RuleThresholdsModel,
  TimeWindowFilterParameters
} from '../../api';
import { useActionDispatch } from '../../hooks/useActionDispatch';
import { addFirstLevelTab } from '../../redux/actions/definition.actions';
import { setCurrentJobId } from '../../redux/actions/source.actions';
import { IRootState } from '../../redux/reducers';
import { getFirstLevelActiveTab } from '../../redux/selectors';
import { JobApiClient } from '../../services/apiClient';
import { CheckTypes, ROUTES } from '../../shared/routes';
import { getLocalDateInUserTimeZone, useDecodedParams } from '../../utils';
import Checkbox from '../Checkbox';
import SvgIcon from '../SvgIcon';
import Switch from '../Switch';
import CheckDetails from './CheckDetails/CheckDetails';
import CheckSettings from './CheckSettings';
import RuleConfiguration from './RuleConfiguration/RuleConfiguration';
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
  isAlreadyDeleted?: boolean;
  ruleParamenterConfigured: boolean;
  onChangeRuleParametersConfigured: (v: boolean) => void;
}

interface IRefetchResultsProps {
  fetchCheckResults: () => void;
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
  isAlreadyDeleted,
  ruleParamenterConfigured,
  onChangeRuleParametersConfigured
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
  const [isTooltipVisible, setIsTooltipVisible] = useState(false);

  const handleMouseEnter = () => {
    setIsTooltipVisible(true);
  };

  const handleMouseLeave = () => {
    setIsTooltipVisible(false);
  };

  const [refreshCheckObject, setRefreshCheckObject] = useState<
    IRefetchResultsProps | undefined
  >();

  const onChangeRefrshCheckObject = (obj: IRefetchResultsProps) => {
    setRefreshCheckObject(obj);
  };
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

  const openCheckSettings = (activeTab?: string) => {
    if (showDetails) {
      closeCheckDetails();
    }
    if (check?.configured) {
      toggleExpand();
      const initTabs = isDefaultEditing
        ? [
            {
              label: 'Check Settings',
              value: 'check-settings'
            },
            {
              label: 'Comments',
              value: 'comments'
            }
          ]
        : [
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
      if (typeof activeTab === 'string') {
        setActiveTab(activeTab);
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

    let newRuleConfiguration: RuleThresholdsModel | undefined = configured
      ? {
          ...check.rule
        }
      : {};

    if (
      configured &&
      !check?.rule?.warning?.configured &&
      !check?.rule?.error?.configured &&
      !check?.rule?.fatal?.configured
    ) {
      switch (check.default_severity) {
        case CheckModelDefaultSeverityEnum.warning:
          newRuleConfiguration = {
            ...newRuleConfiguration,
            warning: {
              ...check.rule?.warning,
              configured: true
            }
          };
          setEnabledType('warning');
          break;

        case CheckModelDefaultSeverityEnum.error:
          newRuleConfiguration = {
            ...newRuleConfiguration,
            error: {
              ...check.rule?.error,
              configured: true
            }
          };
          setEnabledType('error');
          break;

        case CheckModelDefaultSeverityEnum.fatal:
          newRuleConfiguration = {
            ...newRuleConfiguration,
            fatal: {
              ...check.rule?.fatal,
              configured: true
            }
          };
          setEnabledType('fatal');
          break;
      }
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
        ? {
            time_window_filter: timeWindowFilter,
            collect_error_samples: true
          }
        : { collect_error_samples: true })
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
      job?.status === DqoJobHistoryEntryModelStatusEnum.failed ||
      job?.status === DqoJobHistoryEntryModelStatusEnum.cancelled
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
    if (refreshCheckObject) {
      refreshCheckObject.fetchCheckResults();
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

  const history = useHistory();
  const openDataQaulityPolicy = () => {
    const url = ROUTES.DEFAULT_CHECKS_PATTERNS(column ? 'column' : 'table');
    dispatch(
      addFirstLevelTab({
        url,
        value: ROUTES.DEFAULT_CHECKS_PATTERNS_VALUE(),
        label: (column ? 'Column' : 'Table') + '-level quality policy',
        state: {
          type: column ? 'column' : 'table'
        }
      })
    );
    history.push(url);
  };

  return (
    <>
      <tr
        className={clsx(
          expanded || showDetails ? '' : ' border-b border-gray-100',
          !isDisabled ? 'text-gray-700' : 'opacity-75',
          check?.disabled ? 'line-through' : '',
          'h-18'
        )}
      >
        <td className="pl-4 pr-8 min-w-133 max-w-133 !h-full ">
          <div className="flex space-x-1 items-center !h-full relative">
            {isAlreadyDeleted !== true &&
              (mode ? (
                <div className="w-5 h-5 block items-center">
                  {check?.configured && (
                    <Checkbox checked={checkedCopyUI} onChange={changeCopyUI} />
                  )}
                </div>
              ) : (
                <div
                  onMouseEnter={handleMouseEnter}
                  onMouseLeave={handleMouseLeave}
                  className="relative"
                >
                  <Tooltip
                    open={isTooltipVisible} // Control the visibility with state
                    content={
                      check?.configured ? (
                        'Check activated manually or with rule miner'
                      ) : check?.default_check ? (
                        <div className="flex items-center gap-x-1">
                          Check activated with
                          <div
                            className="cursor-pointer underline"
                            onClick={openDataQaulityPolicy}
                          >
                            data quality policy
                          </div>
                        </div>
                      ) : (
                        'Deactivated check'
                      )
                    }
                    className="absolute max-w-80 py-2 px-2"
                  >
                    <div>
                      <Switch
                        checked={!!check?.configured}
                        checkedByDefault={!!check?.default_check}
                        onChange={onChangeConfigured}
                      />
                    </div>
                  </Tooltip>
                </div>
              ))}
            {isAlreadyDeleted !== true && (
              <Tooltip
                content={!check?.disabled ? 'Enabled' : 'Disabled'}
                className="max-w-80 py-2 px-2 bg-gray-800 delay-700"
              >
                <div
                  className={clsx(
                    userProfile.can_manage_data_sources === false
                      ? 'cursor-not-allowed pointer-events-none'
                      : ''
                  )}
                >
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
            )}
            {isAlreadyDeleted !== true && (
              <Tooltip
                content="Settings"
                className="max-w-80 py-2 px-2 bg-gray-800 delay-700"
              >
                <div>
                  <SvgIcon
                    name="cog"
                    className="w-5 h-5 text-gray-700 cursor-pointer"
                    onClick={openCheckSettings}
                  />
                </div>
              </Tooltip>
            )}
            {isAlreadyDeleted !== true && (
              <Tooltip
                content={
                  check?.schedule_override?.disabled
                    ? 'Schedule disabled'
                    : 'Schedule enabled'
                }
                className="max-w-80 py-2 px-2 bg-gray-800 delay-700"
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
            )}
            {(!job ||
              job?.status === DqoJobHistoryEntryModelStatusEnum.finished ||
              job?.status === DqoJobHistoryEntryModelStatusEnum.cancelled ||
              job?.status === DqoJobHistoryEntryModelStatusEnum.failed) &&
              isDefaultEditing !== true &&
              isAlreadyDeleted !== true && (
                <Tooltip
                  content="Save and run check"
                  className="max-w-80 py-2 px-2 bg-gray-800 delay-700"
                >
                  <div>
                    <SvgIcon
                      name="play"
                      className={clsx(
                        'h-[18px]',
                        canUserRunChecks === false
                          ? 'text-gray-500 cursor-not-allowed'
                          : 'text-primary cursor-pointer'
                      )}
                      onClick={
                        canUserRunChecks !== false &&
                        (check?.configured || check?.default_check)
                          ? onRunCheck
                          : undefined
                      }
                    />
                  </div>
                </Tooltip>
              )}
            {job?.status === DqoJobHistoryEntryModelStatusEnum.waiting && (
              <Tooltip
                content="Waiting"
                className="max-w-80 py-2 px-2 bg-gray-800 delay-700"
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
                className="max-w-80 py-2 px-2 bg-gray-800 delay-700"
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
                className="max-w-80 py-2 px-2 bg-gray-800 delay-700"
              >
                <div
                  className={clsx(
                    'w-5 h-5',
                    isAlreadyDeleted === true ? 'pl-[129px] pr-11' : ''
                  )}
                >
                  <SvgIcon
                    name="rectangle-list"
                    className="text-gray-700 h-5 cursor-pointer"
                    onClick={toggleCheckDetails}
                  />
                </div>
              </Tooltip>
            )}
            {isAlreadyDeleted !== true && (
              <Tooltip
                content={check.help_text}
                className="max-w-80 py-2 px-2 bg-gray-800 delay-700"
              >
                <div className="w-5 h-5">
                  <SvgIcon
                    name="info"
                    className="w-5 h-5 text-gray-700 cursor-pointer"
                  />
                </div>
              </Tooltip>
            )}
            {checkResult && (
              <div className="flex space-x-1">
                {checkResult?.statuses?.map((status, index) => (
                  <Tooltip
                    key={index}
                    content={
                      <div className="text-white">
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
                    className="max-w-80 py-2 px-2 bg-gray-800 delay-700"
                  >
                    <div className={clsx('w-3 h-3', getColor(status))} />
                  </Tooltip>
                ))}
              </div>
            )}
            <div className="text-sm !ml-2">
              <p className="text-nowrap">
                {check.display_name && check.display_name !== ''
                  ? check.display_name
                  : check.friendly_name && check.friendly_name !== ''
                  ? check.friendly_name
                  : check.check_name}
              </p>
              <p className="text-xxs mt-0.5">
                {check.friendly_name ? (
                  <>
                    {check.check_name}&nbsp;({check.quality_dimension})
                  </>
                ) : (
                  check.quality_dimension
                )}
              </p>
            </div>
          </div>
        </td>
        <div className="flex items-center gap-x-6 !w-45">
          {check.comments ? (
            <SvgIcon
              name="comment"
              className="w-4 h-4 mt-7"
              onClick={() => openCheckSettings('comments')}
            />
          ) : null}
          {check.configuration_requirements_errors &&
          check.configuration_requirements_errors?.length !== 0 ? (
            <Tooltip
              content={check.configuration_requirements_errors?.map((x) => x)}
              className="max-w-80 py-2 px-2 bg-gray-800 delay-700"
            >
              <div className="relative">
                <div className="absolute" style={{ left: '-30px' }}>
                  <SvgIcon name="warning" className="w-5 h-5 mt-7" />
                </div>
              </div>
            </Tooltip>
          ) : null}
        </div>
        <td className="h-full flex items-center justify-end">
          <div className="text-gray-700 text-sm mt-1.5">
            <SensorParameters
              parameters={check.sensor_parameters || []}
              onChange={(parameters: FieldModel[]) =>
                handleChange({ sensor_parameters: parameters })
              }
              disabled={!check?.configured || check.disabled}
              onUpdate={onUpdate}
            />
          </div>
        </td>
        <RuleConfiguration
          enabledType={enabledType}
          isDisabled={isDisabled}
          isAlreadyDeleted={isAlreadyDeleted}
          changeEnabled={changeEnabled}
          check={check}
          onUpdate={onUpdate}
          handleChange={handleChange}
          ruleParamenterConfigured={ruleParamenterConfigured}
          onChangeRuleParametersConfigured={onChangeRuleParametersConfigured}
        />
      </tr>
      {expanded && (
        <tr className={clsx(' border-b border-gray-100')}>
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
        <tr className={clsx(' border-b border-gray-100')}>
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
