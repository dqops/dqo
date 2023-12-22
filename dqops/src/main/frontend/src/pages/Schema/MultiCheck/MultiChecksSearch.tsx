import React from 'react';
import Input from '../../../components/Input';
import Button from '../../../components/Button';
import { SchemaApiClient } from '../../../services/apiClient';
import { CheckTypes } from '../../../shared/routes';
import { IFilterTemplate } from '../../../shared/constants';
import { CheckTemplate } from '../../../api';

interface IMultiChecksSearch {
  checkTypes: CheckTypes;
  filterParameters: IFilterTemplate;
  onChangeFilterParameters: (obj: Partial<IFilterTemplate>) => void;
  onChangeChecks: (checks: CheckTemplate[]) => void;
}

export default function MultiChecksSearch({
  checkTypes,
  filterParameters,
  onChangeFilterParameters,
  onChangeChecks
}: IMultiChecksSearch) {
  const searchChecks = () => {
    if (checkTypes === CheckTypes.PROFILING) {
      SchemaApiClient.getSchemaProfilingChecksModel(
        filterParameters.connection,
        filterParameters.schema,
        filterParameters.tableNamePattern,
        filterParameters.columnNamePattern,
        filterParameters.columnDataType,
        filterParameters.checkTarget,
        filterParameters.checkCategory,
        filterParameters.checkName,
        undefined,
        true,
        undefined
      ).then((res) => {
        onChangeChecks(res.data);
      });
    } else if (
      checkTypes === CheckTypes.MONITORING &&
      filterParameters.activeTab
    ) {
      SchemaApiClient.getSchemaMonitoringChecksModel(
        filterParameters.connection,
        filterParameters.schema,
        filterParameters.activeTab,
        filterParameters.tableNamePattern,
        filterParameters.columnNamePattern,
        filterParameters.columnDataType,
        filterParameters.checkTarget,
        filterParameters.checkCategory,
        filterParameters.checkName,
        undefined,
        true,
        undefined
      ).then((res) => {
        onChangeChecks(res.data);
      });
    } else if (
      checkTypes === CheckTypes.PARTITIONED &&
      filterParameters.activeTab
    ) {
      SchemaApiClient.getSchemaPartitionedChecksModel(
        filterParameters.connection,
        filterParameters.schema,
        filterParameters.activeTab,
        filterParameters.tableNamePattern,
        filterParameters.columnNamePattern,
        filterParameters.columnDataType,
        filterParameters.checkTarget,
        filterParameters.checkCategory,
        filterParameters.checkName,
        undefined,
        true,
        undefined
      ).then((res) => {
        onChangeChecks(res.data);
      });
    }
  };

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
          />
        </div>
      </div>
      <div className="w-1/4 flex items-end justify-end">
        <Button
          label="Search"
          color={filterParameters.checkName ? 'primary' : 'secondary'}
          onClick={searchChecks}
        />
      </div>
    </div>
  );
}
