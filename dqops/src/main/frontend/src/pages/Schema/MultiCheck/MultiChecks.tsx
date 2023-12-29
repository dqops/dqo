import React, { useState } from 'react';
import { useParams } from 'react-router-dom';
import { CheckTypes } from '../../../shared/routes';
import { CheckTemplate } from '../../../api';
import Tabs from '../../../components/Tabs';
import MultiChecksTable from './MultiChecksTable/MultiChecksTable';
import MultiChecksSearch from './MultiChecksSearch';
import MultiChecksFilter from './MultiChecksFilter';
import { IFilterTemplate } from '../../../shared/constants';
import { SchemaApiClient } from '../../../services/apiClient';

const tabs = [
  {
    label: 'Daily checks',
    value: 'daily'
  },
  {
    label: 'Monthly checks',
    value: 'monthly'
  }
];

export const MultiChecks = () => {
  const {
    checkTypes,
    connection,
    schema
  }: { checkTypes: CheckTypes; connection: string; schema: string } =
    useParams();
  const [checks, setChecks] = useState<CheckTemplate[]>([]);
  const [selectedCheck, setSelectedCheck] = useState<CheckTemplate>({});
  const [filterParameters, setFilterParameters] = useState<IFilterTemplate>({
    connection,
    schema,
    activeTab: 'daily',
    checkTarget: 'table',
    checkTypes: checkTypes
  });

  const onChangeFilterParameters = (obj: Partial<IFilterTemplate>) => {
    setFilterParameters((prev) => ({
      ...prev,
      ...obj
    }));
  };
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
       setChecks(res.data);
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
        setChecks(res.data);
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
        setChecks(res.data);
      });
    }
  };

  return (
    <div className="text-sm py-4">
      {checkTypes !== CheckTypes.PROFILING && (
        <div className="border-b border-gray-300 pb-0 mb-4">
          <Tabs
            tabs={tabs}
            activeTab={filterParameters.activeTab}
            onChange={(value: any) =>
              onChangeFilterParameters({ activeTab: value })
            }
          />
        </div>
      )}
      <div className="px-8">
        <MultiChecksFilter
          filterParameters={filterParameters}
          onChangeFilterParameters={onChangeFilterParameters}
          checkTypes={checkTypes}
          onChangeSelectedCheck={(obj: CheckTemplate) => setSelectedCheck(obj)}
          onChangeChecks={(checks: CheckTemplate[]) => setChecks(checks)}
        />
        <hr className="my-8 border-gray-300" />
        <MultiChecksSearch
          filterParameters={filterParameters}
          onChangeFilterParameters={onChangeFilterParameters}
          searchChecks={searchChecks}
          setActiveOffCheck={setActiveOffChecks}
          activeOffCheck={activeOffCheck}
        />
        {filterParameters.checkName && filterParameters.checkCategory && (
          <MultiChecksTable
            checkTarget={filterParameters.checkTarget}
            checks={checks}
            filterParameters={filterParameters}
            selectedCheckModel={selectedCheck.check_model ?? {}}
            searchChecks={searchChecks}
          />
        )}
      </div>
    </div>
  );
};
