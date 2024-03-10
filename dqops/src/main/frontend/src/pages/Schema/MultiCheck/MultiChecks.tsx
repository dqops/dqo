import { AxiosResponse } from 'axios';
import React, { useMemo, useState } from 'react';
import { useSelector } from 'react-redux';
import { CheckConfigurationModel, CheckTemplate } from '../../../api';
import Tabs from '../../../components/Tabs';
import { useActionDispatch } from '../../../hooks/useActionDispatch';
import {
  setMultiCheckFilters,
  setMultiCheckSearchedChecks
} from '../../../redux/actions/source.actions';
import {
  getFirstLevelActiveTab,
  getFirstLevelState
} from '../../../redux/selectors';
import { SchemaApiClient } from '../../../services/apiClient';
import { IFilterTemplate } from '../../../shared/constants';
import { CheckTypes } from '../../../shared/routes';
import { useDecodedParams } from '../../../utils';
import MultiChecksFilter from './MultiChecksFilter';
import MultiChecksSearch from './MultiChecksSearch';
import MultiChecksTable from './MultiChecksTable/MultiChecksTable';

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
    useDecodedParams();
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
