import React, { Fragment, useEffect, useState } from 'react';
import Select from '../../Select';
import CheckRuleItem from '../CheckRuleItem';
import ConfirmDialogRuleConfiguration from './ConfirmDialogRuleConfiguration';

type RuleConfigurationProps = {
  isDisabled?: boolean;
  isAlreadyDeleted?: boolean;
  check: any;
  handleChange: (data: any) => void;
  onUpdate: () => void;
  changeEnabled: (data: any) => void;
  enabledType: 'error' | 'warning' | 'fatal' | '';
  ruleParamenterConfigured: boolean;
  onChangeRuleParametersConfigured: (v: boolean) => void;
};

type ConfigurationType = 'error' | 'warning' | 'fatal' | '' | 'multiple_levels';

const options = [
  { value: '', label: 'Disabled' },
  { value: 'error', label: 'Error' },
  { value: 'warning', label: 'Warning' },
  { value: 'fatal', label: 'Fatal' },
  { value: 'multiple_levels', label: 'Multiple Levels' }
];

export default function RuleConfiguration({
  isDisabled,
  isAlreadyDeleted,
  check,
  handleChange,
  onUpdate,
  changeEnabled,
  enabledType,
  ruleParamenterConfigured,
  onChangeRuleParametersConfigured
}: RuleConfigurationProps) {
  const getConfiguredType = () => {
    if (ruleParamenterConfigured) {
      return 'multiple_levels';
    }
    if (check?.rule?.warning?.configured) {
      return 'warning';
    }
    if (check?.rule?.error?.configured) {
      return 'error';
    }
    if (check?.rule?.fatal?.configured) {
      return 'fatal';
    }
    return '';
  };

  const [configurationType, setConfigurationType] = useState<ConfigurationType>(
    getConfiguredType()
  );
  const [open, setOpen] = useState(false);

  const onChangeConfigurationType = (value: ConfigurationType) => {
    if (value === configurationType) return;
    if (value === 'multiple_levels') {
      setOpen(true);
    } else {
      let updatedRule = { ...check.rule };

      if (value === '') {
        updatedRule = {
          warning: { ...check.rule.warning, configured: false },
          error: { ...check.rule.error, configured: false },
          fatal: { ...check.rule.fatal, configured: false }
        };
      } else {
        if (configurationType === '') {
          updatedRule = {
            ...check.rule,
            [value]: { ...check.rule[value], configured: true }
          };
        } else {
          updatedRule = {
            ...check.rule,
            [value]: { ...check.rule[configurationType], configured: true },
            [configurationType]: {
              ...check.rule[configurationType],
              configured: false
            }
          };
        }
      }

      handleChange({ rule: updatedRule });
      setConfigurationType(value);
    }
  };

  if (isAlreadyDeleted) {
    return <></>;
  }
  useEffect(() => {
    if (ruleParamenterConfigured) {
      setConfigurationType(getConfiguredType());
    }
  }, [ruleParamenterConfigured]);

  const renderRuleConfiguration = (type: ConfigurationType) => {
    switch (type) {
      case '':
        return <></>;
      case 'warning':
        return (
          <td className="py-2 px-4 bg-yellow-100 relative">
            <div className="flex items-center justify-center">
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
                isSimpleMode={true}
              />
            </div>
          </td>
        );
      case 'error':
        return (
          <td className="py-2 px-4 bg-orange-100">
            <div className="flex items-center justify-center">
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
                isSimpleMode={true}
              />
            </div>
          </td>
        );
      case 'fatal':
        return (
          <td className="py-2 px-4 bg-red-100 h-18">
            <div className="flex items-center justify-center">
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
                isSimpleMode={true}
              />
            </div>
          </td>
        );
      case 'multiple_levels':
        return (
          <>
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
              <div className="w-4 bg-white absolute h-full right-0 top-0"></div>
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
            <td className="py-2 px-4 bg-red-100 h-18">
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
          </>
        );
    }
  };

  return (
    <Fragment>
      {!ruleParamenterConfigured && (
        <td>
          <div className="flex items-center justify-end">
            <Select
              value={configurationType}
              onChange={onChangeConfigurationType}
              options={options}
              className="w-40 mr-2 my-1 text-sm "
              menuClassName="!top-9"
              disabled={isDisabled}
            />
          </div>
        </td>
      )}
      {renderRuleConfiguration(configurationType)}
      <ConfirmDialogRuleConfiguration
        open={open}
        onClose={() => setOpen(false)}
        onConfirm={() => {
          // setConfigurationType('multiple_levels');
          onChangeRuleParametersConfigured(true);
          setOpen(false);
        }}
        message="The data check editor will switch to an advanced mode. You will be able to configure different thresholds for warning, error and fatal error severity levels"
      />
    </Fragment>
  );
}
