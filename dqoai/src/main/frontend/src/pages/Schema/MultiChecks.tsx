import React, { useEffect, useState } from "react";
import Select, { Option } from "../../components/Select";
import Checkbox from "../../components/Checkbox";
import Input from "../../components/Input";
import Button from "../../components/Button";
import { useParams } from "react-router-dom";
import { CheckTypes } from "../../shared/routes";
import { ConnectionApiClient, SchemaApiClient } from "../../services/apiClient";
import { AllChecksModel, CheckTemplate } from "../../api";
import Tabs from "../../components/Tabs";
import { AxiosResponse } from "axios";

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
  const [checkCategory, setCheckCategory] = useState('');
  const [checkName, setCheckName] = useState('');
  const [tableNamePattern, setTableNamePattern] = useState('');
  const [columnNamePattern, setColumnNamePattern] = useState('');
  const [columnDataType, setColumnDataType] = useState('');
  const [checks, setChecks] = useState<AllChecksModel>();
  const [activeTab, setActiveTab] = useState<'daily' | 'monthly'>('daily');
  const [selectedData, setSelectedData] = useState<string[]>([]);

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
      }).catch(err => {

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
      }).catch(err => {

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
      }).catch(err => {

      });
    }
  };


  const bulkEnableChecks = () => {
    ConnectionApiClient.bulkEnableConnectionChecks(connection, schema)
  };

  const selectAll = () => {
    if (checkTarget === 'table') {
      setSelectedData(
        checks
          ?.table_checks_model
          ?.schema_table_checks_models
          ?.map(schemaTable => schemaTable.table_checks_models?.map(item => item.table_name || '') || [])
          ?.reduce((arr, item) => [...arr, ...item], [])
        || []
      )
    } else {
      setSelectedData(
        checks
          ?.column_checks_model
          ?.table_column_checks_models
          ?.map(tableColumn => tableColumn.column_checks_models?.map(item => item.column_name || '') || [])
          ?.reduce((arr, item) => [...arr, ...item], [])
        || []
      )
    }
  };

  const deselectAll = () => {
    setSelectedData([]);
  };

  const onChangeSelection = (name: string) => {
    if (selectedData.includes(name)) {
      setSelectedData(selectedData.filter((item) => item !== name));
    } else {
      setSelectedData([...selectedData, name]);
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
            <Button className="text-sm py-2.5" label="Update selected" disabled color="primary" />
            <Button className="text-sm py-2.5" label="Update all" color="primary" />
            <Button className="text-sm py-2.5" label="Disable selected" color="primary" />
            <Button className="text-sm py-2.5" label="Disable all" variant="outlined" color="primary" />
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
              {checkTarget === 'column' ? (
                <>
                  {checks?.column_checks_model?.table_column_checks_models?.map((columnTable, index) => (
                    <React.Fragment key={index}>
                      {columnTable?.column_checks_models?.map((columnCheck, index2) => (
                        <tr key={index2}>
                          <td className="px-4 py-2 text-left">
                            <div className="flex">
                              <Checkbox
                                onChange={() => onChangeSelection(columnCheck.column_name ?? '')}
                                checked={selectedData.includes(columnCheck.column_name || '')}
                              />
                            </div>
                          </td>
                          <td className="px-4 py-2 text-left">
                            {columnTable.schema_table_name?.table_name}
                          </td>
                          <td className="px-4 py-2 text-left">
                            {columnCheck.column_name}
                          </td>
                          <td className="px-4 py-2 text-left">
                            {/*{check.sensor_name}*/}
                          </td>
                          <td className="px-4 py-2 text-left truncate">
                            {/*{check.rule?.warning?.rule_name}*/}
                          </td>
                          <td className="px-4 py-2 text-left truncate">
                            {/*{check.rule?.error?.rule_name}*/}
                          </td>
                          <td className="px-4 py-2 text-left truncate">
                            {/*{check.rule?.fatal?.rule_name}*/}
                          </td>
                        </tr>
                      ))}
                    </React.Fragment>
                  ))}
                </>
              ) : (
                <>
                  {checks?.table_checks_model?.schema_table_checks_models?.map((schemaTable, index) => (
                    <React.Fragment key={index}>
                      {schemaTable?.table_checks_models?.map((tableCheck, index2) => (
                        <tr key={index2}>
                          <td className="px-4 py-2 text-left">
                            <div className="flex">
                              <Checkbox
                                onChange={() => onChangeSelection(tableCheck.table_name ?? '')}
                                checked={selectedData.includes(tableCheck.table_name || '')}
                              />
                            </div>
                          </td>
                          <td className="px-4 py-2 text-left">
                            {tableCheck.table_name}
                          </td>
                          <td className="px-4 py-2 text-left">
                            {/*{check.sensor_name}*/}
                          </td>
                          <td className="px-4 py-2 text-left truncate">
                            {/*{check.rule?.warning?.rule_name}*/}
                          </td>
                          <td className="px-4 py-2 text-left truncate">
                            {/*{check.rule?.error?.rule_name}*/}
                          </td>
                          <td className="px-4 py-2 text-left truncate">
                            {/*{check.rule?.fatal?.rule_name}*/}
                          </td>
                        </tr>
                      ))}
                    </React.Fragment>
                  ))}
                </>
              )}
              </tbody>
            </table>
          </div>
        </div>
      </div>
    </div>
  );
};
