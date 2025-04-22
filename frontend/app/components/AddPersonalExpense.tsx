import React, { useState } from 'react';
import { ScrollView } from 'react-native';
import { 
  AmountInput, 
  CategoryGrid, 
  DescriptionInput, 
  SubmitButton } from './AddExpense';
import { Category, PersonalExpense } from '../types/expense';
import { DatePicker } from './DatePicker';

interface PersonalExpenseFormProps {
  onSubmit: (expense: Omit<PersonalExpense, 'type'>) => void;
}

export const AddPersonalExpense: React.FC<PersonalExpenseFormProps> = ({ onSubmit }) => {
  const [amount, setAmount] = useState('');
  const [category, setCategory] = useState<Category | null>(null);
  const [description, setDescription] = useState('');
  const [date, setDate] = useState(new Date());

  const handleSubmit = () => {
    if (!amount || !category) {
      alert('Please enter amount and select a category');
      return;
    }

    onSubmit({
      amount: parseFloat(amount),
      category,
      description,
      date: date.toISOString(),
    });
  };

  return (
    <ScrollView className="flex-1 p-4">
      <AmountInput value={amount} onChange={setAmount} />
      <CategoryGrid selectedCategory={category} onSelect={setCategory} />
      <DescriptionInput value={description} onChange={setDescription} />
      <DatePicker date={date} onChange={setDate} />
      <SubmitButton onPress={handleSubmit} />
    </ScrollView>
  );
};