import { View, Text } from 'react-native'
import React from 'react'

interface PaymentCardProps {
    id: number;
    date: string;
    amount: number;
    category: string;
    description: string;
    icon: React.ReactNode;
}


const PaymentCard = ({id, date, amount, category, description, icon}:PaymentCardProps) => {
  return (
    <View className="flex-row items-center justify-between bg-white p-4 rounded-lg shadow-md mx-4 my-2" key={id}>
        <View className='flex-row items-center gap-4'>
            <View>
                {icon}
            </View>
            <View>
                <Text className="text-lg font-bold">{category}</Text>
                <Text className="text-gray-500">{description}</Text>
            </View>
        </View>
        <View className='justify-center items-end'>
            <Text className="text-lg font-bold">${amount}</Text>
            <Text className="text-gray-500">{new Date(date).toLocaleString('en', { month: 'short' })}{" "}{new Date(date).getDay()}</Text>
        </View>
    </View>
  )
}

export default PaymentCard