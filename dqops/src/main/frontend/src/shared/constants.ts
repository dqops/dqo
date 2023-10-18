
import { TimeWindowFilterParameters } from '../api';

export type PageTab = {
  label: string;
  value: string;
};
enum CheckTypes {
  MONITORING = 'monitoring',
  SOURCES = 'sources',
  PROFILING = 'profiling',
  PARTITIONED = 'partitioned',
}
export const CONNECTION_LEVEL_TABS: {
  [key in CheckTypes]: PageTab[];
} = {
  [CheckTypes.SOURCES]: [
    {
      label: 'Connection',
      value: 'detail'
    },
    {
      label: 'Schedule',
      value: 'schedule'
    },
    {
      label: 'Comments',
      value: 'comments'
    },
    {
      label: 'Labels',
      value: 'labels'
    },
    {
      label: 'Schemas',
      value: 'schemas'
    },
    {
      label: 'Default grouping template',
      value: 'data-streams'
    }
  ],
  [CheckTypes.PROFILING]: [
    {
      label: 'Schedule',
      value: 'schedule'
    },
    {
      label: 'Schemas',
      value: 'schemas'
    }
  ],
  [CheckTypes.PARTITIONED]: [
    {
      label: 'Schedule',
      value: 'schedule'
    },
    {
      label: 'Schemas',
      value: 'schemas'
    }
  ],
  [CheckTypes.MONITORING]: [
    {
      label: 'Schedule',
      value: 'schedule'
    },
    {
      label: 'Schemas',
      value: 'schemas'
    }
  ]
};

export const TABLE_LEVEL_TABS: {
  [key in CheckTypes]: PageTab[];
} = {
  [CheckTypes.SOURCES]: [
    {
      label: 'Table',
      value: 'detail'
    },
    {
      label: 'Schedule',
      value: 'schedule'
    },
    {
      label: 'Comments',
      value: 'comments'
    },
    {
      label: 'Labels',
      value: 'labels'
    },
    {
      label: 'Data Groupings',
      value: 'data-streams'
    },
    {
      label: 'Date and time columns',
      value: 'timestamps'
    }
  ],
  [CheckTypes.PROFILING]: [
    {
      label: 'statistics',
      value: 'statistics'
    }, 
    {
      label: 'advanced',
      value: 'advanced'
    },
    {
      label: 'table-comparison',
      value: 'table-comparison'
    },
  ],
  [CheckTypes.PARTITIONED]: [
    {
      label: 'Daily',
      value: 'daily'
    },
    {
      label: 'Monthly',
      value: 'monthly'
    }
  ],
  [CheckTypes.MONITORING]: [
    {
      label: 'Daily',
      value: 'daily'
    },
    {
      label: 'Monthly',
      value: 'monthly'
    }
  ]
};

export const COLUMN_LEVEL_TABS: {
  [key in CheckTypes]: PageTab[];
} = {
  [CheckTypes.SOURCES]: [
    {
      label: 'Column',
      value: 'detail'
    },
    {
      label: 'Comments',
      value: 'comments'
    },
    {
      label: 'Labels',
      value: 'labels'
    }
  ],
  [CheckTypes.PROFILING]: [
    {
      label: 'statistics',
      value: 'statistics'
    },
    {
      label: 'advanced',
      value: 'advanced'
    },
    {
      label: 'table-comparison',
      value: 'table-comparison'
    },
  ],
  [CheckTypes.PARTITIONED]: [
    {
      label: 'Daily',
      value: 'daily'
    },
    {
      label: 'Monthly',
      value: 'monthly'
    }
  ],
  [CheckTypes.MONITORING]: [
    {
      label: 'Daily',
      value: 'daily'
    },
    {
      label: 'Monthly',
      value: 'monthly'
    }
  ]
};

export const RUN_CHECK_TIME_WINDOW_FILTERS: {
  [key in string]: TimeWindowFilterParameters | null;
} = {
  'Default incremental time window': null,
  'Today only': {
    daily_partitioning_include_today: true,
    daily_partitioning_recent_days: 0
  },
  'Yesterday only': {
    daily_partitioning_include_today: false,
    daily_partitioning_recent_days: 1
  },
  'Last 3 days, excluding today': {
    daily_partitioning_include_today: false,
    daily_partitioning_recent_days: 3
  },
  'Last 7 days, excluding today': {
    daily_partitioning_include_today: false,
    daily_partitioning_recent_days: 7
  },
  'Last 30 days, excluding today': {
    daily_partitioning_include_today: false,
    daily_partitioning_recent_days: 30
  },
  'Current month only': {
    monthly_partitioning_include_current_month: true,
    monthly_partitioning_recent_months: 0
  },
  'Last month only': {
    monthly_partitioning_include_current_month: false,
    monthly_partitioning_recent_months: 1
  },
  'Last 3 months, excluding current month': {
    monthly_partitioning_include_current_month: false,
    monthly_partitioning_recent_months: 3
  },
  'Last 12 months, excluding current month': {
    monthly_partitioning_include_current_month: false,
    monthly_partitioning_recent_months: 12
  }
};

export const formatNumber = (t: number) => {
  const k = Math.abs(t);

  if (k > 1000 && k < 1000000) {
    if (k > Math.pow(10, 3) && k < Math.pow(10, 4)) {
      return (k / Math.pow(10, 3)).toFixed(3) + 'k';
    } else if (k > Math.pow(10, 4) && k < Math.pow(10, 5)) {
      return (k / Math.pow(10, 3)).toFixed(2) + 'k';
    } else {
      return (k / Math.pow(10, 3)).toFixed(1) + 'k';
    }
  } else if (k > Math.pow(10, 6) && k < Math.pow(10, 9)) {
    if (k > Math.pow(10, 6) && k < Math.pow(10, 7)) {
      return (k / Math.pow(10, 6)).toFixed(3) + 'M';
    } else if (k > Math.pow(10, 7) && k < Math.pow(10, 8)) {
      return (k / Math.pow(10, 6)).toFixed(2) + 'M';
    } else {
      return (k / Math.pow(10, 6)).toFixed(1) + 'M';
    }
  } else if (k > Math.pow(10, 9) && k < Math.pow(10, 12)) {
    if (k > Math.pow(10, 9) && k < Math.pow(10, 10)) {
      return (k / Math.pow(10, 9)).toFixed(3) + 'G';
    } else if (k > Math.pow(10, 10) && k < Math.pow(10, 11)) {
      return (k / Math.pow(10, 9)).toFixed(2) + 'G';
    } else {
      return (k / Math.pow(10, 9)).toFixed(1) + 'G';
    }
  } else if (k > Math.pow(10, 12) && k < Math.pow(10, 15)) {
    if (k > Math.pow(10, 12) && k < Math.pow(10, 13)) {
      return (k / Math.pow(10, 12)).toFixed(3) + 'T';
    } else if (k > Math.pow(10, 13) && k < Math.pow(10, 14)) {
      return (k / Math.pow(10, 12)).toFixed(2) + 'T';
    } else {
      return (k / Math.pow(10, 12)).toFixed(1) + 'T';
    }
  } else {
    return k;
  }
};

export const dateToString = (k: string) => {
  if (k === '') {
    return false;
  }

  if (isNaN(Date.parse(k))) {
    return false;
  }
  const a = k.replace(/T/g, ' ');
  return a;
};

export const datatype_detected = (numberForFile: any) => {
  if (Number(numberForFile) === 1) {
    return 'INTEGER';
  }
  if (Number(numberForFile) === 2) {
    return 'FLOAT';
  }
  if (Number(numberForFile) === 3) {
    return 'DATETIME';
  }
  if (Number(numberForFile) === 4) {
    return 'TIMESTAMP';
  }
  if (Number(numberForFile) === 5) {
    return 'BOOLEAN';
  }
  if (Number(numberForFile) === 6) {
    return 'STRING';
  }
  if (Number(numberForFile) === 7) {
    return 'Mixed data type';
  }
};
