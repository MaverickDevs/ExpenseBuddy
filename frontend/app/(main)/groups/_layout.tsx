import { Stack } from 'expo-router';

export default function GroupsLayout() {
  return (
    <Stack screenOptions={{ headerShown: false }}>
      <Stack.Screen 
        name="index" 
        // options={{ 
        //   headerShown: true,
        //   title: "Groups"
        // }} 
      />
      <Stack.Screen 
        name="[id]" 
        // options={{ 
        //   headerShown: true,
        //   title: "Group Details"
        //   // You can also use dynamic titles like this:
        //   // title: ({ params }) => `Group ${params.id}`
        // }} 
      />
    </Stack>
  );
}