import React from 'react';
import SvgIcon from '../SvgIcon';
import FieldControl from './FieldControl';
import { UIFieldModel, UIRuleParametersModel } from '../../api';
import clsx from 'clsx';
import { IconButton } from '@material-tailwind/react';
import Button from "../Button";

interface CheckRuleItemProps {
  parameters?: UIRuleParametersModel;
  onChange: (parameters: UIRuleParametersModel) => void;
  type: 'error' | 'warning' | 'fatal';
  disabled?: boolean;
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
  disabled
}: CheckRuleItemProps) => {
  const handleRuleParameterChange = (field: UIFieldModel, idx: number) => {
    const newParameters = parameters?.rule_parameters?.map((item, index) =>
      index === idx ? field : item
    );
    onChange({
      ...parameters,
      rule_parameters: newParameters
    });
  };

  return (
    <div className="text-left text-gray-700 h-13 flex items-center justify-center">
      <div className="flex space-x-2 items-end justify-center">
        {parameters?.configured ? (
          <IconButton
            className={clsx(classesMap[type], 'rounded-full w-6 h-6 my-1 !shadow-none')}
            ripple={false}
            onClick={() =>
              onChange({
                ...parameters,
                configured: false
              })
            }
          >
            <SvgIcon name="close" />
          </IconButton>
        ) : (
          <Button
            className={clsx(
              'px-4 text-sm py-2 whitespace-nowrap disabled:bg-gray-100 disabled:disabled:cursor-not-allowed',
              classesMap[type],
              disabled ? 'text-gray-500 ' : 'text-white',
            )}
            onClick={() =>
              onChange({
                ...parameters,
                configured: true
              })
            }
            disabled={disabled}
            label={buttonLabelMap[type]}
          />
        )}
        {parameters?.configured &&
          parameters?.rule_parameters?.map((item, index) => (
            <div key={index}>
              <FieldControl
                field={item}
                onChange={(field: UIFieldModel) =>
                  handleRuleParameterChange(field, index)
                }
                disabled={disabled}
                className="!max-w-25 !min-w-25"
              />
            </div>
          ))}
      </div>
    </div>
  );
};

export default CheckRuleItem;
