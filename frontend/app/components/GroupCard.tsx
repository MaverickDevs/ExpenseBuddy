import { View, Text, StyleSheet } from 'react-native'
import React from 'react'
import { LinearGradient } from 'expo-linear-gradient';
import { FontAwesome, AntDesign } from '@expo/vector-icons';

type GroupCardProps = {
    id:number,
    name: string;
    size: string;
    edited: string;
    owed: string;
  };

const GroupCard: React.FC<GroupCardProps> = ({id, name, size, edited, owed}) => {
  return (
    <LinearGradient colors={['#ff7e5f', '#feb47b']}
    start={{ x: 0, y: 0 }}
    end={{ x: 1, y: 0 }}
    style={styles.gradient} >
    <View style={styles.container}>
        
        <View style={styles.topcontainer}>
            <View style={styles.leftbox}>
                <Text style={{ fontSize: 18, fontWeight: 'bold', color: 'black', marginTop:2}}>
                    {name}
                </Text>
            </View>
            <View style={[styles.rightbox, { flexDirection: 'row', }]}>
                <AntDesign name="user" size={30} color="black" />
                <Text style={{ fontSize: 18, color: 'black' }}>{size}</Text>
            </View>
        </View>


        <View style={styles.bottomcontainer}>
        <View style={styles.leftbox}>
            <Text>
                Last edited:
            </Text>
            <Text style={{ fontSize: 18, fontWeight: 'bold', color: 'black'}}>
                {edited}
            </Text>
        </View>
        <View style={styles.rightbox}>
            <Text>
                You're owed
            </Text>
            <Text style={{ fontSize: 18, fontWeight: 'bold', color: 'black'}}>
                ${owed}
            </Text>
        </View>
        </View>


    </View>
    </LinearGradient>
  )
}

export default GroupCard

const styles = StyleSheet.create({
    gradient: {
        flex: 1,
        paddingVertical: 0,
        paddingHorizontal: 0,
        marginHorizontal:20,
        marginVertical:10,
        borderRadius:15,
        // alignItems: 'center', 
        // justifyContent: 'center',
      },
    container: {
        //backgroundColor:'lightgray',
        padding:0,
        margin:0,
        borderRadius:15

      },
    topcontainer: {
      flexDirection: 'row',
      justifyContent: 'space-between',
    //   alignItems: 'center',
      //backgroundColor: 'red',
      padding:3,
      borderRadius:15
    },
    bottomcontainer: {
        flexDirection: 'row',
        justifyContent: 'space-between',
        // alignItems: 'center',
        //backgroundColor: 'blue',
        padding:3,
        borderRadius:15
      },
    leftbox: {
        flexDirection: 'column',
        justifyContent: 'space-between',
        alignItems: 'center',
        //backgroundColor: 'lightyellow',
        padding:10,
        marginLeft:10
      },
    rightbox: {
        flexDirection: 'column',
        justifyContent: 'space-between',
        alignItems: 'center',
        //backgroundColor: 'lightblue',
        padding:10,
        marginRight:10
      },
  });