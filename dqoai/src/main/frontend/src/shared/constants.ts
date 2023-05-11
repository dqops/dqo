import { CheckTypes } from "./routes";

export type PageTab = {
  label: string;
  value: string;
};

export const CONNECTION_LEVEL_TABS: {
  [key in CheckTypes]: PageTab[]
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
  [key in CheckTypes]: PageTab[]
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
  [key in CheckTypes]: PageTab[]
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