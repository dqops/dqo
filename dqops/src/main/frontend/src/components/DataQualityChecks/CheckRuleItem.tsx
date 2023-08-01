import React, { useEffect } from 'react';
import SvgIcon from '../SvgIcon';
import FieldControl from './FieldControl';
import { FieldModel, RuleParametersModel } from '../../api';
import clsx from 'clsx';
import { IconButton } from '@material-tailwind/react';
import Button from '../Button';

interface CheckRuleItemProps {
  parameters?: RuleParametersModel;
  onChange: (parameters: RuleParametersModel) => void;
  type: 'error' | 'warning' | 'fatal';
  disabled?: boolean;
  onUpdate: () => void;
  changeEnabled?: (variable: 'error' | 'warning' | 'fatal' | '') => void;
  configuredType?: 'error' | 'warning' | 'fatal' | '';
}

const buttonLabelMap = {
  error: 'Add Error',
  warning: 'Add Warning',
  fatal: 'Add Fatal'
};

const classesMap = {
  error: 'bg-orange-900',
  warning: 'bg-yellow-900',
  fatal: 'bg-red-900'
};

const CheckRuleItem = ({
  parameters,
  onChange,
  type,
  disabled,
  onUpdate,
  changeEnabled,
  configuredType
}: CheckRuleItemProps) => {
  const handleRuleParameterChange = (field: FieldModel, idx: number) => {
    const newParameters = parameters?.rule_parameters?.map((item, index) =>
      index === idx ? field : item
    );
    onChange({
      ...parameters,
      rule_parameters: newParameters
    });
  };

  useEffect(() => {
    if (
      configuredType &&
      configuredType !== type &&
      parameters &&
      parameters.rule_parameters?.length === 0
    ) {
      onChange({
        ...parameters,
        configured: false
      });
    }
  }, [parameters?.configured, configuredType, type]);

  return (
    <div className="text-left text-gray-700 h-13 flex items-center justify-center">
      <div className="flex space-x-2 items-end justify-center">
        {parameters?.configured === true ? (
          <div className="flex items-center gap-x-2">
            <IconButton
              className={clsx(
                classesMap[type],
                'rounded-full w-6 h-6 my-1 !shadow-none'
              )}
              ripple={false}
              onClick={() => {
                onChange({
                  ...parameters,
                  configured: false
                }),
                  changeEnabled && changeEnabled('');
              }}
            >
              <SvgIcon name="close" />
            </IconButton>
            {parameters.rule_parameters?.length === 0 && (
              <div className="font-bold">Enabled </div>
            )}
          </div>
        ) : (
          <Button
            className={clsx(
              'px-4 py-2 whitespace-nowrap disabled:bg-gray-100 disabled:disabled:cursor-not-allowed',
              classesMap[type],
              disabled ? 'text-gray-500 ' : 'text-white'
            )}
            textSize="sm"
            onClick={() => {
              onChange({
                ...parameters,
                configured: true
              }),
                changeEnabled && changeEnabled(type);
            }}
            disabled={disabled}
            label={buttonLabelMap[type]}
          />
        )}
        {parameters?.configured &&
          parameters?.rule_parameters?.map((item, index) => (
            <div key={index}>
              <FieldControl
                field={item}
                onChange={(field: FieldModel) =>
                  handleRuleParameterChange(field, index)
                }
                disabled={disabled}
                onSave={onUpdate}
              />
            </div>
          ))}
        {parameters?.configured && !parameters?.rule_parameters && (
          <div>Enabled</div>
        )}
      </div>
    </div>
  );
};

export default CheckRuleItem;
