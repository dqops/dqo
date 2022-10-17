import React from 'react';
import { UIAllChecksModel } from '../../api';
import CheckListItem from './CheckListItem';

interface IDataQualityChecksProps {
  checksUI?: UIAllChecksModel;
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

const DataQualityChecks = ({ checksUI }: IDataQualityChecksProps) => {
  if (!checksUI?.quality_dimensions) {
    return <div className="p-4">No Checks</div>;
  }

  return (
    <div className="p-4">
      <table className="w-full">
        <tbody>
          {checksUI?.quality_dimensions.map((dimension, index) => (
            <>
              <tr key={index}>
                <td className="" colSpan={3}>
                  <div className="text-xl font-semibold text-gray-700 capitalize">
                    {dimension.quality_dimension}
                  </div>
                </td>
              </tr>
              <TableHeader />
              {dimension.checks &&
                dimension.checks.map((check, jIndex) => (
                  <CheckListItem check={check} key={jIndex} />
                ))}
            </>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default DataQualityChecks;
