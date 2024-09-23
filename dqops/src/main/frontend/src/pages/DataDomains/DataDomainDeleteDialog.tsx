import { Dialog, DialogBody, DialogFooter } from '@material-tailwind/react';
import React, { useState } from 'react';
import Button from '../../components/Button';
import Loader from '../../components/Loader';

interface ConfirmDialogProps {
  open: boolean;
  onClose: () => void;
  onConfirm: () => Promise<void>;
  isCancelExcluded?: boolean;
  yesNo?: boolean;
}

const DataDomainDeleteDialog = ({
  open,
  onClose,
  onConfirm,
  isCancelExcluded,
  yesNo
}: ConfirmDialogProps) => {
  const [showSpinner, setShowSpinner] = useState(false);
  const handleSubmit = async () => {
    setShowSpinner(true);
    await onConfirm()
      .then(() => setShowSpinner(false))
      .finally(() => onClose());
  };

  return (
    <Dialog open={open} handler={onClose}>
      <DialogBody className="pt-10 pb-2 px-8">
        <div className="text-2xl text-gray-700 text-center whitespace-normal break-words">
          Do you want to delete the data domain? You will lose all metadata and
          data quality results.
        </div>
      </DialogBody>
      <DialogFooter className="justify-center space-x-6 pb-8">
        {isCancelExcluded !== true && (
          <Button
            color="primary"
            variant="outlined"
            className="px-8"
            onClick={onClose}
            label={yesNo ? 'No' : 'Cancel'}
          />
        )}
        <Button
          color="primary"
          className="px-8"
          onClick={handleSubmit}
          label={yesNo ? 'Yes' : 'Confirm'}
        />
        {showSpinner && (
          <div className="w-6 h-6 mb-2">
            <Loader isFull={false} className="w-8 h-8 fill-green-700" />
          </div>
        )}
      </DialogFooter>
    </Dialog>
  );
};

export default DataDomainDeleteDialog;
