import React, { useEffect, useState } from 'react';
import Select, { Option } from '../../../components/Select';
import Input from '../../../components/Input';
import Button from '../../../components/Button';
import { useParams } from 'react-router-dom';
import { CheckTypes } from '../../../shared/routes';
import { SchemaApiClient } from '../../../services/apiClient';
import { CheckConfigurationModel, CheckTemplate } from '../../../api';
import Tabs from '../../../components/Tabs';
import { AxiosResponse } from 'axios';
import RadioButton from '../../../components/RadioButton';
import MultiChecksTable from './MultiChecksTable/MultiChecksTable';
import MultiChecksSearch from './MultiChecksSearch';
import MultiChecksFilter from './MultiChecksFilter';

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

interface newCheckTemplate {
  check_category?: string;
  check_name?: string;
  check_target?: string;
}

interface IFilterTemplate {
  connection: string;
  schema: string;
  activeTab: 'daily' | 'monthly';
  tableNamePattern?: string | undefined;
  columnNamePattern?: string | undefined;
  columnDataType?: string | undefined;
  checkTarget?: 'table' | 'column' | undefined;
  checkCategory?: string | undefined;
  checkName?: string | undefined;
}

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
  const [finalOptions, setFinalOptions] = useState<Option[]>([]);
  const [checks, setChecks] = useState<CheckConfigurationModel[]>();
  const [activeTab, setActiveTab] = useState<'daily' | 'monthly'>('daily');

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
      const possibleCategories = Array.from(
        new Set(res.data.map((item) => item.check_category))
      );
      const possibleCheckNames = Array.from(
        new Set(res.data.map((item) => item.check_name))
      );

      // const checkTemplates: newCheckTemplate[] = res.data.map((data) => ({
      //   check_category: data.check_category,
      //   check_name: data.check_name,
      //   check_target: data.check_target
      // }));

      // setFinalOptions(
      //   checkTemplates.map((x) => ({
      //     label: x.check_name ?? '',
      //     value: x.check_category
      //   }))
      // );

      setCheckCategoryOptions(
        possibleCategories.map((item) => ({
          label: item ?? '',
          value: item ?? ''
        }))
      );
      setCheckNameOptions(
        possibleCheckNames.map((item) => ({
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
          finalOptions={finalOptions}
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
        <div className="border border-gray-300 rounded-lg p-4 my-4">
          <MultiChecksTable
            checkTarget={filterParameters.checkTarget}
            checks={checks}
            filterParameters={filterParameters}
          />
        </div>
      </div>
    </div>
  );
};
