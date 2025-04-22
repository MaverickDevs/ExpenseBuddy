import { View, Text } from 'react-native'
import React from 'react'
import AddGroupExpense from '../components/AddGroupExpense';

const groupexp = () => {
    const handleSubmit =(exp:any) => {
        console.log('Expense submitted');
        console.log(exp);
    }
    const groupMembers = [
        { id: '1', name: 'John Doe' },
        { id: '2', name: 'Jane Smith' },
        { id: '3', name: 'Alice Johnson' },
        { id: '4', name: 'Bob Brown' },
    ];
  return (
    <AddGroupExpense groupMembers={groupMembers} onSubmit={handleSubmit}/>
  )
}

export default groupexp