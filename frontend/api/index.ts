import { apiBaseUrl } from '@/utils/constants';
import axios from 'axios';

const apiClient = axios.create({
  baseURL: apiBaseUrl,
  timeout: 10000,
  headers: {
    'Content-Type': 'application/json',
    // Add any default headers here
  },
});

apiClient.interceptors.request.use(
    (config) => {
      // You can modify requests here (e.g., add auth token)
      // const token = getTokenFromStore();
      // if (token) {
      //   config.headers.Authorization = `Bearer ${token}`;
      // }
      return config;
    },
    (error) => {
      return Promise.reject(error);
    }
  );
  
  // Response interceptor
  apiClient.interceptors.response.use(
    (response) => response.data,
    (error) => {
      // Handle errors globally
      return Promise.reject(error?.response?.data || error.message);
    }
  );
  
  export default apiClient;