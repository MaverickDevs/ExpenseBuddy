import { useAuthStore } from '@/store/authStore';
import axios from 'axios';

// Configure axios instance
const api = axios.create({
  baseURL: process.env.REACT_APP_API_BASE_URL,
  timeout: 10000,
  headers: {
    'Content-Type': 'application/json',
  },
});

// Track ongoing refresh token request
let refreshPromise: Promise<string> | null = null;

// Helper to check if route should skip auth
const isUnprotectedRoute = (url?: string) => {
  return url?.match(/\/auth\/v1\/(login|refreshToken|signup)/);
};


// Request interceptor
api.interceptors.request.use(async (config) => {
  if (isUnprotectedRoute(config.url)) {
    return config;
  }

  const { accessToken, refreshToken, isRefreshValid,logout } = useAuthStore.getState();
  
  if (accessToken) {
    config.headers.Authorization = `Bearer ${accessToken}`;
    return config;
  }

  // Check refresh token validity before attempting refresh
  if (!refreshToken || !isRefreshValid()) {
    await logout();
    throw new Error('Session expired');
  }

  if (refreshPromise) {
    const newToken = await refreshPromise;
    config.headers.Authorization = `Bearer ${newToken}`;
    return config;
  }

  try {
    refreshPromise = api.post('/auth/v1/refreshToken', { refreshToken })
      .then(({ data }) => {
        useAuthStore.getState().setTokens(
          data.accessToken,
          data.refreshToken,
          data.expiresIn
        );
        return data.accessToken;
      })
      .finally(() => {
        refreshPromise = null;
      });

    const newToken = await refreshPromise;
    config.headers.Authorization = `Bearer ${newToken}`;
    return config;
  } catch (error) {
    useAuthStore.getState().clearTokens();
    throw error;
  }
});

// Response interceptor
// In your api.ts interceptors
api.interceptors.response.use(
  (response) => response.data,
  async (error) => {
    const originalRequest = error.config;
    
    if (error.response?.status === 401) {
      const { refreshToken, isRefreshValid, clearTokens } = useAuthStore.getState();
      
      // Case 1: Refresh token expired
      if (!isRefreshValid()) {
        clearTokens(); // Just clear tokens - layout will handle redirect
        return Promise.reject(new Error('session_expired'));
      }
      
      // Case 2: Valid refresh exists
      if (!originalRequest._retry) {
        originalRequest._retry = true;
        try {
          const { data } = await api.post('/auth/v1/refreshToken', { refreshToken });
          useAuthStore.getState().setTokens(data.accessToken, data.refreshToken, data.expiresIn);
          originalRequest.headers.Authorization = `Bearer ${data.accessToken}`;
          return api(originalRequest);
        } catch (err) {
          clearTokens();
          return Promise.reject(err);
        }
      }
    }
    
    return Promise.reject(error?.response?.data || error);
  }
);

export const apiWrapper = {
  get: (endpoint:string, params = {}) => {
    return new Promise((resolve, reject) => {
      api.get(endpoint, { params })
        .then(response => resolve(response.data))
        .catch(error => reject(error.response?.data || error.message));
    });
  },
  post: (endpoint:string, body = {}) => {
    return new Promise((resolve, reject) => {
      api.post(endpoint, body)
        .then(response => resolve(response.data))
        .catch(error => reject(error.response?.data || error.message));
    });
  },
  put: (endpoint:string, body = {}) => {
    return new Promise((resolve, reject) => {
      api.put(endpoint, body)
        .then(response => resolve(response.data))
        .catch(error => reject(error.response?.data || error.message));
    });
  },
  delete: (endpoint:string, params = {}) => {
    return new Promise((resolve, reject) => {
      api.delete(endpoint, { params })
        .then(response => resolve(response.data))
        .catch(error => reject(error.response?.data || error.message));
    });
  },
};