import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import { CheckTypes } from '../../../shared/routes';
import { CheckConfigurationModel, CheckTemplate } from '../../../api';
import Tabs from '../../../components/Tabs';
import MultiChecksTable from './MultiChecksTable/MultiChecksTable';
import MultiChecksSearch from './MultiChecksSearch';
import MultiChecksFilter from './MultiChecksFilter';
import { IFilterTemplate } from '../../../shared/constants';
import { SchemaApiClient } from '../../../services/apiClient';
import { useSelector } from 'react-redux';
import { useActionDispatch } from '../../../hooks/useActionDispatch';
import { setMultiCheckSearchedChecks, setMulticheckFilters } from '../../../redux/actions/source.actions';
import { getFirstLevelActiveTab, getFirstLevelState } from '../../../redux/selectors';
import { AxiosResponse } from 'axios';

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
  const { multiCheckFilters, multiCheckSearchedChecks} = useSelector(getFirstLevelState(checkTypes));
  const activeTab = useSelector(getFirstLevelActiveTab(checkTypes));

  const [selectedCheck, setSelectedCheck] = useState<CheckTemplate>({});
  const [timeScale, setTimeScale] = useState<'daily' | 'monthly'>('daily') 
  
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
    dispatch(setMulticheckFilters(checkTypes, activeTab, filterParameters));
  };
  const searchChecks = () => {
    const {
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
      ).then((res: AxiosResponse<CheckConfigurationModel[], any>) => {
        dispatch(setMultiCheckSearchedChecks(checkTypes, activeTab, res.data));
      });
    } else if (checkTypes === CheckTypes.MONITORING) {
      SchemaApiClient.getSchemaMonitoringChecksModel(
        connection,
        schema,
        timeScale,
        tableNamePattern,
        columnNamePattern,
        columnDataType,
        checkTarget,
        checkCategory,
        checkName,
        undefined,
        activeOffCheck ? undefined : true
      ).then((res: AxiosResponse<CheckConfigurationModel[], any>) => {
        dispatch(setMultiCheckSearchedChecks(checkTypes, activeTab, res.data));
      });
    } else if (checkTypes === CheckTypes.PARTITIONED) {
      SchemaApiClient.getSchemaPartitionedChecksModel(
        connection,
        schema,
        timeScale,
        tableNamePattern,
        columnNamePattern,
        columnDataType,
        checkTarget,
        checkCategory,
        checkName,
        undefined,
        activeOffCheck ? undefined : true
      ).then((res: AxiosResponse<CheckConfigurationModel[], any>) => {
        dispatch(setMultiCheckSearchedChecks(checkTypes, activeTab, res.data));
      });
    }
  };

  // useEffect(() => {
  //    if (!multiCheckFilters?.connection) {
  //      dispatch(setMulticheckFilters(checkTypes, activeTab, {
  //        connection,
  //        schema,
  //        activeTab: 'daily',
  //        checkTarget: 'table',
  //        checkTypes: checkTypes
  //       }))
  //     } 
  //   }, [connection, schema])
    
  return (
    <div className="text-sm py-4">
      {checkTypes !== CheckTypes.PROFILING && (
        <div className="border-b border-gray-300 pb-0 mb-4">
          <Tabs
            tabs={tabs}
            activeTab={timeScale}
            onChange={(value: any) => {
              dispatch(setMulticheckFilters(checkTypes, activeTab,{
                checkTarget: 'table'
              })),
              setTimeScale(value)
            }}
          />
        </div>
      )}
      <div className="px-8">
        <MultiChecksFilter
          filterParameters={multiCheckFilters as IFilterTemplate}
          onChangeFilterParameters={onChangemultiCheckFilters}
          onChangeSelectedCheck={(obj: CheckTemplate) => setSelectedCheck(obj)}
          onChangeChecks={(checks: CheckTemplate[]) => dispatch(setMultiCheckSearchedChecks(checkTypes, activeTab, checks))}
          timeScale = {timeScale}
        />
        <hr className="my-8 border-gray-300" />
        <MultiChecksSearch
          filterParameters={multiCheckFilters as IFilterTemplate}
          onChangeFilterParameters={onChangemultiCheckFilters}
          searchChecks={searchChecks}
        />
        <hr className="mt-4 border-gray-300" />
        {multiCheckFilters?.checkName && multiCheckFilters?.checkCategory && (
          <MultiChecksTable
            checkTarget={multiCheckFilters?.checkTarget}
            checks={multiCheckSearchedChecks}
            filterParameters={multiCheckFilters as IFilterTemplate}
            selectedCheckModel={selectedCheck.check_model ?? {}}
            searchChecks={searchChecks}
            timeScale = {timeScale}
          />
        )}
      </div>
    </div>
  );
};
