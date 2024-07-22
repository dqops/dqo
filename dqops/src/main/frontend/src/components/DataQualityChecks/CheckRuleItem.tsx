import { IconButton } from '@material-tailwind/react';
import clsx from 'clsx';
import React, { useEffect } from 'react';
import { FieldModel, RuleParametersModel } from '../../api';
import Button from '../Button';
import SvgIcon from '../SvgIcon';
import FieldControl from './FieldControl';

interface CheckRuleItemProps {
  parameters?: RuleParametersModel;
  onChange: (parameters: RuleParametersModel) => void;
  type: 'error' | 'warning' | 'fatal';
  disabled?: boolean;
  onUpdate: () => void;
  changeEnabled?: (variable: 'error' | 'warning' | 'fatal' | '') => void;
  configuredType?: 'error' | 'warning' | 'fatal' | '';
  isSimpleMode?: boolean;
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

const backgroundClassesMap = {
  error: 'bg-orange-100',
  warning: 'bg-yellow-100',
  fatal: 'bg-red-100'
};

const CheckRuleItem = ({
  parameters,
  onChange,
  type,
  disabled,
  onUpdate,
  changeEnabled,
  configuredType,
  isSimpleMode
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
    <div
      className={
        isSimpleMode
          ? 'text-left text-gray-700 h-20 flex items-center justify-center'
          : 'text-left text-gray-700 h-20 flex items-center justify-center' +
            backgroundClassesMap[type]
      }
    >
      <div
        className={clsx('flex justify-center h-20', backgroundClassesMap[type])}
      >
        {parameters?.configured === true ? (
          <div className="flex items-center ">
            {!isSimpleMode && (
              <IconButton
                className={clsx(
                  classesMap[type],
                  'rounded-full w-6 h-6 my-1 !shadow-none mr-2',
                  parameters.rule_parameters?.length !== 0 && 'mt-6'
                )}
                ripple={false}
                onClick={() => {
                  onChange({
                    ...parameters,
                    configured: false
                  }),
                    changeEnabled && changeEnabled('');
                }}
                disabled={disabled}
              >
                <SvgIcon name="close" />
              </IconButton>
            )}
            {parameters.rule_parameters?.length === 0 && (
              <div className="font-bold">Enabled</div>
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
            <div key={index} className="ml-0">
              <FieldControl
                field={item}
                onChange={(field: FieldModel) =>
                  handleRuleParameterChange(field, index)
                }
                disabled={disabled}
                onSave={onUpdate}
                checkBoxNotRed={true}
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
