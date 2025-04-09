import React from 'react';
import { View, Text, Button } from 'react-native';

const SuccessScreen = ({ navigation }) => {
  return (
    <View style={{ flex: 1, justifyContent: 'center', alignItems: 'center' }}>
      <Text>Успешный вход!</Text>
      <Button title="Перейти на главную" onPress={() => navigation.navigate('HomeDrawer')} />
    </View>
  );
};

export default SuccessScreen;