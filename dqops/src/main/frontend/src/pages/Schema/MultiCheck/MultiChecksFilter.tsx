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
  onChangeSelectedCheck: (obj: CheckTemplate) => void;
  onChangeChecks: (checks: CheckTemplate[]) => void;
}
export default function MultiChecksFilter({
  filterParameters,
  onChangeFilterParameters,
  checkTypes,
  onChangeSelectedCheck,
  onChangeChecks
}: IMultiChecksFilter) {
  const [checkCategoryOptions, setCheckCategoryOptions] = useState<Option[]>(
    []
  );
  const [checkNameOptions, setCheckNameOptions] = useState<Option[]>([]);
  const [checks, setChecks] = useState<CheckTemplate[]>([]);

  const sortObjects = (array: Option[]): Option[] => {
    const sortedArray = array.sort((a, b) =>
      (a.label.toString() ?? '').localeCompare(b.label.toString() ?? '')
    );
    return sortedArray;
  };

  useEffect(() => {
    const processResult = (res: AxiosResponse<CheckTemplate[]>) => {
      setChecks(res.data);
      const categories = Array.from(
        new Set(res.data.map((x) => x.check_category))
      );
      setCheckCategoryOptions(
        categories.map((item) => ({
          label: item ?? '',
          value: item ?? ''
        }))
      );
    };
    if (filterParameters?.connection.length === 0) return
    if (checkTypes === CheckTypes.PROFILING) {
      SchemaApiClient.getSchemaProfilingChecksTemplates(
        filterParameters?.connection,
        filterParameters?.schema,
        filterParameters?.checkTarget
      ).then(processResult);
    } else if (
      checkTypes === CheckTypes.MONITORING &&
      filterParameters?.activeTab
    ) {
      SchemaApiClient.getSchemaMonitoringChecksTemplates(
        filterParameters?.connection,
        filterParameters?.schema,
        filterParameters?.activeTab,
        filterParameters?.checkTarget
      ).then(processResult);
    } else if (
      checkTypes === CheckTypes.PARTITIONED &&
      filterParameters?.activeTab
    ) {
      SchemaApiClient.getSchemaPartitionedChecksTemplates(
        filterParameters?.connection,
        filterParameters?.schema,
        filterParameters?.activeTab,
        filterParameters?.checkTarget
      ).then(processResult);
    }
  }, [
    filterParameters?.connection,
    filterParameters?.schema,
    checkTypes,
    filterParameters?.checkTarget,
    filterParameters?.activeTab
  ]);
  const onChangeCheckOptions = () => {
    const checksCopy = checks
      .filter((x) => x.check_category === filterParameters?.checkCategory)
      .map((x) => x.check_name);

    const sortedChecks = checksCopy.sort((a, b): number => {
      if (a && b) {
        return a?.localeCompare(b);
      }
      return 0;
    });

    setCheckNameOptions(
      sortedChecks.map((item) => ({
        label: item ?? '',
        value: item ?? ''
      }))
    );
  };

  useEffect(() => {
    if (filterParameters?.checkCategory) {
      onChangeCheckOptions();
    }
  }, [filterParameters?.checkCategory, checks]);

  useEffect(() => {
    if (filterParameters?.checkName && filterParameters?.checkName) {
      const selectedCheck = checks.find(
        (x) =>
          x.check_category === filterParameters?.checkCategory &&
          x.check_name === filterParameters?.checkName
      );
      onChangeSelectedCheck(selectedCheck ?? {});
    }
  }, [filterParameters?.checkName]);

  return (
    <div className="flex w-full">
        <div className="flex flex-col gap-3 w-45">
          <p>Check target</p>
          <div className="flex gap-x-3 mr-10">
            <RadioButton
              label="Table"
              onClick={() => {
                onChangeFilterParameters({
                  checkTarget: 'table',
                  columnNamePattern: undefined,
                  columnDataType: undefined,
                  tableNamePattern: undefined,
                  checkName: undefined,
                  checkCategory: undefined
                }),
                  onChangeChecks([]);
                setCheckNameOptions([]);
              }}
              checked={filterParameters?.checkTarget === 'table'}
            />
            <RadioButton
              label="Column"
              onClick={() => {
                onChangeFilterParameters({
                  checkTarget: 'column',
                  checkName: undefined,
                  checkCategory: undefined
                });
                onChangeChecks([]);
                setCheckNameOptions([]);
              }}
              checked={filterParameters?.checkTarget === 'column'}
            />
          </div>
        </div>
          <Select
            label="Check category"
            options={sortObjects(checkCategoryOptions)}
            value={filterParameters?.checkCategory}
            onChange={(value) => {
              onChangeFilterParameters({ checkCategory: value });
              onChangeCheckOptions();
              onChangeChecks([]);
            }}
          />
          <Select
            options={checkNameOptions}
            label="Check name"
            value={filterParameters?.checkName}
            onChange={(value) => {
              onChangeFilterParameters({ checkName: value });
              onChangeChecks([]);
            }}
            className='ml-10'
          />
    </div>
  );
}
