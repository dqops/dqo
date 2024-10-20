import clsx from 'clsx';
import React, { useState } from 'react';
import { useSelector } from 'react-redux';
import { useActionDispatch } from '../../../../../hooks/useActionDispatch';
import {
  getTableDailyMonitoringChecks,
  getTableDailyPartitionedChecks,
  getTableMonthlyMonitoringChecks,
  getTableMonthlyPartitionedChecks,
  getTableProfilingChecksModel
} from '../../../../../redux/actions/table.actions';
import { IRootState } from '../../../../../redux/reducers';
import { getFirstLevelActiveTab } from '../../../../../redux/selectors';
import { TableComparisonsApi } from '../../../../../services/apiClient';
import { TParameters } from '../../../../../shared/constants';
import { CheckTypes } from '../../../../../shared/routes';
import { useDecodedParams } from '../../../../../utils';
import Button from '../../../../Button';
import Input from '../../../../Input';
import SvgIcon from '../../../../SvgIcon';
import { getIsButtonEnabled } from '../TableComparisonUtils';

type TFirstLevelConfiguretion = {
  editConfigurationParameters: TParameters;
  onChangeName: (name: string) => void;
  onBack: (stayOnSamePage?: boolean | undefined) => void;
  timePartitioned?: 'daily' | 'monthly';
  existingTableComparisonConfigurations: (string | undefined)[];
  setConfigurationToEditing: (name: string) => void;
  onChangeIsUpdated: (isUpdated: boolean) => void;
};

export default function FirstLineNameConfiguration({
  editConfigurationParameters,
  onChangeName,
  onBack,
  timePartitioned,
  existingTableComparisonConfigurations,
  setConfigurationToEditing,
  onChangeIsUpdated
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
  } = useDecodedParams();
  const dispatch = useActionDispatch();
  const firstLevelActiveTab = useSelector(getFirstLevelActiveTab(checkTypes));
  const [comparisonAlreadyExist, setComparisonAlreadyExist] = useState(false);
  const { userProfile } = useSelector((state: IRootState) => state.job || {});

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
        grouping_columns: editConfigurationParameters.dataGroupingArray ?? [],
        reference_table_filter:
          editConfigurationParameters.reference_table_filter,
        compared_table_filter: editConfigurationParameters.compared_table_filter
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
          table,
          requestBody
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
              // console.log(error);
              setComparisonAlreadyExist(true);
            }
          });
      }
      onBack(false);
      setConfigurationToEditing(editConfigurationParameters.name ?? '');
      setComparisonAlreadyExist(false);
      onChangeIsUpdated(false);
    }
  };

  return (
    <div className="flex items-center justify-between border-b border-gray-300 mb-4 py-4 px-8 w-full">
      <div className="flex items-center justify-center gap-x-5">
        <div className="font-bold text-center">
          Table comparison configuration name:{' '}
        </div>
        <Input
          className={clsx(
            'flex-1 !w-72',
            editConfigurationParameters.name &&
              editConfigurationParameters?.name?.length > 0
              ? ''
              : 'border border-red-500'
          )}
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
          label="Back"
          color="primary"
          variant="text"
          className="px-0 !mr-2"
          leftIcon={<SvgIcon name="chevron-left" className="w-4 h-4 mr-2" />}
          onClick={onBack}
        />
        <Button
          onClick={onCreate}
          label="Save"
          color="primary"
          className="w-40"
          disabled={
            !getIsButtonEnabled(editConfigurationParameters) ||
            userProfile.can_manage_data_sources !== true
          }
        />
      </div>
    </div>
  );
}
