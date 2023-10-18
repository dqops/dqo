import React from 'react';
import { Dialog, DialogBody, DialogFooter } from '@material-tailwind/react';
import Button from '../Button';

interface ErrorModalProps {
  open: boolean;
  onClose: () => void;
}

const ErrorModal = ({ open, onClose }: ErrorModalProps) => {
  return (
    <div>
      <Dialog open={open} handler={onClose}>
        <DialogBody className="pt-10 pb-2 px-8 flex justify-center">
          <div className="text-2xl text-gray-700 text-center whitespace-normal break-all">
            DQOps Server not reachable
          </div>
        </DialogBody>
        <DialogFooter className="justify-center space-x-6 pb-8">
          <Button
            color="primary"
            className="px-8"
            onClick={onClose}
            label="OK"
          />
        </DialogFooter>
      </Dialog>
    </div>
  );
};

export default ErrorModal;
