import React, { useState, useEffect } from 'react';
import {
  Text,
  SafeAreaView,
  StyleSheet,
  TextInput,
  Alert,
  ImageBackground,
  View,
  TouchableOpacity,
  KeyboardAvoidingView,
  Platform,
  Keyboard,
} from 'react-native';
import axios from 'axios';

const RegistrationScreen = ({ navigation }) => {
  const [username, setUsername] = useState('');
  const [nickname, setNickname] = useState('');
  const [password, setPassword] = useState('');
  const [keyboardVisible, setKeyboardVisible] = useState(false);

  const handleSubmit = async () => {
    if (!username || !nickname || !password) {
      Alert.alert('Ошибка', 'Пожалуйста, заполните все поля.');
      return;
    }

    try {
      const response = await axios.post(
          'http://185.157.214.169:8080/api/register',
          {
            name: username,
            nickname: nickname,
            password: password,
          }
      );

      console.log('Регистрация успешна:', response.data);
      Alert.alert('Успех', 'Вы успешно зарегистрированы!');
      navigation.navigate('Success'); // Перенаправление на экран успеха
    } catch (error) {
      console.error('Ошибка регистрации:', error);

      if (error.response) {
        const { data } = error.response; // Извлекаем данные из ответа

        // Проверяем наличие ошибок валидации
        if (data.details && Array.isArray(data.details)) {
          const errorMessages = data.details.map(detail => {
            switch (detail.path) {
              case 'nickname':
                return `Никнейм должен содержать от 4 до 20 символов. И включать в себя только буквы и цифры`;
              case 'password':
                return `Пароль должен содержать не менее 8 символов и включать буквы и цифры.`;
              case 'name':
                return `Имя не должно быть пустым.`;
              default:
                return `Пожалуйста, проверьте поле ${detail.path}.`;
            }
          }).join('\n');
          Alert.alert('Ошибка валидации', `Пожалуйста, исправьте следующие ошибки:\n\n${errorMessages}`);
        } else if (data.errorType === 'USER_NICKNAME_OCCUPIED') {
          Alert.alert('Ошибка', 'Этот никнейм уже занят. Пожалуйста, выберите другой.');
        } else {
          Alert.alert(
              'Ошибка',
              data.message || 'Что-то пошло не так. Пожалуйста, попробуйте еще раз позже.'
          );
        }
      } else {
        Alert.alert(
            'Ошибка',
            'Не удалось зарегистрироваться. Пожалуйста, проверьте ваше интернет-соединение и попробуйте снова.'
        );
      }
    }
  };

  useEffect(() => {
    const keyboardDidShowListener = Keyboard.addListener(
        'keyboardDidShow',
        () => {
          setKeyboardVisible(true);
        }
    );
    const keyboardDidHideListener = Keyboard.addListener(
        'keyboardDidHide',
        () => {
          setKeyboardVisible(false);
        }
    );

    return () => {
      keyboardDidHideListener.remove();
      keyboardDidShowListener.remove();
    };
  }, []);

  return (
      <ImageBackground
          source={require('./assets/5418303167952711597.jpg')}
          style={styles.background}>
        <KeyboardAvoidingView
            style={styles.container}
            behavior={Platform.OS === 'ios' ? 'padding' : 'height'}
            keyboardVerticalOffset={keyboardVisible ? 100 : 0} // Увеличьте отступ, когда клавиатура видима
        >
          <View style={styles.formContainer}>
            <Text style={styles.title}>Скорее регистрируйся!</Text>
            <TextInput
                style={styles.input}
                placeholder="Как тебя зовут?"
                value={username}
                onChangeText={setUsername}
                placeholderTextColor="#CE9FDD"
            />
            <TextInput
                style={styles.input}
                placeholder="Придумай никнейм"
                value={nickname}
                onChangeText={setNickname}
                placeholderTextColor="#CE9FDD"
            />
            <TextInput
                style={styles.input}
                placeholder="Придумай пароль"
                value={password}
                onChangeText={setPassword}
                secureTextEntry
                placeholderTextColor="#CE9FDD"
            />
            <TouchableOpacity style={styles.button} onPress={handleSubmit}>
              <Text style={styles.buttonText}>Зарегистрироваться</Text>
            </TouchableOpacity>
            {/* Ссылка под кнопкой */}
            <View style={styles.linkContainer}>
              <Text style={styles.linkText}>Уже есть аккаунт? </Text>
              <TouchableOpacity onPress={() => navigation.navigate('Login')}>
                <Text style={styles.link}>Скорее заходи!</Text>
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
    justifyContent: 'flex-start', // Изменено на flex-start, чтобы элементы были вверху
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
    paddingHorizontal:20 ,
    alignItems:'center' ,
  },
  buttonText:{
    color:'#645BAA' ,
    fontSize :25 ,
  },
  linkContainer:{
    flexDirection:'row' ,
    justifyContent:'center' ,
    marginTop :15 , // Отступ сверху для ссылки
  },
  linkText:{
    color:'#937EC3', // Цвет текста
    fontSize :16 ,
  },
  link:{
    color:'#4D3FB7', // Цвет ссылки
    fontSize :16 ,
    textDecorationLine:'underline' , // Подчеркивание ссылки
  },
});

export default RegistrationScreen;