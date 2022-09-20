import axios from 'axios';

import {
  ConnectionsApiFactory,
  Configuration,
  SchemasApiFactory
} from '../api';

export const ConnectionApiClient = ConnectionsApiFactory(
  new Configuration(),
  'http://localhost:8888',
  axios
);

export const SchemaApiClient = SchemasApiFactory(
  new Configuration(),
  'http://localhost:8888',
  axios
);
