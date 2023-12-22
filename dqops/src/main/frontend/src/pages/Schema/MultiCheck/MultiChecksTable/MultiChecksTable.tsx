import React, { useState } from 'react';
import { CheckModel, CheckTemplate } from '../../../../api';
import Button from '../../../../components/Button';
import { isEqual } from 'lodash';
import { UpdateCheckModel } from '../../UpdateCheckModel';
import MultiChecksTableItem from './MultiChecksTableItem';
import { IFilterTemplate } from '../../../../shared/constants';
import RadioButton from '../../../../components/RadioButton';

type TMultiChecksTable = {
  checkTarget: 'column' | 'table' | undefined;
  checks: CheckTemplate[] | undefined;
  filterParameters: IFilterTemplate;
  selectedCheckModel: CheckModel;
};

export default function MultiChecksTable({
  checkTarget,
  checks,
  filterParameters,
  selectedCheckModel
}: TMultiChecksTable) {
  const [selectedData, setSelectedData] = useState<CheckTemplate[]>([]);
  const [action, setAction] = useState<'bulkEnabled' | 'bulkDisabled'>();

  const selectAll = () => {
    setSelectedData(checks || []);
  };

  const deselectAll = () => {
    setSelectedData([]);
  };

  const onChangeSelection = (check: CheckTemplate) => {
    if (selectedData.find((item) => isEqual(item, check))) {
      setSelectedData(selectedData.filter((item) => !isEqual(item, check)));
    } else {
      setSelectedData([...selectedData, check]);
    }
  };
  return (
    <div className="border border-gray-300 rounded-lg p-4 my-4">
      <div className="flex justify-between gap-4">
        {filterParameters.checkName &&
        filterParameters.checkCategory &&
        checks &&
        checks.length > 0 ? (
          <div className="flex gap-x-4">
            <RadioButton
              className="text-sm py-2.5"
              label="Select all"
              checked={selectedData === checks}
              onClick={selectAll}
            />
            <RadioButton
              className="text-sm py-2.5"
              label="Unselect all"
              checked={selectedData.length === 0}
              onClick={deselectAll}
            />
          </div>
        ) : (
          <div />
        )}

        <div className="flex gap-x-4">
          <Button
            className="text-sm py-2.5"
            label={!selectedData.length ? 'Activate for all maching filter' : 'Update for selected'}
            color="primary"
            onClick={() => setAction('bulkEnabled')}
          />
          <Button
            className="text-sm py-2.5"
            label={!selectedData.length ? 'Deactivate for all maching filter' : 'Deactivate selected'}
            color="primary"
            onClick={() => setAction('bulkDisabled')}
          />
        </div>
      </div>
      {filterParameters.checkName &&
        filterParameters.checkCategory &&
        checks &&
        checks.length > 0 && (
          <table className="w-full mt-8">
            <thead>
              <tr>
                <th></th>
                <th className="px-4 py-2 text-left">Check Name</th>
                <th className="px-4 py-2 text-left">Check Category</th>
                <th className="px-4 py-2 text-left">Table</th>
                {checkTarget === 'column' && (
                  <th className="px-4 py-2 text-left">Column</th>
                )}
                <th className="px-4 py-2 text-left">Sensor parameters</th>
                <th className="px-4 py-2 text-left">Warning threshold</th>
                <th className="px-4 py-2 text-left">Error threshold</th>
                <th className="px-4 py-2 text-left">Fatal threshold</th>
              </tr>
            </thead>
            <tbody>
              {checks?.map((check, index) => (
                <MultiChecksTableItem
                  checkTarget={filterParameters.checkTarget}
                  check={check}
                  key={index}
                  checked={selectedData.includes(check)}
                  onChangeSelection={onChangeSelection}
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
      />
    </div>
  );
}
