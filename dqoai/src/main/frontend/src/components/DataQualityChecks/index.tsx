import React from 'react';
import { UIAllChecksModel, UICheckModel } from '../../api';
import CheckListItem from './CheckListItem';
import { useTree } from '../../contexts/treeContext';

interface IDataQualityChecksProps {
  checksUI?: UIAllChecksModel;
  onChange: (ui: UIAllChecksModel) => void;
}

const TableHeader = () => {
  return (
    <tr>
      <td className="text-left text-gray-700 py-3 px-4 border-b font-semibold bg-gray-400 text-xl">
        Data quality check
      </td>
      <td className="text-left text-gray-700 py-3 px-4 border-b font-semibold bg-gray-400 text-xl">
        Sensor parameters
      </td>
      <td className="text-left text-gray-700 py-3 px-4 border-b font-semibold bg-gray-400 text-xl">
        Data quality rules
      </td>
    </tr>
  );
};

const DataQualityChecks = ({ checksUI, onChange }: IDataQualityChecksProps) => {
  const { sidebarWidth } = useTree();
  const handleChangeDataDataStreams = (
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
    <div
      className="p-4 max-h-table overflow-auto"
      style={{ maxWidth: `calc(100vw - ${sidebarWidth + 30}px` }}
    >
      <table className="w-full">
        <TableHeader />
        <tbody>
          {checksUI?.categories.map((category, index) => (
            <>
              <tr key={index}>
                <td
                  className="py-2 px-4 bg-gray-50 border-b border-t"
                  colSpan={3}
                >
                  <div className="text-lg font-semibold text-gray-700 capitalize">
                    {category.category}
                  </div>
                </td>
              </tr>
              {category.checks &&
                category.checks.map((check, jIndex) => (
                  <CheckListItem
                    check={check}
                    key={jIndex}
                    onChange={(item) =>
                      handleChangeDataDataStreams(item, index, jIndex)
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
