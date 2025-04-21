import { View, Text } from 'react-native'
import React from 'react'
import { AddPersonalExpense } from '@/app/components/AddPersonalExpense'

const personalexp = () => {
    const handleSubmit =(exp:any) => {
        console.log('Expense submitted');
        console.log(exp);
    }
  return (
    <AddPersonalExpense onSubmit={handleSubmit}/>
  )
}

export default personalexp