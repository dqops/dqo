import React, { useEffect, useState } from 'react';
import { Option } from '../../../components/Select';
import { useParams } from 'react-router-dom';
import { CheckTypes } from '../../../shared/routes';
import { SchemaApiClient } from '../../../services/apiClient';
import { CheckConfigurationModel, CheckTemplate } from '../../../api';
import Tabs from '../../../components/Tabs';
import { AxiosResponse } from 'axios';
import MultiChecksTable from './MultiChecksTable/MultiChecksTable';
import MultiChecksSearch from './MultiChecksSearch';
import MultiChecksFilter from './MultiChecksFilter';
import { IFilterTemplate } from '../../../shared/constants';

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
  const [checkCategoryOptions, setCheckCategoryOptions] = useState<Option[]>(
    []
  );
  const [checkNameOptions, setCheckNameOptions] = useState<Option[]>([]);
  const [checks, setChecks] = useState<CheckConfigurationModel[]>();
  const [activeTab, setActiveTab] = useState<'daily' | 'monthly'>('daily');
  const [checkTemplateList, setCheckTemplateList] = useState<CheckTemplate[]>(
    []
  );

  const [filterParameters, setFilterParameters] = useState<IFilterTemplate>({
    connection,
    schema,
    activeTab: 'daily',
    checkTarget: 'table'
  });

  const onChangeFilterParameters = (obj: Partial<IFilterTemplate>) => {
    setFilterParameters((prev) => ({
      ...prev,
      ...obj
    }));
  };

  useEffect(() => {
    const processResult = (res: AxiosResponse<CheckTemplate[]>) => {
      setCheckTemplateList(res.data);
      const categories = Array.from(
        new Set(res.data.map((x) => x.check_category))
      );
      setCheckCategoryOptions(
        categories.map((item) => ({
          label: item ?? '',
          value: item ?? ''
        }))
      );
      // TODO: we need to store the array of CheckTemplate, because we will need instances to put as selected
    };

    if (checkTypes === CheckTypes.PROFILING) {
      SchemaApiClient.getSchemaProfilingChecksTemplates(
        connection,
        schema,
        filterParameters.checkTarget
      ).then(processResult);
    } else if (checkTypes === CheckTypes.MONITORING) {
      SchemaApiClient.getSchemaMonitoringChecksTemplates(
        connection,
        schema,
        activeTab,
        filterParameters.checkTarget
      ).then(processResult);
    } else if (checkTypes === CheckTypes.PARTITIONED) {
      SchemaApiClient.getSchemaPartitionedChecksTemplates(
        connection,
        schema,
        activeTab,
        filterParameters.checkTarget
      ).then(processResult);
    }
  }, [connection, schema, checkTypes, filterParameters.checkTarget]);

  useEffect(() => {
    const onChangeCheckOptions = () => {
      const checks = checkTemplateList
        .filter((x) => x.check_category === filterParameters.checkCategory)
        .map((x) => x.check_name);

      setCheckNameOptions(
        checks.map((item) => ({
          label: item ?? '',
          value: item ?? ''
        }))
      );
    };
    if (filterParameters.checkCategory) {
      onChangeCheckOptions();
    }
  }, [filterParameters.checkCategory]);

  return (
    <div className="text-sm py-4">
      {checkTypes !== CheckTypes.PROFILING && (
        <div className="border-b border-gray-300 pb-0 mb-4">
          <Tabs tabs={tabs} activeTab={activeTab} onChange={setActiveTab} />
        </div>
      )}
      <div className="px-8">
        <MultiChecksFilter
          filterParameters={filterParameters}
          onChangeFilterParameters={onChangeFilterParameters}
          checkCategoryOptions={checkCategoryOptions}
          checkNameOptions={checkNameOptions}
        />
        <hr className="my-8 border-gray-300" />
        <MultiChecksSearch
          checkTypes={checkTypes}
          filterParameters={filterParameters}
          onChangeFilterParameters={onChangeFilterParameters}
          onChangeChecks={(checks: CheckConfigurationModel[]) =>
            setChecks(checks)
          }
        />
        <MultiChecksTable
          checkTarget={filterParameters.checkTarget}
          checks={checks}
          filterParameters={filterParameters}
        />
      </div>
    </div>
  );
};
