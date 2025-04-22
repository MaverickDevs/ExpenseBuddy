import { View, Text, StyleSheet } from 'react-native'
import React from 'react'
import { LinearGradient } from 'expo-linear-gradient';
import { FontAwesome, AntDesign } from '@expo/vector-icons';

type PeopleCardProps = {
    id:string;
    name: string;
    number: string;
  };

const PeopleCard: React.FC<PeopleCardProps> = ({id,name, number}) => {

    const initial = name?.charAt(0).toUpperCase();
  return (
    // <LinearGradient colors={['#ff7e5f', '#feb47b']}
    // start={{ x: 0, y: 0 }}
    // end={{ x: 1, y: 0 }}
    // style={styles.gradient} >
        <View style={styles.container}>
            <View style={styles.leftbox}>
            <Text style={styles.initialText}>{initial}</Text>
            </View>
            <View style={styles.rightbox}>
            <Text style={styles.nameText}>{name}</Text>
            <Text style={styles.numberText}>{number}</Text>
            </View>
        </View>



    // </LinearGradient>
  )
}

  const styles = StyleSheet.create({
    gradient: {
        flex: 1,
        paddingVertical: 0,
        paddingHorizontal: 0,
        marginHorizontal:20,
        marginVertical:10,
        borderRadius:15,
      },
    container: {
        //backgroundColor:'lightgray',
        flexDirection:'row',
        padding:0,
        margin:0,
        borderRadius:15,
        //justifyContent:'space-around'

      },
    leftbox: {
        width: 50,
        height: 50,
        borderRadius: 25,
        backgroundColor: 'lightgreen',
        justifyContent: 'center',
        alignItems: 'center',
        marginHorizontal: 10,
        //borderWidth: 2, // or whatever thickness you want
        borderColor: 'white', // or any color you prefer
      },
      initialText: {
        color: 'white',
        fontSize: 24,
        fontWeight: 'thin',
      },
      rightbox: {
        marginLeft:10,
        flex: 1,
        backgroundColor: 'transparent',
      },
      nameText: {
        fontSize: 18,
        fontWeight: 'bold',
        color: '#333',
        marginTop:3
      },
      numberText: {
        fontSize: 14,
        color: '#666',
      },
  }
)

export default PeopleCard;