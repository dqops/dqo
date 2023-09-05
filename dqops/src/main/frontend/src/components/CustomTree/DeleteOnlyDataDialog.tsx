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
import { useSelector } from 'react-redux';
import { IRootState } from '../../redux/reducers';

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
  nameOfCol
}: DeleteOnlyDataDialogProps) => {
  const [startDate, setStartDate] = useState(new Date());
  const [endDate, setEndDate] = useState(new Date());
  const [mode, setMode] = useState('all');
  const [params, setParams] = useState({
    deleteErrors: true,
    deleteStatistics: true,
    deleteCheckResults: true,
    deleteSensorReadouts: true
  });
  const { userProfile } = useSelector(
    (state: IRootState) => state.job || {}
  );

  const myArr: string[] = [];
  useEffect(() => {
    if (nameOfCol && nameOfCol?.length > 0) {
      myArr.push(nameOfCol);
    }
  }, [myArr]);

  const toUTCString = (date: Date) => moment(date).utc().format('YYYY-MM-DD');
  const onConfirm = () => {
    if (mode === 'part') {
      onDelete({
        ...params,
        dateStart: toUTCString(startDate),
        dateEnd: toUTCString(endDate)
      });
    } else {
      onDelete({ ...params });
    }
  };

  const onChangeParams = (obj: { [key: string]: boolean }) => {
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
                checked={params.deleteStatistics}
                onChange={(deleteStatistics) =>
                  onChangeParams({ deleteStatistics })
                }
                label="Basic statistics results"
                checkClassName="bg-teal-500"
              />
              <Checkbox
                checked={params.deleteCheckResults}
                onChange={(deleteCheckResults) =>
                  onChangeParams({ deleteCheckResults })
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
          disabled={userProfile.can_delete_data === false}
        />
      </DialogFooter>
    </Dialog>
  );
};

export default DeleteOnlyDataDialog;
