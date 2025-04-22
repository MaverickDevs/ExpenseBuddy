// app/_layout.tsx
import { Stack } from 'expo-router';
import { useAuthStore } from '../store/authStore';
import { ActivityIndicator, View } from 'react-native';
import { useEffect, useState } from 'react';
import '../global.css';

export default function RootLayout() {
  const [isHydrated, setIsHydrated] = useState(false);
  const { isRefreshValid } = useAuthStore();

  // Wait for Zustand to rehydrate
  useEffect(() => {
    const rehydrate = async () => {
      await useAuthStore.persist.rehydrate();
      setIsHydrated(true);
    };
    rehydrate();
  }, []);

  if (!isHydrated) {
    return (
      <View style={{ flex: 1, justifyContent: 'center', alignItems: 'center' }}>
        <ActivityIndicator size="large" />
      </View>
    );
  }

  return (
    <Stack>
      <Stack.Screen
        name="(auth)"
        options={{ headerShown: false }}
        redirect={!isRefreshValid()}
      />
      <Stack.Screen
        name="(tabs)"
        options={{ headerShown: false }}
        redirect={isRefreshValid()}
      />
      <Stack.Screen
        name="(modals)"
        options={{ headerShown: false }}
        redirect={isRefreshValid()}
      />
    </Stack>
  );
}