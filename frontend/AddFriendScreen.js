import React from 'react';
import { SafeAreaView, View, Text, TextInput, TouchableOpacity, StyleSheet } from 'react-native';

const AddFriendScreen = ({ navigation }) => {
    const handleSendRequest = () => {
        // Логика отправки заявки или сообщение
        alert('Заявка отправлена!');
        // Возврат назад после отправки заявки
        navigation.goBack();
    };

    return (
        <SafeAreaView style={styles.container}>
            {/* Заголовок */}
            <Text style={styles.title}>Добавить в друзья</Text>

            {/* Поле ввода имени или никнейма */}
            <TextInput
                placeholder="Введите имя или никнейм"
                style={styles.input}
            />

            {/* Кнопка отправки заявки */}
            <TouchableOpacity style={styles.button} onPress={handleSendRequest}>
                <Text style={styles.buttonText}>Отправить заявку</Text>
            </TouchableOpacity>
        </SafeAreaView>
    );
};

const styles = StyleSheet.create({
    container: {
        flex: 1,
        justifyContent: 'center',
        alignItems: 'center',
        paddingHorizontal: '10%',
        backgroundColor: '#fff', // Можно добавить фон по желанию
    },
    title: {
        fontSize: 24,
        marginBottom: 20,
        fontWeight: 'bold',
    },
    input: {
        width: '100%',
        borderWidth: 1,
        borderColor: '#ccc',
        borderRadius: 4,
        paddingHorizontal: 10,
        paddingVertical: 8,
        marginBottom: 20,
    },
    button: {
        backgroundColor: '#007AFF',
        paddingVertical: 12,
        paddingHorizontal: 25,
        borderRadius: 4,
    },
    buttonText: {
        color: '#fff',
        fontSize: 16,
    },
});

export default AddFriendScreen;