import { View, Text, TouchableOpacity, StyleSheet, FlatList, ActivityIndicator } from 'react-native';
import React, { useEffect, useState } from 'react';
import { FontAwesome } from '@expo/vector-icons';
import GroupCard from '../../components/GroupCard'
import { Link } from 'expo-router';
import { useRouter } from 'expo-router';

// Inside your component:

const groups = () => {

  const router = useRouter();

  const [groups, setGroups] = useState([
    {
      "id":34,
      "name":"Akme",
      "size":"5",
      "edited":"Aug 5",
      "owed":"123"
    },
    {
      "id":111,
      "name":"SVR",
      "size":"5",
      "edited":"Aug 5",
      "owed":"123"
    },
    {
      "id":23,
      "name":"CEG",
      "size":"25",
      "edited":"Aug 5",
      "owed":"123"
    },
    {
      "id":13,
      "name":"Green Glen",
      "size":"10",
      "edited":"Aug 5",
      "owed":"123"
    },
    {
      "id":1,
      "name":"Bangalore",
      "size":"5",
      "edited":"Aug 5",
      "owed":"-123"
    },
    {
      "id":12,
      "name":"Thailand",
      "size":"5",
      "edited":"Aug 5",
      "owed":"-123"
    },
    {
      "id":1234,
      "name":"Bernabeu",
      "size":"5",
      "edited":"Aug 5",
      "owed":"-123"
    },
  ]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    fetchGroups();
  }, []);

  const fetchGroups = async ()=>{
    //api callll
    setLoading(false);
  }

  return (
    <View>
      <View style={styles.topBar}>
        <TouchableOpacity>
          <FontAwesome name="bars" size={24} color="black" />
        </TouchableOpacity>
        
        <Text style={styles.title}>Groups</Text>
        
        <TouchableOpacity>
          <FontAwesome name="plus" size={24} color="black" />
        </TouchableOpacity>
      </View>
      {loading ? (
        <ActivityIndicator size="large" color="blue" style={styles.loader} />
      ) : (
        <FlatList
          data={groups}
          keyExtractor={(item) => item.id.toString()}
          renderItem={({ item }) => (
            <TouchableOpacity 
            onPress={() => router.push(`/groups/${item.id}?name=${item.name}` as any)}
          >
            <GroupCard
              id={item.id}
              name={item.name}
              size={item.size}
              edited={item.edited}
              owed={item.owed}
            />
          </TouchableOpacity>
           
          )}
          contentContainerStyle={styles.listContainer}
          ItemSeparatorComponent={() => <View style={{ height: 0 }} />}
        />
      )}
    </View>
  )
}

export default groups

const styles = StyleSheet.create({
  topBar: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
    padding: 15,
    backgroundColor: 'white',
    elevation: 3,
    shadowColor: '#000',
    shadowOffset: { width: 0, height: 2 },
    shadowOpacity: 0.2,
    shadowRadius: 3,
  },
  title: {
    fontSize: 20,
    fontWeight: 'bold',
  },
  loader: {
    marginTop: 50,
  },
  listContainer: {
    padding: 5,
    marginVertical: 0,
  },
 }
)
