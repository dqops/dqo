import React from 'react';
import { Dialog, DialogBody, DialogFooter } from '@material-tailwind/react';
import Button from '../Button';

interface ConfirmDialogProps {
  open: boolean;
  onClose: () => void;
  message: string;
  onConfirm: () => Promise<void>;
  isCancelExcluded ?: boolean
}

const ConfirmDialog = ({
  open,
  onClose,
  onConfirm,
  message,
  isCancelExcluded
}: ConfirmDialogProps) => {
  const handleSubmit = async () => {
    await onConfirm();
    onClose();
  };

  return (
    <Dialog open={open} handler={onClose}>
      <DialogBody className="pt-10 pb-2 px-8">
        <div className="text-2xl text-gray-700 text-center whitespace-normal">
          {message}
        </div>
      </DialogBody>
      <DialogFooter className="justify-center space-x-6 pb-8">
      {isCancelExcluded !== true &&
        <Button
          color="primary"
          variant="outlined"
          className="px-8"
          onClick={onClose}
          label="Cancel"
        />
      }
        <Button
          color="primary"
          className="px-8"
          onClick={handleSubmit}
          label="Confirm"
        /> 
      </DialogFooter>
    </Dialog>
  );
};

export default ConfirmDialog;
