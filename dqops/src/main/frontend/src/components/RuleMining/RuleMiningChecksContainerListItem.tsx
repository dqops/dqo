import { Tooltip } from '@material-tailwind/react';
import clsx from 'clsx';
import React, { useEffect, useState } from 'react';
import {
  CheckModel,
  CheckModelDefaultSeverityEnum,
  CheckResultsOverviewDataModel,
  FieldModel,
  RuleParametersModel,
  RuleThresholdsModel,
  TimeWindowFilterParameters
} from '../../api';
import { CheckTypes } from '../../shared/routes';
import { useDecodedParams } from '../../utils';
import Checkbox from '../Checkbox';
import CheckSettings from '../DataQualityChecks/CheckSettings';
import RuleConfiguration from '../DataQualityChecks/RuleConfiguration/RuleConfiguration';
import SensorParameters from '../DataQualityChecks/SensorParameters';
import SvgIcon from '../SvgIcon';
import Switch from '../Switch';

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

const RuleMiningChecksContainerListItem = ({
  mode,
  check,
  onChange,
  onUpdate,
  changeCopyUI,
  checkedCopyUI,
  isDefaultEditing,
  isAlreadyDeleted,
  ruleParamenterConfigured,
  onChangeRuleParametersConfigured
}: ICheckListItemProps) => {
  const [expanded, setExpanded] = useState(false);
  const [activeTab, setActiveTab] = useState('check-settings');
  const [tabs, setTabs] = useState<ITab[]>([]);
  const [showDetails, setShowDetails] = useState(false);
  const {
    checkTypes
  }: {
    checkTypes: CheckTypes;
  } = useDecodedParams();
  const [enabledType, setEnabledType] = useState<
    'error' | 'warning' | 'fatal' | ''
  >('');

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

  const isDisabled = !check?.configured || check?.disabled;

  const closeCheckDetails = () => {
    setShowDetails(false);
    localStorage.setItem(`${checkTypes}_${check.check_name}_details`, 'false');
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
          expanded || showDetails ? '' : ' border-b border-gray-100',
          !isDisabled ? 'text-gray-700' : 'opacity-75',
          check?.disabled ? 'line-through' : '',
          'h-18'
        )}
      >
        <td className="pl-4 pr-8 min-w-133 max-w-133 h-full ">
          <div className="flex space-x-1 items-center">
            <div className=" !w-7 !h-full"></div>
            {isAlreadyDeleted !== true &&
              (mode ? (
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
            {isAlreadyDeleted !== true && (
              <Tooltip
                content={!check?.disabled ? 'Enabled' : 'Disabled'}
                className="max-w-80 py-2 px-2 bg-gray-800 delay-700"
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
    </>
  );
};

export default RuleMiningChecksContainerListItem;
