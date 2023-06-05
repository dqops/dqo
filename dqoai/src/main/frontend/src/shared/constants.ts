import { CheckTypes } from './routes';

export type PageTab = {
  label: string;
  value: string;
};

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
      label: 'Default data stream template',
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
  [CheckTypes.RECURRING]: [
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
      label: 'Data Streams',
      value: 'data-streams'
    },
    {
      label: 'Date and time columns',
      value: 'timestamps'
    }
  ],
  [CheckTypes.PROFILING]: [
    {
      label: 'Detail',
      value: 'detail'
    }
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
  [CheckTypes.RECURRING]: [
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
      label: 'Detail',
      value: 'detail'
    }
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
  [CheckTypes.RECURRING]: [
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
