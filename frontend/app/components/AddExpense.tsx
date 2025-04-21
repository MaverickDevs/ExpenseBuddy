import React from 'react';
import { View, Text, TextInput, TouchableOpacity } from 'react-native';
import { Category } from '../types/expense';
import { MaterialCommunityIcons } from '@expo/vector-icons';
import { getCategoryIcon } from '@/utils/CategoryIcon';

interface AmountInputProps {
  value: string;
  onChange: (value: string) => void;
}

interface CategoryGridProps {
  selectedCategory: Category | null;
  onSelect: (category: Category) => void;
}

interface DescriptionInputProps {
  value: string;
  onChange: (value: string) => void;
}

interface SubmitButtonProps {
  onPress: () => void;
  label?: string;
}

export const categories: Category[] = [
  { id: 1, name: 'FOOD', icon: 'restaurant' },
  { id: 2, name: 'TRANSPORT', icon: 'directions-car' },
  { id: 3, name: 'LIVING', icon: 'shopping-cart' },
  { id: 4, name: 'ENTERTAINMENT', icon: 'movie' },
  { id: 5, name: 'MEDICAL', icon: 'receipt' },
  { id: 6, name: 'CLOTHING', icon: 'local-hospital' },
  { id: 7, name: 'PERSONAL', icon: 'school' },
  { id: 8, name: 'FITNESS', icon: 'flight' },
];


export const AmountInput: React.FC<AmountInputProps> = ({ value, onChange }) => (
  <View className="mb-6">
    <Text className="text-lg font-medium mb-2">Amount</Text>
    <View className="flex-row items-center border border-gray-300 rounded-lg px-3">
      <Text className="text-lg mr-1">Rs</Text>
      <TextInput
        className="flex-1 py-3 text-lg"
        placeholder="0.00"
        keyboardType="decimal-pad"
        value={value}
        onChangeText={onChange}
      />
    </View>
  </View>
);

export const CategoryGrid: React.FC<CategoryGridProps> = ({ selectedCategory, onSelect }) => (
  <View className="mb-6">
    <Text className="text-lg font-medium mb-2">Category</Text>
    <View className="flex-row flex-wrap justify-between">
      {categories.map(category => (
        <TouchableOpacity
          key={category.id}
          className={`w-1/3 p-3 mb-3 items-center rounded-lg ${
            selectedCategory?.id === category.id 
              ? 'bg-blue-100 border border-blue-500' 
              : 'bg-white border border-gray-200'
          }`}
          onPress={() => onSelect(category)}
        >
            <MaterialCommunityIcons
              name={getCategoryIcon(category.name)}
              size={24}
              color="#555"
            />
          <Text className="text-xs">{category.name}</Text>
        </TouchableOpacity>
      ))}
    </View>
  </View>
);

export const DescriptionInput: React.FC<DescriptionInputProps> = ({ value, onChange }) => (
  <View className="mb-6">
    <Text className="text-lg font-medium mb-2">Description (Optional)</Text>
    <TextInput
      className="bg-white border border-gray-300 rounded-lg p-3 h-20"
      placeholder="Add a note..."
      multiline
      value={value}
      onChangeText={onChange}
    />
  </View>
);

export const SubmitButton: React.FC<SubmitButtonProps> = ({ onPress, label = "Add Expense" }) => (
  <TouchableOpacity
    className="bg-blue-500 py-3 rounded-lg items-center"
    onPress={onPress}
  >
    <Text className="text-white font-medium text-lg">{label}</Text>
  </TouchableOpacity>
);