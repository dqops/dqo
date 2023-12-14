import React, { useState } from 'react';
import Button from '../../../../Button';
import clsx from 'clsx';
import SvgIcon from '../../../../SvgIcon';
import Input from '../../../../Input';
import { useParams } from 'react-router-dom';
import {
  getTableProfilingChecksModel,
  getTableDailyPartitionedChecks,
  getTableMonthlyPartitionedChecks,
  getTableDailyMonitoringChecks,
  getTableMonthlyMonitoringChecks
} from '../../../../../redux/actions/table.actions';
import { CheckTypes } from '../../../../../shared/routes';
import { TParameters } from '../../../../../shared/constants';
import { useSelector } from 'react-redux';
import { useActionDispatch } from '../../../../../hooks/useActionDispatch';
import { getFirstLevelActiveTab } from '../../../../../redux/selectors';
import { TableComparisonsApi } from '../../../../../services/apiClient';

type TFirstLevelConfiguretion = {
  editConfigurationParameters: TParameters;
  onChangeName: (name: string) => void;
  isButtonEnabled: boolean;
  onBack: () => void;
  timePartitioned?: 'daily' | 'monthly';
  existingTableComparisonConfigurations: (string | undefined)[];
};

export default function FirstLineNameConfiguration({
  editConfigurationParameters,
  onChangeName,
  isButtonEnabled,
  onBack,
  timePartitioned,
  existingTableComparisonConfigurations
}: TFirstLevelConfiguretion) {
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
  const [comparisonAlreadyExist, setComparisonAlreadyExist] = useState(false);

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

  return (
    <div className="flex items-center justify-between border-b border-gray-300 mb-4 py-4 px-8 w-full">
      <div className="flex items-center justify-center gap-x-5">
        <div className="font-bold text-center">
          Table comparison configuration name:{' '}
        </div>
        <Input
          className={
            editConfigurationParameters.name?.length === 0
              ? 'min-w-80 border border-red-500'
              : 'min-w-80'
          }
          value={editConfigurationParameters.name}
          onChange={(e) => onChangeName(e.target.value)}
          placeholder="Table comparison configuration name"
        />
      </div>
      {comparisonAlreadyExist ? (
        <div className="bg-red-300 p-4 rounded-lg text-white border-2 border-red-500">
          A table comparison with this name already exists
        </div>
      ) : null}
      <div className="flex justify-center items-center gap-x-2">
        <Button
          onClick={onCreate}
          label="Save"
          color="primary"
          className="w-40"
          disabled={!isButtonEnabled}
        />
        <Button
          label="Back"
          color="primary"
          variant="text"
          className="px-0"
          leftIcon={<SvgIcon name="chevron-left" className="w-4 h-4 mr-2" />}
          onClick={onBack}
        />
      </div>
    </div>
  );
}
