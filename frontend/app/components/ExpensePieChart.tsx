import React from 'react';
import { View } from 'react-native';
import { PieChart } from 'react-native-gifted-charts';
import { Text } from 'react-native';


const ExpenseTracker = () => {
  // Sample data
  const totalExpenses = 1850;
  const categories = [
    { name: 'Food', amount: 650, color: '#FF6B6B' },
    { name: 'Transport', amount: 450, color: '#4ECDC4' },
    { name: 'Shopping', amount: 400, color: '#45B7D1' },
    { name: 'Entertainment', amount: 250, color: '#FFBE0B' },
    { name: 'Utilities', amount: 100, color: '#FB5607' },
  ];

  // Pie chart data
  const pieData = categories.map(item => ({
    value: Math.round((item.amount / totalExpenses) * 100),
    color: item.color,
    text: `${Math.round((item.amount / totalExpenses) * 100)}%`,
    textColor: 'white',
  }));

  return (
    <View className="flex-1 p-5 bg-white">
      {/* Donut Chart */}
      <View className="items-center mb-8 text-black">
        <PieChart
          data={pieData}
          donut
          showText
          textColor="black"
          radius={120}
          innerRadius={60}
          textSize={12}
          focusOnPress
          
          centerLabelComponent={() => (
            <View className="justify-center items-center">
              <Text className="text-2xl font-bold">{pieData.length}</Text>
              <Text className="text-sm text-gray-500">Expenses</Text>
            </View>
          )}
        />
      </View>

      {/* Legend List */}
      <View className="mt-5">
        {categories.map((item, index) => {
          const percentage = Math.round((item.amount / totalExpenses) * 100);
          return (
            <View 
              key={index} 
              className="flex-row items-center mb-3"
            >
              <View 
                className="w-5 h-5 rounded mr-2" 
                style={{ backgroundColor: item.color }} 
              />
              <Text className="flex-1 text-base">{item.name}</Text>
              <Text className="w-12 text-base text-right mr-4">{percentage}%</Text>
              <Text className="w-20 text-base font-bold text-right">${item.amount}</Text>
            </View>
          );
        })}
      </View>
    </View>
  );
};

export default ExpenseTracker;