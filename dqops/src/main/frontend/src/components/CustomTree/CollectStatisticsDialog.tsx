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
import Select from '../Select';
import Checkbox from '../Checkbox';
import LabelsView from '../Connection/LabelsView';
import { CustomTreeNode } from '../../shared/interfaces';
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
  const hierarchiArray = nodeId?.split('.');
  const [filters, setFilters] = useState<StatisticsCollectorSearchFilters>({
    connection: hierarchiArray?.[0],
    fullTableName: hierarchiArray?.[1] + '.' + hierarchiArray?.[2],
    columnNames: [hierarchiArray?.[4]]
  });

  const onChangeFilters = (obj: Partial<StatisticsCollectorSearchFilters>) => {
    setFilters((prev) => ({
      ...prev,
      ...obj
    }));
  };

  return (
    <Dialog open={open} handler={onClose} className="min-w-300 p-4">
      <DialogHeader className="font-bold text-center justify-center">
        Collect statistics
      </DialogHeader>
      <DialogBody className="text-sm flex flex-col mb-20">
        <div className="flex justify-between border-b pb-4 border-gray-300 text-black font-semibold">
          <div>
            Connection:
            <Input
              value={filters.connection}
              onChange={(e) => onChangeFilters({ connection: e.target.value })}
            />
          </div>
          <div>
            fullTableName:
            <Input
              value={filters.fullTableName}
              onChange={(e) =>
                onChangeFilters({ fullTableName: e.target.value })
              }
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
        <div className="flex justify-between pt-4 text-black font-semibold">
          <div>
            Collector category:
            <Input
              value={filters.collectorCategory}
              onChange={(e) =>
                onChangeFilters({ collectorCategory: e.target.value })
              }
            />
          </div>
          <div>
            Collector name:
            <Input
              value={filters.collectorName}
              onChange={(e) =>
                onChangeFilters({ collectorName: e.target.value })
              }
            />
          </div>
          <div>
            Sensor name:
            <Input
              value={filters.sensorName}
              onChange={(e) => onChangeFilters({ sensorName: e.target.value })}
            />
          </div>
        </div>
        <div className="flex justify-between pt-4 text-black font-semibold">
          <div>
            Target:
            <Select
              value={filters.target}
              onChange={(value) => onChangeFilters({ target: value })}
              options={Object.keys(
                StatisticsCollectorSearchFiltersTargetEnum
              ).map((x) => ({ label: x, value: x }))}
            />
          </div>
          <div>
            Enabled:
            <Checkbox
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
          onClick={onClose}
          label="Cancel"
        />
        <Button
          color="primary"
          className="px-8"
          onClick={() => onClick(filters)}
          label="Collect statistics"
          //   disabled={userProfile.can_delete_data !== true}
        />
      </DialogFooter>
    </Dialog>
  );
}
