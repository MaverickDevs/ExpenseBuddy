import { Image, StyleSheet, Platform, View } from 'react-native';

export default function HomeScreen() {
  return (
    <View style={styles.parent}>
      Home Page BROSKII
    </View>
  );
}

const styles = StyleSheet.create({
  parent: {
    flexDirection: 'row',
    alignItems: 'center',
    justifyContent: 'center',
    gap: 8,
  },
});
