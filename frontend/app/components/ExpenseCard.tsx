import { View, Text, StyleSheet } from 'react-native'
import React from 'react'
import { LinearGradient } from 'expo-linear-gradient';
import { MaterialCommunityIcons } from '@expo/vector-icons'; // works in Expo


type ExpenseCardProps = {
  id: string,
  paid_name: string;
  description: string;
  amount: string;
  category: string,
};

const getCategoryIcon = (category: string) => {
  switch (category) {
    case 'FOOD':
      return 'food';
    case 'ENTERTAINMENT':
      return 'movie';
    case 'LIVING':
      return 'home';
    case 'MEDICAL':
      return 'medical-bag';
    case 'CLOTHING':
      return 'tshirt-crew';
    case 'PERSONAL':
      return 'face-man-profile';
    case 'FITNESS':
      return 'dumbbell';
    default:
      return 'help-circle'; // fallback icon
  }
};


const ExpenseCard: React.FC<ExpenseCardProps> = ({ id, paid_name, description, amount, category }) => {

  return (
    <LinearGradient colors={['#ff7e5f', '#feb47b']}
      start={{ x: 0, y: 0 }}
      end={{ x: 1, y: 0 }}
      style={styles.gradient} >
      <View style={styles.container}>
        <View style={styles.leftbox}>
          <View style={styles.icon}>
            <MaterialCommunityIcons
              name={getCategoryIcon(category)}
              size={24}
              color="#555"
            />
          </View>
          <View style={styles.verticalLine} />
          <View style={styles.details}>
            <Text style={styles.name}>{paid_name}</Text>
            <Text style={styles.description}>{description}</Text>
          </View>
        </View>
        <View style={styles.rightbox}>
          {amount}₹
        </View>


      </View>


    </LinearGradient>
  )
}

export default ExpenseCard;

const styles = StyleSheet.create({
  container: {
    //backgroundColor:'lightgray',
    flexDirection: 'row',
    justifyContent: 'space-between',
  },
  leftbox: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    //backgroundColor:'red'
  },
  icon: {
    marginLeft: 10,
    justifyContent: 'center',
    alignContent: 'center'
  },
  verticalLine: {
    width: 1,
    height: '80%', // adjust height as needed
    backgroundColor: '#ccc',
    marginHorizontal: 8,
    marginVertical: 4
  },
  details: {
    marginHorizontal: 5,
    flex: 1,
    justifyContent: 'space-between', // Pushes name to top and description to bottom
    paddingVertical: 8, // Adds space from top and bottom edges
  },
  name: {
    fontSize: 16,
    fontWeight: 'bold',
  },
  description: {
    fontSize: 14,
    color: '#555',
  },
  rightbox: {
    marginRight: 20,
    justifyContent: 'center',
    alignContent: 'center',
    fontSize: 16,
    fontWeight: 'bold',
    //backgroundColor:'blue'
  },
  gradient: {
    flex: 1,
    paddingVertical: 0,
    paddingHorizontal: 0,
    marginHorizontal: 20,
    marginVertical: 10,
    borderRadius: 15,
  },
}
)