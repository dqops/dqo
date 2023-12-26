import React, { useEffect, useState } from 'react';
import Input from '../../../components/Input';
import Button from '../../../components/Button';
import { SchemaApiClient } from '../../../services/apiClient';
import { CheckTypes } from '../../../shared/routes';
import { IFilterTemplate } from '../../../shared/constants';
import { CheckTemplate } from '../../../api';
import Checkbox from '../../../components/Checkbox';

interface IMultiChecksSearch {
  checkTypes: CheckTypes;
  filterParameters: IFilterTemplate;
  onChangeFilterParameters: (obj: Partial<IFilterTemplate>) => void;
  onChangeChecks: (checks: CheckTemplate[]) => void;
  isUpdated: boolean;
}

export default function MultiChecksSearch({
  checkTypes,
  filterParameters,
  onChangeFilterParameters,
  onChangeChecks,
  isUpdated
}: IMultiChecksSearch) {
  const [activeOffCheck, setActiveOffChecks] = useState(false);

  const searchChecks = () => {
    const {
      connection,
      schema,
      activeTab,
      tableNamePattern,
      columnNamePattern,
      columnDataType,
      checkTarget,
      checkCategory,
      checkName
    } = filterParameters;

    if (checkTypes === CheckTypes.PROFILING) {
      SchemaApiClient.getSchemaProfilingChecksModel(
        connection,
        schema,
        tableNamePattern,
        columnNamePattern,
        columnDataType,
        checkTarget,
        checkCategory,
        checkName,
        undefined,
        activeOffCheck ? undefined : true
      ).then((res) => {
        onChangeChecks(res.data);
      });
    } else if (checkTypes === CheckTypes.MONITORING && activeTab) {
      SchemaApiClient.getSchemaMonitoringChecksModel(
        connection,
        schema,
        activeTab,
        tableNamePattern,
        columnNamePattern,
        columnDataType,
        checkTarget,
        checkCategory,
        checkName,
        undefined,
        activeOffCheck ? undefined : true
      ).then((res) => {
        onChangeChecks(res.data);
      });
    } else if (checkTypes === CheckTypes.PARTITIONED && activeTab) {
      SchemaApiClient.getSchemaPartitionedChecksModel(
        connection,
        schema,
        activeTab,
        tableNamePattern,
        columnNamePattern,
        columnDataType,
        checkTarget,
        checkCategory,
        checkName,
        undefined,
        activeOffCheck ? undefined : true
      ).then((res) => {
        onChangeChecks(res.data);
      });
    }
  };

  useEffect(() => {
    if (filterParameters.checkCategory && filterParameters.checkName) {
      searchChecks();
    }
  }, [isUpdated]);

  return (
    <div className="flex w-full ">
      <div className="w-1/4">
        <div className="max-w-120">
          <Input
            value={filterParameters.tableNamePattern}
            label="Table name"
            placeholder="Enter table name pattern"
            onChange={(e) =>
              onChangeFilterParameters({ tableNamePattern: e.target.value })
            }
          />
        </div>
      </div>
      <div className="w-1/4 px-10">
        <div className="max-w-120">
          <Input
            value={filterParameters.columnNamePattern}
            label="Column name"
            placeholder="Enter column name pattern"
            onChange={(e) =>
              onChangeFilterParameters({
                columnNamePattern: e.target.value
              })
            }
            disabled={filterParameters.checkTarget === 'table'}
          />
        </div>
      </div>
      <div className="w-1/4 px-10">
        <div className="max-w-120">
          <Input
            value={filterParameters.columnDataType}
            label="Column type"
            placeholder="Enter column type"
            onChange={(e) =>
              onChangeFilterParameters({ columnDataType: e.target.value })
            }
            disabled={filterParameters.checkTarget === 'table'}
          />
        </div>
      </div>
      <div className="w-1/4 flex items-center gap-x-8 justify-end">
        <Checkbox
          checked={activeOffCheck}
          onChange={() => setActiveOffChecks((prev) => !prev)}
          label={"Include also 'off' checks"}
        />
        <Button
          label="Search"
          color={filterParameters.checkName ? 'primary' : 'secondary'}
          onClick={searchChecks}
        />
      </div>
    </div>
  );
}
