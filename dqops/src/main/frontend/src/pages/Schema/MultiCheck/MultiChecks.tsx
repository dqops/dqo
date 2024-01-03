import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import { CheckTypes } from '../../../shared/routes';
import { CheckTemplate } from '../../../api';
import Tabs from '../../../components/Tabs';
import MultiChecksTable from './MultiChecksTable/MultiChecksTable';
import MultiChecksSearch from './MultiChecksSearch';
import MultiChecksFilter from './MultiChecksFilter';
import { IFilterTemplate } from '../../../shared/constants';
import { SchemaApiClient } from '../../../services/apiClient';
import { setMulticheckFilters } from '../../../redux/actions/job.actions';
import { useSelector } from 'react-redux';
import { IRootState } from '../../../redux/reducers';
import { useActionDispatch } from '../../../hooks/useActionDispatch';

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
  const { multiCheckFilters } = useSelector((state: IRootState) => state.job || {});
  
  const [checks, setChecks] = useState<CheckTemplate[]>([]);
  const [selectedCheck, setSelectedCheck] = useState<CheckTemplate>({});
  
  const dispatch = useActionDispatch()
  
  const onChangemultiCheckFilters = (obj: Partial<IFilterTemplate>) => {
    const filterParameters: IFilterTemplate = {
      connection,
      schema,
      checkTypes,
      activeTab: 'daily', 
      ...multiCheckFilters,
      ...obj,
    };
    dispatch(setMulticheckFilters(filterParameters));
  };
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
      checkName,
      activeOffCheck
    } = multiCheckFilters as IFilterTemplate;

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

  useEffect(() => {
     if (multiCheckFilters?.connection.length === 0) {
       dispatch(setMulticheckFilters({
         connection,
         schema,
         activeTab: 'daily',
         checkTarget: 'table',
         checkTypes: checkTypes
        }))
      } 
    }, [connection, schema])
    
  return (
    <div className="text-sm py-4">
      {checkTypes !== CheckTypes.PROFILING && (
        <div className="border-b border-gray-300 pb-0 mb-4">
          <Tabs
            tabs={tabs}
            activeTab={(multiCheckFilters ?? {})?.activeTab}
            onChange={(value: any) => {
              dispatch(setMulticheckFilters({
                connection,
                schema,
                checkTarget: 'table',
                checkTypes: checkTypes,
                activeTab: value
              }));
            }}
          />
        </div>
      )}
      <div className="px-8">
        <MultiChecksFilter
          filterParameters={multiCheckFilters as IFilterTemplate}
          onChangeFilterParameters={onChangemultiCheckFilters}
          checkTypes={checkTypes}
          onChangeSelectedCheck={(obj: CheckTemplate) => setSelectedCheck(obj)}
          onChangeChecks={(checks: CheckTemplate[]) => setChecks(checks)}
        />
        <hr className="my-8 border-gray-300" />
        <MultiChecksSearch
          filterParameters={multiCheckFilters as IFilterTemplate}
          onChangeFilterParameters={onChangemultiCheckFilters}
          searchChecks={searchChecks}
        />
        {multiCheckFilters?.checkName && multiCheckFilters?.checkCategory && (
          <MultiChecksTable
            checkTarget={multiCheckFilters?.checkTarget}
            checks={checks}
            filterParameters={multiCheckFilters as IFilterTemplate}
            selectedCheckModel={selectedCheck.check_model ?? {}}
            searchChecks={searchChecks}
          />
        )}
      </div>
    </div>
  );
};
