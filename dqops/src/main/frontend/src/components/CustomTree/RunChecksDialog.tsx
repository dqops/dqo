import {
  Dialog,
  DialogBody,
  DialogFooter,
  DialogHeader
} from '@material-tailwind/react';
import clsx from 'clsx';
import moment from 'moment';
import React, { useState } from 'react';
import { useSelector } from 'react-redux';
import {
  CheckSearchFilters,
  CheckSearchFiltersCheckTypeEnum,
  CheckSearchFiltersTimeScaleEnum,
  StatisticsCollectorSearchFiltersTargetEnum,
  TimeWindowFilterParameters
} from '../../api';
import { IRootState } from '../../redux/reducers';
import { RUN_CHECK_TIME_WINDOW_FILTERS } from '../../shared/constants';
import { CheckTypes } from '../../shared/routes';
import Button from '../Button';
import Checkbox from '../Checkbox';
import LabelsView from '../Connection/LabelsView';
import SectionWrapper from '../Dashboard/SectionWrapper';
import DatePicker from '../DatePicker';
import Input from '../Input';
import RadioButton from '../RadioButton';
import Select from '../Select';
import SelectInput from '../SelectInput';
import SvgIcon from '../SvgIcon';

type TRunChecksDialogProps = {
  checkType: CheckTypes;
  onClick: (
    filter: CheckSearchFilters,
    timeWindowFilter?: TimeWindowFilterParameters,
    collectErrorSample?: boolean
  ) => void;
  open: boolean;
  onClose: VoidFunction;
  runChecksJobTemplate: CheckSearchFilters;
};

export default function RunChecksDialog({
  checkType,
  onClick,
  onClose,
  open,
  runChecksJobTemplate
}: TRunChecksDialogProps) {
  const { userProfile } = useSelector((state: IRootState) => state.job || {});

  const [filters, setFilters] = useState<
    CheckSearchFilters & {
      collectErrorSample?: boolean;
      timeWindowFilter?: string;
    }
  >({
    fullTableName: '*.*',
    ...runChecksJobTemplate
  });
  const [timeWindowFilter, setTimeWindowFilter] = useState<
    TimeWindowFilterParameters | undefined
  >({
    from_date: moment().utc().subtract(7, 'days').format('YYYY-MM-DD'),
    to_date: moment().utc().format('YYYY-MM-DD')
  });
  const [timeWindowPartitioned, setTimeWindowPartitioned] = useState(true);
  const [additionalParams, setAdditionalParams] = useState(false);

  const onChangeFilters = (
    obj: Partial<
      CheckSearchFilters & {
        collectErrorSample?: boolean;
        timeWindowFilter?: string;
      }
    >
  ) => {
    setFilters((prev) => ({
      ...prev,
      ...obj
    }));
  };

  const onChangeTimeFilterWindow = (
    obj: Partial<TimeWindowFilterParameters>
  ) => {
    console.log(obj);
    setTimeWindowFilter((prev) => ({
      ...prev,
      ...obj
    }));
  };

  const prepareFilters = (filters: CheckSearchFilters) => {
    const copiedFilters = { ...filters };

    copiedFilters.labels = copiedFilters.labels?.filter((x) => x.length > 0);
    copiedFilters.tags = copiedFilters.tags?.filter((x) => x.length > 0);

    return copiedFilters;
  };

  const isDateValid = (date?: string) => {
    if (!date) return false;
    const regex = /^\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])$/;
    return regex.test(date);
  };

  const isSaveEnabled =
    checkType === CheckTypes.PARTITIONED
      ? isDateValid(timeWindowFilter?.to_date) &&
        isDateValid(timeWindowFilter?.from_date)
      : true;
  return (
    <Dialog
      open={open}
      handler={onClose}
      className="min-w-300 p-4 max-h-[96vh] overflow-y-auto overflow-x-hidden"
    >
      <DialogHeader className="font-bold text-center justify-center !py-2">
        Run checks
      </DialogHeader>
      <DialogBody className={clsx('text-sm flex flex-col overflow-y-auto')}>
        <div className="flex justify-between border-b py-4 border-gray-300 text-black">
          <div className="w-[45%] ml-2">
            Connection
            <Input
              value={filters.connection}
              onChange={(e) => onChangeFilters({ connection: e.target.value })}
              className="mt-2"
              placeholder="*"
            />
          </div>
          <div className="w-[45%] ml-2">
            Schema and table name
            <Input
              value={filters.fullTableName}
              onChange={(e) =>
                onChangeFilters({ fullTableName: e.target.value })
              }
              className="mt-2"
              placeholder="*"
            />
          </div>
          <div></div>
        </div>
        <div className="flex justify-between py-4 text-black items-center">
          <div className="w-1/3 ml-2">
            Column name
            <Input
              value={filters.column}
              onChange={(e) => onChangeFilters({ column: e.target.value })}
              className="mt-2"
              placeholder="*"
            />
          </div>
          <div className="w-1/3 ml-2">
            Column datatype
            <Input
              value={filters.columnDataType}
              onChange={(e) =>
                onChangeFilters({ columnDataType: e.target.value })
              }
              className="mt-2"
              placeholder="*"
            />
          </div>
          <div className="flex items-center gap-x-2 w-40 pl-5 mt-5">
            <Checkbox
              checked={!!filters.columnNullable}
              onChange={(value) => onChangeFilters({ columnNullable: value })}
              className="mr-4"
            />
            Column nullable
          </div>
          <div className="flex items-center gap-x-2 w-40 ml-4 mt-5">
            <Checkbox
              checked={!!filters.collectErrorSample}
              onChange={(value) =>
                onChangeFilters({ collectErrorSample: value })
              }
              className="mr-4"
            />
            Collect error samples
          </div>
        </div>
        {additionalParams === false ? (
          <div
            className={clsx(
              'flex items-center text-black mb-4 cursor-pointer',
              checkType === CheckTypes.PARTITIONED && 'mb-[62px]'
            )}
            onClick={() => setAdditionalParams(true)}
          >
            <SvgIcon name="chevron-right" className="w-5 h-5" />
            <span className="font-bold">Additional parameters</span>
          </div>
        ) : (
          <SectionWrapper
            title="Additional parameters"
            onClick={() => setAdditionalParams(false)}
            svgIcon={true}
            titleClassName="cursor-pointer"
          >
            <div className="flex justify-between py-4 text-black">
              <div className="w-1/4 ml-2">
                Check target
                <SelectInput
                  value={filters.checkTarget}
                  onChange={(value) => onChangeFilters({ checkTarget: value })}
                  options={[
                    { label: '', value: '' },
                    ...Object.keys(
                      StatisticsCollectorSearchFiltersTargetEnum
                    ).map((x) => ({ label: x, value: x }))
                  ]}
                  className="mt-2"
                />
              </div>
              <div className="w-1/4 ml-2">
                Check type
                <SelectInput
                  value={filters.checkType}
                  onChange={(value) => onChangeFilters({ checkType: value })}
                  options={[
                    { label: '', value: '' },
                    ...Object.keys(CheckSearchFiltersCheckTypeEnum).map(
                      (x) => ({
                        label: x,
                        value: x
                      })
                    )
                  ]}
                  className="mt-2"
                />
              </div>
              <div className="w-1/4 ml-2">
                Time scale
                <SelectInput
                  value={filters.timeScale}
                  onChange={(value) => onChangeFilters({ timeScale: value })}
                  options={[
                    { label: '', value: '' },
                    ...Object.keys(CheckSearchFiltersTimeScaleEnum).map(
                      (x) => ({
                        label: x,
                        value: x
                      })
                    )
                  ]}
                  className="mt-2"
                />
              </div>
            </div>
            <div className="flex justify-between pt-4 text-black">
              <div className="w-1/3 ml-2">
                Check name
                <Input
                  value={filters.checkName}
                  onChange={(e) =>
                    onChangeFilters({ checkName: e.target.value })
                  }
                  className="mt-2"
                />
              </div>
              <div className="w-1/3 ml-2">
                Sensor name
                <Input
                  value={filters.sensorName}
                  onChange={(e) =>
                    onChangeFilters({ sensorName: e.target.value })
                  }
                  className="mt-2"
                />
              </div>
              <div className="w-1/3 ml-2">
                Table comparison name
                <Input
                  value={filters.tableComparisonName}
                  onChange={(e) =>
                    onChangeFilters({ tableComparisonName: e.target.value })
                  }
                  className="mt-2"
                />
              </div>
            </div>
            <div className="flex justify-between pt-4 text-black">
              <LabelsView
                labels={filters.labels ?? []}
                onChange={(labels: string[]) =>
                  onChangeFilters({ labels: labels })
                }
                titleClassName="font-normal"
                className="!px-2"
              />
              <LabelsView
                labels={filters.tags ?? []}
                onChange={(tags: string[]) => onChangeFilters({ tags: tags })}
                title="Tags"
                titleClassName="font-normal"
              />
            </div>
            <div
              className={clsx(
                'text-sm text-black ml-2',
                checkType !== CheckTypes.PARTITIONED &&
                  'text-gray-300 cursor-not-allowed'
              )}
            >
              <div className="mb-3">Time window for partitioned checks</div>
              <div className="flex items-center pb-4 ">
                <div className="flex items-center">
                  <RadioButton
                    checked={timeWindowPartitioned}
                    onClick={() => {
                      setTimeWindowPartitioned(true);
                    }}
                    className={clsx(
                      'mr-5',
                      filters.checkType !== CheckTypes.PARTITIONED &&
                        'text-gray-500'
                    )}
                    disabled={filters.checkType !== CheckTypes.PARTITIONED}
                  />
                  <Select
                    options={[
                      ...Object.keys(RUN_CHECK_TIME_WINDOW_FILTERS).map(
                        (key) => ({ label: key, value: key })
                      )
                    ]}
                    value={filters.timeWindowFilter}
                    onChange={(value) =>
                      onChangeFilters({ timeWindowFilter: value })
                    }
                    // label="Time window for partitioned checks"
                    disabled={
                      filters.checkType !== CheckTypes.PARTITIONED ||
                      !timeWindowPartitioned
                    }
                    triggerClassName={clsx(
                      filters.checkType !== CheckTypes.PARTITIONED ||
                        !timeWindowPartitioned
                        ? 'text-gray-500'
                        : ''
                    )}
                    menuClassName="!top-9 !max-h-29 !z-50"
                  />
                </div>
              </div>
              <div className="flex items-center pb-4  text-sm">
                <RadioButton
                  checked={!timeWindowPartitioned}
                  onClick={() => setTimeWindowPartitioned(false)}
                  className="mr-6"
                  disabled={filters.checkType !== CheckTypes.PARTITIONED}
                />
                <div className="w-30 text-left">For the time range</div>
                <div className="mx-3">
                  <DatePicker
                    showIcon
                    placeholderText="Select date start"
                    selected={moment
                      .utc(timeWindowFilter?.from_date, 'YYYY-MM-DD')
                      .toDate()}
                    onChange={(e: any) =>
                      onChangeTimeFilterWindow({
                        from_date: moment(e).utc().format('YYYY-MM-DD')
                      })
                    }
                    className={clsx(
                      'border border-gray-300',
                      !isDateValid(timeWindowFilter?.from_date)
                        ? 'border border-red-500'
                        : ''
                    )}
                    dateFormat="yyyy-MM-dd"
                    disabled={
                      filters.checkType !== CheckTypes.PARTITIONED ||
                      timeWindowPartitioned
                    }
                  />
                </div>
                <div>to</div>
                <div className="w-[45%] ml-3">
                  <DatePicker
                    showIcon
                    placeholderText="Select to start"
                    selected={moment
                      .utc(timeWindowFilter?.to_date, 'YYYY-MM-DD')
                      .toDate()}
                    onChange={(e: any) =>
                      onChangeTimeFilterWindow({
                        to_date: moment(e).utc().format('YYYY-MM-DD')
                      })
                    }
                    className={clsx(
                      'border border-gray-300',
                      !isDateValid(timeWindowFilter?.to_date)
                        ? 'border border-red-500'
                        : ''
                    )}
                    disabled={
                      filters.checkType !== CheckTypes.PARTITIONED ||
                      timeWindowPartitioned
                    }
                    dateFormat="yyyy-MM-dd"
                  />
                </div>
                <div></div>
              </div>
            </div>
          </SectionWrapper>
        )}
      </DialogBody>
      <DialogFooter className="flex gap-6 items-center">
        <Button
          color="primary"
          variant="outlined"
          className="px-8"
          onClick={() => {
            onClose(), setFilters(runChecksJobTemplate);
          }}
          label="Cancel"
        />
        <Button
          color="primary"
          className="px-8"
          onClick={() => {
            isSaveEnabled
              ? (onClick(
                  prepareFilters(filters),
                  checkType === CheckTypes.PARTITIONED
                    ? timeWindowPartitioned
                      ? RUN_CHECK_TIME_WINDOW_FILTERS[
                          filters.timeWindowFilter ??
                            'Default incremental time window'
                        ] ?? undefined
                      : timeWindowFilter
                    : undefined,
                  filters.collectErrorSample
                ),
                setFilters(runChecksJobTemplate))
              : undefined;
          }}
          label="Run checks"
          disabled={userProfile.can_delete_data !== true}
        />
      </DialogFooter>
    </Dialog>
  );
}
