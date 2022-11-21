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
    <>
      <tr>
        <td className="text-left whitespace-nowrap text-gray-700 py-3 px-4 border-b font-semibold bg-gray-400 text-lg" />
        <td className="text-left whitespace-nowrap text-gray-700 py-3 px-4 border-b font-semibold bg-gray-400 text-lg" />
        <td
          className="text-center whitespace-nowrap text-gray-700 py-3 px-4 border-l border-b border-r font-semibold bg-gray-400 text-lg"
          colSpan={2}
        >
          Failing check
        </td>
        <td className="w-5 border-b" />
        <td
          className="text-center whitespace-nowrap text-gray-700 py-3 px-4 border-l border-b font-semibold bg-gray-400 text-lg"
          colSpan={2}
        >
          Passing check
        </td>
      </tr>
      <tr>
        <td className="text-left whitespace-nowrap text-gray-700 py-3 px-4 border-b font-semibold bg-gray-400 text-lg">
          Data quality check
        </td>
        <td className="text-left whitespace-nowrap text-gray-700 py-3 px-4 border-b font-semibold bg-gray-400 text-lg">
          Sensor parameters
        </td>
        <td className="text-left whitespace-nowrap text-gray-700 py-3 px-4 border-b font-semibold bg-orange-100 text-lg">
          Error threshold
        </td>
        <td className="text-left whitespace-nowrap text-gray-700 py-3 px-4 border-b font-semibold bg-red-100 text-lg">
          Fatal threshold
        </td>
        <td className="w-5 border-b" />
        <td className="text-left whitespace-nowrap text-gray-700 py-3 px-4 border-b font-semibold bg-yellow-100 text-lg">
          Warning threshold
        </td>
      </tr>
    </>
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
                  colSpan={2}
                >
                  <div className="text-lg font-semibold text-gray-700 capitalize">
                    {category.category}
                  </div>
                </td>
                <td className="py-2 px-4 bg-gray-50 border-b border-t bg-orange-100" />
                <td className="py-2 px-4 bg-gray-50 border-b border-t bg-yellow-100" />
                <td className="w-5 border-b" />
                <td className="py-2 px-4 bg-gray-50 border-b border-t bg-red-100" />
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
