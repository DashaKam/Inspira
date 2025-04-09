import React from 'react';
import { Text, ImageBackground, View, StyleSheet } from 'react-native';

const HomeScreen = ({ navigation }) => {
  return (
    <ImageBackground
      source={require('./assets/fon.jpg')} 
      style={styles.background}
    >
      <View style={styles.container}>
        <Text style={styles.text}>
          Если не хочешь остаться ребенком навеки, нельзя ждать подсказок от других. Ты должен найти решение в себе самом.
        </Text>
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