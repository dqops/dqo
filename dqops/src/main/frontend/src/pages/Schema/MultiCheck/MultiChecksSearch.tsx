import React from 'react';
import Input from '../../../components/Input';
import Button from '../../../components/Button';
import { IFilterTemplate } from '../../../shared/constants';
import Checkbox from '../../../components/Checkbox';

interface IMultiChecksSearch {
  filterParameters: IFilterTemplate;
  onChangeFilterParameters: (obj: Partial<IFilterTemplate>) => void;
  activeOffCheck: boolean;
  setActiveOffCheck: (arg : boolean) => void;
  searchChecks: any
}

export default function MultiChecksSearch({
  filterParameters,
  onChangeFilterParameters,
  setActiveOffCheck, 
  activeOffCheck, 
  searchChecks
}: IMultiChecksSearch) {

  return (
    <div className="flex w-full ">
      <div className="w-1/4">
        <div className="max-w-120">
          <Input
            value={filterParameters.tableNamePattern}
            label="Table name"
            placeholder="Enter table name pattern"
            onChange={(e) =>
              onChangeFilterParameters({ tableNamePattern: e.target.value })
            }
          />
        </div>
      </div>
      <div className="w-1/4 px-10">
        <div className="max-w-120">
          <Input
            value={filterParameters.columnNamePattern}
            label="Column name"
            placeholder="Enter column name pattern"
            onChange={(e) =>
              onChangeFilterParameters({
                columnNamePattern: e.target.value
              })
            }
            disabled={filterParameters.checkTarget === 'table'}
          />
        </div>
      </div>
      <div className="w-1/4 px-10">
        <div className="max-w-120">
          <Input
            value={filterParameters.columnDataType}
            label="Column data type"
            placeholder="Enter data type"
            onChange={(e) =>
              onChangeFilterParameters({ columnDataType: e.target.value })
            }
            disabled={filterParameters.checkTarget === 'table'}
          />
        </div>
      </div>
      <div className="w-1/4 flex items-center gap-x-8 justify-end">
        <Checkbox
          checked={activeOffCheck}
          onChange={() => setActiveOffCheck(!activeOffCheck)}
          label={"Include also inactive checks"}
        />
        <Button
          label="Search"
          color={filterParameters.checkName ? 'primary' : 'secondary'}
          onClick={searchChecks}
        />
      </div>
    </div>
  );
}
