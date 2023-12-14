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
  onBack: () => void;
  columnOptions: {
    comparedColumnsOptions: Option[];
    referencedColumnsOptions: Option[];
  };
};

export default function EditReferenceTable2({
  selectedReference,
  timePartitioned,
  existingTableComparisonConfigurations,
  onBack,
  columnOptions
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
  const [editConfigurationParameters, setEditConfigurationParameters] =
    useState<TParameters>({});
  const [comparisonAlreadyExist, setComparisonAlreadyExist] = useState(false);
  const [isUpdated, setIsUpdated] = useState(false);

  const onChangeParameters = (obj: Partial<TParameters>) => {
    setEditConfigurationParameters((prevState) => ({
      ...prevState,
      ...obj
    }));
  };

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

  console.log(editConfigurationParameters);
  return (
    <div>
      {selectedReference ? (
        <EditingViewFirstLine
          onChangeEditConnectionSchemaTable={onChangeEditConnectionSchemaTable}
          editConfigurationParameters={editConfigurationParameters}
        />
      ) : (
        <FirstLineNameConfiguration
          editConfigurationParameters={editConfigurationParameters}
          onChangeName={onChangeName}
          isButtonEnabled={true}
          onBack={onBack}
          timePartitioned={timePartitioned}
          existingTableComparisonConfigurations={
            existingTableComparisonConfigurations
          }
        />
      )}
      {selectedReference && editConnectionSchemaTable === false ? (
        <SelectConnectionSchemaTableOverlook
          onChangeEditConnectionSchemaTable={onChangeEditConnectionSchemaTable}
          editConfigurationParameters={editConfigurationParameters}
        />
      ) : (
        <SelectConnectionSchemaTable
          editConfigurationParameters={editConfigurationParameters}
          onChangeParameters={onChangeParameters}
          onChangeEditConnectionSchemaTable={onChangeEditConnectionSchemaTable}
        />
      )}
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
  );
}
