import React, { useEffect, useState } from 'react';
import { DefaultTableCheckPatternsApiClient } from '../../services/apiClient';

import DefaultCheckPatternsTable from './DefaultCheckPatternsTable';
import { DefaultTableChecksPatternListModel } from '../../api';

export default function TableLevelPatterns() {
  const [patterns, setPatterns] = useState<
    DefaultTableChecksPatternListModel[]
  >([]);

  useEffect(() => {
    const getPatterns = async () => {
      DefaultTableCheckPatternsApiClient.getAllDefaultTableChecksPatterns().then(
        (res) => setPatterns(res.data)
      );
    };

    getPatterns();
  }, []);

  return (
    <div className="py-2">
      <div className="p-4">
        <DefaultCheckPatternsTable patterns={patterns} />-
      </div>
    </div>
  );
}
