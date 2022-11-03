import axios from 'axios';

import {
  ConnectionsApiFactory,
  Configuration,
  SchemasApiFactory,
  TablesApiFactory,
  ColumnsApiFactory
} from '../api';

export const ConnectionApiClient = ConnectionsApiFactory(
  new Configuration(),
  '',
  axios
);

export const SchemaApiClient = SchemasApiFactory(
  new Configuration(),
  '',
  axios
);

export const TableApiClient = TablesApiFactory(new Configuration(), '', axios);

export const ColumnApiClient = ColumnsApiFactory(
  new Configuration(),
  '',
  axios
);
