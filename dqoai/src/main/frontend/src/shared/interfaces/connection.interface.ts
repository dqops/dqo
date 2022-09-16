export interface IBigQuery {
  authentication_mode: string;
  billing_project_id: string;
  json_key_content: string;
  json_key_path: string;
  quota_project_id: string;
  source_project_id: string;
}

export interface ISnowflake {
  account: string;
  role: string;
  warehouse: string;
}

export interface IComment {
  comment: string;
  comment_by: string;
  date: string;
}

export interface IDimension {
  column: string;
  source: string;
  static_value: string;
}

export interface IDefaultTimeSeries {
  excluded_recent_periods: number;
  incremental_time_window_periods: number;
  mode: string;
  time_gradient: string;
  time_window_periods: number;
  timestamp_column: string;
}

export interface IConnection {
  name: string;
  spec: {
    bigquery?: IBigQuery;
    comments?: IComment[];
    database_name: string;
    default_dimensions?: {
      dimension_1: IDimension,
      dimension_2: IDimension,
      dimension_3: IDimension,
      dimension_4: IDimension,
      dimension_5: IDimension,
      dimension_6: IDimension,
      dimension_7: IDimension,
      dimension_8: IDimension,
    };
    default_time_series?: IDefaultTimeSeries,
    password?: string;
    properties: any;
    provider_type: string;
    schedule?: {
      cron_expression: string;
      disable: boolean;
    };
    snowflake?: ISnowflake
    time_zone?: string;
    url?: string;
    user?: string;
  }
}
