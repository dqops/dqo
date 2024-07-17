import React, { useCallback, useEffect, useState } from 'react';

import { Dialog, Tooltip } from '@material-tailwind/react';
import Button from '../Button';
import LabelsView from '../Connection/LabelsView';
import SvgIcon from '../SvgIcon';

interface IStringListFieldProps {
  className?: string;
  label?: string;
  value: string[];
  tooltipText?: string;
  onChange: (value: string[]) => void;
  onSave?: () => void;
  disabled?: boolean;
}

const StringListField = ({
  label,
  value,
  tooltipText,
  onChange,
  disabled
}: IStringListFieldProps) => {
  const [open, setOpen] = useState(false);
  const [labels, setLabels] = useState<string[]>([]);

  useEffect(() => {
    if (open) {
      setLabels(value);
    }
  }, [value, open]);

  const handleSave = () => {
    onChange(labels);
    setOpen(false);
  };

  const handleChange = useCallback((values: string[]) => {
    setLabels(values);
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
                <div>
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
        <div className="relative text-sm leading-1">{value?.join(', ')}</div>
        <SvgIcon
          name="edit"
          className="w-4 h-4 text-gray-700 cursor-pointer"
          onClick={() => !disabled && setOpen(true)}
        />
      </div>
      <Dialog open={open} handler={() => setOpen(false)}>
        <div className="p-4">
          <LabelsView labels={labels} onChange={handleChange} hasAdd />
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

export default StringListField;
