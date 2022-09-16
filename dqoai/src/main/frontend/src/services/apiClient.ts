import axios from 'axios';

import { ConnectionsApiFactory } from '../api';

export const ConnectionApiClient = ConnectionsApiFactory({}, 'http://localhost:8888', axios);
