import { Stack } from 'expo-router';

export default function ModalsLayout() {
  return (
    <Stack screenOptions={{ headerShown: false }}>
      <Stack.Screen 
        name="personalexp" 
      />
      <Stack.Screen 
        name="groupexp" 
      />
    </Stack>
  );
}