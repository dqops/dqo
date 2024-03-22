import { Dialog, DialogBody, DialogFooter } from '@material-tailwind/react';
import React from 'react';
import Button from '../../../Button';

interface ConfirmErrorModalProps {
  open: boolean;
  onClose: () => void;
  message?: string;
  onConfirm: () => void;
}

const ConfirmErrorModal = ({
  open,
  onClose,
  message,
  onConfirm
}: ConfirmErrorModalProps) => {
  return (
    <Dialog open={open} handler={onClose} className="min-w-200">
      <DialogBody className="pt-6 pb-2 px-6">
        <div className="flex flex-col">
          <div className="text-2xl text-gray-900 whitespace-normal">
          Connection to the data source failed, do you want to save the connection anyway?
          </div>
          <div className="text-lg text-red-700 whitespace-normal break-word p-3 border my-4 rounded-lg max-h-[110px] overflow-y-auto">
            {message}
          </div>
        </div>
      </DialogBody>
      <DialogFooter className="justify-center space-x-6 pb-8">
        <Button
          color="primary"
          className="px-8"
          onClick={onConfirm}
          label="Yes"
          variant="outlined"
        />
        <Button
          color="primary"
          className="px-8"
          onClick={onClose}
          label="No"
          variant="outlined"
        />
      </DialogFooter>
    </Dialog>
  );
};

export default ConfirmErrorModal;
