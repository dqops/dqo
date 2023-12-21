import React from 'react';
import { CheckTypes } from '../../../shared/routes';
import RadioButton from '../../../components/RadioButton';
import Select, { Option } from '../../../components/Select';

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

interface IMultiChecksFilter {
  filterParameters: IFilterTemplate;
  onChangeFilterParameters: (obj: Partial<IFilterTemplate>) => void;
  checkCategoryOptions: Option[];
  finalOptions: Option[];
}
export default function MultiChecksFilter({
  filterParameters,
  onChangeFilterParameters,
  checkCategoryOptions,
  finalOptions
}: IMultiChecksFilter) {
  const sortObjects = (array: Option[]): Option[] => {
    const sortedArray = array.sort((a, b) =>
      (a.label.toString() ?? '').localeCompare(b.label.toString() ?? '')
    );
    return sortedArray;
  };

  const removeDuplicateOptions = (options: Option[]): Option[] => {
    const uniqueOptions: Option[] = [];
    const uniqueValues = new Set<string | number>();

    for (const option of options) {
      if (!uniqueValues.has(option.label as string)) {
        uniqueValues.add(option.label as string);
        uniqueOptions.push(option);
      }
    }

    return uniqueOptions;
  };

  const setNewArray = (oldArray: Option[]): Option[] => {
    const newArray: Option[] = oldArray.map((option) => ({
      ...option,
      value: option.label ?? ''
    }));

    return newArray;
  };

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
            options={setNewArray(
              sortObjects(
                removeDuplicateOptions(
                  // TODO: remove this deduplication, should not be required
                  finalOptions.filter(
                    (x) => x.value === filterParameters.checkCategory
                  )
                )
              )
            )}
            label="Check name"
            value={filterParameters.checkName}
            onChange={(value) => onChangeFilterParameters({ checkName: value })} // TODO: we cannot just change the check, we need to call a function that will take the selected check from array of CheckTemplate and store it as the selectedCheck (We are selecting the check template)
          />
        </div>
      </div>
    </div>
  );
}
