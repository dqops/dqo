import React, { useEffect, useState } from 'react';
import { DefaultColumnCheckPatternsApiClient } from '../../services/apiClient';

import DefaultCheckPatternsTable from './DefaultCheckPatternsTable';
import { DefaultColumnChecksPatternListModel } from '../../api';
import { sortPatterns } from '../../utils';

export default function ColumnLevelPatterns() {
  const [patterns, setPatterns] = useState<
    DefaultColumnChecksPatternListModel[]
  >([]);

  const getPatterns = async () => {
    DefaultColumnCheckPatternsApiClient.getAllDefaultColumnChecksPatterns().then(
      (res) => setPatterns(sortPatterns(res.data ?? [], 'priority', 'desc'))
    );
  };

  useEffect(() => {
    getPatterns();
  }, []);

  const deletePattern = (patternName: string) => {
    DefaultColumnCheckPatternsApiClient.deleteDefaultColumnChecksPattern(
      patternName
    ).then(() => getPatterns());
  };

  return (
    <div className="py-2">
      <div className="p-4">
        <DefaultCheckPatternsTable
          patterns={patterns}
          deletePattern={deletePattern}
          onChange={setPatterns}
        />
      </div>
    </div>
  );
}
