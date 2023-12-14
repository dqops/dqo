import React, { useState } from 'react';
import FirstLineNameConfiguration from './CreatingComparison/FirstLineNameConfiguration';
import EditingViewFirstLine from './EditingComparison/EditingViewFirstLine';
import { useParams } from 'react-router-dom';
import {
  getTableProfilingChecksModel,
  getTableDailyPartitionedChecks,
  getTableMonthlyPartitionedChecks,
  getTableDailyMonitoringChecks,
  getTableMonthlyMonitoringChecks
} from '../../../../redux/actions/table.actions';
import { CheckTypes } from '../../../../shared/routes';
import { TableComparisonsApi } from '../../../../services/apiClient';
import { useSelector } from 'react-redux';
import { getFirstLevelActiveTab } from '../../../../redux/selectors';
import { useActionDispatch } from '../../../../hooks/useActionDispatch';
import SelectConnectionSchemaTableOverlook from './EditingComparison/SelectConnectionSchemaTableOverlook';
import SelectConnectionSchemaTable from './CreatingComparison/SelectConnectionSchemaTable';
import { TParameters } from '../../../../shared/constants';
import { Option } from '../../../Select';
import SelectColumnGrouping from './CreatingComparison/SelectColumnGrouping';
import { TableComparisonGroupingColumnPairModel } from '../../../../api';

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

  const onCreate = async () => {
    if (
      existingTableComparisonConfigurations.includes(
        editConfigurationParameters.toString()
      )
    ) {
      setComparisonAlreadyExist(true);
    } else {
      const requestBody = {
        table_comparison_configuration_name: editConfigurationParameters.name,
        compared_connection: connection,
        compared_table: {
          schema_name: schema,
          table_name: table
        },
        reference_connection: editConfigurationParameters.refConnection,
        reference_table: {
          schema_name: editConfigurationParameters.refSchema,
          table_name: editConfigurationParameters.refTable
        },
        grouping_columns: editConfigurationParameters.dataGroupingArray ?? []
      };
      if (checkTypes === CheckTypes.PROFILING) {
        await TableComparisonsApi.createTableComparisonProfiling(
          connection,
          schema,
          table,
          requestBody
        )
          .then(() => {
            dispatch(
              getTableProfilingChecksModel(
                checkTypes,
                firstLevelActiveTab,
                connection,
                schema,
                table
              )
            );
          })
          .catch((error) => {
            if (error.response.status === 409) {
              setComparisonAlreadyExist(true);
            }
          });
      } else if (
        checkTypes === CheckTypes.PARTITIONED &&
        timePartitioned === 'daily'
      ) {
        await TableComparisonsApi.createTableComparisonPartitionedDaily(
          connection,
          schema,
          table
        )
          .then(() => {
            dispatch(
              getTableDailyPartitionedChecks(
                checkTypes,
                firstLevelActiveTab,
                connection,
                schema,
                table
              )
            );
          })
          .catch((error) => {
            if (error.response.status === 409) {
              setComparisonAlreadyExist(true);
            }
          });
      } else if (
        checkTypes === CheckTypes.PARTITIONED &&
        timePartitioned === 'monthly'
      ) {
        await TableComparisonsApi.createTableComparisonPartitionedMonthly(
          connection,
          schema,
          table,
          requestBody
        )
          .then(() => {
            dispatch(
              getTableMonthlyPartitionedChecks(
                checkTypes,
                firstLevelActiveTab,
                connection,
                schema,
                table
              )
            );
          })
          .catch((error) => {
            if (error.response.status === 409) {
              setComparisonAlreadyExist(true);
            }
          });
      } else if (
        checkTypes === CheckTypes.MONITORING &&
        timePartitioned === 'daily'
      ) {
        await TableComparisonsApi.createTableComparisonMonitoringDaily(
          connection,
          schema,
          table,
          requestBody
        )
          .then(() => {
            dispatch(
              getTableDailyMonitoringChecks(
                checkTypes,
                firstLevelActiveTab,
                connection,
                schema,
                table
              )
            );
          })
          .catch((error) => {
            if (error.response.status === 409) {
              setComparisonAlreadyExist(true);
            }
          });
      } else if (
        checkTypes === CheckTypes.MONITORING &&
        timePartitioned === 'monthly'
      ) {
        await TableComparisonsApi.createTableComparisonMonitoringMonthly(
          connection,
          schema,
          table,
          requestBody
        )
          .then(() => {
            dispatch(
              getTableMonthlyMonitoringChecks(
                checkTypes,
                firstLevelActiveTab,
                connection,
                schema,
                table
              )
            );
          })
          .catch((error) => {
            if (error.response.status === 409) {
              console.log(error);
              setComparisonAlreadyExist(true);
            }
          });
      }
      onBack();
      setComparisonAlreadyExist(false);
    }
  };

  const onChangeDataGroupingArray = (
    reference: boolean,
    index: number,
    columnName: string
  ) => {
    const data = [...(editConfigurationParameters.dataGroupingArray ?? [])];
    if (reference === true) {
      if (data[index]) {
        data[index].reference_table_column_name = columnName;
      } else {
        data[index] = { reference_table_column_name: columnName };
      }
    } else {
      if (data[index]) {
        data[index].compared_table_column_name = columnName;
      } else {
        data[index] = { compared_table_column_name: columnName };
      }
    }
    onChangeParameters({ dataGroupingArray: data });
    setIsUpdated(true);
  };

  return (
    <div>
      {selectedReference ? (
        <EditingViewFirstLine />
      ) : (
        <FirstLineNameConfiguration
          name={editConfigurationParameters.name}
          onChangeName={onChangeName}
          isButtonEnabled={true}
          onSave={onCreate}
          onBack={onBack}
          comparisonAlreadyExist={comparisonAlreadyExist}
        />
      )}
      {selectedReference || editConnectionSchemaTable ? (
        <SelectConnectionSchemaTableOverlook />
      ) : (
        <SelectConnectionSchemaTable />
      )}
      {selectedReference || editColumnGrouping ? (
        <SelectColumnGrouping
          onChangeDataGrouping={onChangeDataGroupingArray}
          onChangeEditColumnGrouping={onChangeEditColumnGrouping}
          columnOptions={columnOptions}
          editConfigurationParameters={editConfigurationParameters}
        />
      ) : (
        <SelectConnectionSchemaTableOverlook />
      )}
    </div>
  );
}
