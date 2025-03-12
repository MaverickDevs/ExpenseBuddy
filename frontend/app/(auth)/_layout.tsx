import { Tabs } from 'expo-router';
import Entypo from '@expo/vector-icons/Entypo';

export default function AuthLayout() {
  return (
    <Tabs screenOptions={{ headerShown: false }} >
        <Tabs.Screen 
          name="index"   
          options={{
          title: 'Sign Up',
          tabBarIcon: ({ color }) => <Entypo name="add-user" size={24} color="black" />,
        }}/>
        <Tabs.Screen 
          name="login"   
          options={{
          title: 'Login',
          tabBarIcon: ({ color }) => <Entypo name="login" size={24} color="black" />
        }}/>
    </Tabs>
  );
}