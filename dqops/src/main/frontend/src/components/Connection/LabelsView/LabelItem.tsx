import React from 'react';
import Input from '../../Input';
import { IconButton } from '@material-tailwind/react';
import SvgIcon from '../../SvgIcon';
import clsx from 'clsx';

interface ILabelItemProps {
  idx: number;
  label: string;
  onChange: (key: number, value: string) => void;
  onRemove: (key: number) => void;
  canUserEditLabel?: boolean
}

const LabelItem = ({
  idx,
  label,
  onChange,
  onRemove,
  canUserEditLabel
}: ILabelItemProps) => {
  console.log(canUserEditLabel)
  return (
    <tr className={clsx(canUserEditLabel === false ? "pointer-events-none cursor-not-allowed" : "")}>
      <td className="pr-4 min-w-40 py-2">
        <div>
          <Input
            className="focus:!ring-0 focus:!border"
            value={label}
            onChange={(e) => onChange(idx, e.target.value)}
            error={label === ''}
          />
        </div>
      </td>
      <td className="px-8 max-w-34 min-w-34 py-2">
        <div className="flex justify-center">
          <IconButton
            color="teal"
            size="sm"
            onClick={() => onRemove(idx)}
            className="!shadow-none"
            disabled={canUserEditLabel === false}
          >
            <SvgIcon name="delete" className="w-4" />
          </IconButton>
        </div>
      </td>
    </tr>
  );
};

export default LabelItem;
