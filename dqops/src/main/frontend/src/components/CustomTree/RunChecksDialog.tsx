import {
  Popover,
  PopoverHandler,
  PopoverContent,
  Dialog,
  DialogBody,
  DialogFooter,
  DialogHeader
} from '@material-tailwind/react';
import React, { useState } from 'react';
import SvgIcon from '../SvgIcon';
import Button from '../Button';
import { CheckSearchFilters } from '../../api';
type TRunChecksDialogProps = {
  onClick: () => void;
  open: boolean;
  onClose: VoidFunction;
  nodeId: string;
};

export default function RunChecksDialog({
  onClick,
  onClose,
  open,
  nodeId
}: TRunChecksDialogProps) {
  const [filters, setFilters] = useState<CheckSearchFilters>({});

  const onChangeFilters = (obj: Partial<CheckSearchFilters>) => {
    setFilters((prev) => ({
      ...prev,
      ...obj
    }));
  };

  return (
    <Dialog open={open} handler={onClose} className="min-w-300 p-4">
      <DialogHeader className="font-bold text-center justify-center">
        Run checks
      </DialogHeader>
      <DialogBody className="text-sm">
        <></>
      </DialogBody>
      <DialogFooter className="flex gap-6 items-center absolute bottom-5 right-5">
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
          //   onClick={onConfirm}
          label="Delete"
          //   disabled={userProfile.can_delete_data !== true}
        />
      </DialogFooter>
    </Dialog>
  );
}
