import React, { useEffect, useMemo, useState } from 'react';
import { Dialog, DialogBody, DialogFooter } from '@material-tailwind/react';
import Button from '../../components/Button';
import {
  CheckConfigurationModel,
  CheckModel,
  CheckTemplate,
  FieldModel
} from '../../api';
import SensorParameters from '../../components/DataQualityChecks/SensorParameters';
import Checkbox from '../../components/Checkbox';
import CheckRuleItem from '../../components/DataQualityChecks/CheckRuleItem';
import { ConnectionApiClient } from '../../services/apiClient';
import { IFilterTemplate } from '../../shared/constants';

interface UpdateCheckModelProps {
  open: boolean;
  onClose: () => void;
  action: 'bulkEnabled' | 'bulkDisabled';
  selectedCheckModel: CheckModel | undefined;
  filterParameters: IFilterTemplate;
  selectedData: CheckTemplate[];
}

export const UpdateCheckModel = ({
  open,
  onClose,
  action,
  selectedCheckModel,
  filterParameters,
  selectedData
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
  const bulkEnableChecks = () => {
    //TODO: this code has two bugs, one is here
    //TODO: before enabling checks, we need to take the current CheckTemplate (for the check selected in the combo box of checks), open the editor and wait until the user edits the configuration... but the user can cancel the editor,
    //TODO: only after the checkModel (inside the selected CheckTemplate) is edited, we can bulk enable the check, also passing the new configuration of sensor and rule parameters
    const selected_tables_to_columns =
      filterParameters.checkTarget === 'table'
        ? { ...mapTables }
        : { ...mapTableColumns };
    ConnectionApiClient.bulkEnableConnectionChecks(
      filterParameters.connection,
      filterParameters.checkName ?? '',
      {
        check_search_filters: {
          connection: filterParameters.connection,
          fullTableName: filterParameters.schema,
          checkTarget: filterParameters.checkTarget,
          columnDataType: filterParameters.columnDataType,
          checkName: filterParameters.checkName,
          checkCategory: filterParameters.checkCategory
        },
        check_model_patch: updatedCheck,
        // TODO: pass the CheckModel here, with the configuration of the sensor parameters and rule parameters, the model that should be edited and copied here should be from the selectedCheck (which shoudl be changed to CheckTemplate). CheckTemplate class has  a "checkModel" object which is the same model used on the main check editor screen.
        selected_tables_to_columns,
        override_conflicts: overideConflicts
      }
    );
  };

  const bulkDisableChecks = () => {
    const selected_tables_to_columns =
      filterParameters.checkTarget === 'table'
        ? { ...mapTables }
        : { ...mapTableColumns };
    ConnectionApiClient.bulkDisableConnectionChecks(
      filterParameters.connection,
      filterParameters.checkName ?? '',
      {
        check_search_filters: {
          connection: filterParameters.connection,
          fullTableName: filterParameters.schema,
          checkTarget: filterParameters.checkTarget,
          columnDataType: filterParameters.columnDataType,
          checkName: filterParameters.checkName,
          checkCategory: filterParameters.checkCategory
        },
        selected_tables_to_columns
      }
    );
  };
  // console.log(checks, updatedCheck);
  // console.log(updatedCheck);

  const bulkChecks = (): void => {
    action === 'bulkEnabled' ? bulkEnableChecks() : bulkDisableChecks();
    onClose();
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
    <Dialog open={open} handler={onClose} className="min-w-150 max-w-150">
      <DialogBody className="pt-10 pb-2 px-8">
        <div className="w-full flex flex-col items-center">
          <h1 className="text-center mb-4 text-gray-700 text-2xl">
            {action === 'bulkEnabled' ? 'Update Check:' : 'Disable Check: '}{' '}
            {updatedCheck?.check_name}
          </h1>
        </div>
        {updatedCheck?.sensor_parameters &&
          updatedCheck.sensor_parameters.length > 0 && (
            <div className="relative z-10 border rounded text-gray-700 border-gray-300 px-4 py-4  w-50">
              <p className="text-gray-700 text-lg mb-4">Sensor parameters</p>
              <SensorParameters
                parameters={updatedCheck?.sensor_parameters || []}
                onChange={(parameters: FieldModel[]) =>
                  handleChange({ sensor_parameters: parameters })
                }
                onUpdate={() => undefined}
              />
            </div>
          )}
        <div className="grid grid-cols-3 my-4">
          <div className="bg-yellow-100 border border-gray-300 py-2">
            <CheckRuleItem
              parameters={updatedCheck?.rule?.warning}
              onChange={(warning) =>
                handleChange({
                  rule: {
                    ...updatedCheck?.rule,
                    warning: warning
                  }
                })
              }
              type="warning"
              onUpdate={() => {}}
            />
          </div>
          <div className="bg-orange-100 border border-gray-300 py-2">
            <CheckRuleItem
              parameters={updatedCheck?.rule?.error}
              onChange={(error) =>
                handleChange({
                  rule: {
                    ...updatedCheck?.rule,
                    error: error
                  }
                })
              }
              type="error"
              onUpdate={() => {}}
            />
          </div>
          <div className="bg-red-100 border border-gray-300 py-2">
            <CheckRuleItem
              parameters={updatedCheck?.rule?.fatal}
              onChange={(fatal) =>
                handleChange({
                  rule: {
                    ...updatedCheck?.rule,
                    fatal
                  }
                })
              }
              type="fatal"
              onUpdate={() => {}}
            />
          </div>
        </div>
        <div className="text-gray-700">
          <Checkbox
            label="Override existing configurations"
            checked={overideConflicts}
            onChange={() => setOverrideConflicts((prev) => !prev)}
          />
        </div>
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
              ? 'Update all selected checks'
              : 'Disable all selected checks'
          }
        />
      </DialogFooter>
    </Dialog>
  );
};
