import React, { useEffect, useMemo, useState } from 'react';
import { Dialog, DialogBody, DialogFooter } from '@material-tailwind/react';
import Button from '../../components/Button';
import {
  CheckConfigurationModel,
  CheckModel,
  CheckSearchFiltersCheckTypeEnum,
  CheckTemplate
} from '../../api';
import Checkbox from '../../components/Checkbox';
import { ConnectionApiClient } from '../../services/apiClient';
import { IFilterTemplate } from '../../shared/constants';
import UpdateCheckRuleSensor from './MultiCheck/UpdateCheckRuleSensor';
import { CheckTypes } from '../../shared/routes';

interface UpdateCheckModelProps {
  open: boolean;
  onClose: () => void;
  action: 'bulkEnabled' | 'bulkDisabled';
  selectedCheckModel: CheckModel | undefined;
  filterParameters: IFilterTemplate;
  selectedData: CheckTemplate[];
  onChangeIsUpdated: () => void;
  onChangeLoading: (param: boolean) => void;
}

export const UpdateCheckModel = ({
  open,
  onClose,
  action,
  selectedCheckModel,
  filterParameters,
  selectedData,
  onChangeIsUpdated,
  onChangeLoading
}: UpdateCheckModelProps) => {
  const [updatedCheck, setUpdatedCheck] = useState<CheckModel>();
  const [overideConflicts, setOverrideConflicts] = useState(true);

  useEffect(() => {
    setUpdatedCheck(selectedCheckModel);
  }, [selectedCheckModel]);

  const handleChange = (obj: Partial<CheckModel>) => {
    setUpdatedCheck((prev) => ({
      ...prev,
      ...obj
    }));
  };
  const bulkActivateChecks = () => {
    onChangeLoading(true);
    const selected_tables_to_columns =
      filterParameters.checkTarget === 'table'
        ? { ...mapTables }
        : { ...mapTableColumns };
    ConnectionApiClient.bulkActivateConnectionChecks(
      filterParameters.connection,
      filterParameters.checkName ?? '',
      {
        check_search_filters: {
          connection: filterParameters.connection,
          fullTableName: filterParameters.schema + '.*',
          checkTarget: filterParameters.checkTarget,
          columnDataType: filterParameters.columnDataType,
          checkName: filterParameters.checkName,
          checkCategory: filterParameters.checkCategory,
          checkType:
            filterParameters.checkTypes as CheckSearchFiltersCheckTypeEnum,
          timeScale:
            filterParameters.checkTypes !== CheckTypes.PROFILING
              ? filterParameters.activeTab
              : undefined
        },
        check_model_patch: updatedCheck,
        selected_tables_to_columns,
        override_conflicts: overideConflicts
      }
    ).finally(() => onChangeLoading(false));
  };

  const bulkDeactivateChecks = () => {
    onChangeLoading(true);
    const selected_tables_to_columns =
      filterParameters.checkTarget === 'table'
        ? { ...mapTables }
        : { ...mapTableColumns };
    ConnectionApiClient.bulkDeactivateConnectionChecks(
      filterParameters.connection,
      filterParameters.checkName ?? '',
      {
        check_search_filters: {
          connection: filterParameters.connection,
          fullTableName: filterParameters.schema + '.*',
          checkTarget: filterParameters.checkTarget,
          columnDataType: filterParameters.columnDataType,
          checkName: filterParameters.checkName,
          checkCategory: filterParameters.checkCategory,
          checkType:
            filterParameters.checkTypes as CheckSearchFiltersCheckTypeEnum,
          timeScale:
            filterParameters.checkTypes !== CheckTypes.PROFILING
              ? filterParameters.activeTab
              : undefined
        },
        selected_tables_to_columns
      }
    ).finally(() => onChangeLoading(false));
  };

  const bulkChecks = (): void => {
    action === 'bulkEnabled' ? bulkActivateChecks() : bulkDeactivateChecks();
    onClose();
    onChangeIsUpdated();
  };

  const mapTableColumns = useMemo(() => {
    const checksCopy = [...(selectedData as CheckConfigurationModel[])];
    const mappedTableColumns: { [key: string]: Array<string> } = {};

    for (const key in checksCopy) {
      const tableName = checksCopy[key].table_name;
      const columnName = checksCopy[key].column_name;
      if (tableName && columnName) {
        if (!mappedTableColumns[tableName]) {
          mappedTableColumns[tableName] = [columnName];
        } else {
          mappedTableColumns[tableName].push(columnName);
        }
      }
    }
    return mappedTableColumns;
  }, [selectedData]);

  const mapTables = useMemo(() => {
    const checksCopy = [...(selectedData as CheckConfigurationModel[])];
    const mappedTables: { [key: string]: Array<string> } = {};
    checksCopy.forEach((x) => {
      if (x.table_name !== undefined) {
        mappedTables[x.table_name] = [];
      }
    });
    return mappedTables;
  }, [selectedData]);

  return (
    <Dialog open={open} handler={onClose} className="min-w-240 max-w-240">
      <DialogBody className="pt-10 pb-2 px-8">
        <div className="w-full flex flex-col items-center">
          <h1 className="text-center mb-4 text-gray-700 text-2xl">
            {action === 'bulkEnabled'
              ? 'Activate Check:'
              : 'Deactivate Check: '}{' '}
            {updatedCheck?.check_name}
          </h1>
        </div>
        {action === 'bulkEnabled' && (
          <UpdateCheckRuleSensor
            updatedCheck={updatedCheck ?? {}}
            handleChange={handleChange}
          />
        )}
        {action === 'bulkEnabled' && (
          <div className="text-gray-700">
            <Checkbox
              label="Override existing configurations"
              checked={overideConflicts}
              onChange={() => setOverrideConflicts((prev) => !prev)}
            />
          </div>
        )}
      </DialogBody>

      <DialogFooter className="flex justify-center space-x-6 px-8 pb-8">
        <Button
          color="primary"
          variant="outlined"
          className="px-8"
          onClick={onClose}
          label="Cancel"
        />
        <Button
          color="primary"
          className="px-8"
          onClick={bulkChecks}
          label={
            action === 'bulkEnabled'
              ? 'Activate all selected checks'
              : 'Deactivate all selected checks'
          }
        />
      </DialogFooter>
    </Dialog>
  );
};
