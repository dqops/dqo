import React from 'react';
import { Dialog, DialogBody, DialogFooter } from '@material-tailwind/react';
import Button from '../Button';

interface ErrorModalProps {
  open: boolean;
  onClose: () => void;
  message?: string
}

const ErrorModal = ({ open, onClose, message }: ErrorModalProps) => {
  return (
    <div>
      <Dialog open={open} handler={onClose}>
        <DialogBody className="pt-10 pb-2 px-8 flex flex-col justify-center">
          <div className="text-2xl text-gray-700 text-center whitespace-normal break-all">
            {message ? 'DQOps server returned an error' : 'DQOps server not reachable'}
          </div>
          <div className='text-sm overflow-y-auto text-gray-700 w-full' style={{ maxHeight: '300px' }}>
            {message}
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
