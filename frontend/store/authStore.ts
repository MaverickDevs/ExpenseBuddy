// store/authStore.ts
import { create } from 'zustand';
import { persist, PersistOptions } from 'zustand/middleware';
import * as SecureStore from 'expo-secure-store';

interface AuthState {
  refreshToken: string | null;
  accessToken: string | null;
  refreshTokenExpiry: number | null;
//   hydrated: boolean;
  setTokens: (accessToken: string, refreshToken:string,expiresIn: number) => void;
  clearTokens: () => void;
  isRefreshValid: () => boolean;
  logout: () => Promise<void>;
}

type AuthPersist = {
    refreshToken: string | null;
    refreshTokenExpiry: number | null;
    accessToken: string | null;
}
  
  // Configure persistence with explicit types
const persistConfig: PersistOptions<AuthState, AuthPersist> = {
    name: 'auth-storage',
    storage: {
      getItem: async (name) => {
        const value = await SecureStore.getItemAsync(name);
        return value ? JSON.parse(value) : null;
      },
      setItem: async (name, value) => {
        await SecureStore.setItemAsync(name, JSON.stringify(value));
      },
      removeItem: async (name) => {
        await SecureStore.deleteItemAsync(name);
      },
    },
    // onRehydrateStorage: () => (state) => {
    //     state?.hydrated && (state.hydrated = true);    
    // },
    partialize: (state) => ({
      refreshToken: state.refreshToken,
      refreshTokenExpiry: state.refreshTokenExpiry,
        accessToken: state.accessToken
    })
  }

export const useAuthStore = create<AuthState>()(
  persist(
    (set, get) => ({
      refreshToken: null,
      refreshTokenExpiry: null,
      hydrated: false,
      accessToken: null,
      
      setTokens: (accessToken, refreshToken, expiresIn) => {
        const expiryTime = Date.now() + expiresIn * 1000;
        console.log("Setting tokens",accessToken, refreshToken, expiresIn)
        set({
          refreshToken: refreshToken,
          accessToken: accessToken,
          refreshTokenExpiry: expiryTime
        });
      },
      
      clearTokens: () => {
        set({
          refreshToken: null,
          refreshTokenExpiry: null,
          accessToken: null
        });
      },
      
      isRefreshValid: () => {
        const { refreshToken, refreshTokenExpiry } = get();
        const now = Date.now();
        return !!refreshToken && (refreshTokenExpiry || 0) > now;
      },
      logout: async () => {
        // Clear both memory and SecureStore
        await SecureStore.deleteItemAsync('auth-storage');
        set({
          accessToken: null,
          refreshToken: null,
          refreshTokenExpiry: null
        });
        // Add any additional cleanup here
      },
    }),
    persistConfig
  )
);