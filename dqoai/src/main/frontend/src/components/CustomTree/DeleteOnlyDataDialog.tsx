import {
  Dialog,
  DialogBody,
  DialogFooter,
  DialogHeader,
  Radio
} from '@material-tailwind/react';
import DatePicker from '../DatePicker';
import Button from '../Button';
import React, { useEffect, useMemo, useState } from 'react';
import moment from 'moment';
import Checkbox from '../Checkbox';
import { JobApiClient } from '../../services/apiClient';
import { CustomTreeNode } from '../../shared/interfaces';
import { CheckTypes } from '../../shared/routes';

type DeleteOnlyDataDialogProps = {
  open: boolean;
  onClose: VoidFunction;
  onDelete: (
    params: { [key: string]: string | boolean },
    myArr?: string[]
  ) => void;
  columnBool?: boolean;
  nameOfCol?: string;
};
const DeleteOnlyDataDialog = ({
  open,
  onClose,
  onDelete,
  columnBool,
  nameOfCol
}: DeleteOnlyDataDialogProps) => {
  const [startDate, setStartDate] = useState(new Date());
  const [endDate, setEndDate] = useState(new Date());
  const [mode, setMode] = useState('all');
  const [arrayOfCol, setArrayOfCol] = useState<string[]>([]);
  const [params, setParams] = useState({
    deleteErrors: true,
    deleteProfilingResults: true,
    deleteRuleResults: true,
    deleteSensorReadouts: true
  });

  // const deleteStoredData2 = async (node: CustomTreeNode, params: { [key: string]: string | boolean }) => {
  //   if (node.data_clean_job_template) {

  //     JobApiClient.deleteStoredData({
  //       ...node.data_clean_job_template,
  //       ...node.data_clean_job_template.columnNames = myArr,
  //       ...params,
  //     });
  //     return;
  //   }
  // };

  const myArr: string[] = [];
  useEffect(() => {
    if (nameOfCol && nameOfCol?.length > 0) {
      myArr.push(nameOfCol);
    }
  }, [myArr]);

  // console.log(myArr);
  // console.log(columnBool);
  const toUTCString = (date: Date) => moment(date).utc().format('YYYY-MM-DD');
  const onConfirm = () => {
    if (mode === 'all') {
      onDelete({});
      console.log('if');
    }
    if (myArr.length === 1) {
      console.log('delete column data');
      console.log(myArr);
      onDelete({}, myArr);
    } else {
      onDelete({
        ...params,
        dateStart: toUTCString(startDate),
        dateEnd: toUTCString(endDate)
      });
      console.log('else');
    }
  };

  const onChangeParams = (obj: { [key: string]: boolean }) => {
    setMode('part');
    setParams({
      ...params,
      ...obj
    });
  };

  const isDisabled = useMemo(() => mode === 'all', [mode]);

  return (
    <Dialog open={open} handler={onClose} className="min-w-200 p-4">
      <DialogHeader className="font-bold text-center justify-center">
        Delete data
      </DialogHeader>
      <DialogBody>
        <div className="flex flex-col">
          <div>
            <Radio
              id="all"
              name="delete_mode"
              value="all"
              label="All"
              checked={mode === 'all'}
              onChange={() => setMode('all')}
              className="outline-none"
              color="teal"
            />
          </div>
          <div>
            <div className="flex items-start text-gray-700">
              <Radio
                id="part"
                name="delete_mode"
                value="part"
                checked={mode === 'part'}
                onChange={() => setMode('part')}
                color="teal"
              />
              <div className="mt-2">
                <p>For the time range:</p>
                <div className="flex space-x-6 items-center">
                  <DatePicker
                    showIcon
                    placeholderText="Select date start"
                    onChange={setStartDate}
                    selected={startDate}
                    disabled={isDisabled}
                    dateFormat="yyyy-MM-dd"
                  />
                  <span>to</span>
                  <DatePicker
                    showIcon
                    placeholderText="Select date end"
                    onChange={setEndDate}
                    selected={endDate}
                    disabled={isDisabled}
                    dateFormat="yyyy-MM-dd"
                  />
                </div>
              </div>
            </div>
            <div className="flex flex-col gap-4 px-4 my-4 text-gray-700 ml-7">
              <Checkbox
                checked={params.deleteProfilingResults}
                onChange={(deleteProfilingResults) =>
                  onChangeParams({ deleteProfilingResults })
                }
                label="Basic statistics results"
                checkClassName="bg-teal-500"
              />
              <Checkbox
                checked={params.deleteRuleResults}
                onChange={(deleteRuleResults) =>
                  onChangeParams({ deleteRuleResults })
                }
                label="Check results"
                checkClassName="bg-teal-500"
              />
              <Checkbox
                checked={params.deleteSensorReadouts}
                onChange={(deleteSensorReadouts) =>
                  onChangeParams({ deleteSensorReadouts })
                }
                label="Sensor readouts"
                checkClassName="bg-teal-500"
              />
              <Checkbox
                checked={params.deleteErrors}
                onChange={(deleteErrors) => onChangeParams({ deleteErrors })}
                label="Execution errors"
                checkClassName="bg-teal-500"
              />
            </div>
          </div>
        </div>
      </DialogBody>
      <DialogFooter className="flex gap-6 items-center mt-10">
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
          onClick={onConfirm}
          label="Delete"
        />
      </DialogFooter>
    </Dialog>
  );
};

export default DeleteOnlyDataDialog;
