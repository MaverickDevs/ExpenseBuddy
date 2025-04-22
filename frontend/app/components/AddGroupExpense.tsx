import React, { useState, useEffect } from 'react';
import { View, Text, Switch, TouchableOpacity, ScrollView, TextInput } from 'react-native';
import Icon from 'react-native-vector-icons/MaterialIcons';
import { 
  AmountInput, 
  CategoryGrid, 
  DescriptionInput, 
  SubmitButton 
} from './AddExpense';
import { GroupExpense, GroupMember } from '../types/expense';
import { Category } from '../types/expense';
import { DatePicker } from './DatePicker';

interface GroupExpenseFormProps {
  groupMembers: GroupMember[];
  onSubmit: (expense: Omit<GroupExpense, 'type'>) => void;
}

export default function AddGroupExpense ({ 
  groupMembers, 
  onSubmit 
}:GroupExpenseFormProps) {
  const [amount, setAmount] = useState('');
  const [category, setCategory] = useState<Category | null>(null);
  const [description, setDescription] = useState('');
  const [splitEqually, setSplitEqually] = useState(true);
  const [customAmounts, setCustomAmounts] = useState<Record<string, string>>({});
  const [selectedMembers, setSelectedMembers] = useState<string[]>([]);
  const [date, setDate] = useState(new Date());

  useEffect(() => {
    if (groupMembers) {
      setSelectedMembers(groupMembers.map(member => member.id));
    }
  }, [groupMembers]);

  const toggleMemberSelection = (memberId: string) => {
    if (selectedMembers.includes(memberId)) {
      setSelectedMembers(selectedMembers.filter(id => id !== memberId));
    } else {
      setSelectedMembers([...selectedMembers, memberId]);
    }
  };

  const handleCustomAmountChange = (memberId: string, value: string) => {
    setCustomAmounts({
      ...customAmounts,
      [memberId]: value,
    });
  };

  const calculateSplitAmounts = (): Record<string, number> => {
    if (!amount) return {};
    
    const total = parseFloat(amount);
    if (splitEqually) {
      const equalAmount = total / selectedMembers.length;
      return selectedMembers.reduce((acc, memberId) => {
        acc[memberId] = parseFloat(equalAmount.toFixed(2));
        return acc;
      }, {} as Record<string, number>);
    } else {
      return Object.entries(customAmounts).reduce((acc, [memberId, amount]) => {
        if (selectedMembers.includes(memberId)) {
          acc[memberId] = parseFloat(amount) || 0;
        }
        return acc;
      }, {} as Record<string, number>);
    }
  };

  const handleSubmit = () => {
    if (!amount || !category) {
      alert('Please enter amount and select a category');
      return;
    }

    const splitAmounts = calculateSplitAmounts();
    
    onSubmit({
      amount: parseFloat(amount),
      category,
      description,
      date:date.toISOString(),
      splitType: splitEqually ? 'equal' : 'custom',
      splitAmounts,
      participants: selectedMembers,
    });
  };

  return (
    <ScrollView className="flex-1 p-4">
      <Text className="text-xl font-bold mb-4">Group Expense</Text>
      
      <AmountInput value={amount} onChange={setAmount} />
      <CategoryGrid selectedCategory={category} onSelect={setCategory} />
      <DatePicker date={date} onChange={setDate} />
      
      <View className="mb-6">
        <Text className="text-lg font-medium mb-2">Split Expense</Text>
        
        <View className="flex-row items-center justify-between mb-4">
          <Text className="text-base">Split equally</Text>
          <Switch
            value={splitEqually}
            onValueChange={setSplitEqually}
            trackColor={{ false: '#767577', true: '#81b0ff' }}
            thumbColor={splitEqually ? '#f5dd4b' : '#f4f3f4'}
          />
        </View>
        
        <Text className="text-base font-medium mb-2">With:</Text>
        <View className="bg-white rounded-lg border border-gray-200">
          {groupMembers.map(member => (
            <View key={member.id} className="flex-row items-center justify-between p-3 border-b border-gray-100">
              <View className="flex-row items-center">
                <TouchableOpacity onPress={() => toggleMemberSelection(member.id)}>
                  <Icon 
                    name={selectedMembers.includes(member.id) ? 'check-box' : 'check-box-outline-blank'} 
                    size={24} 
                    className="mr-3 text-blue-500" 
                  />
                </TouchableOpacity>
                <Text className="text-base">{member.name}</Text>
              </View>
              
              {!splitEqually && selectedMembers.includes(member.id) && (
                <View className="flex-row items-center">
                  <Text className="mr-1">Rs</Text>
                  <TextInput
                    className="border-b border-gray-300 w-16 py-1"
                    placeholder="0.00"
                    keyboardType="decimal-pad"
                    value={customAmounts[member.id] || ''}
                    onChangeText={(value) => handleCustomAmountChange(member.id, value)}
                  />
                </View>
              )}
            </View>
          ))}
        </View>
      </View>
      
      <DescriptionInput value={description} onChange={setDescription} />
      <SubmitButton onPress={handleSubmit} />
    </ScrollView>
  );
};