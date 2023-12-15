import React, { useEffect, useState } from 'react';
import FirstLineNameConfiguration from './CreatingComparison/FirstLineNameConfiguration';
import EditingViewFirstLine from './EditingComparison/EditingViewFirstLine';
import { useParams } from 'react-router-dom';
import { CheckTypes } from '../../../../shared/routes';
import { useSelector } from 'react-redux';
import { getFirstLevelActiveTab } from '../../../../redux/selectors';
import { useActionDispatch } from '../../../../hooks/useActionDispatch';
import SelectConnectionSchemaTableOverlook from './EditingComparison/SelectConnectionSchemaTableOverlook';
import SelectConnectionSchemaTable from './CreatingComparison/SelectConnectionSchemaTable';
import { TParameters } from '../../../../shared/constants';
import { Option } from '../../../Select';
import SelectColumnGrouping from './CreatingComparison/SelectColumnGrouping';
import { TableComparisonGroupingColumnPairModel } from '../../../../api';
import SelectColumnGroupingOverlook from './EditingComparison/SelectColumnGroupingOverlook';
import { TableComparisonsApi } from '../../../../services/apiClient';

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
  setConfigurationToEditing
}: TEditReferenceTable) {
  const {
    checkTypes,
    connection,
    schema,
    table
  }: {
    checkTypes: CheckTypes;
    connection: string;
    schema: string;
    table: string;
  } = useParams();
  const dispatch = useActionDispatch();
  const firstLevelActiveTab = useSelector(getFirstLevelActiveTab(checkTypes));
  const [editConnectionSchemaTable, setEditConnectionSchemaTable] =
    useState(false);
  const [editColumnGrouping, setEditColumnGrouping] = useState(false);
  const [comparisonAlreadyExist, setComparisonAlreadyExist] = useState(false);
  const [isUpdated, setIsUpdated] = useState(false);

  const onChangeName = (name: string) => {
    onChangeParameters({ name: name });
  };

  const onChangeEditColumnGrouping = (open: boolean) => {
    setEditColumnGrouping(open);
  };

  const onChangeEditConnectionSchemaTable = (open: boolean) => {
    setEditConnectionSchemaTable(open);
  };

  useEffect(() => {
    if (selectedReference) {
      TableComparisonsApi.getTableComparisonConfiguration(
        connection,
        schema,
        table,
        selectedReference
      ).then((res) => {
        if (res && res?.data) {
          onChangeParameters({
            name: res.data?.table_comparison_configuration_name ?? '',
            refConnection: res.data?.reference_connection ?? '',
            refSchema: res.data?.reference_table?.schema_name ?? '',
            refTable: res.data?.reference_table?.table_name ?? '',
            dataGroupingArray: res.data.grouping_columns ?? []
          });
        }
      });
    }
  }, [selectedReference]);

  return (
    <div className="w-full ">
      <div className="w-full">
        {selectedReference ? (
          <EditingViewFirstLine
            onChangeEditConnectionSchemaTable={
              onChangeEditConnectionSchemaTable
            }
            editConfigurationParameters={editConfigurationParameters}
            onBack={onBack}
            onUpdateChecks={onUpdateChecks}
            selectedReference={selectedReference}
            timePartitioned={timePartitioned}
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
            onChangeParameters={onChangeParameters}
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
            onChangeParameters={onChangeParameters}
          />
        )}
      </div>
    </div>
  );
}
