import { useState } from 'react';
import { Image, StyleSheet, Platform, View, TextInput, Button, Pressable, Text } from 'react-native';
import * as yup from 'yup';
import axios from 'axios';
import { useAuthStore } from '@/store/authStore';
import { apiBaseUrl } from '@/utils/constants';
import { useRouter } from 'expo-router';

export default function LoginScreen() {
  const setTokens = useAuthStore((state) => state.setTokens);
  const router = useRouter();

  const [formData, setFormData] = useState({
    username: '',
    password: ''
  });

  const [errors, setErrors] = useState({});

  const handleChange = (field: string, value: string) => {
    setFormData({
      ...formData,
      [field]: value
    });
  };

  const handleSubmit = async () => {
    try {
      setErrors({});
      await validationSchema.validate(formData, { abortEarly: false });

      const res = await axios.post(`${apiBaseUrl}/auth/v1/login`, { ...formData });
      const refreshExpiry = Date.now() + 7 * 24 * 3600 * 1000; // 7 days
      setTokens(res.data.accessToken, res.data.refreshToken, refreshExpiry);

      // Redirect to main screen after login
      router.replace('/(tabs)');
    } catch (error) {
      if (error instanceof yup.ValidationError) {
        const newErrors: any = {};
        error.inner.forEach((err) => {
          if (err.path) {
            newErrors[err.path] = err.message;
          }
        });
        setErrors(newErrors);
      }
    }
  };

  const validationSchema = yup.object().shape({
    username: yup
      .string()
      .min(3, 'Username must be at least 3 characters')
      .required('Username is required'),
    password: yup
      .string()
      .min(3, 'Password must be at least 3 characters')
      .required('Password is required'),
  });

  return (
    <View style={styles.parent}>
      <Text style={styles.headerText}>Expense Buddy</Text>
      <TextInput
        style={styles.formInput}
        placeholder="Enter username"
        value={formData.username}
        onChangeText={(text) => handleChange('username', text)}
      />
      {/* {errors.username && <Text style={styles.errorText}>{errors.username}</Text>} */}

      <TextInput
        style={styles.formInput}
        placeholder="Enter password"
        secureTextEntry={true}
        value={formData.password}
        onChangeText={(text) => handleChange('password', text)}
      />
      {/* {errors.password && <Text style={styles.errorText}>{errors.password}</Text>} */}

      <Pressable onPress={handleSubmit} style={styles.submitButton}>
        <Text style={styles.buttonText}>Login</Text>
      </Pressable>
    </View>
  );
}

const styles = StyleSheet.create({
  parent: {
    flexDirection: 'column',
    alignItems: 'center',
    justifyContent: 'center',
    gap: 8,
    height: '100%',
  },
  formInput: {
    height: 40,
    width: 200,
    borderColor: 'gray',
    borderWidth: 1,
    padding: 10,
    marginTop: 10,
  },
  submitButton: {
    backgroundColor: 'gray',
    padding: 10,
    margin: 10,
    width: 200,
    borderRadius: 5,
    textAlign: 'center',
  },
  headerText: {
    fontSize: 24,
    fontWeight: 'bold',
    marginBottom: 20,
  },
  errorText: {
    color: 'red',
    fontSize: 14,
    marginTop: 3,
    marginBottom: 10,
  },
  buttonText: {
    color: 'white',
    fontSize: 16,
    fontWeight: 'bold',
    textAlign: 'center',
  },
});
