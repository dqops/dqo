import React, { useCallback, useState } from 'react';
import { CheckModel, CheckTemplate } from '../../../../api';
import { isEqual } from 'lodash';
import { UpdateCheckModel } from '../../UpdateCheckModel';
import MultiChecksTableItem from './MultiChecksTableItem';
import { IFilterTemplate } from '../../../../shared/constants';
import {
  MultiChecksTableButtons,
  MultiChecksTableHeader
} from './MultiCheckTableHeaderButtons';
import { addFirstLevelTab } from '../../../../redux/actions/source.actions';
import { useHistory } from 'react-router-dom';
import { useActionDispatch } from '../../../../hooks/useActionDispatch';
import {
  getCommonParams,
  getAdditionalParams,
  getUrl,
  getValue
} from './MultiChecksTableChecksRouting.utils';

type TMultiChecksTable = {
  checkTarget: 'column' | 'table' | undefined;
  checks: CheckTemplate[] | undefined;
  filterParameters: IFilterTemplate;
  selectedCheckModel: CheckModel;
  searchChecks: () => void;
};

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

  const goToCheckDefinition = useCallback(
    (table: string, column?: string) => {
      if (!filterParameters.checkCategory || !filterParameters.checkName)
        return;

      const commonParams = getCommonParams(filterParameters, table);
      const additionalParams = getAdditionalParams(filterParameters, column);

      const url = getUrl(filterParameters, commonParams, additionalParams, column);
      const value = getValue(filterParameters.checkTypes, commonParams, column);

      dispatch(
        addFirstLevelTab(filterParameters.checkTypes, {
          url,
          value,
          state: {},
          label: table
        })
      );
      history.push(url);
    },
    [filterParameters]
  );

  return (
    <div className="w-max rounded-lg my-4">
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
