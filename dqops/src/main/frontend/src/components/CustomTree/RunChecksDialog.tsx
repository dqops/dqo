import {
  Dialog,
  DialogBody,
  DialogFooter,
  DialogHeader
} from '@material-tailwind/react';
import React, { useState } from 'react';
import Button from '../Button';
import {
  CheckSearchFilters,
  CheckSearchFiltersCheckTypeEnum,
  CheckSearchFiltersTimeScaleEnum,
  StatisticsCollectorSearchFiltersTargetEnum
} from '../../api';
import LabelsView from '../Connection/LabelsView';
import Input from '../Input';
import SelectInput from '../SelectInput';
import CheckboxThreeSteps from '../CheckBoxThreeSteps';
import { useSelector } from 'react-redux';
import { IRootState } from '../../redux/reducers';
type TRunChecksDialogProps = {
  onClick: (filter: CheckSearchFilters) => void;
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
  const hierarchiArray = nodeId?.split('.');
  const { userProfile } = useSelector((state: IRootState) => state.job || {});

  const defaultFilters = {
    connection: hierarchiArray?.[0],
    fullTableName:
      hierarchiArray?.[1] &&
      hierarchiArray?.[2] &&
      hierarchiArray?.[1] + '.' + hierarchiArray?.[2],
    column: hierarchiArray?.[4],
    enabled: true
  };
  const [filters, setFilters] = useState<CheckSearchFilters>(defaultFilters);

  const onChangeFilters = (obj: Partial<CheckSearchFilters>) => {
    setFilters((prev) => ({
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

  return (
    <Dialog open={open} handler={onClose} className="min-w-300 p-4">
      <DialogHeader className="font-bold text-center justify-center">
        Run checks
      </DialogHeader>
      <DialogBody className="text-sm flex flex-col mb-20">
        <div className="flex justify-between border-b pb-4 border-gray-300 text-black font-semibold">
          <div className="w-1/4">
            Connection:
            <Input
              value={filters.connection}
              onChange={(e) => onChangeFilters({ connection: e.target.value })}
              className="mt-2"
            />
          </div>
          <div className="w-1/4">
            fullTableName:
            <Input
              value={filters.fullTableName}
              onChange={(e) =>
                onChangeFilters({ fullTableName: e.target.value })
              }
              className="mt-2"
            />
          </div>
          <div></div>
        </div>
        <div className="flex justify-between py-4 text-black font-semibold">
          <div className="w-1/4">
            Column name:
            <Input
              value={filters.column}
              onChange={(e) => onChangeFilters({ column: e.target.value })}
              className="mt-2"
            />
          </div>
          <div className="w-1/4">
            Column datatype:
            <Input
              value={filters.columnDataType}
              onChange={(e) =>
                onChangeFilters({ columnDataType: e.target.value })
              }
              className="mt-2"
            />
          </div>
          <div></div>
        </div>
        <div className="flex justify-between py-4 text-black font-semibold">
          <div className="flex items-center gap-x-2">
            Enabled:
            <CheckboxThreeSteps
              checked={!!filters.enabled}
              onChange={(value) => onChangeFilters({ enabled: value })}
            />
          </div>
          <div className="flex items-center gap-x-2">
            Column nullable:
            <CheckboxThreeSteps
              checked={!!filters.columnNullable}
              onChange={(value) => onChangeFilters({ columnNullable: value })}
            />
          </div>
          <div></div>
        </div>
        <div className="flex justify-between py-4 text-black font-semibold">
          <div className="w-1/4">
            Check target:
            <SelectInput
              value={filters.checkTarget}
              onChange={(value) => onChangeFilters({ checkTarget: value })}
              options={[
                { label: '', value: '' },
                ...Object.keys(StatisticsCollectorSearchFiltersTargetEnum).map(
                  (x) => ({ label: x, value: x })
                )
              ]}
              className="mt-2"
            />
          </div>
          <div className="w-1/4">
            Check type:
            <SelectInput
              value={filters.checkType}
              onChange={(value) => onChangeFilters({ checkType: value })}
              options={[
                { label: '', value: '' },
                ...Object.keys(CheckSearchFiltersCheckTypeEnum).map((x) => ({
                  label: x,
                  value: x
                }))
              ]}
              className="mt-2"
            />
          </div>
          <div className="w-1/4">
            Time scale:
            <SelectInput
              value={filters.timeScale}
              onChange={(value) => onChangeFilters({ timeScale: value })}
              options={[
                { label: '', value: '' },
                ...Object.keys(CheckSearchFiltersTimeScaleEnum).map((x) => ({
                  label: x,
                  value: x
                }))
              ]}
              className="mt-2"
            />
          </div>
        </div>
        <div className="flex justify-between pt-4 text-black font-semibold">
          <div className="w-1/4">
            Check name:
            <Input
              value={filters.checkName}
              onChange={(e) => onChangeFilters({ checkName: e.target.value })}
              className="mt-2"
            />
          </div>
          <div className="w-1/4">
            Table comparison name:
            <Input
              value={filters.tableComparisonName}
              onChange={(e) =>
                onChangeFilters({ tableComparisonName: e.target.value })
              }
              className="mt-2"
            />
          </div>
          <div className="w-1/4">
            Sensor name:
            <Input
              value={filters.sensorName}
              onChange={(e) => onChangeFilters({ sensorName: e.target.value })}
              className="mt-2"
            />
          </div>
        </div>
        <div className="flex justify-between pt-4 text-black font-semibold">
          <LabelsView
            labels={filters.labels ?? []}
            onChange={(labels: string[]) => onChangeFilters({ labels: labels })}
          />
          <LabelsView
            labels={filters.tags ?? []}
            onChange={(tags: string[]) => onChangeFilters({ tags: tags })}
            title="Tags"
          />
        </div>
      </DialogBody>
      <DialogFooter className="flex gap-6 items-center absolute bottom-5 right-5">
        <Button
          color="primary"
          variant="outlined"
          className="px-8"
          onClick={() => {
            onClose(), setFilters(defaultFilters);
          }}
          label="Cancel"
        />
        <Button
          color="primary"
          className="px-8"
          onClick={() => {
            onClick(prepareFilters(filters)), setFilters(defaultFilters);
          }}
          label="Run checks"
          disabled={userProfile.can_delete_data !== true}
        />
      </DialogFooter>
    </Dialog>
  );
}
