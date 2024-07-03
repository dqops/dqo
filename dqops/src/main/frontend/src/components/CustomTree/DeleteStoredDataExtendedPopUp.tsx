import {
  Dialog,
  DialogBody,
  DialogFooter,
  DialogHeader,
  Radio
} from '@material-tailwind/react';
import moment from 'moment';
import React, { useCallback, useEffect, useMemo, useState } from 'react';
import { useSelector } from 'react-redux';
import {
  CheckDefinitionFolderModel,
  DeleteStoredDataQueueJobParameters,
  SensorListModel
} from '../../api';
import { IRootState } from '../../redux/reducers';
import { ChecksApi, SensorsApi } from '../../services/apiClient';
import { CheckTypes } from '../../shared/routes';
import { urlencodeEncoder, useDecodedParams } from '../../utils';
import Button from '../Button';
import Checkbox from '../Checkbox';
import SectionWrapper from '../Dashboard/SectionWrapper';
import DatePicker from '../DatePicker';
import Input from '../Input';
import SelectInput from '../SelectInput';
import SvgIcon from '../SvgIcon';

type DeleteOnlyDataDialogProps = {
  open: boolean;
  onClose: VoidFunction;
  onDelete: (params: DeleteStoredDataQueueJobParameters) => void;
  columnBool?: boolean;
  nameOfCol?: string;
  nodeId?: string;
};

type TParams = DeleteStoredDataQueueJobParameters & { schema?: string } & {
  table?: string;
};
const DeleteStoredDataExtendedPopUp = ({
  open,
  onClose,
  onDelete,
  nodeId
}: DeleteOnlyDataDialogProps) => {
  const hierarchiArray = nodeId?.split('.');

  const { checkTypes }: { checkTypes: CheckTypes } = useDecodedParams();
  const [startDate, setStartDate] = useState(new Date());
  const [endDate, setEndDate] = useState(new Date());
  const [mode, setMode] = useState('all');
  const [allSensors, setAllSensors] = useState<SensorListModel[]>([]);
  const [params, setParams] = useState<TParams>({
    connection: urlencodeEncoder(hierarchiArray?.[0]),
    schema: urlencodeEncoder(hierarchiArray?.[1]),
    table: urlencodeEncoder(hierarchiArray?.[2]),
    columnNames: urlencodeEncoder(hierarchiArray?.[4])
      ? [urlencodeEncoder(hierarchiArray?.[4])]
      : [],
    deleteErrors: true,
    deleteStatistics: true,
    deleteCheckResults: true,
    deleteSensorReadouts: true,
    deleteErrorSamples: true,
    checkType: checkTypes === 'sources' ? undefined : checkTypes
  });

  const [allChecks, setAllChecks] = useState<CheckDefinitionFolderModel>();
  const [basicStatisticsFiltersOpen, setBasicStatisticsFiltersOpen] =
    useState(true);
  const { userProfile } = useSelector((state: IRootState) => state.job || {});

  const toUTCString = (date: Date) => moment(date).utc().format('YYYY-MM-DD');
  const onConfirm = () => {
    const { schema, table, ...restParams } = params;
    const newParams: DeleteStoredDataQueueJobParameters = {
      ...restParams,
      fullTableName: (schema || table) ? `${schema}.${table}` : undefined
    };
    if (mode === 'part') {
      onDelete({
        ...newParams,
        dateStart: toUTCString(startDate),
        dateEnd: toUTCString(endDate)
      });
    } else {
      onDelete({ ...newParams });
    }
  };

  const onChangeParams = (obj: Partial<TParams>) => {
    setParams({
      ...params,
      ...obj
    });
  };

  const isDisabled = useMemo(() => mode === 'all', [mode]);

  const fetchData = useCallback(async () => {
    try {
      const [sensorsResponse, checksResponse] = await Promise.all([
        SensorsApi.getAllSensors(),
        ChecksApi.getCheckFolderTree()
      ]);

      setAllSensors(sensorsResponse.data);
      setAllChecks(checksResponse.data);
    } catch (error) {
      console.error('Error fetching data:', error);
    }
  }, [open]);

  useEffect(() => {
    if (open === true) {
      fetchData();
    }
  }, [fetchData]);

  const getCategories = (allChecks: CheckDefinitionFolderModel) => {
    const mainTableFolder =
      allChecks.folders?.table?.folders?.[
        (params.checkType ??
          CheckTypes.PROFILING) as keyof CheckDefinitionFolderModel
      ]?.folders;

    const mainColumnFolder =
      allChecks.folders?.column?.folders?.[
        (params.checkType ??
          CheckTypes.PROFILING) as keyof CheckDefinitionFolderModel
      ]?.folders;

    let tableCategories = {};
    let columnCategories = {};
    if (
      params.checkType === CheckTypes.MONITORING ||
      params.checkType === CheckTypes.PARTITIONED
    ) {
      if (params.timeGradient === 'monthly') {
        tableCategories = mainTableFolder?.monthly?.folders ?? {};
        columnCategories = mainColumnFolder?.monthly?.folders ?? {};
      } else {
        tableCategories = mainTableFolder?.daily?.folders ?? {};
        columnCategories = mainColumnFolder?.daily?.folders ?? {};
      }
    } else {
      tableCategories = mainTableFolder ?? {};
      columnCategories = mainColumnFolder ?? {};
    }
    return hierarchiArray?.[4]
      ? columnCategories
      : { ...columnCategories, ...tableCategories };
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
    <Dialog open={open} handler={onClose} className="min-w-300 p-4 pb-0">
      <DialogHeader className="font-bold text-center justify-center !py-0.5">
        Delete data quality results
      </DialogHeader>
      <DialogBody className="text-sm">
        <div className="flex flex-col">
          <div className="flex justify-between border-b pb-4 border-gray-300 text-black">
            <div>
              {' '}
              <Input
                label="Connection"
                onChange={(e) => onChangeParams({ connection: e.target.value })}
                value={params.connection}
              />{' '}
            </div>
            <div>
              {' '}
              <Input
                label="Schema"
                onChange={(e) => onChangeParams({ schema: e.target.value })}
                value={params.schema}
              />{' '}
            </div>
            <div>
              {' '}
              <Input
                label="Table"
                onChange={(e) => onChangeParams({ table: e.target.value })}
                value={params.table}
              />{' '}
            </div>
            <div>
              {' '}
              <Input
                label="Column"
                onChange={(e) =>
                  onChangeParams({ columnNames: [e.target.value] })
                }
                value={params.columnNames?.[0]}
              />{' '}
            </div>
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
                <p>For the time range</p>
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
              options={['All check types', ...Object.values(CheckTypes)]
                .map((x) => ({ label: x, value: x }))
                .filter((_, index) => index !== 2)}
              label="Check type (profiling, monitoring, partitioned)"
              value={
                params.checkType !== undefined
                  ? params.checkType
                  : 'All check types'
              }
              onChange={(value) =>
                onChangeParams({
                  checkType:
                    String(value) !== 'All check types' ? value : undefined,
                  checkCategory: undefined,
                  checkName: undefined
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
                  timeGradient: String(value).length !== 0 ? value : undefined,
                  checkCategory: undefined,
                  checkName: undefined
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
              checked={params.deleteStatistics}
              onChange={(deleteStatistics) => {
                onChangeParams({ deleteStatistics });
              }}
              label="Basic statistics results"
              checkClassName="bg-teal-500"
            />
            {basicStatisticsFiltersOpen ? (
              <SectionWrapper
                svgIcon={true}
                title={'Basic statistics filters'}
                onClick={() => setBasicStatisticsFiltersOpen(false)}
                className="flex flex-col gap-y-2"
                titleClassName={
                  params.deleteStatistics
                    ? '!font-normal '
                    : '!font-normal  !text-gray-150'
                }
              >
                <Input
                  label="Collector Category"
                  value={params.collectorCategory}
                  onChange={(e) =>
                    onChangeParams({ collectorCategory: e.target.value })
                  }
                  className={
                    params.deleteStatistics ? 'text-black' : 'text-gray-150'
                  }
                  labelClassName={
                    params.deleteStatistics ? 'text-black' : 'text-gray-150'
                  }
                />
                <Input
                  label="Collector Name"
                  value={params.collectorName}
                  onChange={(e) =>
                    onChangeParams({ collectorName: e.target.value })
                  }
                  className={
                    params.deleteStatistics ? 'text-black' : 'text-gray-150'
                  }
                  labelClassName={
                    params.deleteStatistics ? 'text-black' : 'text-gray-150'
                  }
                />
                <Input
                  label="Collector Target"
                  value={params.collectorTarget}
                  onChange={(e) =>
                    onChangeParams({ collectorTarget: e.target.value })
                  }
                  className={
                    params.deleteStatistics ? 'text-black' : 'text-gray-150'
                  }
                  labelClassName={
                    params.deleteStatistics ? 'text-black' : 'text-gray-150'
                  }
                />
              </SectionWrapper>
            ) : (
              <div
                className="flex items-center cursor-pointer"
                onClick={() => setBasicStatisticsFiltersOpen(true)}
              >
                <SvgIcon name="chevron-down" className="w-5 h-5" />
                <span>Basic statistics filters</span>
              </div>
            )}
          </div>
          <div className="flex flex-col space-y-5 w-1/4">
            <Checkbox
              checked={params.deleteCheckResults}
              onChange={(deleteCheckResults) =>
                onChangeParams({ deleteCheckResults })
              }
              label="Check results"
              checkClassName="bg-teal-500"
            />
            <SelectInput
              label="Check category"
              options={[
                '',
                ...Object.keys(getCategories(allChecks ?? {}))
              ]?.map((item: any) => ({
                label: item,
                value: item
              }))}
              value={params.checkCategory}
              onChange={(value) =>
                onChangeParams({
                  checkCategory: String(value).length !== 0 ? value : undefined,
                  checkName: undefined
                })
              }
            />
            <SelectInput
              label="Check name"
              options={[
                '',
                ...(getChecks(getCategories(allChecks ?? {})) ?? [])
              ]?.map((item: any) => ({
                label: item.check_name,
                value: item.check_name
              }))}
              value={params.checkName}
              onChange={(value) =>
                onChangeParams({
                  checkName: String(value).length !== 0 ? value : undefined
                })
              }
            />
          </div>
          <div className="flex flex-col space-y-5 w-1/4">
            <Checkbox
              checked={params.deleteSensorReadouts}
              onChange={(deleteSensorReadouts) => {
                onChangeParams({ deleteSensorReadouts });
              }}
              label="Sensor readouts"
              checkClassName="bg-teal-500"
            />
            <SelectInput
              label="Sensor name"
              options={['', ...allSensors]?.map((x: any) => ({
                label: x.full_sensor_name,
                value: x.full_sensor_name ?? ''
              }))}
              value={params.sensorName}
              onChange={(value) =>
                onChangeParams({
                  sensorName: String(value).length !== 0 ? value : undefined
                })
              }
            />
          </div>
          <div className="w-1/4 flex flex-col items-start">
            <div>
              <Checkbox
                checked={params.deleteErrors}
                onChange={(deleteErrors) => onChangeParams({ deleteErrors })}
                label="Execution errors"
                checkClassName="bg-teal-500"
              />
            </div>
            <div>
              <Checkbox
                checked={params.deleteErrorSamples}
                onChange={(deleteErrorSamples) => onChangeParams({ deleteErrorSamples })}
                label="Error samples"
                checkClassName="bg-teal-500"
              />
            </div>
          </div>
        </div>
      </DialogBody>
      <DialogFooter className="flex gap-6 items-center absolute bottom-6 right-5">
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
