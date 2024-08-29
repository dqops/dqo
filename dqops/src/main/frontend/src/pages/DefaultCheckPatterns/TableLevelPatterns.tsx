import React, { useEffect, useState } from 'react';
import { DefaultTableCheckPatternsApiClient } from '../../services/apiClient';

import { DefaultTableChecksPatternListModel } from '../../api';
import Loader from '../../components/Loader';
import { sortPatterns } from '../../utils';
import DefaultCheckPatternsTable from './DefaultCheckPatternsTable';

export default function TableLevelPatterns() {
  const [patterns, setPatterns] = useState<
    DefaultTableChecksPatternListModel[]
  >([]);
  const [loading, setLoading] = useState(false);
  const getPatterns = async () => {
    setLoading(true);
    DefaultTableCheckPatternsApiClient.getAllDefaultTableChecksPatterns()
      .then((res) =>
        setPatterns(sortPatterns(res.data ?? [], 'priority', 'asc'))
      )
      .finally(() => setLoading(false));
  };
  useEffect(() => {
    getPatterns();
  }, []);

  const deletePattern = (patternName: string) => {
    DefaultTableCheckPatternsApiClient.deleteDefaultTableChecksPattern(
      patternName
    ).then(() => getPatterns());
  };

  if (loading) {
    return (
      <div className="w-full h-screen flex justify-center items-center">
        <Loader isFull={false} className="w-8 h-8 fill-green-700" />
      </div>
    );
  }

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
