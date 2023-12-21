import React, { useState } from 'react';
import { CheckConfigurationModel, CheckTemplate } from '../../../../api';
import Checkbox from '../../../../components/Checkbox';
import DisplaySensorParameters from '../../DisplaySensorParameters';
import FieldValue from '../../FieldValue';
import Button from '../../../../components/Button';
import { ConnectionApiClient } from '../../../../services/apiClient';
import { isEqual } from 'lodash';
import { UpdateCheckModel } from '../../UpdateCheckModel';

type TMultiChecksTable = {
  checkTarget: 'column' | 'table' | undefined;
  checks: CheckConfigurationModel[] | undefined;
  filterParameters: IFilterTemplate;
};

interface IFilterTemplate {
  connection: string;
  schema: string;
  activeTab: 'daily' | 'monthly';
  tableNamePattern?: string | undefined;
  columnNamePattern?: string | undefined;
  columnDataType?: string | undefined;
  checkTarget?: 'table' | 'column' | undefined;
  checkCategory?: string | undefined;
  checkName?: string | undefined;
}

export default function MultiChecksTable({
  checkTarget,
  checks,
  filterParameters
}: TMultiChecksTable) {
  const [selectedData, setSelectedData] = useState<CheckConfigurationModel[]>(
    []
  );
  const [open, setOpen] = useState(false);
  const [selectedCheck, setSelectedCheck] = useState<CheckTemplate>(); // TODO: this component is fundamentally wrong, it should be editing a CheckTemplate (a clone of the check template), not a CheckConfigurationModel. CheckTemplate is a template of parameters to apply on all checks (for bulk), while the CheckConfigurationModel is a current configuration of the check on one table or one column (current configuration)
  // TODO: change it to ChangeTemplate, and change the selected  check template (that will be edited) when user changes a check in the combo box for selecting a check

  const selectAll = () => {
    setSelectedData(checks || []);
  };

  const deselectAll = () => {
    setSelectedData([]);
  };

  const bulkEnableChecks = () => {
    // TODO: this code has two bugs, one is here
    // TODO: before enabling checks, we need to take the current CheckTemplate (for the check selected in the combo box of checks), open the editor and wait until the user edits the configuration... but the user can cancel the editor,
    // TODO: only after the checkModel (inside the selected CheckTemplate) is edited, we can bulk enable the check, also passing the new configuration of sensor and rule parameters

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
        // TODO: pass the CheckModel here, with the configuration of the sensor parameters and rule parameters, the model that should be edited and copied here should be from the selectedCheck (which shoudl be changed to CheckTemplate). CheckTemplate class has  a "checkModel" object which is the same model used on the main check editor screen.
        selected_tables_to_columns: {
          ...(filterParameters.checkTarget === 'table'
            ? {
                table: Array.from(
                  new Set(selectedData.map((item) => item.table_name ?? ''))
                )
              }
            : {
                column: Array.from(
                  new Set(selectedData.map((item) => item.column_name ?? ''))
                )
              })
        },
        override_conflicts: true
      }
    );
  };

  const bulkDisableChecks = () => {
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
        selected_tables_to_columns: {
          ...(filterParameters.checkTarget === 'table'
            ? {
                table: Array.from(
                  new Set(selectedData.map((item) => item.table_name ?? ''))
                )
              }
            : {
                column: Array.from(
                  new Set(selectedData.map((item) => item.column_name ?? ''))
                )
              })
        }
      }
    );
  };

  const onChangeSelection = (check: CheckConfigurationModel) => {
    if (selectedData.find((item) => isEqual(item, check))) {
      setSelectedData(selectedData.filter((item) => !isEqual(item, check)));
    } else {
      setSelectedCheck(check);
      setSelectedData([...selectedData, check]);
    }
  };
  const onChangeSelectedData = (check: CheckConfigurationModel) => {
    setSelectedData(
      selectedData.map((item) =>
        check.check_name === item.check_name ? check : item
      )
    );
  };

  return (
    <div className="border border-gray-300 rounded-lg p-4 my-4">
      <div className="flex justify-end gap-4">
        <Button
          className="text-sm py-2.5"
          label="Select All"
          color="primary"
          onClick={selectAll}
        />
        <Button
          className="text-sm py-2.5"
          label="Unselect All"
          color="secondary"
          onClick={deselectAll}
        />
        <Button
          className="text-sm py-2.5"
          label="Update selected"
          disabled={!selectedData.length}
          color="primary"
          onClick={bulkEnableChecks}
        />
        <Button
          className="text-sm py-2.5"
          label="Update all"
          color="primary"
          onClick={bulkEnableChecks}
        />
        <Button
          className="text-sm py-2.5"
          label="Disable selected"
          color="primary"
          disabled={!selectedData.length}
          onClick={bulkDisableChecks}
        />
        <Button
          className="text-sm py-2.5"
          label="Disable all"
          variant="outlined"
          color="primary"
          onClick={bulkDisableChecks}
        />
      </div>
      <table className="w-full mt-8">
        <thead>
          <tr>
            <th className="px-4 py-2 text-left"></th>
            <th className="px-4 py-2 text-left">Table</th>
            {checkTarget === 'column' && (
              <th className="px-4 py-2 text-left">Column</th>
            )}
            <th className="px-4 py-2 text-left">Sensor parameters</th>
            <th className="px-4 py-2 text-left">Warning threshold</th>
            <th className="px-4 py-2 text-left">Error threshold</th>
            <th className="px-4 py-2 text-left">Fatal threshold</th>
          </tr>
        </thead>
        <tbody>
          {checks?.map((check, index) => (
            <tr key={index}>
              <td className="px-4 py-2 text-left">
                <div className="flex">
                  <Checkbox
                    onChange={() => onChangeSelection(check)}
                    checked={selectedData.includes(check)}
                  />
                </div>
              </td>
              <td className="px-4 py-2 text-left">{check.table_name}</td>
              {checkTarget === 'column' && (
                <td className="px-4 py-2 text-left">{check.column_name}</td>
              )}
              <td className="px-4 py-2 text-left">
                <DisplaySensorParameters
                  parameters={check.sensor_parameters || []}
                />
              </td>
              <td className="px-4 py-2 text-left truncate">
                {check.warning?.rule_parameters?.map((item, index) => (
                  <div key={index}>
                    <FieldValue field={item} />
                  </div>
                ))}
              </td>
              <td className="px-4 py-2 text-left truncate">
                {check.error?.rule_parameters?.map((item, index) => (
                  <div key={index}>
                    <FieldValue field={item} />
                  </div>
                ))}
              </td>
              <td className="px-4 py-2 text-left truncate">
                {check.fatal?.rule_parameters?.map((item, index) => (
                  <div key={index}>
                    <FieldValue field={item} />
                  </div>
                ))}
              </td>
            </tr>
          ))}
        </tbody>
      </table>
      <UpdateCheckModel // TODO: this component is fundamentally wrong, it should be editing a CheckTemplate (a clone of the check template), not a CheckConfigurationModel. CheckTemplate is a template of parameters to apply on all checks (for bulk), while the CheckConfigurationModel is a current configuration of the check on one table or one column (current configuration)
        open={open}
        onClose={() => setOpen(false)}
        check={selectedCheck}
        onSubmit={onChangeSelectedData}
      />
    </div>
  );
}
