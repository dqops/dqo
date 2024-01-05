import React, { useMemo, useState } from 'react';
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
import {
  setMultiCheckSearchedChecks,
  setMultiCheckFilters
} from '../../../redux/actions/source.actions';
import {
  getFirstLevelActiveTab,
  getFirstLevelState
} from '../../../redux/selectors';
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
  const { multiCheckFilters, multiCheckSearchedChecks } = useSelector(
    getFirstLevelState(checkTypes)
  );
  const activeTab = useSelector(getFirstLevelActiveTab(checkTypes));
  const [timeScale, setTimeScale] = useState<'daily' | 'monthly'>('daily');

  const dispatch = useActionDispatch();

  const filterParameters = useMemo(() => {
    if (
      multiCheckFilters?.[
        checkTypes === CheckTypes.PROFILING ? 'advanced' : timeScale
      ]?.checkTarget !== undefined
    ) {
      return multiCheckFilters?.[
        checkTypes === CheckTypes.PROFILING ? 'advanced' : timeScale
      ];
    }
    return { checkTarget: 'table' };
  }, [connection, schema, multiCheckFilters, timeScale]);

  const searchResults = useMemo(() => {
    return (
      multiCheckSearchedChecks?.[
        checkTypes === CheckTypes.PROFILING ? 'advanced' : timeScale
      ]
    );
  }, [connection, schema, timeScale, multiCheckFilters]);

  const onChangefilterParameters = (obj: Partial<IFilterTemplate>) => {
    const filters: IFilterTemplate = {
      ...filterParameters,
      ...obj
    };
    // dispatch(
    //   setMultiCheckSearchedChecks(
    //     checkTypes,
    //     activeTab,
    //     [],
    //     checkTypes === CheckTypes.PROFILING ? 'advanced' : timeScale
    //   )
    // );
    dispatch(
      setMultiCheckFilters(
        checkTypes,
        activeTab,
        filters,
        checkTypes === CheckTypes.PROFILING ? 'advanced' : timeScale
      )
    );
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
    } = filterParameters as IFilterTemplate;
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
        dispatch(
          setMultiCheckSearchedChecks(
            checkTypes,
            activeTab,
            res.data,
            'advanced'
          )
        );
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
        dispatch(
          setMultiCheckSearchedChecks(
            checkTypes,
            activeTab,
            res.data,
            timeScale
          )
        );
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
        dispatch(
          setMultiCheckSearchedChecks(
            checkTypes,
            activeTab,
            res.data,
            timeScale
          )
        );
      });
    }
  };
  console.log(searchResults, filterParameters, multiCheckSearchedChecks)

  return (
    <div className="text-sm py-4">
      {checkTypes !== CheckTypes.PROFILING && (
        <div className="border-b border-gray-300 pb-0 mb-4">
          <Tabs
            tabs={tabs}
            activeTab={timeScale}
            onChange={(value: any) => setTimeScale(value)}
          />
        </div>
      )}
      <div className="px-8">
        <MultiChecksFilter
          filterParameters={filterParameters as IFilterTemplate}
          onChangeFilterParameters={onChangefilterParameters}
          onChangeChecks={(checks: CheckTemplate[]) =>
            dispatch(
              setMultiCheckSearchedChecks(
                checkTypes,
                activeTab,
                checks,
                checkTypes === CheckTypes.PROFILING ? 'advanced' : timeScale
              )
            )
          }
          timeScale={timeScale}
        />
        <div className="mb-8" />
        <MultiChecksSearch
          filterParameters={filterParameters as IFilterTemplate}
          onChangeFilterParameters={onChangefilterParameters}
          searchChecks={searchChecks}
        />
        <hr className="mt-4 border-gray-300" />
        {filterParameters?.checkName && filterParameters?.checkCategory && (
          <MultiChecksTable
            checkTarget={filterParameters?.checkTarget}
            checks={searchResults}
            filterParameters={filterParameters as IFilterTemplate}
            selectedCheckModel={filterParameters?.selectedCheck?.check_model ?? {}}
            searchChecks={searchChecks}
            timeScale={timeScale}
          />
        )}
      </div>
    </div>
  );
};
