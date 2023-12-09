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
import { CheckTypes } from '../../shared/routes';
import { useParams } from 'react-router-dom';
import SelectInput from '../SelectInput';
import {
  CheckDefinitionFolderModel,
  DeleteStoredDataQueueJobParameters,
  SensorListModel
} from '../../api';
import { ChecksApi, SensorsApi } from '../../services/apiClient';
import Input from '../Input';

type DeleteOnlyDataDialogProps = {
  open: boolean;
  onClose: VoidFunction;
  onDelete: (params: DeleteStoredDataQueueJobParameters) => void;
  columnBool?: boolean;
  nameOfCol?: string;
  nodeId?: string;
};
const DeleteStoredDataExtendedPopUp = ({
  open,
  onClose,
  onDelete,
  nameOfCol,
  nodeId
}: DeleteOnlyDataDialogProps) => {
  const { checkTypes }: { checkTypes: CheckTypes } = useParams();
  const [startDate, setStartDate] = useState(new Date());
  const [endDate, setEndDate] = useState(new Date());
  const [mode, setMode] = useState('all');
  const [allSensors, setAllSensors] = useState<SensorListModel[]>([]);
  const [params, setParams] = useState<DeleteStoredDataQueueJobParameters>({
    deleteErrors: true,
    deleteStatistics: true,
    deleteCheckResults: true,
    deleteSensorReadouts: true,
    checkType: checkTypes === 'sources' ? 'profiling' : checkTypes
  });
  const [allChecks, setAllChecks] = useState<CheckDefinitionFolderModel>();
  const [filteredStatistics, setFilteredStatistics] = useState<
    'all' | 'part' | ''
  >('all');
  const [filteredChecks, setFilteredChecks] = useState<'all' | 'part' | ''>(
    'all'
  );
  const [filteredSensors, setFilteredSensors] = useState<'all' | 'part' | ''>(
    'all'
  );
  const { userProfile } = useSelector((state: IRootState) => state.job || {});

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

  const onChangeParams = (obj: Partial<DeleteStoredDataQueueJobParameters>) => {
    setParams({
      ...params,
      ...obj
    });
  };

  const isDisabled = useMemo(() => mode === 'all', [mode]);

  const getAllSensors = async () => {
    await SensorsApi.getAllSensors()
      .then((res) => setAllSensors(res.data))
      .catch((err) => console.error(err));
  };

  useEffect(() => {
    getAllSensors();
  }, []);

  const hierarchiArray = nodeId?.split('.');

  useEffect(() => {
    ChecksApi.getCheckFolderTree().then((res) => setAllChecks(res.data));
  }, []);

  const getCategories = (allChecks: CheckDefinitionFolderModel) => {
    const mainTableFolder =
      allChecks.folders?.table?.folders?.[
        params.checkType as keyof CheckDefinitionFolderModel
      ].folders;

    const mainColumnFolder =
      allChecks.folders?.column?.folders?.[
        params.checkType as keyof CheckDefinitionFolderModel
      ].folders;

    let categories = {};
    if (params.checkType !== CheckTypes.PROFILING) {
      if (params.timeGradient === 'monthly') {
        categories = mainTableFolder?.monthly.folders ?? {};
      } else {
        categories = mainTableFolder?.monthly.folders ?? {};
      }
    } else {
      categories = mainTableFolder ?? {};
    }
    return categories;
  };

  const getChecks = (
    categories:
      | {
          [key: string]: CheckDefinitionFolderModel;
        }
      | undefined
  ) => {
    return categories?.[
      params.checkCategory as keyof CheckDefinitionFolderModel
    ]?.checks;
  };

  return (
    <Dialog open={open} handler={onClose} className="min-w-300 p-4">
      <DialogHeader className="font-bold text-center justify-center">
        Delete data
      </DialogHeader>
      <DialogBody>
        <div className="flex flex-col">
          <div className="flex justify-between border-b pb-4 border-gray-300 text-black font-semibold">
            {hierarchiArray?.[0] && (
              <div> {'Connection: ' + hierarchiArray?.[0]} </div>
            )}
            {hierarchiArray?.[1] && (
              <div> {'Schema: ' + hierarchiArray?.[1]} </div>
            )}
            {hierarchiArray?.[2] && (
              <div> {'Table: ' + hierarchiArray?.[2]} </div>
            )}
            {hierarchiArray?.[4] && (
              <div> {'Column: ' + hierarchiArray?.[4]} </div>
            )}
          </div>
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
          <div className=" border-b pb-4 border-gray-300">
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
          </div>
          <div className="flex w-full gap-4 px-4 py-4 text-gray-700 pl-12 border-b border-gray-300">
            <SelectInput
              className="w-1/3"
              options={['', ...Object.values(CheckTypes)]
                .map((x) => ({ label: x, value: x }))
                .filter((_, index) => index !== 2)}
              label="CheckType (profiling, monitoring, partitioned)"
              value={params.checkType}
              onChange={(value) =>
                onChangeParams({
                  checkType: String(value).length !== 0 ? value : undefined
                })
              }
            />
            <SelectInput
              label="Time gradient (daily/monthly)"
              className="w-1/4"
              options={[
                { label: '', value: '' },
                { label: 'daily', value: 'daily' },
                { label: 'monthly', value: 'monthly' }
              ]}
              value={params.timeGradient}
              onChange={(value) =>
                onChangeParams({
                  timeGradient: String(value).length !== 0 ? value : undefined
                })
              }
              disabled={
                !(
                  params.checkType === 'partitioned' ||
                  params.checkType === 'monitoring'
                )
              }
            />
          </div>
        </div>
        <div className="flex w-full gap-4 px-4 my-4 text-gray-700 ml-7">
          <div className="flex flex-col space-y-5 w-1/4">
            <Checkbox
              checked={params.deleteStatistics && filteredStatistics === 'all'}
              onChange={(deleteStatistics) => {
                onChangeParams({ deleteStatistics }),
                  setFilteredStatistics(
                    filteredStatistics === 'all' ? '' : 'all'
                  );
              }}
              label="All basic statistics results"
              checkClassName="bg-teal-500"
            />
            <Checkbox
              checked={params.deleteStatistics && filteredStatistics === 'part'}
              onChange={(deleteStatistics) => {
                onChangeParams({ deleteStatistics }),
                  setFilteredStatistics(
                    filteredStatistics === 'part' ? '' : 'part'
                  );
              }}
              label="Filtered basic statistics results"
              checkClassName="bg-teal-500"
            />
            <Input
              label="Collector Category"
              value={params.collectorCategory}
              onChange={(e) =>
                onChangeParams({ collectorCategory: e.target.value })
              }
              disabled={filteredStatistics !== 'part'}
            />
            <Input
              label="Collector Name"
              value={params.collectorName}
              onChange={(e) =>
                onChangeParams({ collectorName: e.target.value })
              }
              disabled={filteredStatistics !== 'part'}
            />
            <Input
              label="Collector Target"
              value={params.collectorTarget}
              onChange={(e) =>
                onChangeParams({ collectorTarget: e.target.value })
              }
              disabled={filteredStatistics !== 'part'}
            />
          </div>
          <div className="flex flex-col space-y-5 w-1/4">
            <Checkbox
              checked={params.deleteCheckResults && filteredChecks === 'all'}
              onChange={(deleteCheckResults) => {
                onChangeParams({ deleteCheckResults }),
                  setFilteredChecks(filteredChecks === 'all' ? '' : 'all');
              }}
              label="All check results"
              checkClassName="bg-teal-500"
            />
            <Checkbox
              checked={params.deleteCheckResults && filteredChecks === 'part'}
              onChange={(deleteCheckResults) => {
                onChangeParams({ deleteCheckResults }),
                  setFilteredChecks(filteredChecks === 'part' ? '' : 'part');
              }}
              label="Filtered check results"
              checkClassName="bg-teal-500"
            />
            <SelectInput
              label="Check category"
              options={Object.keys(getCategories(allChecks ?? {}))?.map(
                (item: any) => ({
                  label: item,
                  value: item
                })
              )}
              value={params.checkCategory}
              onChange={(value) => onChangeParams({ checkCategory: value })}
              disabled={filteredChecks !== 'part'}
            />
            <SelectInput
              label="Check name"
              options={(getChecks(getCategories(allChecks ?? {})) ?? [])?.map(
                (item: any) => ({
                  label: item.check_name,
                  value: item.check_name
                })
              )}
              value={params.checkName}
              onChange={(value) => onChangeParams({ checkName: value })}
              disabled={filteredChecks !== 'part'}
            />
          </div>
          <div className="flex flex-col space-y-5 w-1/4">
            <Checkbox
              checked={params.deleteSensorReadouts && filteredSensors === 'all'}
              onChange={(deleteSensorReadouts) => {
                onChangeParams({ deleteSensorReadouts }),
                  setFilteredSensors(filteredSensors === 'all' ? '' : 'all');
              }}
              label="All sensor readouts"
              checkClassName="bg-teal-500"
            />
            <Checkbox
              checked={
                params.deleteSensorReadouts && filteredSensors === 'part'
              }
              onChange={(deleteSensorReadouts) => {
                onChangeParams({ deleteSensorReadouts }),
                  setFilteredSensors(filteredSensors === 'part' ? '' : 'part');
              }}
              label="Filtered sensor readout"
              checkClassName="bg-teal-500"
            />
            <SelectInput
              label="Sensor name"
              options={allSensors?.map((x: any) => ({
                label: x.full_sensor_name,
                value: x.full_sensor_name ?? ''
              }))}
              value={params.sensorName}
              onChange={(value) => onChangeParams({ sensorName: value })}
              disabled={filteredSensors !== 'part'}
            />
          </div>
          <div className="w-1/4 flex items-start">
            <Checkbox
              checked={params.deleteErrors}
              onChange={(deleteErrors) => onChangeParams({ deleteErrors })}
              label="All execution errors"
              checkClassName="bg-teal-500"
            />
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
          disabled={userProfile.can_delete_data !== true}
        />
      </DialogFooter>
    </Dialog>
  );
};

export default DeleteStoredDataExtendedPopUp;
