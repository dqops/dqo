import React, { useEffect, useState } from 'react';
import { DefaultTableCheckPatternsApiClient } from '../../services/apiClient';

import DefaultCheckPatternsTable from './DefaultCheckPatternsTable';
import { DefaultTableChecksPatternListModel } from '../../api';
import { sortPatterns } from '../../utils';

export default function TableLevelPatterns() {
  const [patterns, setPatterns] = useState<
    DefaultTableChecksPatternListModel[]
  >([]);
  const getPatterns = async () => {
    DefaultTableCheckPatternsApiClient.getAllDefaultTableChecksPatterns().then(
      (res) => setPatterns(sortPatterns(res.data ?? [], 'priority'))
    );
  };
  useEffect(() => {
    getPatterns();
  }, []);

  const deletePattern = (patternName: string) => {
    DefaultTableCheckPatternsApiClient.deleteDefaultTableChecksPattern(
      patternName
    ).then(() => getPatterns());
  };

  return (
    <div className="py-2">
      <div className="p-4">
        <DefaultCheckPatternsTable
          patterns={patterns}
          deletePattern={deletePattern}
        />
      </div>
    </div>
  );
}
