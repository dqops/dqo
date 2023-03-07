import { Dialog, DialogBody, DialogFooter, DialogHeader, Radio } from "@material-tailwind/react";
import DatePicker from "../DatePicker";
import Button from "../Button";
import React, { useMemo, useState } from "react";
import moment from "moment";
import Checkbox from "../Checkbox";

type DeleteOnlyDataDialogProps = {
  open: boolean;
  onClose: VoidFunction;
  onDelete: (params: { [key: string]: string | boolean }) => void;
}
const DeleteOnlyDataDialog = ({ open, onClose, onDelete }: DeleteOnlyDataDialogProps) => {
  const [startDate, setStartDate] = useState(new Date());
  const [endDate, setEndDate] = useState(new Date());
  const [mode, setMode] = useState('all');
  const [params, setParams] = useState({
    deleteErrors: true,
    deleteProfilingResults: true,
    deleteRuleResults: true,
    deleteSensorReadouts: true,
  });

  const toUTCString = (date: Date) => moment(date).utc().format("YYYY-MM-DD");
  const onConfirm = () => {
    if (mode === 'all') {
      onDelete({});
    } else {
      onDelete({
        ...params,
        dateStart: toUTCString(startDate),
        dateEnd: toUTCString(endDate),
      });
    }
  }

  const onChangeParams = (obj: { [key: string]: boolean }) => {
    setParams({
      ...params,
      ...obj,
    });
  };

  const isDisabled = useMemo(() => mode === 'all', [mode]);

  return (
    <Dialog open={open} handler={onClose} className="min-w-200 p-4">
      <DialogHeader className="font-bold text-center justify-center">Delete all collected results for the time range:</DialogHeader>
      <DialogBody>
        <Radio
          id="all"
          name="delete_mode"
          value="all"
          label="All"
          checked={mode === 'all'}
          onChange={(e) => setMode('all')}
          className="outline-none"
          color="teal"
        />
        <div>
          <div className="flex mt-4 items-center">
            <Radio
              id="part"
              name="delete_mode"
              value="part"
              checked={mode === 'part'}
              onChange={(e) => setMode('part')}
              color="teal"
            />
            <div className="flex space-x-6 items-center">
              <DatePicker
                showIcon
                placeholderText='Select date start'
                onChange={setStartDate}
                selected={startDate}
                disabled={isDisabled}
              />
              <span>to</span>
              <DatePicker
                showIcon
                placeholderText='Select date end'
                onChange={setEndDate}
                selected={endDate}
                disabled={isDisabled}
              />
            </div>
          </div>
          <div className="flex flex-col gap-4 px-4 my-4">
            <Checkbox
              checked={params.deleteErrors}
              onChange={(deleteErrors) => onChangeParams({ deleteErrors })}
              label="Error"
              disabled={isDisabled}
              checkClassName="bg-teal-500"
            />
            <Checkbox
              checked={params.deleteProfilingResults}
              onChange={(deleteProfilingResults) => onChangeParams({ deleteProfilingResults })}
              label="Profiling Results"
              disabled={isDisabled}
              checkClassName="bg-teal-500"
            />
            <Checkbox
              checked={params.deleteRuleResults}
              onChange={(deleteRuleResults) => onChangeParams({ deleteRuleResults })}
              label="Rule Results"
              disabled={isDisabled}
              checkClassName="bg-teal-500"
            />
            <Checkbox
              checked={params.deleteSensorReadouts}
              onChange={(deleteSensorReadouts) => onChangeParams({ deleteSensorReadouts })}
              label="Sensor Readouts"
              disabled={isDisabled}
              checkClassName="bg-teal-500"
            />
          </div>
        </div>
      </DialogBody>
      <DialogFooter className="flex gap-6 items-center mt-10">
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