import {
  Popover,
  PopoverHandler,
  PopoverContent,
  Dialog,
  DialogBody,
  DialogFooter,
  DialogHeader
} from '@material-tailwind/react';
import React, { useState } from 'react';
import SvgIcon from '../SvgIcon';
import Button from '../Button';
import {
  CheckSearchFilters,
  CheckSearchFiltersCheckTypeEnum,
  CheckSearchFiltersTimeScaleEnum,
  StatisticsCollectorSearchFiltersTargetEnum
} from '../../api';
import LabelsView from '../Connection/LabelsView';
import Input from '../Input';
import Select from '../Select';
import Checkbox from '../Checkbox';
import SelectInput from '../SelectInput';
import CheckboxThreeSteps from '../CheckBoxThreeSteps';
type TRunChecksDialogProps = {
  onClick: () => void;
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
  const [filters, setFilters] = useState<CheckSearchFilters>({});

  const onChangeFilters = (obj: Partial<CheckSearchFilters>) => {
    setFilters((prev) => ({
      ...prev,
      ...obj
    }));
  };

  return (
    <Dialog open={open} handler={onClose} className="min-w-300 p-4">
      <DialogHeader className="font-bold text-center justify-center">
        Run checks
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
        <div className="flex justify-between py-4 text-black font-semibold">
          <div>
            Column name:
            <Input
              value={filters.column}
              onChange={(e) => onChangeFilters({ column: e.target.value })}
            />
          </div>
          <div>
            Column datatype:
            <Input
              value={filters.columnDataType}
              onChange={(e) =>
                onChangeFilters({ columnDataType: e.target.value })
              }
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
          <div>
            Check target:
            <SelectInput
              value={filters.checkTarget}
              onChange={(value) => onChangeFilters({ checkTarget: value })}
              options={Object.keys(
                StatisticsCollectorSearchFiltersTargetEnum
              ).map((x) => ({ label: x, value: x }))}
            />
          </div>
          <div>
            Check type:
            <SelectInput
              value={filters.checkType}
              onChange={(value) => onChangeFilters({ checkType: value })}
              options={Object.keys(CheckSearchFiltersCheckTypeEnum).map(
                (x) => ({ label: x, value: x })
              )}
            />
          </div>
          <div>
            Time scale:
            <SelectInput
              value={filters.timeScale}
              onChange={(value) => onChangeFilters({ timeScale: value })}
              options={Object.keys(CheckSearchFiltersTimeScaleEnum).map(
                (x) => ({ label: x, value: x })
              )}
            />
          </div>
        </div>
        <div className="flex justify-between pt-4 text-black font-semibold">
          <div>
            Check name:
            <Input
              value={filters.checkName}
              onChange={(e) => onChangeFilters({ checkName: e.target.value })}
            />
          </div>
          <div>
            Table comparison name:
            <Input
              value={filters.tableComparisonName}
              onChange={(e) =>
                onChangeFilters({ tableComparisonName: e.target.value })
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
          onClick={onClose}
          label="Cancel"
        />
        <Button
          color="primary"
          className="px-8"
          onClick={onClick}
          label="Run checks"
          //   disabled={userProfile.can_delete_data !== true}
        />
      </DialogFooter>
    </Dialog>
  );
}
