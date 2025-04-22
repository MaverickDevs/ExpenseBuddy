import { Text, TouchableOpacity, View } from 'react-native';
import FontAwesome from '@expo/vector-icons/FontAwesome';
import { Avatar } from 'react-native-elements';
import ExpenseTracker from '../components/ExpensePieChart';
import { FloatingActionButton } from '../components/FloatingButton';
import { useRouter } from 'expo-router';

export default function HomeScreen() {

  const router = useRouter();

  function addExpense() {
    console.log('Add Expense button pressed');
    router.navigate('/(modals)/personalexp');
    
  }
  return (
    <View className='h-full'>
      <View className="flex-row items-center justify-between border-b border-gray-200 px-4 py-3">
        <Text className="text-2xl font-bold">ExpenseBuddy</Text>
        <View className='flex-row items-center gap-3'>
          <TouchableOpacity className="p-1">
            <FontAwesome name="bell-o" size={24} color="black" />
          </TouchableOpacity>
          <TouchableOpacity className="flex items-center justify-center rounded-full overflow-hidden">
            <Avatar rounded 
            icon={{ name: 'user',type: 'font-awesome', color:"black" }}  size="small" 
            overlayContainerStyle={{backgroundColor: '#8a8a8a'}}  onPress={() => console.log("Works!")} activeOpacity={0.6}/>
          </TouchableOpacity>
        </View>
      </View>
      <ExpenseTracker />
      <FloatingActionButton onPress={addExpense}/>
    </View>
    
  );
}