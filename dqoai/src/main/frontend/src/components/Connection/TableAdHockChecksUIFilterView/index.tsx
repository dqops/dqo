import React, { useEffect, useState } from 'react';
import { useSelector } from 'react-redux';
import { IRootState } from '../../../redux/reducers';
import { useActionDispatch } from '../../../hooks/useActionDispatch';
import {
  getTableAdHockChecksUIFilter,
} from '../../../redux/actions/table.actions';
import SvgIcon from '../../SvgIcon';
import DataQualityChecks from '../../DataQualityChecks';
import { UIAllChecksModel } from '../../../api';

interface TableAdHockChecksUIFilterViewProps {
  connectionName: string;
  schemaName: string;
  tableName: string;
  category: string;
  checkName: string;
}

const TableAdHockChecksUIFilterView = ({
  connectionName,
  schemaName,
  tableName,
  category,
  checkName
}: TableAdHockChecksUIFilterViewProps) => {
  const { checksUIFilter } = useSelector(
    (state: IRootState) => state.table
  );
  const dispatch = useActionDispatch();
  const [updatedChecksUI, setUpdatedChecksUI] = useState<UIAllChecksModel>();
  
  useEffect(() => {
    setUpdatedChecksUI(checksUIFilter);
  }, [checksUIFilter]);
  
  useEffect(() => {
    dispatch(
      getTableAdHockChecksUIFilter(connectionName, schemaName, tableName, category, checkName)
    );
  }, [connectionName, schemaName, tableName, category, checkName]);
  
  return (
    <div className="">
      <div className="flex justify-between px-4 py-2 border-b border-gray-300 mb-2 min-h-14">
        <div className="flex items-center space-x-2">
          <SvgIcon name="database" className="w-5 h-5" />
          <div className="text-xl font-semibold">{`${connectionName}.${schemaName}.${tableName}.checks.${category} - ${checkName}`}</div>
        </div>
      </div>
      <div>
        <DataQualityChecks
          className="max-h-checks-1"
          checksUI={updatedChecksUI}
          onChange={setUpdatedChecksUI}
        />
      </div>
    </div>
  );
};

export default TableAdHockChecksUIFilterView;
