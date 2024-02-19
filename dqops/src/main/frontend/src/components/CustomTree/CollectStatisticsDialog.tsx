import {
  Dialog,
  DialogBody,
  DialogFooter,
  DialogHeader
} from '@material-tailwind/react';
import React, { useState } from 'react';
import Button from '../Button';
import {
  StatisticsCollectorSearchFilters,
  StatisticsCollectorSearchFiltersTargetEnum
} from '../../api';
import Input from '../Input';
import LabelsView from '../Connection/LabelsView';
import CheckboxThreeSteps from '../CheckBoxThreeSteps';
import SelectInput from '../SelectInput';
import { useSelector } from 'react-redux';
import { IRootState } from '../../redux/reducers';
type TCollectStatisticsDialogProps = {
  onClick: (node: StatisticsCollectorSearchFilters) => void;
  open: boolean;
  onClose: VoidFunction;
  nodeId: string;
};

export default function CollectStatisticsDialog({
  onClick,
  onClose,
  open,
  nodeId
}: TCollectStatisticsDialogProps) {
  const { userProfile } = useSelector((state: IRootState) => state.job || {});
  const hierarchiArray = nodeId?.split('.');
  const defaultFilters = {
    connection: hierarchiArray?.[0],
    fullTableName:
      hierarchiArray?.[1] &&
      hierarchiArray?.[2] &&
      hierarchiArray?.[1] + '.' + hierarchiArray?.[2],
    columnNames: hierarchiArray?.[4] ? [hierarchiArray?.[4], ''] : [''],
    enabled: true
  };

  const [filters, setFilters] =
    useState<StatisticsCollectorSearchFilters>(defaultFilters);

  const onChangeFilters = (obj: Partial<StatisticsCollectorSearchFilters>) => {
    setFilters((prev) => ({
      ...prev,
      ...obj
    }));
  };

  const prepareFilters = (filters: StatisticsCollectorSearchFilters) => {
    const copiedFilters = { ...filters };
    copiedFilters.columnNames = copiedFilters.columnNames?.filter(
      (x) => x.length > 0
    );
    copiedFilters.labels = copiedFilters.labels?.filter((x) => x.length > 0);
    copiedFilters.tags = copiedFilters.tags?.filter((x) => x.length > 0);

    return copiedFilters;
  };

  return (
    <Dialog open={open} handler={onClose} className="min-w-300 p-4">
      <DialogHeader className="font-bold text-center justify-center">
        Collect statistics
      </DialogHeader>
      <DialogBody className="text-sm flex flex-col mb-20">
        <div className="flex justify-between border-b pb-4 border-gray-300 text-black font-semibold px-4">
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
        <div className="flex justify-between pt-4 text-black font-semibold">
          <LabelsView
            labels={filters.columnNames ?? []}
            onChange={(columns: string[]) =>
              onChangeFilters({ columnNames: columns })
            }
            title="Columns"
          />
        </div>
        <div className="flex justify-between pt-4 text-black font-semibold px-4">
          <div className="w-1/4">
            Collector category:
            <Input
              value={filters.collectorCategory}
              onChange={(e) =>
                onChangeFilters({ collectorCategory: e.target.value })
              }
              className="mt-2"
            />
          </div>
          <div className="w-1/4">
            Collector name:
            <Input
              value={filters.collectorName}
              onChange={(e) =>
                onChangeFilters({ collectorName: e.target.value })
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
        <div className="flex justify-between pt-4 text-black font-semibold px-4">
          <div className="w-1/4">
            Target:
            <SelectInput
              value={filters.target}
              onChange={(value) => onChangeFilters({ target: value })}
              options={[
                { label: '', value: '' },
                ...Object.keys(StatisticsCollectorSearchFiltersTargetEnum).map(
                  (x) => ({ label: x, value: x })
                )
              ]}
              className="mt-2"
            />
          </div>
          <div className="flex items-center gap-x-2">
            Enabled:
            <CheckboxThreeSteps
              checked={!!filters.enabled}
              onChange={(value) => onChangeFilters({ enabled: value })}
            />
          </div>
          <div></div>
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
      <DialogFooter className="flex gap-6 items-center absolute bottom-5 right-5 mt-20">
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
          label="Collect statistics"
          disabled={userProfile.can_delete_data !== true}
        />
      </DialogFooter>
    </Dialog>
  );
}
