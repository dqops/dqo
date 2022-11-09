import React from 'react';
import { UIAllChecksModel, UICheckModel } from '../../api';
import CheckListItem from './CheckListItem';

interface IDataQualityChecksProps {
  checksUI?: UIAllChecksModel;
  onChange: (ui: UIAllChecksModel) => void;
}

const TableHeader = () => {
  return (
    <tr>
      <td className="text-left text-gray-700 py-2 border-b font-semibold">
        Data quality check
      </td>
      <td className="text-left text-gray-700 py-2 border-b font-semibold">
        Sensor parameters
      </td>
      <td className="text-left text-gray-700 py-2 border-b font-semibold">
        Data quality rules
      </td>
    </tr>
  );
};

const DataQualityChecks = ({ checksUI, onChange }: IDataQualityChecksProps) => {
  const handleChangeDimension = (
    check: UICheckModel,
    idx: number,
    jdx: number
  ) => {
    if (!checksUI) return;

    const newChecksUI = {
      ...checksUI,
      categories: checksUI?.categories?.map((category, index) =>
        index !== idx
          ? category
          : {
              ...category,
              checks: category?.checks?.map((item, jindex) =>
                jindex !== jdx ? item : check
              )
            }
      )
    };

    onChange(newChecksUI);
  };

  if (!checksUI?.categories) {
    return <div className="p-4">No Checks</div>;
  }

  return (
    <div className="p-4 max-w-tab-wrapper overflow-auto">
      <table className="w-full">
        <tbody>
          {checksUI?.categories.map((category, index) => (
            <>
              <tr key={index}>
                <td className="" colSpan={3}>
                  <div className="text-xl font-semibold text-gray-700 capitalize">
                    {category.category}
                  </div>
                </td>
              </tr>
              <TableHeader />
              {category.checks &&
                category.checks.map((check, jIndex) => (
                  <CheckListItem
                    check={check}
                    key={jIndex}
                    onChange={(item) =>
                      handleChangeDimension(item, index, jIndex)
                    }
                  />
                ))}
            </>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default DataQualityChecks;
