import React, { useState } from 'react';
import { CheckModel, CheckTemplate } from '../../../../api';
import { isEqual } from 'lodash';
import { UpdateCheckModel } from '../../UpdateCheckModel';
import MultiChecksTableItem from './MultiChecksTableItem';
import { IFilterTemplate } from '../../../../shared/constants';
import {
  MultiChecksTableButtons,
  MultiChecksTableHeader
} from './MultiCheckTableHeaderButtons';
import { CheckTypes, ROUTES } from '../../../../shared/routes';
import { addFirstLevelTab } from '../../../../redux/actions/source.actions';
import { useHistory } from 'react-router-dom';
import { useActionDispatch } from '../../../../hooks/useActionDispatch';

type TMultiChecksTable = {
  checkTarget: 'column' | 'table' | undefined;
  checks: CheckTemplate[] | undefined;
  filterParameters: IFilterTemplate;
  selectedCheckModel: CheckModel;
  searchChecks: () => void;
};

type TCheckDefRouting =
  | {
      table: string;
      column: string;
    }
  | { table: string };

export default function MultiChecksTable({
  checkTarget,
  checks,
  filterParameters,
  selectedCheckModel,
  searchChecks
}: TMultiChecksTable) {
  const [selectedData, setSelectedData] = useState<CheckTemplate[]>([]);
  const [action, setAction] = useState<'bulkEnabled' | 'bulkDisabled'>();
  const [loading, setLoading] = useState(false);
  const history = useHistory();
  const dispatch = useActionDispatch();
  const selectAll = () => {
    setSelectedData(checks || []);
  };

  const deselectAll = () => {
    setSelectedData([]);
  };
  const onChangeLoading = (param: boolean) => {
    setLoading(param);
  };

  const onChangeSelection = (check: CheckTemplate) => {
    if (selectedData.find((item) => isEqual(item, check))) {
      setSelectedData(selectedData.filter((item) => !isEqual(item, check)));
    } else {
      setSelectedData([...selectedData, check]);
    }
  };

  const goToSingleCheckScreenTable = (table: string) => {
    let url = '';
    let value;
    if (!filterParameters.checkCategory || !filterParameters.checkName) return;
    switch (filterParameters.checkTypes) {
      case CheckTypes.PROFILING:
        url = ROUTES.TABLE_PROFILING_UI_FILTER(
          filterParameters.checkTypes,
          filterParameters.connection,
          filterParameters.schema,
          table,
          filterParameters.checkCategory,
          filterParameters.checkName
        );
        value = ROUTES.TABLE_PROFILING_VALUE(
          filterParameters.checkTypes,
          filterParameters.connection,
          filterParameters.schema,
          table
        );
        break;
      case CheckTypes.PARTITIONED:
        url = ROUTES.TABLE_PARTITIONED_UI_FILTER(
          filterParameters.checkTypes,
          filterParameters.connection,
          filterParameters.schema,
          table,
          filterParameters.activeTab ?? 'daily',
          filterParameters.checkCategory,
          filterParameters.checkName
        );
        value = ROUTES.TABLE_PARTITIONED_VALUE(
          filterParameters.checkTypes,
          filterParameters.connection,
          filterParameters.schema,
          table
        );
        break;
      case CheckTypes.MONITORING:
        url = ROUTES.TABLE_MONITORING_UI_FILTER(
          filterParameters.checkTypes,
          filterParameters.connection,
          filterParameters.schema,
          table,
          filterParameters.activeTab ?? 'daily',
          filterParameters.checkCategory,
          filterParameters.checkName
        );
        value = ROUTES.TABLE_MONITORING_VALUE(
          filterParameters.checkTypes,
          filterParameters.connection,
          filterParameters.schema,
          table
        );
        break;
    }
    dispatch(
      addFirstLevelTab(filterParameters.checkTypes, {
        url,
        value,
        state: {},
        label: table
      })
    );
    history.push(url);
  };

  const goToSingleCheckScreenColumn = (table: string, column: string) => {
    let url = '';
    let value;
    if (!filterParameters.checkCategory || !filterParameters.checkName) return;
    switch (filterParameters.checkTypes) {
      case CheckTypes.PROFILING:
        url = ROUTES.COLUMN_PROFILING_UI_FILTER(
          filterParameters.checkTypes,
          filterParameters.connection,
          filterParameters.schema,
          table,
          column,
          filterParameters.checkCategory,
          filterParameters.checkName
        );
        value = ROUTES.COLUMN_PROFILING_VALUE(
          filterParameters.checkTypes,
          filterParameters.connection,
          filterParameters.schema,
          table,
          column
        );
        break;
      case CheckTypes.PARTITIONED:
        url = ROUTES.COLUMN_PARTITIONED_UI_FILTER(
          filterParameters.checkTypes,
          filterParameters.connection,
          filterParameters.schema,
          table,
          column,
          filterParameters.activeTab ?? 'daily',
          filterParameters.checkCategory,
          filterParameters.checkName
        );
        value = ROUTES.COLUMN_PARTITIONED_VALUE(
          filterParameters.checkTypes,
          filterParameters.connection,
          filterParameters.schema,
          table,
          column
        );
        break;
      case CheckTypes.MONITORING:
        url = ROUTES.COLUMN_MONITORING_UI_FILTER(
          filterParameters.checkTypes,
          filterParameters.connection,
          filterParameters.schema,
          table,
          column,
          filterParameters.activeTab ?? 'daily',
          filterParameters.checkCategory,
          filterParameters.checkName
        );
        value = ROUTES.COLUMN_MONITORING_VALUE(
          filterParameters.checkTypes,
          filterParameters.connection,
          filterParameters.schema,
          table,
          column
        );
        break;
    }
    dispatch(
      addFirstLevelTab(filterParameters.checkTypes, {
        url,
        value,
        state: {},
        label: table
      })
    );
    history.push(url);
  };

  const goToCheckDefinition = (def: TCheckDefRouting) => {
    if (checkTarget === 'column' && 'column' in def) {
      goToSingleCheckScreenColumn(def.table, def.column);
    } else {
      goToSingleCheckScreenTable(def.table);
    }
  };

  return (
    <div className="w-max border border-gray-300 rounded-lg p-4 my-4">
      <MultiChecksTableButtons
        selectAll={selectAll}
        deselectAll={deselectAll}
        selectedData={selectedData}
        checks={checks}
        setAction={setAction}
        loading={loading}
      />
      {filterParameters.checkName &&
        filterParameters.checkCategory &&
        checks &&
        checks.length > 0 && (
          <table className="mt-8">
            <MultiChecksTableHeader checkTarget={checkTarget} />
            <tbody>
              {checks?.map((check, index) => (
                <MultiChecksTableItem
                  checkTarget={filterParameters.checkTarget}
                  check={check}
                  key={index}
                  checked={selectedData.includes(check)}
                  onChangeSelection={onChangeSelection}
                  goToCheckDefinition={goToCheckDefinition}
                />
              ))}
            </tbody>
          </table>
        )}
      <UpdateCheckModel
        open={action !== undefined}
        action={action ?? 'bulkEnabled'}
        onClose={() => setAction(undefined)}
        selectedCheckModel={selectedCheckModel}
        filterParameters={filterParameters}
        selectedData={selectedData}
        fetchResults={() => {
          searchChecks();
          setSelectedData([]);
        }}
        onChangeLoading={onChangeLoading}
      />
    </div>
  );
}
