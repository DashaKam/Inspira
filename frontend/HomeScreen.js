import React, { useEffect, useState } from 'react';
import { View, Text, ImageBackground, StyleSheet } from 'react-native';
import axios from 'axios';
import AsyncStorage from '@react-native-async-storage/async-storage';
const HomeScreen = ({ navigation }) => {
  const [message, setMessage] = useState(null);
  const [error, setError] = useState(null);

  // Функция для получения сообщения с сервера
  const fetchMessage = async () => {
    try {
      const token = await AsyncStorage.getItem('userToken'); // Получаем токен из памяти

      if (!token) {
        throw new Error('Токен не найден');
      }
      console.log('Используемый токен:', token);
      const response = await axios.get(
          'http://185.157.214.169:8080/api/messages/message',
          {
            headers: {
              Authorization: `Bearer ${token}`, // Отправляем токен в заголовке
            },
          }
      );

      setMessage(response.data);
      setError(null);
    } catch (err) {
      console.error('Ошибка при получении сообщения:', err);
      setError('Не удалось загрузить сообщение.');
      setMessage(null);
    }
  };

  // Выполняется при монтировании компонента
  useEffect(() => {
    fetchMessage(); // Получаем сообщение сразу при входе

    // Запускаем таймер на каждые 3 минуты
    const interval = setInterval(fetchMessage, 1 * 60 * 1000); // 3 минуты в миллисекундах

    // Очистка интервала при размонтировании
    return () => clearInterval(interval);
  }, []);

  return (
      <ImageBackground
          source={require('./assets/fon.jpg')}
          style={styles.background}
      >
        <View style={styles.container}>
          {/* Текст изначального приветствия */}
          <Text style={styles.text}>
            Если не хочешь остаться ребенком навеки, нельзя ждать подсказок от других. Ты должен найти решение в себе самом.
          </Text>

          {/* Сообщение от сервера */}
          {message && (
              <View style={styles.messageContainer}>
                <Text style={styles.messageText}>{message.messageText}</Text>
                <Text style={styles.sender}>— {message.senderNickname}</Text>
                <Text style={styles.type}>Тип: {message.messageType}</Text>
              </View>
          )}

          {error && <Text style={styles.error}>{error}</Text>}
        </View>
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
    justifyContent: 'center',
    alignItems: 'center',
    paddingHorizontal: 20, 
    paddingVertical: 10, 
  },
  text: {
    textAlign: 'center', 
    color: '#000000', 
    fontSize: 16, 
  },
});

export default HomeScreen;