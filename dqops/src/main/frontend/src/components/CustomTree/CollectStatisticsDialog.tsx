import {
  Dialog,
  DialogBody,
  DialogFooter,
  DialogHeader
} from '@material-tailwind/react';
import React, { useState } from 'react';
import { useSelector } from 'react-redux';
import {
  StatisticsCollectorSearchFilters,
  StatisticsCollectorSearchFiltersTargetEnum
} from '../../api';
import { IRootState } from '../../redux/reducers';
import Button from '../Button';
import LabelsView from '../Connection/LabelsView';
import SectionWrapper from '../Dashboard/SectionWrapper';
import Input from '../Input';
import SelectInput from '../SelectInput';
import SvgIcon from '../SvgIcon';
type TCollectStatisticsDialogProps = {
  onClick: (node: StatisticsCollectorSearchFilters) => void;
  open: boolean;
  onClose: VoidFunction;
  collectStatisticsJobTemplate: StatisticsCollectorSearchFilters;
};

export default function CollectStatisticsDialog({
  onClick,
  onClose,
  open,
  collectStatisticsJobTemplate
}: TCollectStatisticsDialogProps) {
  const { userProfile } = useSelector((state: IRootState) => state.job || {});

  const [filters, setFilters] = useState<StatisticsCollectorSearchFilters>({
    fullTableName: '*.*',
    ...collectStatisticsJobTemplate
  });
  const [additionalParams, setAdditionalParams] = useState(false);

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
        <div className="flex justify-between border-b pb-4 border-gray-300 text-black px-4">
          <div className="w-[45%]">
            Connection
            <Input
              value={filters.connection}
              onChange={(e) => onChangeFilters({ connection: e.target.value })}
              className="mt-2"
            />
          </div>
          <div className="w-[45%]">
            Schema and table name
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
        <div className="flex justify-between text-black  ">
          <LabelsView
            labels={filters.columnNames ?? []}
            onChange={(columns: string[]) =>
              onChangeFilters({ columnNames: columns })
            }
            title="Columns"
            titleClassName="font-normal"
          />
        </div>
        {additionalParams === false ? (
          <div
            className="flex items-center text-black mb-4 cursor-pointer"
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
            <div className="flex justify-between pt-4 text-black   px-4">
              <div className="w-1/3 ml-2">
                Collector category
                <Input
                  value={filters.collectorCategory}
                  onChange={(e) =>
                    onChangeFilters({ collectorCategory: e.target.value })
                  }
                  className="mt-2"
                />
              </div>
              <div className="w-1/3 ml-2">
                Collector name
                <Input
                  value={filters.collectorName}
                  onChange={(e) =>
                    onChangeFilters({ collectorName: e.target.value })
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
            </div>
            <div className="flex justify-between pt-4 text-black   px-4">
              <div className="w-1/3 ml-2">
                Target
                <SelectInput
                  value={filters.target}
                  onChange={(value) => onChangeFilters({ target: value })}
                  options={[
                    { label: '', value: '' },
                    ...Object.keys(
                      StatisticsCollectorSearchFiltersTargetEnum
                    ).map((x) => ({ label: x, value: x }))
                  ]}
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
      <DialogFooter className="flex gap-6 items-center absolute bottom-5 right-5 mt-20">
        <Button
          color="primary"
          variant="outlined"
          className="px-8"
          onClick={() => {
            onClose(), setFilters(collectStatisticsJobTemplate);
          }}
          label="Cancel"
        />
        <Button
          color="primary"
          className="px-8"
          onClick={() => {
            onClick(prepareFilters(filters)),
              setFilters(collectStatisticsJobTemplate);
          }}
          label="Collect statistics"
          disabled={userProfile.can_delete_data !== true}
        />
      </DialogFooter>
    </Dialog>
  );
}
