import React from 'react';
import { View, Text, Image, TouchableOpacity, StyleSheet } from 'react-native';
import { DrawerContentScrollView, DrawerItemList } from '@react-navigation/drawer';
import Icon from 'react-native-vector-icons/Ionicons'; 

const CustomDrawerContent = (props) => {
  return (
    <DrawerContentScrollView {...props} contentContainerStyle={styles.container}>
      <View style={styles.userInfoContainer}>
        {/* Картинка пользователя */}
        <Image 
          source={require('./assets/cat.jpg')} 
          style={styles.avatar}
        />
        {/* Имя пользователя */}
        <View style={styles.userInfo}>
          <Text style={styles.userName}>Имя пользователя</Text>
          <Text style={styles.userNickname}>@Никнейм</Text>
        </View>
      </View>

      {/* Кнопки навигации */}
      <View style={styles.navigationContainer}>
        <TouchableOpacity 
          onPress={() => props.navigation.navigate('SearchFriends')}
          style={styles.button}
        >
          <Icon name="search" size={20} color="#000" style={styles.icon} />
          <Text style={styles.buttonText}>Поиск друзей</Text>
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