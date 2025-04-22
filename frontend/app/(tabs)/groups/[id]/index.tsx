import { View, Text, TouchableOpacity, StyleSheet, FlatList } from 'react-native';
import { ActivityIndicator } from 'react-native';
import React, { useEffect, useState } from 'react';
import { FontAwesome } from '@expo/vector-icons';
import ExpenseCard from '../../../components/ExpenseCard';
import { useLocalSearchParams, useRouter } from 'expo-router';
import { FloatingActionButton } from '@/app/components/FloatingButton';

export default function GroupHome(){
  const { name } = useLocalSearchParams<{ name: string }>();
  const router = useRouter();
  const [expenses, setExpenses] = useState([
    {
      id: "9f4c0d40-8a2a-4d01-9850-49ef60f70d1a",
      paid_name: "Alice",
      amount: "250",
      description: "Dinner at Italian Bistro",
      category: "FOOD"
    },
    {
      id: "c8fa8a62-1c20-4b2b-b4f2-8ae5a7b05f22",
      paid_name: "Bob",
      amount: "120",
      description: "Groceries",
      category: "FOOD"
    },
    {
      id: "d3e730a4-bc44-4ac9-947d-671f4382b89f",
      paid_name: "Charlie",
      amount: "75",
      description: "Movie tickets",
      category: "ENTERTAINMENT"
    },
    {
      id: "7b3c7c61-1e3d-4d89-a33b-8d04d65cbcf0",
      paid_name: "Dana",
      amount: "300",
      description: "Monthly utilities",
      category: "LIVING"
    },
    {
      id: "e1289e5f-9f46-4953-84ec-9fc9f2d676b1",
      paid_name: "Ethan",
      amount: "90",
      description: "Fuel for road trip",
      category: "ENTERTAINMENT"
    }
  ]);


  const [loading, setLoading] = useState(true);

  useEffect(() => {
    fetchExpenses();
  }, []);

  const fetchExpenses = async () => {
    //api callll
    setLoading(false);
  }

  function addExpense() {
    console.log('Add Expense button pressed');
    router.navigate('/(modals)/groupexp');
    
  }


  return (

    <>

      <View style={styles.topBar}>
        <TouchableOpacity>
          <FontAwesome name="bars" size={24} color="black" />
        </TouchableOpacity>

        <Text style={styles.title}>{name}</Text>

        <TouchableOpacity>
          <FontAwesome name="inr" size={24} color="black" />
        </TouchableOpacity>
      </View>
      {loading ? (
        <ActivityIndicator size="large" color="blue" style={styles.loader} />
      ) : (
        <FlatList
          data={expenses}
          keyExtractor={(item) => item.id.toString()}
          renderItem={({ item }) => (
            <ExpenseCard
              id={item.id}
              paid_name={item.paid_name}
              description={item.description}
              amount={item.amount}
              category={item.category}
            />
          )}
          contentContainerStyle={styles.listContainer}
          ItemSeparatorComponent={() => <View style={{ height: 0 }} />}
        />
      )}
      <FloatingActionButton onPress={addExpense}/>
      
    </>

  )
}

const styles = StyleSheet.create({
  listContainer: {
    padding: 5,
    marginVertical: 0,
  },
  loader: {
    marginTop: 50,
  },
  title: {
    fontSize: 20,
    fontWeight: 'bold',
    alignContent: 'center',
    justifyContent: 'center',
  },
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

}

)