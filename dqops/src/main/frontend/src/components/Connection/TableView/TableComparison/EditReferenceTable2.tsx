import React, { useState } from 'react';
import FirstLineNameConfiguration from './CreatingComparison/FirstLineNameConfiguration';
import EditingViewFirstLine from './EditingComparison/EditingViewFirstLine';
import SelectConnectionSchemaTableOverlook from './EditingComparison/SelectConnectionSchemaTableOverlook';
import SelectConnectionSchemaTable from './CreatingComparison/SelectConnectionSchemaTable';
import { TParameters } from '../../../../shared/constants';
import { Option } from '../../../Select';
import SelectColumnGrouping from './CreatingComparison/SelectColumnGrouping';
import SelectColumnGroupingOverlook from './EditingComparison/SelectColumnGroupingOverlook';

type TEditReferenceTable = {
  selectedReference?: string;
  timePartitioned?: 'daily' | 'monthly';
  existingTableComparisonConfigurations: (string | undefined)[];
  onBack: (stayOnSamePage?: boolean | undefined) => void;
  columnOptions: {
    comparedColumnsOptions: Option[];
    referencedColumnsOptions: Option[];
  };
  editConfigurationParameters: TParameters;
  onChangeParameters: (obj: Partial<TParameters>) => void;
  onUpdateChecks: () => void;
  setConfigurationToEditing: (name: string) => void;
  onChangeIsUpdated: (isUpdated: boolean) => void;
  isUpdated: boolean;
  compareTables: () => Promise<void>;
  deleteData: (params: { [key: string]: string | boolean }) => Promise<void>;
  disabled: boolean | undefined;
};

export default function EditReferenceTable2({
  selectedReference,
  timePartitioned,
  existingTableComparisonConfigurations,
  onBack,
  columnOptions,
  editConfigurationParameters,
  onChangeParameters,
  onUpdateChecks,
  setConfigurationToEditing,
  isUpdated,
  onChangeIsUpdated,
  compareTables,
  deleteData,
  disabled
}: TEditReferenceTable) {
  const [editConnectionSchemaTable, setEditConnectionSchemaTable] =
    useState(false);
  const [editColumnGrouping, setEditColumnGrouping] = useState(false);

  const onChangeName = (name: string) => {
    onChangeParameters({ name: name });
  };

  const onChangeEditColumnGrouping = (open: boolean) => {
    setEditColumnGrouping(open);
  };

  const onChangeEditConnectionSchemaTable = (open: boolean) => {
    setEditConnectionSchemaTable(open);
  };

  return (
    <div className="w-full ">
      <div className="w-full">
        {selectedReference ? (
          <EditingViewFirstLine
            editConfigurationParameters={editConfigurationParameters}
            onBack={onBack}
            onUpdateChecks={onUpdateChecks}
            selectedReference={selectedReference}
            timePartitioned={timePartitioned}
            isUpdated={isUpdated}
            onChangeIsUpdated={onChangeIsUpdated}
            compareTables={compareTables}
            deleteData={deleteData}
            disabled={disabled}
          />
        ) : (
          <FirstLineNameConfiguration
            editConfigurationParameters={editConfigurationParameters}
            onChangeName={onChangeName}
            onBack={onBack}
            timePartitioned={timePartitioned}
            existingTableComparisonConfigurations={
              existingTableComparisonConfigurations
            }
            setConfigurationToEditing={setConfigurationToEditing}
            onChangeIsUpdated={onChangeIsUpdated}
          />
        )}
      </div>
      <div className="pt-4">
        {selectedReference && editConnectionSchemaTable === false ? (
          <SelectConnectionSchemaTableOverlook
            onChangeEditConnectionSchemaTable={
              onChangeEditConnectionSchemaTable
            }
            editConfigurationParameters={editConfigurationParameters}
          />
        ) : (
          <SelectConnectionSchemaTable
            editConfigurationParameters={editConfigurationParameters}
            onChangeParameters={(obj: Partial<TParameters>) => {
              onChangeParameters(obj), onChangeIsUpdated(true);
            }}
            onChangeEditConnectionSchemaTable={
              onChangeEditConnectionSchemaTable
            }
          />
        )}
      </div>
      <div className="py-4">
        {selectedReference && editColumnGrouping === false ? (
          <SelectColumnGroupingOverlook
            onChangeEditColumnGrouping={onChangeEditColumnGrouping}
            dataGroupingArray={
              editConfigurationParameters.dataGroupingArray ?? []
            }
          />
        ) : (
          <SelectColumnGrouping
            onChangeEditColumnGrouping={onChangeEditColumnGrouping}
            columnOptions={columnOptions}
            editConfigurationParameters={editConfigurationParameters}
            onChangeParameters={(obj: Partial<TParameters>) => {
              onChangeParameters(obj), onChangeIsUpdated(true);
            }}
          />
        )}
      </div>
    </div>
  );
}
