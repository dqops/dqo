import React, { useEffect, useState } from 'react';
import { DefaultColumnCheckPatternsApiClient } from '../../services/apiClient';

import DefaultCheckPatternsTable from './DefaultCheckPatternsTable';
import { DefaultColumnChecksPatternListModel } from '../../api';

export default function ColumnLevelPatterns() {
  const [patterns, setPatterns] = useState<
    DefaultColumnChecksPatternListModel[]
  >([]);

  useEffect(() => {
    const getPatterns = async () => {
      DefaultColumnCheckPatternsApiClient.getAllDefaultColumnChecksPatterns().then(
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
