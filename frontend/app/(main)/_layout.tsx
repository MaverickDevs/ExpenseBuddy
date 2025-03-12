import { Tabs } from 'expo-router';
import FontAwesome from '@expo/vector-icons/FontAwesome';

export default function MainLayout() {
  return (
     <Tabs screenOptions={{ headerShown: false }} >
        <Tabs.Screen 
          name="index"   
          options={{
          title: 'Home',
          tabBarIcon: ({ color }) => <FontAwesome size={28} name="home" color={color} />,
        }}/>
        <Tabs.Screen 
          name="groups"   
          options={{
          title: 'Groups',
          tabBarIcon: ({ color }) => <FontAwesome size={28} name="group" color={color} />,
        }}/>
        <Tabs.Screen 
          name="payments"   
          options={{
          title: 'Payments',
          tabBarIcon: ({ color }) => <FontAwesome size={28} name="money" color={color} />,
        }}/>
      </Tabs>
  );
}