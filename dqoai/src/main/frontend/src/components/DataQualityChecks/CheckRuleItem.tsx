import React from 'react';
import SvgIcon from '../SvgIcon';
import FieldControl from './FieldControl';
import { UIFieldModel, UIRuleParametersModel } from '../../api';
import clsx from 'clsx';
import { IconButton } from '@material-tailwind/react';

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
    <div className="text-left text-gray-700">
      <div className="flex space-x-2 items-end">
        {parameters?.configured ? (
          <IconButton
            className={clsx(classesMap[type], 'rounded-full w-6 h-6 my-1')}
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
          <button
            className={clsx(
              'px-4 py-1.5 text-sm whitespace-nowrap rounded-full text-white',
              classesMap[type]
            )}
            onClick={() =>
              onChange({
                ...parameters,
                configured: true
              })
            }
          >
            {buttonLabelMap[type]}
          </button>
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
              />
            </div>
          ))}
      </div>
    </div>
  );
};

export default CheckRuleItem;
