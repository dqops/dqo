import apiClient from './apiClient';

export const getAllConnections = async () =>
  apiClient
    .get('/api/connections')
    .then((res) => res.data)
    .catch((err) => Promise.reject({ ...err }));