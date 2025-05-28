import React, { useEffect, useState } from 'react';
import { View, Text, Image, TouchableOpacity, StyleSheet } from 'react-native';
import AsyncStorage from '@react-native-async-storage/async-storage';
import axios from 'axios';
import { DrawerContentScrollView } from '@react-navigation/drawer';
import Icon from 'react-native-vector-icons/Ionicons';

const CustomDrawerContent = (props) => {
  const [user, setUser] = useState(null);
  const [loading, setLoading] = useState(true);

  const fetchUserData = async () => {
    try {
      const token = await AsyncStorage.getItem('userToken');
      if (!token) throw new Error('Токен не найден');

      const response = await axios.get(
          'http://185.157.214.169:8080/api/users/me',
          {
            headers: {
              Authorization: `Bearer ${token}`,
            },
          }
      );

      setUser(response.data);
    } catch (err) {
      console.error('Ошибка загрузки данных пользователя:', err);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchUserData();
  }, []);

  return (
      <DrawerContentScrollView {...props} contentContainerStyle={styles.container}>
        {!loading ? (
            <View style={styles.userInfoContainer}>
              {/* Картинка пользователя */}
              <Image
                  source={require('./assets/cat.jpg')}
                  style={styles.avatar}
              />
              {/* Имя пользователя */}
              <View style={styles.userInfo}>
                <Text style={styles.userName}>{user?.name || 'Пользователь'}</Text>
                <Text style={styles.userNickname}>@{user?.nickname || 'никнейм'}</Text>
              </View>
            </View>
        ) : (
            <Text style={styles.loadingText}>Загрузка...</Text>
        )}

        {/* Кнопки навигации */}
        <View style={styles.navigationContainer}>
          <TouchableOpacity
              onPress={() => props.navigation.navigate('Friends')}
              style={styles.button}
          >
            <Icon name="search" size={20} color="#000" style={styles.icon} />
            <Text style={styles.buttonText}>Друзья</Text>
          </TouchableOpacity>

          <TouchableOpacity
              onPress={() => props.navigation.navigate('Settings')}
              style={styles.button}
          >
            <Icon name="settings-outline" size={20} color="#000" style={styles.icon} />
            <Text style={styles.buttonText}>Настройки</Text>
          </TouchableOpacity>
        </View>

        {/* Кнопка выхода */}
        <TouchableOpacity
            onPress={() => {
              AsyncStorage.removeItem('userToken');
              props.navigation.navigate('Login');
            }}
            style={styles.button}
        >
          <Icon name="log-out-outline" size={20} color="#000" style={styles.icon} />
          <Text style={styles.buttonText}>Выйти из аккаунта</Text>
        </TouchableOpacity>
      </DrawerContentScrollView>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: 'rgba(255, 0, 255, 0.1)',
   
  },
  userInfoContainer: {
    flexDirection: 'row',
    alignItems: 'center',
    marginBottom: 20,
  },
  avatar: {
    width: 80,
    height: 80,
    borderRadius: 10, 
  },
  userInfo: {
    marginLeft: 15,
  },
  userName: {
    fontSize: 18,
    fontWeight: 'bold',
  },
  userNickname: {
    color: '#888',
  },
  navigationContainer: {
    marginTop: 20,
    flexGrow: 1,
    justifyContent: 'flex-start', // Выравнивание кнопок по верхней части
     
  },
  button: {
    paddingVertical: 15,
    borderRadius: 5,
    alignItems: 'center',
    flexDirection: 'row', 
    marginBottom: 10, 
    backgroundColor: 'rgba(255,255,255,0.1)', // Полупрозрачный фон для кнопок
    paddingHorizontal: 10,
  },
  buttonText: {
    color: '#000',
    fontWeight: 'bold',
    marginLeft: 10, // Отступ между иконкой и текстом
  },
  
});

export default CustomDrawerContent;