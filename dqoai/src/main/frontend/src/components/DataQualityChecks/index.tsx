import React, { useEffect } from 'react';
import { CheckResultsOverviewDataModel, UIAllChecksModel, UICheckModel } from '../../api';
import { useTree } from '../../contexts/treeContext';
import clsx from 'clsx';
import { useParams } from "react-router-dom";
import CheckCategoriesView from "./CheckCategoriesView";
import TableHeader from "./CheckTableHeader";
import Loader from "../Loader";

interface IDataQualityChecksProps {
  checksUI?: UIAllChecksModel;
  onChange: (ui: UIAllChecksModel) => void;
  className?: string;
  checkResultsOverview: CheckResultsOverviewDataModel[];
  getCheckOverview: () => void;
  onUpdate: () => void;
  loading?: boolean;
}

const DataQualityChecks = ({ checksUI, onChange, className, checkResultsOverview = [], getCheckOverview, onUpdate, loading }: IDataQualityChecksProps) => {
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

  if (loading) {
    return (
      <div className="flex justify-center min-h-80">
        <Loader isFull={false} className="w-8 h-8 fill-green-700" />
      </div>
    );
  }

  if (!checksUI?.categories) {
    return <div className="p-4">No Checks</div>;
  }

  return (
    <div
      className={clsx(className, 'p-4 overflow-auto')}
      style={{ maxWidth: `calc(100vw - ${sidebarWidth + 30}px` }}
    >
      <table className="w-full">
        <TableHeader checksUI={checksUI} />
        <tbody>
          {checksUI?.categories.map((category, index) => (
            <CheckCategoriesView
              key={index}
              category={category}
              checkResultsOverview={checkResultsOverview}
              handleChangeDataDataStreams={(check, jIndex) => handleChangeDataDataStreams(check, index, jIndex)}
              onUpdate={onUpdate}
              getCheckOverview={getCheckOverview}
            />
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default DataQualityChecks;
