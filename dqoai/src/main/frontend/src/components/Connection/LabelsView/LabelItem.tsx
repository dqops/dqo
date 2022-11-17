import React, { useEffect, useState } from 'react';
import Input from '../../Input';
import { IconButton } from '@material-tailwind/react';
import SvgIcon from '../../SvgIcon';

interface ILabelItemProps {
  idx: number;
  label: string;
  onChange: (key: number, value: string) => void;
  onRemove: (key: number) => void;
  isLast: boolean;
}

const LabelItem = ({
  idx,
  label,
  onChange,
  onRemove,
  isLast
}: ILabelItemProps) => {
  return (
    <tr>
      <td className="pr-4 min-w-40 py-2">
        <Input
          value={label}
          onChange={(e) => onChange(idx, e.target.value)}
          error={!isLast && label === ''}
        />
      </td>
      <td className="px-8 min-w-20 py-2">
        {isLast ? (
          <IconButton size="sm">
            <SvgIcon name="add" className="w-4" />
          </IconButton>
        ) : (
          <IconButton
            className="bg-red-500"
            size="sm"
            onClick={() => onRemove(idx)}
          >
            <SvgIcon name="delete" className="w-4" />
          </IconButton>
        )}
      </td>
    </tr>
  );
};

export default LabelItem;
