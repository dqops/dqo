import apiClient from './apiClient';

export const getCurrentUser = async () =>
  apiClient
    .get('/user/userinfo')
    .then((res) => res.data)
    .catch((err) => Promise.reject({ ...err }));

export const login = async (data: any) =>
  apiClient
    .post('/user/login', data)
    .then((res) => res.data)
    .catch((err) => Promise.reject({ ...err }));
