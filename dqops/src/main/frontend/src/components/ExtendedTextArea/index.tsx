import React, { ChangeEvent, useState } from 'react';
import TextArea from '../TextArea';
import SvgIcon from '../SvgIcon';
import { Dialog } from '@material-tailwind/react';
import Button from '../Button';
interface IExtendedTextAreaProps {
  className?: string;
  label?: string;
  placeholder?: string;
  error?: boolean;
  helperText?: string;
  name?: string;
  value?: string | number;
  onChange?: (e: ChangeEvent<HTMLTextAreaElement>) => void;
  onClear?: () => void;
  rows?: number;
  dataTestId?: string;
  tooltipText?: string;
  disabled?: boolean;
  onClick?: (e: any) => void;
  setValue?: (value: string) => void;
}
export default function ExtendedTextAre({
  label,
  className,
  placeholder,
  error,
  helperText,
  name,
  value,
  onChange,
  onClear,
  rows,
  dataTestId,
  tooltipText,
  disabled,
  onClick,
  setValue
}: IExtendedTextAreaProps) {
  const [open, setOpen] = useState(false);
  const [editedValue, setEditedValue] = useState(String(value));

  const onChangeEditedValue = (e: React.ChangeEvent<HTMLTextAreaElement>) => {
    setEditedValue(e.target.value);
  };
  console.log(editedValue);
  return (
    <div className="flex items-center justify-center space-x-2">
      <TextArea
        label={label}
        className={className}
        placeholder={placeholder}
        error={error}
        helperText={helperText}
        name={name}
        value={value}
        onChange={(e) => {
          onChange && onChange(e);
          onChangeEditedValue(e);
        }}
        onClear={onClear}
        rows={rows}
        dataTestId={dataTestId}
        tooltipText={tooltipText}
        disabled={disabled}
        onClick={onClick}
      />
      <div className="flex space-x-2 items-center justify-center">
        <SvgIcon
          name="edit"
          className="w-4 h-4 text-gray-700 cursor-pointer"
          onClick={() => setOpen(true)}
        />
      </div>
      <Dialog open={open} handler={() => setOpen(false)}>
        <div className="py-4 flex flex-col justify-center items-center w-full">
          <div className="w-full p-4">
            <TextArea
              label={label}
              className="!w-full min-h-30"
              placeholder={placeholder}
              error={error}
              helperText={helperText}
              name={name}
              value={editedValue}
              onChange={onChangeEditedValue}
              onClear={onClear}
              rows={rows}
              dataTestId={dataTestId}
              tooltipText={tooltipText}
              disabled={disabled}
              onClick={onClick}
            />
          </div>
          <div className="flex space-x-4 p-4 justify-end">
            <Button
              color="primary"
              variant="outlined"
              label="Cancel"
              className="w-40"
              onClick={() => {
                setOpen(false);
                setEditedValue(String(value));
              }}
            />
            <Button
              variant="contained"
              label="Save"
              color="primary"
              className="w-40"
              onClick={() => {
                setValue && setValue(editedValue);
                setOpen(false);
              }}
            />
          </div>
        </div>
      </Dialog>
    </div>
  );
}
