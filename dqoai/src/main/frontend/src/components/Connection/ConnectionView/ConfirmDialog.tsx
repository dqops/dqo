import React from 'react';
import { Dialog, DialogBody, DialogFooter } from '@material-tailwind/react';
import { ConnectionBasicModel } from '../../../api';
import Button from '../../Button';
import { useTree } from '../../../contexts/treeContext';

interface ConfirmDialogProps {
  open: boolean;
  onClose: () => void;
  connection?: ConnectionBasicModel;
  onConfirm: () => Promise<void>;
  nodeId: string;
}

const ConfirmDialog = ({
  open,
  onClose,
  connection,
  onConfirm,
  nodeId
}: ConfirmDialogProps) => {
  const { removeTreeNode } = useTree();
  const handleSubmit = async () => {
    await onConfirm();
    removeTreeNode(nodeId);
    onClose();
  };

  return (
    <div>
      <Dialog open={open} handler={onClose}>
        <DialogBody className="pt-10 pb-2 px-8">
          <div className="text-2xl text-gray-700 text-center whitespace-normal break-all">
            Are you sure you want to delete the connection{' '}
            {`${connection?.connection_name}`}?
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
