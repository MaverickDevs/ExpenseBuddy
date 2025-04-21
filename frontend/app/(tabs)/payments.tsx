import { View, Text, FlatList } from 'react-native'
import React from 'react'
import PaymentCard from '../components/PaymentCard';
import { CategoryIcons } from '@/utils/constants';

const paymentData = [
  {
    id:1,
    date: "2023-10-01",
    amount: 100,
    category: "Food",
    description: "Groceries",
    icon: CategoryIcons["Food"],
},
{
    id:2,
    date: "2023-10-02",
    amount: 50,
    category: "Transport",
    description: "Bus ticket",
    icon: CategoryIcons["Transport"],
},
{
    id:3,
    date: "2023-10-03",
    amount: 200,
    category: "Shopping",
    description: "New shoes",
    icon: CategoryIcons["Shopping"],
},
{
    id:4,
    date: "2023-10-04",
    amount: 150,
    category: "Entertainment",
    description: "Movie tickets",
    icon: CategoryIcons["Entertainment"],
},
{
    id:5,
    date: "2023-10-05",
    amount: 80,
    category: "Utilities",
    description: "Electricity bill",
    icon: CategoryIcons["Utilities"],
},
{
  id:6,
  date: "2023-10-06",
  amount: 120,
  category: "Health",
  description: "Doctor's appointment",
  icon: CategoryIcons["Health"],
}
]

const payments = () => {
  return (
    <View className='flex-1 bg-gray-100'>
      <Text className='text-xl font-bold p-4 mt-2'>Payment History</Text>
      <FlatList
          data={paymentData}
          keyExtractor={(item) => item.id.toString()}
          renderItem={({ item }) => (
            <PaymentCard
              id={item.id}
              date={item.date}
              amount={item.amount}
              category={item.category}
              description={item.description}
              icon={item.icon}
            />
          )}
          contentContainerStyle={{padding: 5}}
          ItemSeparatorComponent={() => <View style={{ height: 0 }} />}
        />
    </View>
  )
}

export default payments