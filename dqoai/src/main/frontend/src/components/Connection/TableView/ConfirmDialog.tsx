import React from 'react';
import { Dialog, DialogBody, DialogFooter } from '@material-tailwind/react';
import { TableBasicModel } from '../../../api';
import Button from '../../Button';

interface ConfirmDialogProps {
  open: boolean;
  onClose: () => void;
  table?: TableBasicModel;
  onConfirm: () => void;
}

const ConfirmDialog = ({
  open,
  onClose,
  table,
  onConfirm
}: ConfirmDialogProps) => {
  const handleSubmit = () => {
    onConfirm();
    onClose();
  };

  return (
    <div>
      <Dialog open={open} handler={onClose}>
        <DialogBody className="pt-10 pb-2 px-8">
          <div className="text-2xl text-gray-700 text-center whitespace-normal break-all">
            Are you sure you want to delete the table{' '}
            {`${table?.connection_name}.${table?.target?.schema_name}.${table?.target?.table_name}`}
            ?
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
    </div>
  );
};

export default ConfirmDialog;
