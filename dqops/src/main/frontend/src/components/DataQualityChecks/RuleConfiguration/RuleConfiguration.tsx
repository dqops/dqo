import React, { Fragment, useState } from 'react';
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
  const [configurationType, setConfigurationType] =
    useState<ConfigurationType>('');
  const [open, setOpen] = useState(false);

  const onChangeConfigurationType = (value: ConfigurationType) => {
    if (value === 'multiple_levels') {
      setOpen(true);
    } else {
      setConfigurationType(value);
    }
  };
  if (isAlreadyDeleted) {
    return <></>;
  }
  console.log(ruleParamenterConfigured);
  const renderRuleConfiguration = (type: ConfigurationType) => {
    switch (type) {
      case '':
        return <></>;
      case 'warning':
        return (
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
        );
      case 'error':
        return (
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
        );
      case 'fatal':
        return (
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
          <Select
            value={configurationType}
            onChange={onChangeConfigurationType}
            options={options}
            label="Issue severity level"
          />
        </td>
      )}
      {renderRuleConfiguration(configurationType)}
      <ConfirmDialogRuleConfiguration
        open={open}
        onClose={() => setOpen(false)}
        onConfirm={() => {
          setConfigurationType('multiple_levels');
          setOpen(false);
        }}
        message="The data check editor will switch to an advanced mode. You will be able to configure different thresholds for warning, error and fatal error severity levels"
      />
    </Fragment>
  );
}
