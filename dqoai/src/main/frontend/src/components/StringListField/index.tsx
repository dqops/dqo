import React, { useState } from 'react';

import SvgIcon from '../SvgIcon';
import { Dialog, Tooltip } from '@material-tailwind/react';
import LabelsView from '../Connection/LabelsView';
import Button from '../Button';

interface IStringListFieldProps {
  className?: string;
  label?: string;
  value: string[];
  tooltipText?: string;
}

const StringListField = ({
  label,
  value,
  tooltipText
}: IStringListFieldProps) => {
  const [open, setOpen] = useState(false);

  return (
    <div>
      <div className="flex space-x-1">
        {label && (
          <label className="block font-regular text-gray-700 mb-1 text-sm flex space-x-1">
            <span>{label}</span>
            {!!tooltipText && (
              <Tooltip
                content={tooltipText}
                className="max-w-80 py-4 px-4 bg-gray-800"
              >
                <div>
                  <SvgIcon
                    name="info"
                    className="w-4 h-4 text-blue-700 cursor-pointer"
                  />
                </div>
              </Tooltip>
            )}
          </label>
        )}
      </div>
      <div className="flex space-x-2 items-center">
        <div className="relative text-sm leading-1">{value.join(', ')}</div>
        <SvgIcon
          name="edit"
          className="w-4 h-4 text-blue-700 cursor-pointer"
          onClick={() => setOpen(true)}
        />
      </div>
      <Dialog open={open} handler={() => setOpen(false)}>
        <div className="p-4">
          <LabelsView labels={[]} onChange={() => {}} />
          <div className="flex space-x-4 p-4 justify-end">
            <Button
              color="error"
              variant="outlined"
              label="Cancel"
              className="w-40"
              onClick={() => setOpen(false)}
            />
            <Button
              variant="contained"
              label="Save"
              className="w-40 bg-blue-500 text-white"
              onClick={() => setOpen(false)}
            />
          </div>
        </div>
      </Dialog>
    </div>
  );
};

export default StringListField;
