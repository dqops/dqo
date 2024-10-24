import React, { useCallback, useEffect, useState } from 'react';

import { Dialog, Tooltip } from '@material-tailwind/react';
import Button from '../Button';
import NumbersView from '../Connection/NumbersView';
import SvgIcon from '../SvgIcon';

interface IIntegerListFieldProps {
  className?: string;
  label?: string;
  value: number[];
  tooltipText?: string;
  onChange: (value: number[]) => void;
  disabled?: boolean;
}

const IntegerListField = ({
  label,
  value,
  tooltipText,
  onChange,
  disabled
}: IIntegerListFieldProps) => {
  const [open, setOpen] = useState(false);
  const [numbers, setNumbers] = useState<number[]>([]);

  useEffect(() => {
    if (open) {
      setNumbers(value);
    }
  }, [value, open]);

  const handleSave = () => {
    onChange(numbers);
    setOpen(false);
  };

  const handleChange = useCallback((values: number[]) => {
    setNumbers(values);
  }, []);

  return (
    <div>
      <div className="flex space-x-1">
        {label && (
          <label className="block font-regular text-gray-700 mb-1 text-sm flex space-x-1">
            <span>{label}</span>
            {!!tooltipText && (
              <Tooltip
                content={tooltipText}
                className="max-w-80 py-2 px-2 bg-gray-800"
              >
                <div className="!min-w-4">
                  <SvgIcon
                    name="info"
                    className="w-4 h-4 text-gray-700 cursor-pointer"
                  />
                </div>
              </Tooltip>
            )}
          </label>
        )}
      </div>
      <div className="flex space-x-2 items-center">
        <div className="relative text-sm leading-1">
          {value?.slice(0, 10)?.join(', ')}
          {value?.length > 10 && '...'}
        </div>
        <div className="!min-w-4 !w-4 ">
          <SvgIcon
            name="edit"
            className="w-4 h-4 text-gray-700 cursor-pointer"
            onClick={() => !disabled && setOpen(true)}
          />
        </div>
      </div>
      <Dialog
        open={open}
        handler={() => setOpen(false)}
        className=" max-h-[96vh] text-sm flex flex-col overflow-y-auto "
      >
        <div className="p-4">
          <NumbersView values={numbers} onChange={handleChange} />
          <div className="flex space-x-4 p-4 justify-end">
            <Button
              color="primary"
              variant="outlined"
              label="Cancel"
              className="w-40"
              onClick={() => setOpen(false)}
            />
            <Button
              variant="contained"
              label="Save"
              color="primary"
              className="w-40"
              onClick={handleSave}
            />
          </div>
        </div>
      </Dialog>
    </div>
  );
};

export default IntegerListField;
