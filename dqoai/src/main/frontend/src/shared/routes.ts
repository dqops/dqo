export const ROUTES = {
  CONNECTION_DETAIL: (connection: string, tab: string) => `/checks/connection/${connection}/${tab}`,
  SCHEMA_LEVEL_PAGE: (connection: string, schema: string, tab: string) => `/checks/connection/${connection}/schema/${schema}/${tab}`,
  TABLE_LEVEL_PAGE: (connection: string, schema: string, table: string, tab: string) => `/checks/connection/${connection}/schema/${schema}/table/${table}/${tab}`,
  TABLE_AD_HOCS: (connection: string, schema: string, table: string) => `/checks/connection/${connection}/schema/${schema}/table/${table}/checks/ad-hocs`,
  TABLE_AD_HOCS_UI_FILTER: (connection: string, schema: string, table: string, category: string, checkName: string) => `/checks/connection/${connection}/schema/${schema}/table/${table}/checks/ad-hocs/${category}/${checkName}`,
  TABLE_CHECKPOINTS: (connection: string, schema: string, table: string, timePartitioned: string) => `/checks/connection/${connection}/schema/${schema}/table/${table}/checkpoints/${timePartitioned}`,
  TABLE_PARTITIONED: (connection: string, schema: string, table: string, timePartitioned: string) => `/checks/connection/${connection}/schema/${schema}/table/${table}/partitioned/${timePartitioned}`,
};