import React from 'react';
import { Dialog, DialogBody, DialogFooter } from '@material-tailwind/react';
import Button from '../../Button';

interface ConfirmDialogProps {
  open: boolean;
  onClose: () => void;
  message: string;
  onConfirm: () => Promise<void>;
}

const SetDefaultDialog = ({
  open,
  onClose,
  onConfirm,
  message
}: ConfirmDialogProps) => {
  const handleSubmit = async () => {
    await onConfirm();
    onClose();
  };

  return (
    <Dialog open={open} handler={onClose}>
      <DialogBody className="pt-10 pb-2 px-8">
        <div className='text-xl text-red-500 text-center'>
          Warning <br/><br/>
        </div>
        <div className="text-xl text-gray-700 text-center whitespace-normal ">
          {message}
          <br></br><br></br>
          Please review DQOps documentation before turning on a custom data grouping configuration.
          <br></br><br></br>
          Do you want to enable data grouping?
        </div>
      </DialogBody>
      <DialogFooter className="justify-center space-x-6 pb-8">
        <Button
          color="primary"
          className="px-8"
          onClick={onClose}
          label="No"
        />
        <Button
          color="primary"
          variant="outlined"
          className="px-8"
          onClick={handleSubmit}
          label="Yes"
        />
      </DialogFooter>
    </Dialog>
  );
};

export default SetDefaultDialog;