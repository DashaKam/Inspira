import React, { useState, useEffect } from 'react';
import { Text, SafeAreaView, StyleSheet, TextInput, Alert, ImageBackground, View, TouchableOpacity, KeyboardAvoidingView, Platform, Keyboard } from 'react-native';
import axios from 'axios';

const LoginScreen = ({ navigation }) => {
  const [nickname, setNickname] = useState('');
  const [password, setPassword] = useState('');
  const [keyboardVisible, setKeyboardVisible] = useState(false);

  const handleSubmit = async () => {
    if (!nickname || !password) {
      Alert.alert('Ошибка', 'Пожалуйста, заполните все поля.');
      return;
    }

    try {
      const response = await axios.post(
          'http://185.157.214.169:8080/api/login', // URL для входа
          {
            nickname: nickname,
            password: password,
          }
      );

      // Проверка статуса ответа
      if (response.status !== 200) {
        throw new Error('Ошибка входа');
      }

      const data = response.data; // Получаем данные из ответа
      console.log('Вход успешен:', data);
      Alert.alert('Успех', 'Вы успешно вошли!');

      navigation.navigate('Success'); // Перенаправление на экран успеха

    } catch (error) {
      if (error.response) {
        const { data } = error.response; // Извлекаем данные из ответа

        // Проверяем наличие ошибок валидации
        if (data.details && Array.isArray(data.details)) {
          const errorMessages = data.details.map(detail =>
              `${detail.path}: ${detail.message}`
          ).join('\n');
          Alert.alert('Ошибка валидации', errorMessages);
        } else if (data.errorType === 'USER_NOT_FOUND') { // Проверка на существование пользователя
          Alert.alert('Ошибка', 'Пользователь с таким никнеймом не найден.');
        } else if (data.errorType === 'INVALID_PASSWORD') { // Проверка на неверный пароль
          Alert.alert('Ошибка', 'Неверный пароль. Попробуйте еще раз.');
        } else {
          Alert.alert(
              'Ошибка',
              data.message || 'Не удалось войти. Попробуйте еще раз.'
          );
        }
      } else {
        Alert.alert(
            'Ошибка',
            'Не удалось войти. Попробуйте еще раз.'
        );
      }
    }
  };

  useEffect(() => {
    const keyboardDidShowListener = Keyboard.addListener('keyboardDidShow', () => {
      setKeyboardVisible(true);
    });
    const keyboardDidHideListener = Keyboard.addListener('keyboardDidHide', () => {
      setKeyboardVisible(false);
    });

    return () => {
      keyboardDidHideListener.remove();
      keyboardDidShowListener.remove();
    };
  }, []);

  return (
      <ImageBackground
          source={require('./assets/5418303167952711597.jpg')}
          style={styles.background}
      >
        <KeyboardAvoidingView
            style={styles.container}
            behavior={Platform.OS === 'ios' ? 'padding' : 'height'}
            keyboardVerticalOffset={keyboardVisible ? 100 : 0} // Увеличьте отступ, когда клавиатура видима
        >
          <View style={styles.formContainer}>
            <Text style={styles.title}>Привет! Мы скучали, скорее заходи :)</Text>

            <TextInput
                style={styles.input}
                placeholder="Твой никнейм"
                value={nickname}
                onChangeText={setNickname}
                placeholderTextColor="#CE9FDD"
            />
            <TextInput
                style={styles.input}
                placeholder="Парольчик"
                value={password}
                onChangeText={setPassword}
                secureTextEntry
                placeholderTextColor="#CE9FDD"
            />
            <TouchableOpacity style={styles.button} onPress={handleSubmit}>
              <Text style={styles.buttonText}>Войти</Text>
            </TouchableOpacity>
            {/* Ссылка под кнопкой */}
            <View style={styles.linkContainer}>
              <Text style={styles.linkText}>Еще нет аккаунта? </Text>
              <TouchableOpacity onPress={() => navigation.navigate('Registration')}>
                <Text style={styles.link}>Скорее регистрируйся!</Text>
              </TouchableOpacity>
            </View>
          </View>
        </KeyboardAvoidingView>
      </ImageBackground>
  );
};

const styles = StyleSheet.create({
  background: {
    flex: 1,
    justifyContent: 'center',
  },
  container: {
    flex: 1,
    padding: 16,
  },
  formContainer: {
    flex: 1,
    justifyContent: 'flex-start',
    alignItems: 'center',
    marginTop: 50,
  },
  title: {
    fontSize: 40,
    fontWeight: 'regular',
    textAlign: 'center',
    textbackgroundColor: '#ccc',
    marginBottom: 16,
    color: '#645BAA',
  },
  input: {
    height: 50,
    width: '80%',
    backgroundColor: '#fff',
    color: '#CE9FDD',
    borderColor: '#fff',
    borderWidth: 1,
    borderRadius: 25,
    paddingHorizontal: 10,
    marginBottom: 20,
  },
  button: {
    backgroundColor: '#fff',
    width: '80%',
    borderRadius: 20,
    paddingVertical: 10,
    paddingHorizontal: 20,
    alignItems: 'center',
  },
  buttonText: {
    color: '#645BAA',
    fontSize: 25,
  },
  linkContainer: {
    flexDirection: 'row',
    justifyContent: 'center',
    marginTop: 15,
  },
  linkText: {
    color:'#937EC3',
    fontSize :16,
  },
  link:{
    color:'#4D3FB7',
    fontSize :16,
    textDecorationLine:'underline'
  },
});

export default LoginScreen;