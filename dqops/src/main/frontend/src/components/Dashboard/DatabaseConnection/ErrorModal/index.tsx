import { Dialog, DialogBody, DialogFooter } from '@material-tailwind/react';
import React from 'react';
import Button from '../../../Button';

interface ErrorModalProps {
  open: boolean;
  onClose: () => void;
  message?: string;
}

const ErrorModal = ({ open, onClose, message }: ErrorModalProps) => {
  return (
    <div>
      <Dialog open={open} handler={onClose}>
        <DialogBody className="pt-10 pb-2 px-8 flex justify-center">
          <div className="text-lg text-red-700 text-center whitespace-normal break-all">
            {message}
          </div>
        </DialogBody>
        <DialogFooter className="justify-center space-x-6 pb-8">
          <Button
            color="primary"
            className="px-8"
            onClick={onClose}
            label="Close"
          />
        </DialogFooter>
      </Dialog>
    </div>
  );
};

export default ErrorModal;
