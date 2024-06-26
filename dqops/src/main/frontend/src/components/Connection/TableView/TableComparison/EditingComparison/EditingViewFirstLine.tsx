import clsx from 'clsx';
import React, { useState } from 'react';
import { useSelector } from 'react-redux';
import { TableComparisonConfigurationModelCheckTypeEnum } from '../../../../../api';
import { IRootState } from '../../../../../redux/reducers';
import { TableComparisonsApi } from '../../../../../services/apiClient';
import { TParameters } from '../../../../../shared/constants';
import { CheckTypes } from '../../../../../shared/routes';
import { useDecodedParams } from '../../../../../utils';
import Button from '../../../../Button';
import DeleteOnlyDataDialog from '../../../../CustomTree/DeleteOnlyDataDialog';
import SvgIcon from '../../../../SvgIcon';
import { getIsButtonEnabled } from '../TableComparisonUtils';
type TEditingViewFirstLine = {
  editConfigurationParameters: TParameters;
  selectedReference: string;
  timePartitioned?: 'daily' | 'monthly';
  onBack: (stayOnSamePage?: boolean | undefined) => void;
  disabled?: boolean;
  onUpdateChecks: () => void;
  onChangeIsUpdated: (isUpdated: boolean) => void;
  isUpdated: boolean;
  compareTables: () => Promise<void>;
  deleteData: (params: { [key: string]: string | boolean }) => Promise<void>;
};

export default function EditingViewFirstLine({
  selectedReference,
  editConfigurationParameters,
  onBack,
  disabled,
  onUpdateChecks,
  timePartitioned,
  isUpdated,
  onChangeIsUpdated,
  deleteData,
  compareTables
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
  } = useDecodedParams();
  const [deleteDataDialogOpened, setDeleteDataDialogOpened] = useState(false);
  const { userProfile } = useSelector((state: IRootState) => state.job || {});
  const onUpdate = async () => {
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
        time_scale: timePartitioned,
        reference_table_filter:
          editConfigurationParameters.reference_table_filter,
        compared_table_filter: editConfigurationParameters.compared_table_filter
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
          <Button
            color="primary"
            variant="contained"
            disabled={disabled || userProfile.can_manage_data_sources !== true}
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
            disabled={disabled || userProfile.can_run_checks !== true}
          />
          <Button
            onClick={onUpdate}
            label="Save"
            color="primary"
            className="w-40"
            disabled={
              isUpdated === false ||
              getIsButtonEnabled(editConfigurationParameters) === false ||
              userProfile.can_manage_data_sources !== true
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
      <DeleteOnlyDataDialog
        open={deleteDataDialogOpened}
        onClose={() => setDeleteDataDialogOpened(false)}
        onDelete={(params: { [key: string]: string | boolean }) => {
          deleteData(params), setDeleteDataDialogOpened(false);
        }}
        hierarchiArray={(connection + '.' + schema + '.' + table).split('.')}
        selectedReference={selectedReference}
        checkTypes={checkTypes}
      />
    </div>
  );
}
