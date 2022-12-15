import React, { ChangeEvent } from 'react';
import Input from '../../Input';
import { IconButton } from '@material-tailwind/react';
import SvgIcon from '../../SvgIcon';

interface INumberItemProps {
  idx: number;
  integer?: number;
  onChange: (key: number, value: number) => void;
  onRemove: (key: number) => void;
  isLast: boolean;
}

const NumberItem = ({
  idx,
  integer,
  onChange,
  onRemove,
  isLast
}: INumberItemProps) => {
  const handleChange = (e: ChangeEvent<HTMLInputElement>) => {
    if (!e.target.value) {
      onRemove(idx);
    }
    if (/^[1-9][0-9]*$/.test(e.target.value)) {
      onChange(idx, +e.target.value);
    }
  };

  return (
    <tr>
      <td className="pr-4 min-w-40 py-2">
        <Input
          value={integer}
          onChange={handleChange}
          error={!isLast && !integer}
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

export default NumberItem;
