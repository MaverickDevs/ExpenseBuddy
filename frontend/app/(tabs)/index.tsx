import { useState } from 'react';
import { Image, StyleSheet, Platform, View, TextInput, Button, Pressable, Text } from 'react-native';
import * as yup from 'yup';
import axios from 'axios';

export default function HomeScreen() {
  interface FormData {
    username: string;
    email: string;
    password: string;
  }
  
  interface FormErrors {
    username?: string;
    email?: string;
    password?: string;
  }

  const [formData, setFormData] = useState<FormData>({
    username: '',
    email: '',
    password: ''
  });
  // Set up errors state
  const [errors, setErrors] = useState<FormErrors>({});

  // Handle input changes
  const handleChange = (field: keyof FormData, value: string): void => {
    setFormData({
      ...formData,
      [field]: value
    });
  };

  const handleSubmit = async (): Promise<void> => {
    try {
      // Reset errors
      setErrors({});
      
      // Validate all fields
      await validationSchema.validate(formData, { abortEarly: false });
      
      // If validation passes, you can submit the form
      console.log('Form is valid:', formData);
      // Call your API here
      try{
        console.log("Hello")
        const res = await axios.post('http://localhost:8080/auth/v1/signup', {...formData});
        console.log(res.data)
      }catch(error){
        console.log(error)
      }
      
    } catch (error) {
      // Handle validation errors
      if (error instanceof yup.ValidationError) {
        const newErrors: FormErrors = {};
        
        error.inner.forEach((err) => {
          if (err.path) {
            newErrors[err.path as keyof FormErrors] = err.message;
          }
        });
        
        setErrors(newErrors);
        console.log('Validation errors:', newErrors);
      }
    }
  };


  const validationSchema = yup.object().shape({
    email: yup
      .string()
      .email('Please enter a valid email')
      .required('Email is required'),
    username: yup
      .string()
      .min(6, ({ min }) => `Username must be at least ${min} characters`)
      .required('Username is required'),
    password: yup
      .string()
      .min(6, ({ min }) => `Password must be at least ${min} characters`)
      .required('Password is required'),
  });


  return (
    <View style={styles.parent}>
      <Text style={styles.headerText}>Expense Buddy</Text>
      <TextInput
        style={styles.formInput}
        placeholder="Enter email"
        value={formData.email}
        onChangeText={(text) => handleChange('email', text)}      />
      {errors.email ? <Text style={styles.errorText}>{errors.email}</Text> : null}
      <TextInput
        style={styles.formInput}
        placeholder="Enter user name"
        value={formData.username}
        onChangeText={(text) => handleChange('username', text)}      />
      {errors.username ? <Text style={styles.errorText}>{errors.username}</Text> : null}
      <TextInput
        style={styles.formInput}
        placeholder="Enter password"
        value={formData.password}
        onChangeText={(text) => handleChange('password', text)}      />
       {errors.password ? <Text style={styles.errorText}>{errors.password}</Text> : null}
      <Pressable  onPress={handleSubmit} style={styles.submitButton}><Text style={styles.buttonText}>Sign in</Text></Pressable>

    </View>
  );
}

const styles = StyleSheet.create({
  parent: {
    flexDirection: 'column',
    alignItems: 'center',
    justifyContent: 'center',
    gap: 8,
    height:"100%"
  },
  formInput: {
     height: 40, width: 200, borderColor: 'gray', borderWidth: 1,padding:10 , marginTop: 10, }
     ,
     submitButton: {
        backgroundColor: 'gray',
        color: 'white',
        padding: 10,
        margin: 10,
        width: 200,
        borderRadius: 5,
        textAlign: 'center',
     },
      headerText: {
          fontSize: 24,
          fontWeight: 'bold',
          color: 'black',
          marginBottom: 20,
    
      },
      errorText: {
        color: 'red',
        fontSize: 14,
        marginTop: 3,
        marginBottom: 10,
      },
      buttonText:{
        color: 'white',
        fontSize: 16,
        fontWeight: 'bold',
        textAlign: 'center'
      }
});
