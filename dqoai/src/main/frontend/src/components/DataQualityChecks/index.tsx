import React, { useEffect, Fragment } from 'react';
import { CheckResultsOverviewDataModel, UIAllChecksModel, UICheckModel } from '../../api';
import CheckListItem from './CheckListItem';
import { useTree } from '../../contexts/treeContext';
import clsx from 'clsx';
import { useParams } from "react-router-dom";

interface IDataQualityChecksProps {
  checksUI?: UIAllChecksModel;
  onChange: (ui: UIAllChecksModel) => void;
  className?: string;
  checkResultsOverview: CheckResultsOverviewDataModel[];
  getCheckOverview: () => void;
  onUpdate: () => void;
}

const TableHeader = () => {
  return (
    <thead>
      <tr>
        <td className="text-left whitespace-nowrap text-gray-700 py-3 px-4 border-b font-semibold bg-gray-400" />
        <td className="text-left whitespace-nowrap text-gray-700 py-3 px-4 border-b font-semibold bg-gray-400" />
        <td
          className="text-center whitespace-nowrap text-gray-700 py-3 px-4 border-l border-b border-r font-semibold bg-gray-400"
          colSpan={2}
        >
          Failing check
        </td>
        <td className="w-5 border-b" />
        <td
          className="text-center whitespace-nowrap text-gray-700 py-3 px-4 border-l border-b font-semibold bg-gray-400"
          colSpan={2}
        >
          Passing check
        </td>
      </tr>
      <tr>
        <td className="text-left whitespace-nowrap text-gray-700 py-3 px-4 border-b font-semibold bg-gray-400">
          Data quality check
        </td>
        <td className="text-left whitespace-nowrap text-gray-700 py-3 px-4 border-b font-semibold bg-gray-400">
          Sensor parameters
        </td>
        <td className="text-left whitespace-nowrap text-gray-700 py-3 px-4 border-b font-semibold bg-orange-100">
          Error threshold
        </td>
        <td className="text-left whitespace-nowrap text-gray-700 py-3 px-4 border-b font-semibold bg-red-100">
          Fatal threshold
        </td>
        <td className="w-5 border-b" />
        <td className="text-left whitespace-nowrap text-gray-700 py-3 px-4 border-b font-semibold bg-yellow-100">
          Warning threshold
        </td>
      </tr>
    </thead>
  );
};

const DataQualityChecks = ({ checksUI, onChange, className, checkResultsOverview = [], getCheckOverview, onUpdate }: IDataQualityChecksProps) => {
  const { connection, schema, table, column }: { connection: string, schema: string, table: string, column: string } = useParams();

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

  useEffect(() => {
    getCheckOverview();
  }, [connection, schema, table, column]);

  if (!checksUI?.categories) {
    return <div className="p-4">No Checks</div>;
  }

  return (
    <div
      className={clsx(className, 'p-4 overflow-auto')}
      style={{ maxWidth: `calc(100vw - ${sidebarWidth + 30}px` }}
    >
      <table className="w-full">
        <TableHeader />
        <tbody>
          {checksUI?.categories.map((category, index) => (
            <Fragment key={index}>
              <tr>
                <td
                  className="py-2 px-4 bg-gray-50 border-b border-t"
                  colSpan={2}
                >
                  <div className="font-semibold text-gray-700 capitalize">
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
                    checkResult={checkResultsOverview.find((item) => item.checkName === check.check_name && category.category === item.checkCategory)}
                    getCheckOverview={getCheckOverview}
                    onUpdate={onUpdate}
                  />
                ))}
            </Fragment>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default DataQualityChecks;
