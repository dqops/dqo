import React from 'react';
import { Dialog, DialogBody, DialogFooter } from '@material-tailwind/react';
import Button from '../../Button';
import { useTree } from '../../../contexts/treeContext';

interface ConfirmDialogProps {
  open: boolean;
  onClose: () => void;
  connection?: string;
  onConfirm: () => Promise<void>;
}

const ConfirmDialog = ({
  open,
  onClose,
  connection,
  onConfirm
}: ConfirmDialogProps) => {
  const { removeTreeNode, activeTab } = useTree();
  const handleSubmit = async () => {
    await onConfirm();
    removeTreeNode(activeTab);
    onClose();
  };

  return (
    <div>
      <Dialog open={open} handler={onClose}>
        <DialogBody className="pt-10 pb-2 px-8">
          <div className="text-2xl text-gray-700 text-center whitespace-normal">
            Are you sure you want to delete the connection{' '}
            {`${connection}`}?
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
