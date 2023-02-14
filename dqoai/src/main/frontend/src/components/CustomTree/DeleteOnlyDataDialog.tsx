import { Dialog, DialogBody, DialogFooter, DialogHeader, Typography } from "@material-tailwind/react";
import DatePicker from "../DatePicker";
import Button from "../Button";
import React, { useState } from "react";
import { CustomTreeNode } from "../../shared/interfaces";
import moment from "moment";

type DeleteOnlyDataDialogProps = {
  open: boolean;
  onClose: VoidFunction;
  onDelete: (startDate: string, endDate: string) => void;
}
const DeleteOnlyDataDialog = ({ open, onClose, onDelete }: DeleteOnlyDataDialogProps) => {
  const [startDate, setStartDate] = useState(new Date());
  const [endDate, setEndDate] = useState(new Date());
  const toUTCString = (date: Date) => moment(date).utc().format("YYYY-MM-DD");
  const onConfirm = () => {
    onDelete(toUTCString(startDate), toUTCString(endDate));
  }
  return (
    <Dialog open={open} handler={onClose}>
      <DialogHeader className="font-bold text-center justify-center">Delete all collected results for the time range:</DialogHeader>
      <DialogBody className="flex gap-6 items-center justify-center">
        <DatePicker
          showIcon
          placeholderText='Select date start'
          onChange={setStartDate}
          selected={startDate}
        />
        <span>to</span>
        <DatePicker
          showIcon
          placeholderText='Select date end'
          onChange={setEndDate}
          selected={endDate}
        />
      </DialogBody>
      <DialogFooter className="flex gap-6 items-center">
        <Button
          color="secondary"
          className="px-8"
          onClick={onClose}
          label="Cancel"
        />
        <Button
          color="primary"
          className="px-8"
          onClick={onConfirm}
          label="Delete"
        />
      </DialogFooter>
    </Dialog>
  )
}

export default DeleteOnlyDataDialog;