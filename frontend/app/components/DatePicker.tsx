import React, { useState } from 'react';
import { View, Text, TouchableOpacity } from 'react-native';
import DateTimePicker from '@react-native-community/datetimepicker';
import Icon from 'react-native-vector-icons/MaterialIcons';

interface DatePickerProps {
  date: Date;
  onChange: (date: Date) => void;
}

export const DatePicker: React.FC<DatePickerProps> = ({ date, onChange }) => {
  const [showPicker, setShowPicker] = useState(false);

  const handleDateChange = (event: any, selectedDate?: Date) => {
    setShowPicker(false);
    if (selectedDate) {
      onChange(selectedDate);
    }
  };

  return (
    <View className="mb-6">
      <Text className="text-lg font-medium mb-2">Date</Text>
      <TouchableOpacity
        className="flex-row items-center justify-between bg-white border border-gray-300 rounded-lg p-3"
        onPress={() => setShowPicker(true)}
      >
        <Text className="text-base">
          {date.toLocaleDateString('en-US', {
            year: 'numeric',
            month: 'long',
            day: 'numeric',
          })}
        </Text>
        <Icon name="event" size={24} className="text-gray-500" />
      </TouchableOpacity>
      
      {showPicker && (
        <DateTimePicker
          value={date}
          mode="date"
          display="default"
          onChange={handleDateChange}
        />
      )}
    </View>
  );
};