import React, { useState } from 'react';
import FirstLineNameConfiguration from './CreatingComparison/FirstLineNameConfiguration';
import EditingViewFirstLine from './EditingComparison/EditingViewFirstLine';
import SelectConnectionSchemaTableOverlook from './EditingComparison/SelectConnectionSchemaTableOverlook';
import SelectConnectionSchemaTable from './CreatingComparison/SelectConnectionSchemaTable';
import { TParameters } from '../../../../shared/constants';
import SelectColumnGrouping from './CreatingComparison/SelectColumnGrouping';
import SelectColumnGroupingOverlook from './EditingComparison/SelectColumnGroupingOverlook';
import { TEditReferenceTable } from './TableComparisonConstans';

export default function EditReferenceTable({
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
      <div className="pb-4">
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
