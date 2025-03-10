import { Tabs } from 'expo-router';

export default function AuthLayout() {
  return (
    <Tabs screenOptions={{ headerShown: false }}  initialRouteName="signup">
      <Tabs.Screen name="login" />
      <Tabs.Screen name="signup" />
    </Tabs>
  );
}