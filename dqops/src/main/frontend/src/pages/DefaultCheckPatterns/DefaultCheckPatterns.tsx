import React from 'react';
import TableLevelPatterns from './TableLevelPatterns';
import Button from '../../components/Button';
import { useSelector } from 'react-redux';
import { getFirstLevelSensorState } from '../../redux/selectors';
import ColumnLevelPatterns from './ColumnLevelPatterns';
import {
  DefaultColumnCheckPatternsApiClient,
  DefaultTableCheckPatternsApiClient
} from '../../services/apiClient';
import {
  DefaultColumnChecksPatternModel,
  DefaultTableChecksPatternModel
} from '../../api';

export default function DefaultCheckPatterns() {
  const { type }: { type: 'table' | 'column' } = useSelector(
    getFirstLevelSensorState
  );

  const addTablePattern = async (
    patternName: string,
    body?: DefaultTableChecksPatternModel | undefined
  ) => {
    DefaultTableCheckPatternsApiClient.createDefaultTableChecksPattern(
      patternName,
      body
    );
  };

  const addColumnPattern = async (
    patternName: string,
    body?: DefaultColumnChecksPatternModel | undefined
  ) => {
    DefaultColumnCheckPatternsApiClient.createDefaultColumnChecksPattern(
      patternName,
      body
    );
  };

  return (
    <>
      <div className="flex justify-between px-4 py-2 border-b border-gray-300 mb-2 h-14 items-center flex-shrink-0">
        <div className="flex items-center justify-between w-full">
          <div className="text-xl font-semibold truncate">
            Defualt check patterns {type}
          </div>
        </div>
        <Button label="Add pattern" color="primary" className="w-45" />
      </div>
      {type === 'table' ? <TableLevelPatterns /> : <ColumnLevelPatterns />}
    </>
  );
}
