import { AxiosResponse } from 'axios';
import React, { useEffect, useState } from 'react';
import { CheckTemplate } from '../../../api';
import RadioButton from '../../../components/RadioButton';
import Select, { Option } from '../../../components/Select';
import { SchemaApiClient } from '../../../services/apiClient';
import { IFilterTemplate } from '../../../shared/constants';
import { CheckTypes } from '../../../shared/routes';
import { useDecodedParams } from '../../../utils';

interface IMultiChecksFilter {
  filterParameters: IFilterTemplate;
  onChangeFilterParameters: (obj: Partial<IFilterTemplate>) => void;
  onChangeChecks: (checks: CheckTemplate[]) => void;
  timeScale: 'daily' | 'monthly';
}
export default function MultiChecksFilter({
  filterParameters,
  onChangeFilterParameters,
  onChangeChecks,
  timeScale
}: IMultiChecksFilter) {
  const {
    checkTypes,
    connection,
    schema
  }: { checkTypes: CheckTypes; connection: string; schema: string } =
    useDecodedParams();
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
      if (filterParameters?.checkName && filterParameters?.checkName) {
        const selectedCheck = res.data.find(
          (x) =>
            x.check_category === filterParameters?.checkCategory &&
            x.check_name === filterParameters?.checkName
        );
        onChangeFilterParameters({ selectedCheck: selectedCheck });
      }
    };
    if (checkTypes === CheckTypes.PROFILING) {
      SchemaApiClient.getSchemaProfilingChecksTemplates(
        connection,
        schema,
        filterParameters?.checkTarget
      ).then(processResult);
    } else if (checkTypes === CheckTypes.MONITORING) {
      SchemaApiClient.getSchemaMonitoringChecksTemplates(
        connection,
        schema,
        timeScale,
        filterParameters?.checkTarget
      ).then(processResult);
    } else if (checkTypes === CheckTypes.PARTITIONED) {
      SchemaApiClient.getSchemaPartitionedChecksTemplates(
        connection,
        schema,
        timeScale,
        filterParameters?.checkTarget
      ).then(processResult);
    }
  }, [
    connection,
    schema,
    checkTypes,
    filterParameters?.checkTarget,
    timeScale
  ]);
  const onChangeCheckOptions = () => {
    const checksForCategory = checks
      .filter((x) => x.check_category === filterParameters?.checkCategory)
      .map((x) => x.check_name);

    setCheckNameOptions(
      checksForCategory.map((item) => ({
        label: item ?? '',
        value: item ?? ''
      }))
    );
  };

  useEffect(() => {
    if (filterParameters?.checkCategory) {
      onChangeCheckOptions();
    }
  }, [filterParameters?.checkCategory, checks, timeScale, connection, schema]);

  useEffect(() => {
    if (filterParameters?.checkName && filterParameters?.checkName) {
      const selectedCheck = checks.find(
        (x) =>
          x.check_category === filterParameters?.checkCategory &&
          x.check_name === filterParameters?.checkName
      );
      onChangeFilterParameters({ selectedCheck: selectedCheck });
    }
  }, [filterParameters?.checkName, timeScale, connection, schema]);

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
        className="min-w-60"
        menuClassName="!top-14"
      />
      <Select
        options={checkNameOptions}
        label="Check name"
        value={filterParameters?.checkName}
        onChange={(value) => {
          onChangeFilterParameters({ checkName: value });
          onChangeChecks([]);
        }}
        className="ml-10 min-w-60"
        menuClassName="!top-14"
      />
    </div>
  );
}
