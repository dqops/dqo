import { Dialog, DialogBody, DialogFooter } from '@material-tailwind/react';
import React from 'react';
import { TableLineageFlowModel } from '../../api';

export default function DataLineageDetailsDialog({
  isOpen,
  onClose,
  flow
}: {
  isOpen: boolean;
  onClose: () => void;
  flow: TableLineageFlowModel;
}) {
  console.log(flow);
  return (
    <Dialog open={isOpen} handler={() => onClose()} className="!z-[99999]">
      <DialogBody>
        <></>
      </DialogBody>
      <DialogFooter>
        <></>
      </DialogFooter>
    </Dialog>
  );
}
