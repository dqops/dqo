import axios from 'axios';
import { ConnectionsApiFactory, Configuration } from '../api';

export const ConnectionApiClient = ConnectionsApiFactory(
  new Configuration(),
  "http://localhost:8888",
  axios
);