import React from 'react';
import TableLevelPatterns from './TableLevelPatterns';
import Button from '../../components/Button';
import { useSelector } from 'react-redux';
import { getFirstLevelSensorState } from '../../redux/selectors';
import ColumnLevelPatterns from './ColumnLevelPatterns';
import { useDefinition } from '../../contexts/definitionContext';

export default function DefaultCheckPatterns() {
  const { type }: { type: 'table' | 'column' } = useSelector(
    getFirstLevelSensorState
  );

  const { openDefaultCheckPatternFirstLevelTab } = useDefinition()

  const addPattern = () => {
    openDefaultCheckPatternFirstLevelTab(type, 'new pattern', {create: true})
  }

  return (
    <>
      <div className="flex justify-between px-4 py-2 border-b border-gray-300 mb-2 h-14 items-center flex-shrink-0">
        <div className="flex items-center justify-between w-full">
          <div className="text-xl font-semibold truncate">
            Defualt check patterns {type}
          </div>
        </div>
        <Button label="Add pattern" color="primary" className="w-45" onClick={addPattern}/>
      </div>
      {type === 'table' ? <TableLevelPatterns /> : <ColumnLevelPatterns />}
    </>
  );
}
