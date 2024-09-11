import React, { useEffect, useState } from 'react';
import { ColumnQualityPoliciesApiClient } from '../../services/apiClient';

import { ColumnQualityPolicyListModel } from '../../api';
import Loader from '../../components/Loader';
import { sortPatterns } from '../../utils';
import DefaultCheckPatternsTable from './DefaultCheckPatternsTable';

const getPreparedPatterns = (
  patterns: ColumnQualityPolicyListModel[]
) => {
  const arr: any[] = [];

  patterns.forEach((x) => {
    const targetSpec: any = x.target_column;
    if (
      targetSpec &&
      typeof targetSpec === 'object' &&
      Object.keys(targetSpec).length !== 0
    ) {
      arr.push({ ...x, ...targetSpec });
    } else {
      arr.push(x);
    }
  });

  return arr;
};

export default function ColumnLevelPatterns() {
  const [patterns, setPatterns] = useState<
    ColumnQualityPolicyListModel[]
  >([]);
  const [loading, setLoading] = useState(false);

  const getPatterns = async () => {
    setLoading(true);
    ColumnQualityPoliciesApiClient.getColumnQualityPolicies()
      .then((res) =>
        setPatterns(
          sortPatterns(getPreparedPatterns(res.data ?? []), 'priority', 'asc')
        )
      )
      .finally(() => setLoading(false));
  };

  useEffect(() => {
    getPatterns();
  }, []);

  const deletePattern = (patternName: string) => {
    ColumnQualityPoliciesApiClient.deleteColumnQualityPolicy(
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
