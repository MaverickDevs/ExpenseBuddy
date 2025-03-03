import { StyleSheet, Image, Platform, View, Text } from 'react-native';

export default function TabTwoScreen() {
  return (
    <View style={styles.parent}>
      <Text>Hello bro</Text>
    </View>
  );
}

const styles = StyleSheet.create({
  parent:{
     flex: 1, justifyContent: 'center', alignItems: 'center' 
  }
});
