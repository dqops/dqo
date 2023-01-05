import React, { useEffect, useState } from 'react';
import { useSelector } from 'react-redux';
import { IRootState } from '../../../redux/reducers';
import { useActionDispatch } from '../../../hooks/useActionDispatch';
import SvgIcon from '../../SvgIcon';
import DataQualityChecks from '../../DataQualityChecks';
import { UIAllChecksModel } from '../../../api';
import { getColumnAdHockChecksUIFilter } from '../../../redux/actions/column.actions';

interface ColumnAdHockChecksUIFilterViewProps {
  connectionName: string;
  schemaName: string;
  tableName: string;
  columnName: string;
  category: string;
  checkName: string;
}

const ColumnAdHockChecksUIFilterView = ({
  connectionName,
  schemaName,
  tableName,
  category,
  columnName,
  checkName
}: ColumnAdHockChecksUIFilterViewProps) => {
  const { checksUIFilter } = useSelector(
    (state: IRootState) => state.column
  );
  const dispatch = useActionDispatch();
  const [updatedChecksUI, setUpdatedChecksUI] = useState<UIAllChecksModel>();
  
  useEffect(() => {
    setUpdatedChecksUI(checksUIFilter);
  }, [checksUIFilter]);
  
  useEffect(() => {
    dispatch(
      getColumnAdHockChecksUIFilter(connectionName, schemaName, tableName, columnName, category, checkName)
    );
  }, [connectionName, schemaName, tableName, category, checkName]);
  
  return (
    <div className="">
      <div className="flex justify-between px-4 py-2 border-b border-gray-300 mb-2 min-h-14">
        <div className="flex items-center space-x-2">
          <SvgIcon name="database" className="w-5 h-5" />
          <div className="text-xl font-semibold">{`${connectionName}.${schemaName}.${tableName}.${columnName}.checks.${category} - ${checkName}`}</div>
        </div>
      </div>
      <div>
        <DataQualityChecks
          onUpdate={() => {}}
          className="max-h-checks-1"
          checksUI={updatedChecksUI}
          onChange={setUpdatedChecksUI}
        />
      </div>
    </div>
  );
};

export default ColumnAdHockChecksUIFilterView;
