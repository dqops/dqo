import React from 'react';
import { Dialog, DialogBody, DialogFooter } from '@material-tailwind/react';
import { ColumnBasicModel } from '../../../api';
import Button from '../../Button';

interface ConfirmDialogProps {
  open: boolean;
  onClose: () => void;
  column?: ColumnBasicModel;
  onConfirm: () => void;
}

const ConfirmDialog = ({
  open,
  onClose,
  column,
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
            Are you sure you want to delete the column{' '}
            {`${column?.connection_name}.${column?.table?.schemaName}.${column?.table?.tableName}.${column?.column_name}`}
            ?
          </div>
        </DialogBody>
        <DialogFooter className="justify-center space-x-6 pb-8">
          <Button
            color="error"
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
