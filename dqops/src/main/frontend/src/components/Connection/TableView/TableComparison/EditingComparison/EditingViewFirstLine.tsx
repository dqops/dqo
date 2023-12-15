import React, { useState } from 'react';
import DeleteOnlyDataDialog from '../../../../CustomTree/DeleteOnlyDataDialog';
import { TParameters } from '../../../../../shared/constants';
import SvgIcon from '../../../../SvgIcon';
import Button from '../../../../Button';
import clsx from 'clsx';
import {
  JobApiClient,
  TableComparisonsApi
} from '../../../../../services/apiClient';
import { getFirstLevelActiveTab } from '../../../../../redux/selectors';
import { useSelector } from 'react-redux';
import { useParams } from 'react-router-dom';
import { setCurrentJobId } from '../../../../../redux/actions/source.actions';
import { CheckTypes } from '../../../../../shared/routes';
import { useActionDispatch } from '../../../../../hooks/useActionDispatch';
import { IRootState } from '../../../../../redux/reducers';
import { TableComparisonConfigurationModelCheckTypeEnum } from '../../../../../api';
import { getIsButtonEnabled } from '../TableComparisonUtils';
type TEditingViewFirstLine = {
  editConfigurationParameters: TParameters;
  onChangeEditConnectionSchemaTable: (open: boolean) => void;
  selectedReference: string;
  timePartitioned?: 'daily' | 'monthly';
  onBack: (stayOnSamePage?: boolean | undefined) => void;
  disabled?: boolean;
  isButtonEnabled?: any;
  cleanDataTemplate?: any;
  onUpdateChecks: () => void;
  onChangeIsUpdated: (isUpdated: boolean) => void;
  isUpdated: boolean;
  onRunChecks: () => Promise<void>;
  deleteData?: () => Promise<void>;
};

export default function EditingViewFirstLine({
  selectedReference,
  editConfigurationParameters,
  onChangeEditConnectionSchemaTable,
  onBack,
  disabled,
  isButtonEnabled,
  cleanDataTemplate,
  onUpdateChecks,
  timePartitioned,
  isUpdated,
  onChangeIsUpdated,
  deleteData,
  onRunChecks
}: TEditingViewFirstLine) {
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
  const [deleteDataDialogOpened, setDeleteDataDialogOpened] = useState(false);
  const firstLevelActiveTab = useSelector(getFirstLevelActiveTab(checkTypes));
  const [jobId, setJobId] = useState<number>();
  const { job_dictionary_state } = useSelector(
    (state: IRootState) => state.job || {}
  );
  const job = jobId ? job_dictionary_state[jobId] : undefined;
  const dispatch = useActionDispatch();

  // const deleteData = async (params: { [key: string]: string | boolean }) => {
  //   setDeleteDataDialogOpened(false);
  //   try {
  //     const res = await JobApiClient.deleteStoredData(
  //       undefined,
  //       false,
  //       undefined,
  //       {
  //         ...(cleanDataTemplate || {}),
  //         ...params
  //       }
  //     );
  //     dispatch(
  //       setCurrentJobId(
  //         checkTypes,
  //         firstLevelActiveTab,
  //         res.data?.jobId?.jobId ?? 0
  //       )
  //     );
  //     setJobId(res.data?.jobId?.jobId);
  //   } catch (err) {
  //     console.error(err);
  //   }
  // };

  const compareTables = () => {};

  //   const disabledDeleting =
  //   job &&
  //   job?.status !== DqoJobHistoryEntryModelStatusEnum.succeeded &&
  //   job?.status !== DqoJobHistoryEntryModelStatusEnum.failed;

  // useEffect(() => {
  //   if (
  //     job?.status === DqoJobHistoryEntryModelStatusEnum.succeeded ||
  //     job?.status === DqoJobHistoryEntryModelStatusEnum.failed
  //   ) {
  //     onChangeIsDataDeleted(!isDataDeleted);
  //     setDeletingData(false);
  //   }
  // }, [job?.status]);
  // console.log(connection, schema, table, selectedReference, {
  //   table_comparison_configuration_name: selectedReference,
  //   compared_connection: connection,
  //   compared_table: {
  //     schema_name: schema,
  //     table_name: table
  //   },
  //   reference_connection: editConfigurationParameters.refConnection,
  //   reference_table: {
  //     schema_name: editConfigurationParameters.refSchema,
  //     table_name: editConfigurationParameters.refTable
  //   },
  //   grouping_columns: editConfigurationParameters.dataGroupingArray,
  //   check_type: checkTypes as TableComparisonConfigurationModelCheckTypeEnum,
  //   time_scale: timePartitioned
  // });
  const onUpdate = async () => {
    console.log(editConfigurationParameters.dataGroupingArray);
    await TableComparisonsApi.updateTableComparisonConfiguration(
      connection,
      schema,
      table,
      selectedReference,
      {
        table_comparison_configuration_name: selectedReference,
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
        grouping_columns: editConfigurationParameters.dataGroupingArray,
        check_type:
          checkTypes as TableComparisonConfigurationModelCheckTypeEnum,
        time_scale: timePartitioned
      }
    );
    onChangeIsUpdated(false);
    onUpdateChecks();
  };

  return (
    <div>
      <div className="flex items-center justify-between my-4">
        <div className="flex items-center justify-center gap-x-5">
          <div className="font-bold text-center">
            Table comparison configuration name:{' '}
          </div>
          <span className="font-bold">{editConfigurationParameters.name}</span>
        </div>
        <div className="flex justify-center items-center gap-x-2">
          <SvgIcon
            name="sync"
            className={clsx(
              'w-4 h-4 mr-3',
              disabled ? 'animate-spin' : 'hidden'
            )}
          />
          <Button
            color="primary"
            variant="contained"
            disabled={disabled}
            label="Delete results"
            onClick={() => setDeleteDataDialogOpened(true)}
          />
          <SvgIcon
            name="sync"
            className={clsx(
              'w-4 h-4 mr-3',
              disabled ? 'animate-spin' : 'hidden'
            )}
          />
          <Button
            label="Compare Tables"
            color="primary"
            variant="contained"
            onClick={compareTables}
            disabled={disabled}
          />
          <Button
            onClick={onUpdate}
            label="Save"
            color="primary"
            className="w-40"
            disabled={
              isUpdated === false ||
              getIsButtonEnabled(editConfigurationParameters) === false
            }
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
      {/* <DeleteOnlyDataDialog
        open={deleteDataDialogOpened}
        onClose={() => setDeleteDataDialogOpened(false)}
        onDelete={deleteData}
      /> */}
    </div>
  );
}
