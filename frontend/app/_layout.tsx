import { useEffect, useState } from 'react';
import { router, Stack } from 'expo-router';
import { ActivityIndicator, View } from 'react-native';
import { useAuthStore } from '../store/authStore';
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

  useEffect(() => {
    if (isHydrated) {
      if (isRefreshValid()) {
        router.replace('/(tabs)');
      } else {
        router.replace('/(auth)/login');
      }
    }
  }, [isHydrated]);

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
      />
      <Stack.Screen
        name="(tabs)"
        options={{ headerShown: false }}
      />
    </Stack>
  );
}
