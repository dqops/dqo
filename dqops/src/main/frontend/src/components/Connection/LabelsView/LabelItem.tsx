import { IconButton } from '@material-tailwind/react';
import clsx from 'clsx';
import React from 'react';
import Input from '../../Input';
import SvgIcon from '../../SvgIcon';

interface ILabelItemProps {
  idx: number;
  label: string;
  onChange: (key: number, value: string) => void;
  onRemove: (key: number) => void;
  canUserEditLabel?: boolean;
}

const LabelItem = ({
  idx,
  label,
  onChange,
  onRemove,
  canUserEditLabel
}: ILabelItemProps) => {
  return (
    <div
      className={clsx(
        'flex items-center w-full ',
        canUserEditLabel !== true
          ? 'pointer-events-none cursor-not-allowed'
          : ''
      )}
    >
      <div className="min-w-40 w-11/12 py-2">
        <div>
          <Input
            className="focus:!ring-0 focus:!border"
            value={label}
            onChange={(e) => onChange(idx, e.target.value)}
            error={label === ''}
          />
        </div>
      </div>
      <div className="px-0 pr-8 max-w-34 min-w-34 py-2">
        <div className="flex justify-center">
          <IconButton
            color="teal"
            size="sm"
            onClick={() => onRemove(idx)}
            className="!shadow-none"
            disabled={canUserEditLabel !== true}
          >
            <SvgIcon name="delete" className="w-4" />
          </IconButton>
        </div>
      </div>
    </div>
  );
};

export default LabelItem;
