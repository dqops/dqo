import React, { useEffect, useState } from "react";
import Select, { Option } from "../../components/Select";
import Checkbox from "../../components/Checkbox";
import Input from "../../components/Input";
import Button from "../../components/Button";
import { useParams } from "react-router-dom";
import { CheckTypes } from "../../shared/routes";
import { ConnectionApiClient, SchemaApiClient } from "../../services/apiClient";
import { CheckConfigurationModel, CheckTemplate } from "../../api";
import Tabs from "../../components/Tabs";
import { AxiosResponse } from "axios";
import DisplaySensorParameters from "./DisplaySensorParameters";
import FieldValue from "./FieldValue";
import { isEqual } from "lodash";
import { UpdateCheckModel } from "./UpdateCheckModel";

const tabs = [
  {
    label: 'Daily',
    value: 'daily'
  },
  {
    label: 'Monthly',
    value: 'monthly'
  }
];

export const MultiChecks = () => {
  const { checkTypes, connection, schema }: { checkTypes:CheckTypes, connection: string, schema: string } = useParams()
  const [checkCategoryOptions, setCheckCategoryOptions] = useState<Option[]>([]);
  const [checkNameOptions, setCheckNameOptions] = useState<Option[]>([]);
  const [checkTarget, setCheckTarget] = useState<'table' | 'column' | undefined>('table');
  const [checkCategory, setCheckCategory] = useState<string>();
  const [checkName, setCheckName] = useState<string>();
  const [tableNamePattern, setTableNamePattern] = useState<string>();
  const [columnNamePattern, setColumnNamePattern] = useState<string>();
  const [columnDataType, setColumnDataType] = useState<string>();
  const [checks, setChecks] = useState<CheckConfigurationModel[]>();
  const [activeTab, setActiveTab] = useState<'daily' | 'monthly'>('daily');
  const [selectedData, setSelectedData] = useState<CheckConfigurationModel[]>([]);
  const [open, setOpen] = useState(false);
  const [selectedCheck, setSelectedCheck] = useState<CheckConfigurationModel>();

  useEffect(() => {
    const processResult = (res: AxiosResponse<CheckTemplate[]>) => {
      const possibleCategories = Array.from(new Set(res.data.map((item) => item.check_category)));
      const possibleCheckNames = Array.from(new Set(res.data.map((item) => item.check_name)));

      setCheckCategoryOptions(possibleCategories.map((item) => ({
        label: item ?? '',
        value: item ?? ''
      })));
      setCheckNameOptions(possibleCheckNames.map((item) => ({
        label: item ?? '',
        value: item ?? ''
      })));
    };

    if (checkTypes === CheckTypes.PROFILING) {
      SchemaApiClient.getSchemaProfilingChecksTemplates(connection, schema, checkTarget).then(processResult);
    } else if (checkTypes === CheckTypes.RECURRING) {
      SchemaApiClient.getSchemaRecurringChecksTemplates(connection, schema, activeTab, checkTarget).then(processResult);
    } else if (checkTypes === CheckTypes.PARTITIONED) {
      SchemaApiClient.getSchemaPartitionedChecksTemplates(connection, schema, activeTab, checkTarget).then(processResult);
    }
  }, [connection, schema, checkTypes, checkTarget]);

  const searchChecks = () => {
    if (checkTypes === CheckTypes.PROFILING) {
      SchemaApiClient.getSchemaProfilingChecksModel(
        connection,
        schema,
        tableNamePattern,
        columnNamePattern,
        columnDataType,
        checkTarget,
        checkCategory,
        checkName
      ).then((res) => {
        setChecks(res.data);
      });
    } else if (checkTypes === CheckTypes.RECURRING) {
      SchemaApiClient.getSchemaRecurringChecksModel(
        connection,
        schema,
        activeTab,
        tableNamePattern,
        columnNamePattern,
        columnDataType,
        checkTarget,
        checkCategory,
        checkName
      ).then((res) => {
        setChecks(res.data);
      });
    } else if (checkTypes === CheckTypes.PARTITIONED) {
      SchemaApiClient.getSchemaPartitionedChecksModel(
        connection,
        schema,
        activeTab,
        tableNamePattern,
        columnNamePattern,
        columnDataType,
        checkTarget,
        checkCategory,
        checkName
      ).then((res) => {
        setChecks(res.data);
      });
    }
  };


  const bulkEnableChecks = () => {
    ConnectionApiClient.bulkEnableConnectionChecks(connection, checkName ?? '', {
      check_search_filters: {
        connectionName: connection,
        schemaTableName: schema,
        checkTarget,
        columnDataType,
        checkName,
        checkCategory,
      },
      selected_tables_to_columns: {
        ...checkTarget === 'table' ? {
          table: Array.from(new Set(selectedData.map((item) => item.table_name ?? '')))
        } : {
          column: Array.from(new Set(selectedData.map((item) => item.column_name ?? '')))
        }
      },
      override_conflicts: true
    });
  };

  const bulkDisableChecks = () => {
    ConnectionApiClient.bulkDisableConnectionChecks(connection, checkName ?? '', {
      check_search_filters: {
        connectionName: connection,
        schemaTableName: schema,
        checkTarget,
        columnDataType,
        checkName,
        checkCategory,
      },
      selected_tables_to_columns: {
        ...checkTarget === 'table' ? {
          table: Array.from(new Set(selectedData.map((item) => item.table_name ?? '')))
        } : {
          column: Array.from(new Set(selectedData.map((item) => item.column_name ?? '')))
        }
      },
    });
  }

  const onChangeSelectedData = (check: CheckConfigurationModel) => {
    setSelectedData(selectedData.map((item) => check.check_name === item.check_name ? check : item));
  };

  const openDialog = () => {
    setOpen(true);
  }

  const selectAll = () => {
    setSelectedData(checks || [])
  };

  const deselectAll = () => {
    setSelectedData([]);
  };

  const onChangeSelection = (check: CheckConfigurationModel) => {
    if (selectedData.find(item => isEqual(item, check))) {
      setSelectedData(selectedData.filter((item) => !isEqual(item, check)));
    } else {
      setSelectedCheck(check);
      setOpen(true);
      setSelectedData([...selectedData, check]);
    }
  }

  return (
    <div className="text-sm py-4">
      {checkTypes !== CheckTypes.PROFILING && (
        <div className="border-b border-gray-300 pb-0 mb-4">
          <Tabs tabs={tabs} activeTab={activeTab} onChange={setActiveTab} />
        </div>
      )}
      <div className="px-8">
        <div className="flex gap-8 mb-6">
          <div className="w-80">
            <Select
              label="Check category"
              options={checkCategoryOptions}
              value={checkCategory}
              onChange={setCheckCategory}
            ></Select>
          </div>
          <div className="flex flex-col gap-3">
            <p>Check target</p>
            <Checkbox
              label="Table"
              onChange={(checked) => setCheckTarget(checked ? 'table' : undefined)}
              checked={checkTarget === 'table'}
            />
            <Checkbox
              label="Column"
              onChange={(checked) => setCheckTarget(checked ? 'column' : undefined)}
              checked={checkTarget === 'column'}
            />
          </div>
        </div>
        <div className="max-w-120">
          <Select
            options={checkNameOptions}
            label="Check name"
            value={checkName}
            onChange={setCheckName}
          />
        </div>
        <hr className="my-4 border-gray-300" />

        <div className="max-w-120 mb-4">
          <Input
            value={tableNamePattern}
            label="Table name"
            placeholder="Enter table name pattern"
            onChange={(e) => setTableNamePattern(e.target.value)}
          />
        </div>
        <div className="max-w-120 mb-4">
          <Input
            value={columnNamePattern}
            label="Column name"
            placeholder="Enter column name pattern"
            onChange={(e) => setColumnNamePattern(e.target.value)}
          />
        </div>
        <div className="flex items-end justify-between">
          <div className="max-w-120">
            <Input
              value={columnDataType}
              label="Column type"
              placeholder="Enter column type"
              onChange={(e) => setColumnDataType(e.target.value)}
            />
          </div>

          <Button
            label="Search"
            color="primary"
            onClick={searchChecks}
          />
        </div>

        <div className="border border-gray-300 rounded-lg p-4 my-4">
          <div className="flex justify-end gap-4">
            <Button
              className="text-sm py-2.5" label="Select All" color="primary"
              onClick={selectAll}
            />
            <Button
              className="text-sm py-2.5" label="Unselect All" color="secondary"
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

          <div className="mt-6">
            <table className="w-full">
              <thead>
              <tr>
                <th className="px-4 py-2 text-left">
                </th>
                <th className="px-4 py-2 text-left">
                  Table
                </th>
                {checkTarget === 'column' && (
                  <th className="px-4 py-2 text-left">
                    Column
                  </th>
                )}
                <th className="px-4 py-2 text-left">
                  Sensor parameters
                </th>
                <th className="px-4 py-2 text-left">
                  Warning threshold
                </th>
                <th className="px-4 py-2 text-left">
                  Error threshold
                </th>
                <th className="px-4 py-2 text-left">
                  Fatal threshold
                </th>
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
                  <td className="px-4 py-2 text-left">
                    {check.table_name}
                  </td>
                  {checkTarget === 'column' && (
                    <td className="px-4 py-2 text-left">
                      {check.column_name}
                    </td>
                  )}
                  <td className="px-4 py-2 text-left">
                    <DisplaySensorParameters parameters={check.sensor_parameters || []} />
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
          </div>
        </div>
      </div>

      <UpdateCheckModel
        open={open}
        onClose={() => setOpen(false)}
        check={selectedCheck}
        onSubmit={onChangeSelectedData}
      />
    </div>
  );
};
