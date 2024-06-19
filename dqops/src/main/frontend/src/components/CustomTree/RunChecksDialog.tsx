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
import { CheckTypes } from '../../shared/routes';
import Button from '../Button';
import CheckboxThreeSteps from '../CheckBoxThreeSteps';
import LabelsView from '../Connection/LabelsView';
import SectionWrapper from '../Dashboard/SectionWrapper';
import Input from '../Input';
import SelectInput from '../SelectInput';
import SvgIcon from '../SvgIcon';
type TRunChecksDialogProps = {
  checkType: CheckTypes;
  onClick: (
    filter: CheckSearchFilters,
    timeWindowFilter?: TimeWindowFilterParameters
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

  const [filters, setFilters] = useState<CheckSearchFilters>({
    fullTableName: '*.*',
    ...runChecksJobTemplate
  });
  const [timeWindowFilter, setTimeWindowFilter] = useState<
    TimeWindowFilterParameters | undefined
  >({
    from_date: moment().utc().subtract(7, 'days').format('YYYY-MM-DD'),
    to_date: moment().utc().format('YYYY-MM-DD')
  });

  const [additionalParams, setAdditionalParams] = useState(false);

  const onChangeFilters = (obj: Partial<CheckSearchFilters>) => {
    setFilters((prev) => ({
      ...prev,
      ...obj
    }));
  };

  const onChangeTimeFilterWindow = (
    obj: Partial<TimeWindowFilterParameters>
  ) => {
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
    <Dialog open={open} handler={onClose} className="min-w-300 p-4">
      <DialogHeader className="font-bold text-center justify-center">
        Run checks
      </DialogHeader>
      <DialogBody className="text-sm flex flex-col mb-20">
        {checkType === CheckTypes.PARTITIONED && (
          <div className="flex justify-between border-b pb-4 border-gray-300 text-black  ">
            <div className="w-[45%] ml-2">
              From
              <Input
                value={timeWindowFilter?.from_date}
                onChange={(e) =>
                  onChangeTimeFilterWindow({ from_date: e.target.value })
                }
                className={clsx(
                  'mt-2',
                  !isDateValid(timeWindowFilter?.from_date)
                    ? 'border border-red-500'
                    : ''
                )}
                placeholder="*"
              />
            </div>
            <div className="w-[45%] ml-2">
              To
              <Input
                value={timeWindowFilter?.to_date}
                onChange={(e) =>
                  onChangeTimeFilterWindow({ to_date: e.target.value })
                }
                className={clsx(
                  'mt-2',
                  !isDateValid(timeWindowFilter?.to_date)
                    ? 'border border-red-500'
                    : ''
                )}
                placeholder="*"
              />
            </div>
            <div></div>
          </div>
        )}
        <div className="flex justify-between border-b py-4 border-gray-300 text-black  ">
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
        <div className="flex justify-between py-4 text-black   items-center">
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
          <div className="flex items-center gap-x-2 w-1/3 ml-4">
            Column nullable
            <CheckboxThreeSteps
              checked={!!filters.columnNullable}
              onChange={(value) => onChangeFilters({ columnNullable: value })}
            />
          </div>
        </div>
        {additionalParams === false ? (
          <div
            className="flex items-center text-black mb-4 cursor-default"
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
            className="cursor-default"
          >
            <div className="flex justify-between py-4 text-black  ">
              <div className="w-1/3 ml-2">
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
              <div className="w-1/3 ml-2">
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
              <div className="w-1/3 ml-2">
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
            <div className="flex justify-between pt-4 text-black  ">
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
            <div className="flex justify-between pt-4 text-black  ">
              <LabelsView
                labels={filters.labels ?? []}
                onChange={(labels: string[]) =>
                  onChangeFilters({ labels: labels })
                }
                titleClassName="font-normal"
              />
              <LabelsView
                labels={filters.tags ?? []}
                onChange={(tags: string[]) => onChangeFilters({ tags: tags })}
                title="Tags"
                titleClassName="font-normal"
              />
            </div>
          </SectionWrapper>
        )}
      </DialogBody>
      <DialogFooter className="flex gap-6 items-center absolute bottom-5 right-5">
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
              ? (onClick(prepareFilters(filters), timeWindowFilter),
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
