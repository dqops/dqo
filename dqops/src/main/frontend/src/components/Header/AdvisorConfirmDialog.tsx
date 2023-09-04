import React from 'react';
import { Dialog, DialogBody, DialogFooter } from '@material-tailwind/react';
import Button from "../Button";

interface ConfirmDialogProps {
  open: boolean;
  onClose: () => void;
}

const AdvisorConfirmDialog = ({
  open,
  onClose,
}: ConfirmDialogProps) => {
  const handleSubmit = async () => {
    onClose();
  };

  return (
    <div>
      <Dialog open={open} handler={onClose}>
        <DialogBody className="pt-10 pb-2 px-8">
          <div className="text-2xl text-gray-700 text-center whitespace-normal">
            Not all configuration actions were performed on a connection, do you want to close the advisor?
          </div>
        </DialogBody>
        <DialogFooter className="justify-center space-x-6 pb-8">
          <Button
            color="primary"
            variant="outlined"
            className="px-8"
            onClick={onClose}
            label="No"
          />
          <Button
            color="primary"
            className="px-8"
            onClick={handleSubmit}
            label="Yes"
          />
        </DialogFooter>
      </Dialog>
    </div>
  );
};

export default AdvisorConfirmDialog;
