import { Dialog, DialogBody, DialogFooter } from '@material-tailwind/react';
import React, { useEffect, useState } from 'react';
import Button from '../../Button';
import Checkbox from '../../Checkbox';

interface ConfirmDialogProps {
  open: boolean;
  onClose: () => void;
  message: string;
  onConfirm: () => void;
}

const ConfirmDialog = ({
  open,
  onClose,
  onConfirm,
  message
}: ConfirmDialogProps) => {
  const [show, setShow] = useState(false);

  useEffect(() => {
    if (localStorage.getItem('check-editor-advanced') === 'true') {
      onConfirm();
      onClose();
    }
  }, [open]);

  const handleSubmit = () => {
    if (!show) {
      localStorage.setItem('check-editor-advanced', 'true');
    }
    onConfirm();
    onClose();
  };

  if (localStorage.getItem('check-editor-advanced') === 'true') {
    return null;
  }

  return (
    <Dialog open={open} handler={onClose}>
      <DialogBody className="pt-10 pb-2 px-8">
        <div className="text-2xl text-gray-700 text-center whitespace-normal break-words">
          {message}
        </div>
        <div className="flex items-center gap-x-4 pt-6">
          <Checkbox
            type="checkbox"
            checked={!show}
            onChange={() => {
              setShow(!show);
            }}
          />
          <div className="ml-2 text-black text-lg">
            Do not show this message again
          </div>
        </div>
      </DialogBody>
      <DialogFooter className="justify-center space-x-6 pb-8">
        <Button
          color="primary"
          variant="outlined"
          className="px-8"
          onClick={onClose}
          label="Cancel"
        />
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
