import React from 'react';
import { Dialog, DialogBody, DialogFooter } from '@material-tailwind/react';
import Button from "../../../Button";

interface ConfirmErrorModalProps {
  open: boolean;
  onClose: () => void;
  message?: string;
  onConfirm: () => void;
}

const ConfirmErrorModal = ({ open, onClose, message, onConfirm }: ConfirmErrorModalProps) => {
  return (
    <div>
      <Dialog open={open} handler={onClose}>
        <DialogBody className="pt-6 pb-2 px-6">
          <div className="text-2xl text-gray-900 whitespace-normal break-all">
            Connection to the data source failed
          </div>
          <div className="text-xl text-red-700 text-center whitespace-normal break-all p-3 border my-4 rounded-lg">
            {message}
          </div>
          <div className="text-center text-xl text-gray-900">
            Do you want to save the connection anyway?
          </div>
        </DialogBody>
        <DialogFooter className="justify-center space-x-6 pb-8">
          <Button
            color="primary"
            className="px-8"
            onClick={onClose}
            label="No"
            variant="outlined"
          />

          <Button
            color="primary"
            className="px-8"
            label="Yes"
            onClick={onConfirm}
          />
        </DialogFooter>
      </Dialog>
    </div>
  );
};

export default ConfirmErrorModal;
