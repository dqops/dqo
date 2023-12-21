import React, { useEffect, useState } from 'react';
import RadioButton from '../../../components/RadioButton';
import Select, { Option } from '../../../components/Select';
import { IFilterTemplate } from '../../../shared/constants';
import { AxiosResponse } from 'axios';
import { CheckTemplate } from '../../../api';
import { SchemaApiClient } from '../../../services/apiClient';
import { CheckTypes } from '../../../shared/routes';

interface IMultiChecksFilter {
  filterParameters: IFilterTemplate;
  onChangeFilterParameters: (obj: Partial<IFilterTemplate>) => void;
  checkTypes: CheckTypes;
  checkTemplateList: CheckTemplate[];
  onChangeCheckTemplateList: (obj: CheckTemplate[]) => void;
}
export default function MultiChecksFilter({
  filterParameters,
  onChangeFilterParameters,
  checkTemplateList,
  checkTypes,
  onChangeCheckTemplateList
}: IMultiChecksFilter) {
  const [checkCategoryOptions, setCheckCategoryOptions] = useState<Option[]>(
    []
  );
  const [checkNameOptions, setCheckNameOptions] = useState<Option[]>([]);

  const sortObjects = (array: Option[]): Option[] => {
    const sortedArray = array.sort((a, b) =>
      (a.label.toString() ?? '').localeCompare(b.label.toString() ?? '')
    );
    return sortedArray;
  };

  useEffect(() => {
    const processResult = (res: AxiosResponse<CheckTemplate[]>) => {
      onChangeCheckTemplateList(res.data);
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
        filterParameters.connection,
        filterParameters.schema,
        filterParameters.checkTarget
      ).then(processResult);
    } else if (checkTypes === CheckTypes.MONITORING) {
      SchemaApiClient.getSchemaMonitoringChecksTemplates(
        filterParameters.connection,
        filterParameters.schema,
        filterParameters.activeTab,
        filterParameters.checkTarget
      ).then(processResult);
    } else if (checkTypes === CheckTypes.PARTITIONED) {
      SchemaApiClient.getSchemaPartitionedChecksTemplates(
        filterParameters.connection,
        filterParameters.schema,
        filterParameters.activeTab,
        filterParameters.checkTarget
      ).then(processResult);
    }
  }, [
    filterParameters.connection,
    filterParameters.schema,
    checkTypes,
    filterParameters.checkTarget
  ]);

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
    <div className="flex w-full">
      <div className="flex w-1/4">
        <div className="flex flex-col gap-3 w-45">
          <p>Check target</p>
          <div className="flex gap-x-3 mr-2">
            <RadioButton
              label="Table"
              onClick={() => onChangeFilterParameters({ checkTarget: 'table' })}
              checked={filterParameters.checkTarget === 'table'}
            />
            <RadioButton
              label="Column"
              onClick={() =>
                onChangeFilterParameters({ checkTarget: 'column' })
              }
              checked={filterParameters.checkTarget === 'column'}
            />
          </div>
        </div>
        <div className="max-w-75 w-75">
          <Select
            label="Check category"
            options={sortObjects(checkCategoryOptions)}
            value={filterParameters.checkCategory}
            onChange={(value) =>
              onChangeFilterParameters({ checkCategory: value })
            }
          ></Select>
        </div>
      </div>
      <div className="flex w-1/4 px-10">
        <div className="max-w-120 w-120">
          <Select
            options={checkNameOptions}
            label="Check name"
            value={filterParameters.checkName}
            onChange={(value) => onChangeFilterParameters({ checkName: value })} // TODO: we cannot just change the check, we need to call a function that will take the selected check from array of CheckTemplate and store it as the selectedCheck (We are selecting the check template)
          />
        </div>
      </div>
    </div>
  );
}
