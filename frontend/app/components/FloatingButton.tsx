import React from 'react';
import { TouchableOpacity, View } from 'react-native';
import Icon from 'react-native-vector-icons/MaterialIcons';

interface FloatingActionButtonProps {
  onPress: () => void;
}

export const FloatingActionButton: React.FC<FloatingActionButtonProps> = ({ onPress }) => {
  return (
    <View className="absolute bottom-7 right-4 z-10">
      <TouchableOpacity
        className="w-16 h-16 rounded-full bg-blue-500 items-center justify-center shadow-lg"
        onPress={onPress}
      >
        <Icon name="add" size={28} color="white" />
      </TouchableOpacity>
    </View>
  );
};