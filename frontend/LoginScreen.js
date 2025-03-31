import React, { useState, useEffect } from 'react';
import { Text, SafeAreaView, StyleSheet, TextInput, Alert, ImageBackground, View, TouchableOpacity, KeyboardAvoidingView, Platform, Keyboard } from 'react-native';

const LoginScreen = ({ navigation }) => {
  const [nickname, setNickname] = useState('');
  const [password, setPassword] = useState('');
  const [keyboardVisible, setKeyboardVisible] = useState(false);

  const handleSubmit = () => {
    if (!nickname || !password) {
      Alert.alert('Ошибка', 'Пожалуйста, заполните все поля.');
      return;
    }
    console.log('Вход успешен:', { nickname, password });
    Alert.alert('Успех', 'Вы успешно вошли!');
    // Здесь нужно добавить логику для перенаправления пользователя или отправки данных на сервер
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
          <Text style={styles.title}>Привет! 
          Мы скучали, скорее заходи :)</Text>
          
          <TextInput
            style={styles.input}
            placeholder="Твой никнейм"
            value={nickname}
            onChangeText={setNickname} //функцию потом поменяй на чек никнейм
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
    marginTop: 50, // Добавлен отступ сверху для поднятия формы
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
    marginTop: 15, // Отступ сверху для ссылки
  },
  linkText: {
    color: '#937EC3', // Цвет текста
    fontSize: 16,
  },
  link: {
    color: '#4D3FB7', // Цвет ссылки
    fontSize: 16,
    textDecorationLine: 'underline', // Подчеркивание ссылки
  },
});

export default LoginScreen;
