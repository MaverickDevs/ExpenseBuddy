import {
  View,
  Text,
  TouchableOpacity,
  StyleSheet,
  FlatList,
  TextInput,
} from "react-native";
import { ActivityIndicator } from "react-native";
import React, { useEffect, useState } from "react";
import { FontAwesome } from "@expo/vector-icons";
import PeopleCard from "@/app/components/PeopleCard";

const add_people = () => {
  const tempData = [
    { id: "1", name: "Alice Johnson", number: "9876543210" },
    { id: "2", name: "Bob Smith", number: "8765432109" },
    { id: "3", name: "Charlie Davis", number: "7654321098" },
    { id: "4", name: "Diana Moore", number: "6543210987" },
    { id: "5", name: "Ethan Clark", number: "5432109876" },
  ];
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const timer = setTimeout(() => {
      setLoading(false); // 👈 your state update
    }, 3000);
  
    return () => clearTimeout(timer); // cleanup on unmount
  }, []);

  return (
    <>
      <View style={styles.topBar}>
        <TouchableOpacity>
          <FontAwesome name="arrow-left" size={24} color="black" />
        </TouchableOpacity>

        <Text style={styles.title}>Add members to the group</Text>

        <TouchableOpacity>
          <FontAwesome name="ellipsis-v" size={24} color="black" />
        </TouchableOpacity>
      </View>
        <View style={styles.searchbar}>
      <TouchableOpacity>
          <FontAwesome name="search" size={24} color="black" />
        </TouchableOpacity>
      <TextInput
        style={styles.searchBox}
        placeholder="Search by name or number"
      />
      </View>
      {loading ? (
        <ActivityIndicator size="large" color="blue" style={styles.loader} />
      ) : (
        <FlatList
          data={tempData}
          keyExtractor={(item) => item.id.toString()}
          renderItem={({ item }) => (
            <PeopleCard id={item.id} name={item.name} number={item.number} />
          )}
          contentContainerStyle={styles.listContainer}
          ItemSeparatorComponent={() => <View style={{ height: 10 }} />}
        />
      )}
    </>
  );
};

export default add_people;

const styles = StyleSheet.create({
  listContainer: {
    padding: 5,
    marginVertical: 0,
  },
  title: {
    fontSize: 20,
    fontWeight: "bold",
    alignContent: "center",
    justifyContent: "center",
  },
  topBar: {
    flexDirection: "row",
    justifyContent: "space-between",
    alignItems: "center",
    padding: 15,
    backgroundColor: "white",
    elevation: 3,
    shadowColor: "#000",
    shadowOffset: { width: 0, height: 2 },
    shadowOpacity: 0.2,
    shadowRadius: 3,
  },
  loader: {
    marginTop: 50,
  },
  searchbar: {
    flexDirection: 'row',
    alignItems: 'center',
    borderWidth: 1,
    borderColor: '#ccc',
    borderRadius: 10,
    paddingHorizontal: 10,
    height: 50,
    backgroundColor: '#fff',
    margin: 10,
  },
  
  searchBox: {
    flex: 1,
    marginLeft: 10,
    fontSize: 16,
  },
});
